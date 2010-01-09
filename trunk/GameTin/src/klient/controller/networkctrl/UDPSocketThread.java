package klient.controller.networkctrl;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.SocketAddress;

import klient.model.Model;


public class UDPSocketThread extends Thread{

	private MulticastSocket socket;
	private InetAddress group;
	private Model model;
	
	public UDPSocketThread(MulticastSocket socket, Model m) {
		this.socket = socket;
		this.model = m;
	}
	
	public void run() {
		while(!isInterrupted()) {
			try {
				if (model.isGameOn() && (model.isGameOff() == false)) {
					String msg = String.valueOf(model.getLocalPlayerId());
					msg.concat(":" + String.valueOf(i));
					DatagramPacket hi = new DatagramPacket(msg.getBytes(), msg.length(),
                            group, 6789);
					socket.send(hi);
					
					byte[] buf = new byte[1000];
					DatagramPacket recv = new DatagramPacket(buf, buf.length);
					socket.receive(recv);
					String str = new String(buf);
					/* tokens - tablica stringow rozdzielonych dwukropkiem */
					String[] tokens = str.split(":");
					if (tokens[0].equals("coins")) {
						int coins = Integer.getInteger(tokens[1]);
						for (int i=2; i<coins+2; i++) {
							int x = Integer.parseInt(tokens[i].split(",")[0]);
							int y = Integer.parseInt(tokens[i].split(",")[1]);
							model.setActualCoinPosition(x,y);
						}
						for (int i=coins+4; i<tokens.length; i++) {
							int id = Integer.parseInt(tokens[i].split(",")[0]);
							int newX = Integer.parseInt(tokens[i].split(",")[1]);
							int newY = Integer.parseInt(tokens[i].split(",")[2]);
							int points = Integer.parseInt(tokens[i].split(",")[3]);
							model.setActualPlayerPosition(id, newX, newY);
							model.setPoints(id, points);
						}
					}
				}
				else if ((model.isGameOn() == false) && model.isGameOff()) {
					socket.leaveGroup(group);
				}
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
	}

}
