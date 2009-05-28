/**
 * 
 */
package tin.engine.exceptions;

/**
 * Wyj¹tek stworzony na potrzeby klas z pakietu tin.engine.*
 *
 */
public class TINException extends Exception {

	/**
	 * 
	 */
	public TINException() {
	
	}

	/**
	 * @param arg0
	 */
	public TINException(String arg0) {
		super(arg0);
	}

	/**
	 * @param arg0
	 */
	public TINException(Throwable arg0) {
		super(arg0);
	}

	/**
	 * @param arg0
	 * @param arg1
	 */
	public TINException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

}
