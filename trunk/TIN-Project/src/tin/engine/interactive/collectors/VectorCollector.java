/**
 * 
 */
package tin.engine.interactive.collectors;

import java.util.Collection;
import java.util.Vector;

import tin.engine.exceptions.TINException;
import tin.engine.interactive.collectors.Collector;

/**
 * Klasa, która gromadzi dane do kontenera: Vector<Object>
 */
public class VectorCollector extends Collector {

	/**
	 * Ten konstruktor jest niedozwolony w tej klasie.
	 * @param collection
	 * @throws TINException
	 */
	public VectorCollector(Collection collection) throws TINException {
		throw new TINException("This constructor is not applicable for this class");
	}

	/**
	 * Konstruktor tworz¹cy kolekcjê typu Vector.
	 */
	public VectorCollector() {
		collector = new Vector<Object>(5);
	}

}
