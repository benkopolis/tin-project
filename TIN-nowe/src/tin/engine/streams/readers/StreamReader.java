/**
 * 
 */
package tin.engine.streams.readers;

import java.io.IOException;
import java.io.InputStream;
import java.util.Vector;

import tin.engine.data.Data;
import tin.engine.exceptions.TINException;
import tin.engine.interactive.Interactive;

/**
 * Klasa abstrakcyjna s�u��ca jako baza dla klas odczytuj�cych 
 * dane ze strumienia i przekazuj�cych te dane w paczkach dalej.
 */
public abstract class StreamReader {
	
	/**
	 * Wektor element�w klasy Interactive, do kt�rych b�d� przesy�ane 
	 * dane odczytane ze strumienia.
	 */
	private Vector<Interactive> targets = new Vector<Interactive>(5);
	
	/**
	 * Referencja do strumienia wej�ciowego, z kt�rego b�dzie 
	 * nast�powa�o czytanie danych.
	 */
	protected InputStream inputStream;
	
	/**
	 * Referencja na obiekt w�tku odczytuj�cego dane ze strumienia.
	 */
	protected StreamReaderThread streamReaderThread = new StreamReaderThread();
	
	/**
	 * Konstruktor.
	 * @param InputStream inputStream - strumie�, z kt�rego maj� by� czytane dane
	 */
	public StreamReader(InputStream inputStream) {
		this.inputStream = inputStream;
	}
	
	/**
	 * Metoda uruchamiaj�ca w�tek odczytu ze strumienia. Powinna by� wywo�ana 
	 * dopiero po dodaniu wszystkich target�w. 
	 */
	public void startThread() {
		streamReaderThread.start();
		return;
	}
	
	/**
	 * Abstrakcyjna metoda odczytuj�ca dane ze strumienia i formatuj�ca je w odpowiednie
	 * pakiety a nast�pnie zwracaj�ca taki pakiet danych.
	 * Rzuca Exception gdy wyst�pi jaki� b��d (najprawdopodobniej odczytu danych - IOException).
	 * @return Data - pakiet danych
	 * @throws Exception 
	 */
	protected abstract Data readDataPacketFromStream() throws Exception;
		
	/**
	 * Metoda przesy�aj�ca otrzymane w parametrze dane do ka�dego 
	 * podczepionego targetu (z wektora targets).
	 * @param Data data - dane do przes�ania
	 * @throws TINException
	 */
	private void propagateData(Data data) throws TINException {
		//System.out.println("Reader: Propaguje dane...");
		if (targets.size() == 0) throw new TINException("No targets added.");
		for (Interactive i : targets) {
			i.addToBuffer(data);
		}
	}
	
	/**
	 * Dodaje do listy target�w podany w parametrze target. Aby element m�g� dzia�a�
	 * musi posiada� przynajmniej jeden target.
	 * @param Interactive target - referencja do elementu, kt�ry b�dzie targetem
	 * @throws TINException
	 */
	public void addTarget(Interactive target) throws TINException {
		if (target == null) throw new TINException("Target can not be null.");
		targets.add(target);
	}
	
	/* ============== W�TEK OBS�UGI WCZYTYWANIA ZE STRUMIENIA ============== */
	
	/**
	 * Klasa w�tku, kt�ry odpowiada za odczyt danych ze strumienia a nast�pnie
	 * przesy�anie odczytanych paczek danych do pod��czonych target�w.
	 * Aby uruchomi� ten w�tek nale�y wywo�a� metod� startThread(); 
	 */
	protected class StreamReaderThread extends Thread {
			
		/**
		 * Konstruktor w�tku. Ustawia flag� demona, ale <b>NIE</b> uruchamia w�tku.
		 * Do uruchomienia w�tku nale�y u�y� metody startThread();
		 */
		public StreamReaderThread() {
			super();
			this.setDaemon(true);
		}
		
		public boolean rawRuns = false;
		
		/**
		 * Metoda wywo�ywana podczas uruchamiania w�tku.
		 */
		public void run() {
			Data d;
			while ( ! this.isInterrupted() ) {
				try {
					if (rawRuns) continue;
					//System.out.println("Reader: Probuje pobrac dane ze strumienia...");
					d = readDataPacketFromStream();
					//System.out.println("Reader: Dane pobrane.");
					propagateData(d);
				} catch (TINException e) {
					//System.err.println("No targets added to StreamReader !");
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
	
}
