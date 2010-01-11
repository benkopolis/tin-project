package klient.controller.networkctrl;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.Socket;
import java.net.UnknownHostException;

import klient.controller.ViewsController;
import klient.model.Model;


public class NetworkController {
	private Socket tcpsocket;
	private MulticastSocket udpMsocket;
	private DatagramSocket udpDsocket;
	private Model model;
	private ViewsController viewsctrl;
	private TCPSocketThread tcpThread;
	private UDPSocketSendThread udpSendThread;
	private InetAddress group;
	private UDPSocketReceiveThread udpReceiveThread;
	
	public NetworkController(String adres, int port, Model m, ViewsController v) throws UnknownHostException, IOException {
		this.model = m;
		this.viewsctrl = v;
		
		///////////////// TCP ////////////////////////
		tcpsocket = new Socket(adres, port);
		tcpThread = new TCPSocketThread(tcpsocket, m, v);
		tcpThread.setDaemon(true);
		tcpThread.start();
		
		///////////////// UDP ////////////////////////
		///////////////// multicast //////////////////
//		udpMsocket = new MulticastSocket(555);
//		group = InetAddress.getByName("225.225.225.225");
//		udpMsocket.joinGroup(group);
		///////////////// datagram send ///////////////
		udpDsocket = new DatagramSocket(556);
		udpSendThread = new UDPSocketSendThread(udpDsocket, adres, m);
		udpSendThread.setDaemon(true);
		udpSendThread.start();
		///////////////// datagram receive ////////////
		//udpDsocket = new DatagramSocket(556);
		udpReceiveThread = new UDPSocketReceiveThread(udpDsocket, adres, m, v);
		udpReceiveThread.setDaemon(true);
		udpReceiveThread.start();
	}
}
