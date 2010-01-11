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
	//private MulticastSocket udpMsocket;
	private DatagramSocket udpsocket;
	private Model model;
	private ViewsController viewsctrl;
	private TCPSocketThread tcpThread;
	private UDPSocketSendThread udpSendThread;
	//private InetAddress group;
	private UDPSocketReceiveThread udpReceiveThread;
	
	public NetworkController(String adres, int port, Model m, ViewsController v) throws UnknownHostException, IOException {
		this.model = m;
		this.viewsctrl = v;
		
		tcpsocket = new Socket(adres, port);
		udpsocket = new DatagramSocket();
		
		///////////////// TCP ////////////////////////
		tcpThread = new TCPSocketThread(tcpsocket, m, v, udpsocket);
		tcpThread.setDaemon(true);
		tcpThread.start();
		
		///////////////// UDP ////////////////////////
		///////////////// multicast //////////////////
//		udpMsocket = new MulticastSocket(555);
//		group = InetAddress.getByName("225.225.225.225");
//		udpMsocket.joinGroup(group);
		///////////////// datagram send ///////////////
		udpSendThread = new UDPSocketSendThread(udpsocket, adres, m);
		udpSendThread.setDaemon(true);
		udpSendThread.start();
		///////////////// datagram receive ////////////
		udpReceiveThread = new UDPSocketReceiveThread(udpsocket, adres, m, v);
		udpReceiveThread.setDaemon(true);
		udpReceiveThread.start();
	}
}
