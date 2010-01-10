package klient.controller.networkctrl;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.SocketAddress;

import klient.controller.ViewsController;
import klient.model.Model;
import klient.model.Move;


public class UDPSocketThread extends Thread{

	private MulticastSocket mSocket;
	private DatagramSocket dSocket;
	private InetAddress group;
	private Model model;
	private ViewsController viewsctrl;
	
	public UDPSocketThread(DatagramSocket d, MulticastSocket socket, InetAddress g, Model m, ViewsController v) {
		this.mSocket = socket;
		this.group = g;
		this.model = m;
		this.viewsctrl = v;
		this.dSocket = d;
	}
	
	public void run() {
		int count=0;
		while(!isInterrupted()) {
			try {
				if (model.isGameOn() && (model.isGameOff() == false)) {
					Move move = model.getMoves().poll();
					if (move != null) {
						count++;
						String msg = String.valueOf(model.getLocalPlayerId())+ ":" + String.valueOf(count);
						msg = msg.concat(":" + move.getOldX() + "," + move.getNewY());
						msg = msg.concat(move.getNewX() + "," + move.getNewY() + "\n");
						
						DatagramPacket d = new DatagramPacket(msg.getBytes(), msg.length(),
			                            					group, 555);
						dSocket.send(d);
						System.out.println("<<" + msg);
					}
					
					byte[] buf = new byte[1000];
					DatagramPacket recv = new DatagramPacket(buf, buf.length);
					mSocket.receive(recv);
					String str = new String(buf);
					/* tokens - tablica stringow rozdzielonych dwukropkiem */
					String[] tokens = str.split(":");
					if (tokens[0].equals("coins")) {
						System.out.println(">>" + str);
						int coins = Integer.getInteger(tokens[1]);
						for (int i=2; i<coins+2; i++) {
							int x = Integer.parseInt(tokens[i].split(",")[0]);
							int y = Integer.parseInt(tokens[i].split(",")[1]);
							model.setActualCoinPosition(x,y);
							viewsctrl.refreshBoardView();
						}
						for (int i=coins+4; i<tokens.length; i++) {
							int id = Integer.parseInt(tokens[i].split(",")[0]);
							int newX = Integer.parseInt(tokens[i].split(",")[1]);
							int newY = Integer.parseInt(tokens[i].split(",")[2]);
							int points = Integer.parseInt(tokens[i].split(",")[3]);
							model.setActualPlayerPosition(id, newX, newY);
							model.setPoints(id, points);
							viewsctrl.refreshInfoView();
						}
					}
				}
				else if ((model.isGameOn() == false) && model.isGameOff()) {
					//TODO: sokety
					mSocket.leaveGroup(group);
					this.interrupt();
				}
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			
			System.out.println("Watek UDP zakonczony");
		}
	}

}
