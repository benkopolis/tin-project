package model;

/**
 * Wyj¹tek oznaczaj¹cy, ¿e podjêta zosta³a próba wykonania operacji na 
 * planszy, która nie zosta³a utworzona/wczytana.
 * @author Szaman
 *
 */
public class IllegalOperation extends Exception {
	
	private static final long serialVersionUID = -1222539010099757566L;

	public IllegalOperation(String s) {
		super(s);
	}
}
