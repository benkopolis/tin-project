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
 * Abstrakcyjna klasa bazowa dla wszelkich elementów 
 * w sieci przep³ywowej (poza elementem StreamReader).
 */
public abstract class Interactive {
	
	/**
	 * Jest to tablica przechowuj¹ca referencje na obiekty, do których
	 * nale¿y przekazaæ dane po ich ewentualnym przetworzeniu
	 * (to, czy przetwrzenie bêdzie mia³o miejsce zale¿y od rodzaju elementu).
	 */
	protected Vector<Interactive> targets = new 	Vector<Interactive>(5);
	
	/**
	 * Synchronizowana kolejka FIFO u¿ywana jako bufor.
	 * W celu dodania elementu do bufora wykorzystuje siê metodê addToBuffer(). 
	 */
	private LinkedBlockingQueue<Data> buffer = new LinkedBlockingQueue<Data>();	
	
	/**
	 * Referencja do obiektu reprezentuj¹cego w¹tek obs³ugi bufora.
	 */
	protected BufferThread bufferThread = new BufferThread();
	
	/**
	 * Abstrakcyjna metoda, ktora ma za zadanie obrobiæ dane przekazane jej w parametrze.
	 * @param Data data - dane do przetworzenia
	 */
	protected abstract Data processData(Data data);
	
	/**
	 * Dodaje do listy targetów podany w parametrze target. Aby element móg³ dzia³aæ
	 * musi posiadaæ przynajmniej jeden target.
	 * @param Interactive target - referencja do elementu, który bêdzie targetem
	 * @throws TINException
	 */
	public void addTarget(Interactive target) throws TINException {
		if (target == null) throw new TINException("Target can not be null.");
		targets.add(target);
	}
	
	/**
	 * Metoda, ktora dane otrzymane w parametrze wysy³a do ka¿dego z "targetów" lub do strumienia wyjœciowego.
	 * @param Data data - dane do przes³ania dalej
	 * @throws Exception
	 */
	protected abstract void propagateData(Data data) throws Exception;
	
	/**
	 * Metoda, która próbuje pobraæ dane z bufora. Jeœli bufor jest pusty, to zawiesza w¹tek.
	 * @return Data - pobrane z bufora dane
	 * @throws InterruptedException
	 */
	protected synchronized Data getElementFromBuffer() throws InterruptedException {
		return buffer.take();
	}	
	
	/**
	 * Metoda s³u¿¹ca do wstawiania na koniec kolejki FIFO elementu
	 * do obrobienia. U¿ywana przez elementy bêd¹ce bezpoœrednimi poprzednikami 
	 * danego elementu.
	 * @param Data data - dane do wstawienia
	 */
	public void addToBuffer(Data data) {
		try {
			buffer.put(data);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		//System.out.println("Interactive: Dosta³em dane.");
		return;
	}

	/**
	 * Metoda uruchamiaj¹ca w¹tek obs³ugi bufora. Powinna byæ wywo³ana 
	 * dopiero po dodaniu wszystkich targetów (chyba ¿e to jest StreamWriter). 
	 */
	public void startThread() {
		this.bufferThread.start();
		return;
	}
	
	/* ========= WATEK OBSLUGI BUFORA ======== */
	
	/**
	 * Watek s³u¿¹cy do pobierania nowo wstawionych wartoœci do bufora. 
	 */
	private class BufferThread extends Thread {
		
		/**
		 * Tworzy w¹tek, ustawia flagê demona i uruchamia go.
		 */
		public BufferThread() {
			super();
			this.setDaemon(true); /* ustawienie, ¿e w¹tek jest demonem */
		}		
		
		/**
		 * Metoda, która jest wywo³ywana podczas dzia³ania w¹tku.
		 */
		public void run() {
			Data d; 
			while ( ! this.isInterrupted() ) {
				try {
						/* 1. pobranie z bufora */
						//System.out.println("Interactive: Probuje pobrac dane z bufora... "); // TODO: usunac to
						d = getElementFromBuffer(); /* ta metoda mo¿e uœpiæ w¹tek gdy bufor jest pusty */
						//System.out.println("Interactive: pobieram dane.");
						/* 2. przetworzenie danych */
						d = processData(d);
						/* wys³anie dalej wyniku */
						propagateData(d);
						//System.out.println("Interactive: rozpropagowuje dane...");
				} catch (InterruptedException e) {
					//
				} catch (TINException e) {
					//System.err.println("Nie ma gdzie propagowaæ danych !");
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				} 
				
			}
		}

	}
}
