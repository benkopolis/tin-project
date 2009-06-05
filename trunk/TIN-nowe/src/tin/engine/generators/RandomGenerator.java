/**
 * 
 */
package tin.engine.generators;

import java.lang.Math;
import tin.engine.data.Data;
import tin.engine.exceptions.TINException;

/**
 * Klasa generuj¹ca pakiety danych w formacie:<br>
 * "random":[obiekt klasy Integer z zakresu 0..10000]
 */
public class RandomGenerator extends Generator {

	/**
	 * Konstruktor.
	 * @param int dataPerSec - iloœæ danych do wygenerowania w czasie sekundy
	 */
	public RandomGenerator(int dataPerSec) {
		super(dataPerSec);
	}

	/* (non-Javadoc)
	 * @see tin.engine.generators.Generator#generateData()
	 */
	@Override
	public Data generateData() {
		Data data = new Data();
		Integer i = null;
		String s = "random";
		int licznik = 1;
		double val = 0;
		while(licznik < 11)
		{
			val = 10000.0 * Math.random();
			i = new Integer((int)val);
			try {
				data.addObject(s + licznik, i);
			} catch (TINException e) {
				// TODO a tu cos robimy ? ;p
			}
		}
		return data;
	}

}
