package tin.klient.model.fields;

/**
* Klasa bazowa (abstrakcyjna) reprezentuj¹ca pole planszy.
* @author Szaman
*
*/
public abstract class Field {
	/**
	 * Okreœla znak przypisany do danej klasy, u¿ywany podczas wypisywania planszy w wersji tekstowej.
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
