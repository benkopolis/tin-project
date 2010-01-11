package klient.controller.networkctrl;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.UnknownHostException;

import klient.controller.ViewsController;
import klient.model.Model;

public class UDPSocketReceiveThread extends Thread {

	private DatagramSocket dSocket;
	private InetAddress adres;
	private Model model;
	private ViewsController viewsctrl;
	
	public UDPSocketReceiveThread(DatagramSocket d, String adres, Model m, ViewsController v) throws UnknownHostException  {
		this.model = m;
		this.viewsctrl = v;
		this.dSocket = d;
		this.adres = InetAddress.getByName(adres);
	}
	
	public void run() {
		int count=0;
		while(!isInterrupted()) {
			try {
				if (model.isGameOn() && (model.isGameOff() == false)) {
					byte[] buf = new byte[1000];
					DatagramPacket recv = new DatagramPacket(buf, buf.length);
					dSocket.receive(recv);
					String str = new String(buf);
					str = str.split("\n")[0];
				
					/* tokens - tablica stringow rozdzielonych dwukropkiem */
					String[] tokens = str.split(":");
//					if (tokens == null) continue;
//					int ix = 0;
//					for (String t : tokens) {
//						ix++;
//						System.out.println("" + ix + ": " + t);
//					}
					if (tokens[0].equals("coins")) {
						System.out.println(">>" + str + ", length = " + tokens.length);
						System.out.println("wtf " + tokens[1]);
						int coins = Integer.getInteger(tokens[1]);
						for (int i=2; i<coins+2; i++) {
							int x = Integer.parseInt(tokens[i].split(",")[0]);
							int y = Integer.parseInt(tokens[i].split(",")[1]);
							model.setActualCoinPosition(x,y);
						}
						viewsctrl.refreshBoardView();
						
						for (int i=coins+4; i<tokens.length; i++) {
							int id = Integer.parseInt(tokens[i].split(",")[0]);
							int newX = Integer.parseInt(tokens[i].split(",")[1]);
							int newY = Integer.parseInt(tokens[i].split(",")[2]);
							int points = Integer.parseInt(tokens[i].split(",")[3]);
							model.setActualPlayerPosition(id, newX, newY, false);
							model.setPoints(id, points);
						}
						viewsctrl.refreshInfoView();
						
					}
				}
				else if ((model.isGameOn() == false) && model.isGameOff()) {
					//TODO: sokety
					System.out.println("Watek UDP zakonczony (interrupt)");
					//mSocket.leaveGroup(group);
					this.interrupt();
				}
			} catch (NullPointerException e) {
					System.out.println("czemu jest null point exc? :(");
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
		System.out.println("Watek UDP zakonczony");
	}
}
