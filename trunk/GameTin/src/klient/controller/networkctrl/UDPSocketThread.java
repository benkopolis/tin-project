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
					String msg = "Hello";
					DatagramPacket hi = new DatagramPacket(msg.getBytes(), msg.length(),
                            group, 6789);
					socket.send(hi);
					byte[] buf = new byte[1000];
					String str = "Ala";
					byte[] tab = str.getBytes();
					DatagramPacket recv = new DatagramPacket(buf, buf.length);
					socket.receive(recv);

					/* tab - tablica przeslanych pakietow */
					//String[] tab = InputReader.getStringsFromPacket();
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
