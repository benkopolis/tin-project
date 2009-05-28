/**
 * 
 */
package tin.engine.interactive;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import tin.engine.data.Data;
import tin.engine.exceptions.TINException;

/**
 * Abstrakcyjna klasa bazowa dla wszelkich element�w 
 * w sieci przep�ywowej (poza elementem StreamReader).
 */
public abstract class Interactive {
	
	/**
	 * Jest to tablica przechowuj�ca referencje na obiekty, do kt�rych
	 * nale�y przekaza� dane po ich ewentualnym przetworzeniu
	 * (to, czy przetwrzenie b�dzie mia�o miejsce zale�y od rodzaju elementu).
	 */
	protected Vector<Interactive> targets = new 	Vector<Interactive>(5);
	
	/**
	 * Synchronizowana kolejka FIFO u�ywana jako bufor.
	 * W celu dodania elementu do bufora wykorzystuje si� metod� addToBuffer(). 
	 */
	private LinkedBlockingQueue<Data> buffer = new LinkedBlockingQueue<Data>();	
	
	/**
	 * Referencja do obiektu reprezentuj�cego w�tek obs�ugi bufora.
	 */
	protected BufferThread bufferThread = new BufferThread();
	
	/**
	 * Abstrakcyjna metoda, ktora ma za zadanie obrobi� dane przekazane jej w parametrze.
	 * @param Data data - dane do przetworzenia
	 */
	protected abstract Data processData(Data data);
	
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
	
	/**
	 * Metoda, ktora dane otrzymane w parametrze wysy�a do ka�dego z "target�w" lub do strumienia wyj�ciowego.
	 * @param Data data - dane do przes�ania dalej
	 * @throws Exception
	 */
	protected abstract void propagateData(Data data) throws Exception;
	
	/**
	 * Metoda, kt�ra pr�buje pobra� dane z bufora. Je�li bufor jest pusty, to zawiesza w�tek.
	 * @return Data - pobrane z bufora dane
	 * @throws InterruptedException
	 */
	protected synchronized Data getElementFromBuffer() throws InterruptedException {
		return buffer.take();
	}	
	
	/**
	 * Metoda s�u��ca do wstawiania na koniec kolejki FIFO elementu
	 * do obrobienia. U�ywana przez elementy b�d�ce bezpo�rednimi poprzednikami 
	 * danego elementu.
	 * @param Data data - dane do wstawienia
	 */
	public void addToBuffer(Data data) {
		try {
			buffer.put(data);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		//System.out.println("Interactive: Dosta�em dane.");
		return;
	}

	/**
	 * Metoda uruchamiaj�ca w�tek obs�ugi bufora. Powinna by� wywo�ana 
	 * dopiero po dodaniu wszystkich target�w (chyba �e to jest StreamWriter). 
	 */
	public void startThread() {
		this.bufferThread.start();
		return;
	}
	
	/* ========= WATEK OBSLUGI BUFORA ======== */
	
	/**
	 * Watek s�u��cy do pobierania nowo wstawionych warto�ci do bufora. 
	 */
	private class BufferThread extends Thread {
		
		/**
		 * Tworzy w�tek, ustawia flag� demona i uruchamia go.
		 */
		public BufferThread() {
			super();
			this.setDaemon(true); /* ustawienie, �e w�tek jest demonem */
		}		
		
		/**
		 * Metoda, kt�ra jest wywo�ywana podczas dzia�ania w�tku.
		 */
		public void run() {
			Data d; 
			while ( ! this.isInterrupted() ) {
				try {
						/* 1. pobranie z bufora */
						//System.out.println("Interactive: Probuje pobrac dane z bufora... "); // TODO: usunac to
						d = getElementFromBuffer(); /* ta metoda mo�e u�pi� w�tek gdy bufor jest pusty */
						//System.out.println("Interactive: pobieram dane.");
						/* 2. przetworzenie danych */
						d = processData(d);
						/* wys�anie dalej wyniku */
						propagateData(d);
						//System.out.println("Interactive: rozpropagowuje dane...");
				} catch (InterruptedException e) {
					//
				} catch (TINException e) {
					//System.err.println("Nie ma gdzie propagowa� danych !");
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				} 
				
			}
		}

	}
}
