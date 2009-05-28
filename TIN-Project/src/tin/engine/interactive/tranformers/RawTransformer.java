/**
 * 
 */
package tin.engine.interactive.tranformers;

import tin.engine.data.Data;
import tin.engine.exceptions.TINException;
import tin.engine.interactive.Interactive;

/**
 * Przyk³adowy transformer. Nie zmienia w danych nic.
 * Po prostu przesy³a dalej to, co otrzyma³. Mo¿e byæ 
 * u¿yteczny podczas duplikacji (lub zwielokratniania) 
 * danych.
 */
public class RawTransformer extends Interactive {

	/**
	 * Konstruktor.
	 */
	public RawTransformer() {
		super();
	}
	
	/**
	 * W tej klasie metoda ta przekazuje dalej to, co zosta³o jej przekazane.
	 * @param Data data - dane do obrobienia
	 * @return Data - dane po obrobieniu 
	 */
	@Override
	public Data processData(Data data) {
		//System.out.println("RawTransf.: Przekazuje dane dalej.");
		return data;
	}
	
	/**
	 * Metoda, ktora dane otrzymane w parametrze wysy³a do ka¿dego z "targetów".
	 * Rzuca wyj¹tkiem, gdy dana instancja klasy nie posiada dodanych targetów.
	 * @param Data data - dane do przes³ania dalej
	 * @throws TINException
	 * @throws Exception
	 */
	@Override
	protected void propagateData(Data data) throws TINException, Exception {
		if (targets.size() == 0) throw new TINException("No targets added.");
		for (Interactive target : targets) {
			target.addToBuffer(data); /* dodajemy dane na koniec kolejki FIFO obiektu docelowego */
		}
	}

}
