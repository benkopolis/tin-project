package tin.project.net;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.concurrent.LinkedBlockingQueue;

import tin.engine.exceptions.TINException;
import tin.project.common.IDCreator;

/**
 * Klasa posiadajaca zbior socketow serwerowych, oraz pozwalaj�ca na zarz�dzanie 
 * zbiorem tych socket�w serwerowych (dodawnie, usuwanie, stopowanie, startowanie).
 * We w�asnym w�tku wy�uskuje sockety z syn�w i wydobywa z nich strumienie, a nast�pnie
 * przekazuje dalej.
 */
public class ServerSocketsSetThread extends Thread
{
	/**
	 * kolekjca wątków socketow serwerowych
	 */
	private LinkedBlockingQueue<ServerSocketThread> servers; 
	
	/**
	 * kolejka socketów zbindowanych, uzyskanych przez accepty, przez wszystkie wątki serwerowe
	 */
	private LinkedBlockingQueue<Socket> sockets;
	
	/**
	 * kolejka input streamow wyluskanych z socketow
	 */
	private LinkedBlockingQueue<InputStream> ins;
	
	/**
	 * kolejka output streamow wyluskanych z socketow 
	 */
	private LinkedBlockingQueue<OutputStream> out;
	
	/**
	 * id creator - do przydzielania id
	 */
	private IDCreator ids;
	
	/**
	 * Konstruktor domy�lny-bezparametrowy klasy.
	 */
	public ServerSocketsSetThread()
	{
		servers = new LinkedBlockingQueue<ServerSocketThread>();
		sockets = new LinkedBlockingQueue<Socket>();
		ids = IDCreator.getInstance();
	}
	
	/**
	 * Dodaje do kolejki socket�w serwerowych socketa o zadanym numerze portu.
	 * UWAGA nie uruchamia go!
	 * @param sockNR
	 */
	public synchronized void addServer(int sockNR)
	{
		ServerSocketThread sst = new ServerSocketThread(sockNR);
		try
		{
			servers.put(sst);
		} catch (InterruptedException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Dodaje do kolejki socket�w serwerowych socketa o zadanym numerze portu.
	 * Uruchamia go, je�eli parametr start jest true, je�eli false to nie jest 
	 * uruchamiany.
	 * @param sockNR
	 * @param start
	 */
	public synchronized void addServer(int sockNR, boolean start)
	{
		ServerSocketThread sst = new ServerSocketThread(sockNR);
		try
		{
			servers.put(sst);
			if(start == true)
				sst.startServerSocketThread();
		} catch (InterruptedException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Uruchamia w�tek socketa.
	 * @param portNumber
	 * @throws TINException
	 */
	public synchronized void startServerThread(int portNumber) throws TINException
	{
		ServerSocketThread sst = findServerSocket(portNumber);
		if(sst == null)
			throw new TINException("Start: No such socket:" + portNumber + "in set");
		sst.startServerSocketThread();
	}
	
	/**
	 * W zbiorze ServerSockeetThreadow jest wyszukiwany socket o zadanym nuemrze portu,
	 * gdy zostanie znaleziony zostaje usuni�ty i jest zwracany, gdy nie b�dzie 
	 * znaleziony lub nie uda si� go usun��, to rzucany jest wyj�tek.
	 * 
	 * UWAGA nie stopuje w�tku serwera.
	 * @param portNumber
	 * @return
	 * @throws TINException
	 */
	public synchronized ServerSocketThread remove(int portNumber) throws TINException
	{
		ServerSocketThread ret = null;
		ret = findServerSocket(portNumber);
		if(ret == null)
			throw new TINException("Removing ServerSocket: " + portNumber 
					+	"didn't succeed - no such socket in set");
		return ret;
	}

	/**
	 * @param portNumber
	 * @param ret
	 * @return
	 * @throws TINException
	 */
	private ServerSocketThread findServerSocket(int portNumber)
	{
		ServerSocketThread ret = null;
		for(ServerSocketThread sst: servers)
		{
			if(sst.getPortNumber() == portNumber)
				ret = sst;
		}
		return ret;
	}
	
	public synchronized void run()
	{
		Socket s;
		while(true)
		{
			for(ServerSocketThread sst: servers)
			{
				if(sst.noSocketsInQeue() == false)
				{
					s = sst.getBindedSocket();
					try
					{
						sockets.put(s);
						ins.put(s.getInputStream());
						out.put(s.getOutputStream());
						//TODO 
					} catch (InterruptedException e)
					{
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e)
					{
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
	}
	
	
}
