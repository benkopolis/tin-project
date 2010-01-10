package klient.controller.networkctrl;

import java.io.IOException;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.Socket;
import java.net.UnknownHostException;

import klient.controller.ViewsController;
import klient.model.Model;


public class NetworkController {
	private Socket tcpsocket;
	private MulticastSocket udpsocket;
	private Model model;
	private ViewsController viewsctrl;
	private TCPSocketThread tcpThread;
	private UDPSocketThread udpThread;
	private InetAddress group;
	
	public NetworkController(String adres, int port, Model m, ViewsController v) throws UnknownHostException, IOException {
		this.model = m;
		this.viewsctrl = v;
		
		tcpsocket = new Socket(adres, port);
		tcpThread = new TCPSocketThread(tcpsocket, m, v);
		tcpThread.start();
		
		udpsocket = new MulticastSocket(555);
		group = InetAddress.getByName("225.225.225.225");
		udpsocket.joinGroup(group);
		udpThread = new UDPSocketThread(udpsocket, m, v);
		udpThread.start();
	}
}
