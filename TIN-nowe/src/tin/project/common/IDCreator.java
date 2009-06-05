/**
 * 
 */
package tin.project.common;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Klasa generujaca ID, jest zrealizowana jako Singleton - mozna stowirzyc tylko jrdna jej instancje.
 *
 */
public class IDCreator
{
	/**
	 * Pole statyczne trzymajace nastepne id.
	 */
	private static AtomicInteger nextID;
	
	/**
	 * Jedyna dostepna instancja tej klasy.
	 */
	private static IDCreator instance = null;
	
	/**
	 * Metoda zwracajaca nastepne dostepne id.
	 * @return
	 */
	public Integer getNextID()
	{
		return nextID.addAndGet(1);
	}
	
	/**
	 * Metoda zwracajaca jedyna instancje klasy, lub tworzaca ja gdy jeszcze nie zostala utworzona.
	 * @return
	 */
	public static IDCreator getInstance()
	{
		if(instance == null)
			instance = new IDCreator();
		return instance;
			
	}
	
	/**
	 * Prywatny kontruktor - zeby nie dalo sie stworzyc wiecej instancji klasy niz jedna
	 */
	private IDCreator()
	{
		nextID = new AtomicInteger(0);
	}

}
