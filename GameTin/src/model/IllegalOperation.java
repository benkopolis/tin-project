package model;

/**
 * Wyj�tek oznaczaj�cy, �e podj�ta zosta�a pr�ba wykonania operacji na 
 * planszy, kt�ra nie zosta�a utworzona/wczytana.
 * @author Szaman
 *
 */
public class IllegalOperation extends Exception {
	
	private static final long serialVersionUID = -1222539010099757566L;

	public IllegalOperation(String s) {
		super(s);
	}
}
