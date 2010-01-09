package klient.controller.networkctrl;

import java.io.IOException;
import java.net.Socket;

import klient.model.IllegalOperation;
import klient.model.Model;



public class TCPSocketThread extends Thread {
	
	
	private Socket socket;
	private Model model;

	public TCPSocketThread(Socket socket, Model m) {
		this.socket = socket;
		this.model = m;
	}

	public void run() {
		while(!isInterrupted()) {
			try {
				/* tab - tablica przeslanych pakietow */
				String[] tab = InputReader.getStringsFromPacket(socket.getInputStream());
				/* t - linia stringa, ktora jest pakietem */
				for(String t:tab) {
					/* tokens - tablica stringow rozdzielonych dwukropkiem */
					String[] tokens = t.split(":");
					// klient zaakceptowany
					if(tokens[0].equals("allow")) {
						int ID = Integer.parseInt(tokens[1]);
						model.setPlayerID(ID);
					}
					else if (tokens[0].equals("deny")) {
						model.connectionDenied(tokens[1]);
					}
					else if (tokens[0].equals("players")) {
						for(int i=1; i<tokens.length; i++) {
							String nick = tokens[i].split(",")[0];
							int id = Integer.parseInt(tokens[i].split(",")[1]);
							model.addPlayer(nick, id);
						}
					}
					else if (tokens[0].equals("board")) {
						model.getLm().loadFromMessage(tokens[1]);
						for(int i=2; i<tokens.length; i++) {
							int id = Integer.parseInt(tokens[i].split(",")[0]);
							int x = Integer.parseInt(tokens[i].split(",")[1]);
							int y = Integer.parseInt(tokens[i].split(",")[2]);
							model.setActualPlayersPosition(id, x, y);
						}
					}
					else if (tokens[0].equals("countingdown")) {
						while (Integer.parseInt(tokens[1]) != 1) {
							;
						}
						
					}
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalOperation e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		}
	}
}