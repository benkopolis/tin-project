package klient.controller.networkctrl;

import java.io.IOException;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.Socket;
import java.net.UnknownHostException;

import klient.model.Model;


public class NetworkController {
	private Socket tcpsocket;
	private MulticastSocket udpsocket;
	private Model model;
	private TCPSocketThread tcpThread;
	private UDPSocketThread udpThread;
	private InetAddress group;
	
	public NetworkController(String adres, int port, Model m) throws UnknownHostException, IOException {
		this.model = m;
		
		tcpsocket = new Socket(adres, port);
		tcpThread = new TCPSocketThread(tcpsocket, m);
		tcpThread.start();
		
		udpsocket = new MulticastSocket(555);
		group = InetAddress.getByName("225.225.225.225");
		udpsocket.joinGroup(group);
		udpThread = new UDPSocketThread(udpsocket, m);
		udpThread.start();
	}
}
