package klient.controller.networkctrl;

import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;

import javax.swing.JOptionPane;

import klient.controller.ViewsController;
import klient.model.IllegalOperation;
import klient.model.Model;
import klient.model.fields.Player;



public class TCPSocketThread extends Thread {
	
	
	private Socket socket;
	private Model model;
	private ViewsController viewsctrl;

	public TCPSocketThread(Socket socket, Model m, ViewsController v) {
		this.socket = socket;
		this.model = m;
		this.viewsctrl = v;
	}

	public void run() {
		if ((model.isGameOn() == false) && (model.isGameOff() == false)) {
			String send = "hello:"+ model.getLocalPlayerNick() + "\n";
			try {
				OutputWriter.sendStringAsPacket(send, socket.getOutputStream());
				System.out.print("<<" + send);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		while(!isInterrupted()) {
			try {
				if ((model.isGameOn() == false) && (model.isGameOff() == false)){
					
					/* tab - tablica przeslanych pakietow */
					String[] tab = InputReader.getStringsFromPacket(socket.getInputStream());
					/* t - linia stringa, ktora jest pakietem */
					for(String t:tab) {
						/* tokens - tablica stringow rozdzielonych dwukropkiem */
						String[] tokens = t.split(":");
						// klient zaakceptowany
						if(tokens[0].equals("allow")) {
							int ID = Integer.parseInt(tokens[1]);
							model.setLocalPlayerId(ID);
							JOptionPane.showMessageDialog(null, "Gra rozpocznie sie, gdy pojawi sie okno z informacjami o innych graczach.\n Prosze czekac.\n");
						}
						else if (tokens[0].equals("deny")) {
							model.endGame();
							JOptionPane.showMessageDialog(null, tokens[1]);
							viewsctrl.closeApplication();
							this.interrupt();
						}
						else if (tokens[0].equals("players")) {
							System.out.println(">>" + t);
							for(int i=1; i<tokens.length; i++) {
								String nick = tokens[i].split(",")[0];
								int id = Integer.parseInt(tokens[i].split(",")[1]);
								Player p = model.addPlayer(nick, id);
								viewsctrl.addPlayerToInfoView(p);
							}
						}
						else if (tokens[0].equals("board")) {
							model.getLm().loadFromMessage(tokens[1]);
							for(int i=2; i<tokens.length; i++) {
								int id = Integer.parseInt(tokens[i].split(",")[0]);
								int x = Integer.parseInt(tokens[i].split(",")[1]);
								int y = Integer.parseInt(tokens[i].split(",")[2]);
								model.setActualPlayerPosition(id, x, y);
							}
							viewsctrl.refreshBoardView();
						}
						else if (tokens[0].equals("countingdown")) {
							int number = Integer.parseInt(tokens[1]);
							System.out.println(">>" + number);
							if (number == 1) {
								model.startGame();
								viewsctrl.refreshInfoView();
							}
						}
					}
				}
				else if (model.isGameOn() && (model.isGameOff() == false)) {
					/* tab - tablica przeslanych pakietow */
					String[] tab = InputReader.getStringsFromPacket(socket.getInputStream());
					for(String t:tab) {	
						/* tokens - tablica stringow rozdzielonych dwukropkiem */
						String[] tokens = t.split(":");
						if (tokens[0].equals("end")) {
							System.out.println(">>" + t);
							model.getLm().loadFromMessage(tokens[1]);
							System.out.println(tokens[1]);
							for(int i=2; i<tokens.length; i++) {
								int id = Integer.parseInt(tokens[i].split(",")[0]);
								int x = Integer.parseInt(tokens[i].split(",")[1]);
								int y = Integer.parseInt(tokens[i].split(",")[2]);
								int points = Integer.parseInt(tokens[i].split(",")[3]);
								model.setActualPlayerPosition(id, x, y);
								model.setPoints(id, points);
								model.endGame(); // ustawia zmienna gameOff na true
							}
							viewsctrl.refreshBoardView();
							//TODO refresh end game
						}
					}
				}
				else if ((model.isGameOn() == false) && model.isGameOff()) {
					String send = String.valueOf(model.getLocalPlayerId());
					send = send.concat(":quit\n");
					OutputWriter.sendStringAsPacket(send, socket.getOutputStream());
					System.out.print("<<" + send);
					viewsctrl.closeApplication();
					this.interrupt();
				}
			} catch (SocketException e) {
				viewsctrl.closeApplication();
				this.interrupt();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalOperation e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		System.out.println("Watek TCP zakonczony");
	}
}
