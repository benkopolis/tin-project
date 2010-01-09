package klient.controller.networkctrl;

import java.io.IOException;
import java.net.MulticastSocket;
import java.net.SocketAddress;

import klient.model.Model;

public class UDPSocketThread extends Thread{

	private MulticastSocket socket;
	private Model model;
	
	public UDPSocketThread(MulticastSocket socket, Model m) {
		this.socket = socket;
		this.model = m;
	}
	
	public void run() {
		
	}

}
