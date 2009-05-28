/**
 * 
 */
package tin.engine.generators;

import java.util.Vector;

import tin.engine.data.Data;
import tin.engine.exceptions.TINException;
import tin.engine.interactive.Interactive;

/**
 * Abstrakcyjna klasa bazowa dla generator�w danych.
 */
public abstract class Generator 
{
	/**
	 * Wektor cel�w, do kt�rych maj� by� generowane dane losowe.
	 */
	protected Vector<Interactive> targets;
	
	/**
	 * Ilo�� danych losowych jaka ma by� generowana w ci�gu 1 sekundy.
	 */
	protected int dataPerSec;
	
	/**
	 * Zmienna w�tku generuj�cego dane i przesy�aj�cego je.
	 */
	protected GeneratorThread genThread;
	
	/**
	 * Konstruktor, kt�ry w parametrze dostaje ilo�� danych, kt�re powinien generowa� - w ci�gu sekundy.
	 * @param dataPerSec
	 */
	public Generator(int dataPerSec)
	{
		this.targets = new Vector<Interactive>(5);
		this.dataPerSec = dataPerSec;
		this.genThread = new GeneratorThread();
	}
	
	/**
	 * Metoda abstrakcyjna odpowiadaj�ca za generowanie danych.
	 * @return
	 */
	public abstract Data generateData();
	
	/**
	 * Metoda dodaj�ca do zbioru target�w nowy target.
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
	 * Metoda wysy�aj�ca otrzymane dane do target�w.
	 * @param Data data - dane do wys�ania
	 */
	public void propagateData(Data data) {
		for(Interactive i : targets) {
			i.addToBuffer(data);
		}
	}		

	/**
	 * Uruchamia w�tek. Musi by� wywo�ane po konstruktorze i pod��czeniu target�w.
	 * @throws TINException
	 */
	public void startThread() throws TINException
	{
		if (targets.size() == 0) throw new TINException("No targets !");
		this.genThread.start();
	}
	
	/**
	 * W�tek s�u��cy do obs�ugi klas generuj�cych dane.
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
		 * W�tek tworzy okre�lon� ilo�� danych w ci�gu jednej sekundy i nast�pnie rozsy�a je do swoich cel�w.
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
			
			
			// wydaje mi si�, �e tak bedzie lepiej, ale mozemy polemizowac ;p
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
