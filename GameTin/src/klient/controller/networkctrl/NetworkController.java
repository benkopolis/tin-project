package klient.controller.networkctrl;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import klient.model.Model;


public class NetworkController {
	private Socket socket;
	private TCPSocketThread tcpThread;
	private Model model;
	
	public NetworkController(String adres, int port, Model m) throws UnknownHostException, IOException {
		this.model = m;
		socket = new Socket(adres, port);
		tcpThread = new TCPSocketThread(socket, m);
		tcpThread.start();
		
	}
}
