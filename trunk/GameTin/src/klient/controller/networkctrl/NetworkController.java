package klient.controller.networkctrl;

import java.io.IOException;
import java.net.MulticastSocket;
import java.net.Socket;
import java.net.UnknownHostException;

import klient.model.Model;


public class NetworkController {
	private Socket tcpsocket;
	private MulticastSocket udpsocket;
	private TCPSocketThread tcpThread;
	private Model model;
	private UDPSocketThread udpThread;
	
	public NetworkController(String adres, int port, Model m) throws UnknownHostException, IOException {
		this.model = m;
		
		tcpsocket = new Socket(adres, port);
		tcpThread = new TCPSocketThread(tcpsocket, m);
		tcpThread.start();
		
		udpsocket = new MulticastSocket();
		udpThread = new UDPSocketThread(udpsocket, m);
		if (model.isGameOn() && (model.isGameOff() == false)) {
			udpThread.start();
		}
	}
}
