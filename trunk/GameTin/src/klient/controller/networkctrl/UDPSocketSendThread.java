package klient.controller.networkctrl;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
//import java.net.MulticastSocket;
//import java.net.SocketAddress;
import java.net.UnknownHostException;

import klient.controller.ViewsController;
import klient.model.Model;
import klient.model.Move;


public class UDPSocketSendThread extends Thread{

	//private MulticastSocket mSocket;
	private DatagramSocket dSocket;
	//private InetAddress group;
	private InetAddress adres;
	private Model model;
	//private ViewsController viewsctrl;
	
	public UDPSocketSendThread(DatagramSocket d, String adres, Model m) throws UnknownHostException  {
		this.model = m;
		this.dSocket = d;
		this.adres = InetAddress.getByName(adres);
	}
	
	public void run() {
		int count=0;
		while(!isInterrupted()) {
			try {
				if (model.isGameOn() && (model.isGameOff() == false)) {
					
					Move move = model.workOnMoves(false, null);
					if (move != null) {
						count++;
						String msg = String.valueOf(model.getLocalPlayerId())+ ":" + String.valueOf(count);
						msg = msg.concat(":" + move.getOldX() + "," + move.getOldY() + ":");
						msg = msg.concat(move.getNewX() + "," + move.getNewY() + "\n");
						
						DatagramPacket d = new DatagramPacket(msg.getBytes(), msg.length(),
			                            					adres, dSocket.getLocalPort());
						dSocket.send(d);
						System.out.print("<<" + msg);
					}
				}
				else if ((model.isGameOn() == false) && model.isGameOff()) {
					System.out.println("Watek UDP zakonczony (interrupt)");
					//mSocket.leaveGroup(group);
					this.interrupt();
				}
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
		System.out.println("Watek UDP zakonczony");
	}

}
