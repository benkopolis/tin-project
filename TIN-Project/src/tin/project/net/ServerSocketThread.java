/**
 * 
 */
package tin.project.net;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.LinkedBlockingQueue;

import tin.engine.interactive.collectors.HashMapCollector;
import tin.engine.interactive.collectors.SearchableMap;
import tin.project.common.IDCreator;

/**
 * Abstrakcyjna klasa SocketThread do obslugi we/wy socketa
 *
 */
public class ServerSocketThread extends Thread
{

	private int mainSocketNumber;
	
	private ServerSocket socket;
	
	private IDCreator creator;
	
	protected LinkedBlockingQueue<Socket> sockets;
	
	
	/**
	 * 
	 */
	public ServerSocketThread(int sockNR)
	{
		mainSocketNumber = sockNR;
		creator = IDCreator.getInstance();
		sockets = new LinkedBlockingQueue<Socket>();
		this.setDaemon(true);
		try
		{
			socket = new ServerSocket(mainSocketNumber);
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}


	/**
	 * @param name
	 */
	public ServerSocketThread(String name, int sockNR)
	{
		super(name);
		mainSocketNumber = sockNR;
		sockets = new LinkedBlockingQueue<Socket>();
		this.setDaemon(true);
		try
		{
			socket = new ServerSocket(mainSocketNumber);
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	public synchronized void startServerSocketThread()
	{
		this.start();
	}

	public synchronized void run()
	{
		Socket s;
		while(! (this.isInterrupted() ))
		{
			try
			{
				s = socket.accept();
				sockets.put(s);
			} catch (IOException e)
			{
				e.printStackTrace();
			} catch (InterruptedException e)
			{
				e.printStackTrace();
			}
			// TODO nowo utworzonego socketa metoda ta przekazuje do jakiegos 
			// TODO collectora. Pobiera rowniez jego strumienie In/Out,
			// TODO nadaje im jakies nowe ID zaczerpniete z klasy IDCreator
		}
	}

	public synchronized Socket getBindedSocket()
	{
		return sockets.poll();
	}
	
	public synchronized int getPortNumber()
	{
		return socket.getLocalPort();
	}
	
	public synchronized boolean noSocketsInQeue()
	{
		return sockets.isEmpty();
	}
}
