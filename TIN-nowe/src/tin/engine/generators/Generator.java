/**
 * 
 */
package tin.engine.generators;

import java.util.Vector;

import tin.engine.data.Data;
import tin.engine.exceptions.TINException;
import tin.engine.interactive.Interactive;

/**
 * Abstrakcyjna klasa bazowa dla generatorów danych.
 */
public abstract class Generator 
{
	/**
	 * Wektor celów, do których maj¹ byæ generowane dane losowe.
	 */
	protected Vector<Interactive> targets;
	
	/**
	 * Iloœæ danych losowych jaka ma byæ generowana w ci¹gu 1 sekundy.
	 */
	protected int dataPerSec;
	
	/**
	 * Zmienna w¹tku generuj¹cego dane i przesy³aj¹cego je.
	 */
	protected GeneratorThread genThread;
	
	/**
	 * Konstruktor, który w parametrze dostaje iloœæ danych, które powinien generowaæ - w ci¹gu sekundy.
	 * @param dataPerSec
	 */
	public Generator(int dataPerSec)
	{
		this.targets = new Vector<Interactive>(5);
		this.dataPerSec = dataPerSec;
		this.genThread = new GeneratorThread();
	}
	
	/**
	 * Metoda abstrakcyjna odpowiadaj¹ca za generowanie danych.
	 * @return
	 */
	public abstract Data generateData();
	
	/**
	 * Metoda dodaj¹ca do zbioru targetów nowy target.
	 * @param Interactive inter - nowy cel.
	 * @throws TINException
	 */
	public void addTarget(Interactive inter) throws TINException
	{
		if(inter == null)
			throw new TINException("Target Interactive for Generator has value null.");
		else
			this.targets.add(inter);
	}
	
	/**
	 * Metoda wysy³aj¹ca otrzymane dane do targetów.
	 * @param Data data - dane do wys³ania
	 */
	public void propagateData(Data data) {
		for(Interactive i : targets) {
			i.addToBuffer(data);
		}
	}		

	/**
	 * Uruchamia w¹tek. Musi byæ wywo³ane po konstruktorze i pod³¹czeniu targetów.
	 * @throws TINException
	 */
	public void startThread() throws TINException
	{
		if (targets.size() == 0) throw new TINException("No targets !");
		this.genThread.start();
	}
	
	/**
	 * W¹tek s³u¿¹cy do obs³ugi klas generuj¹cych dane.
	 */
	private class GeneratorThread extends Thread
	{
		/**
		 * Constructor from superclass
		 */
		public GeneratorThread() {
			super();
			this.setDaemon(true);
		}

		/**
		 * Constructor from superclass
		 */
		public GeneratorThread(String arg0) {
			super(arg0);
			this.setDaemon(true);
		}

		/**
		 * W¹tek tworzy okreœlon¹ iloœæ danych w ci¹gu jednej sekundy i nastêpnie rozsy³a je do swoich celów.
		 */
		public synchronized void run()
		{
			/*
			int licznik = 0;
			Data d;
			while(true)
			{
				licznik = 0;
				while(licznik < dataPerSec)
				{
					++licznik;
					d = generateData();
					for(Interactive i : targets)
						i.addToBuffer(d);
				}
				try {
					sleep(1000 - licznik/10); // TODO - taka heurystyczna metoda na generowanie okreslonej ilosci danych w ciagu sekundy
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			*/
			
			
			// wydaje mi siê, ¿e tak bedzie lepiej, ale mozemy polemizowac ;p
			// TODO: conieco zmienilem ;p
			Data d;
			while(true)
			{
				d = generateData();
				propagateData(d);
				try {
					sleep(1000 / dataPerSec); // ?
				} catch (InterruptedException e) {
					// nic ?
				}
			}
		}
		
	}

}
