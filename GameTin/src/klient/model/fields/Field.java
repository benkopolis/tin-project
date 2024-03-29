package klient.model.fields;

/**
* Klasa bazowa (abstrakcyjna) reprezentująca pole planszy.
* @author Szaman
*
*/
public abstract class Field {
	/**
	 * Określa znak przypisany do danej klasy, używany podczas wypisywania planszy w wersji tekstowej.
	 */
	protected char sign;

	/**
	 * Zwraca znak przypisany do danej klasy.
	 * @return
	 */
	public char getSign() {
		return sign;
	}
}
