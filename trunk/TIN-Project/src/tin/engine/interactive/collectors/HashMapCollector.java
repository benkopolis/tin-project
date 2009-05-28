/**
 * 
 */
package tin.engine.interactive.collectors;

import java.util.HashMap;
import java.util.Map;

import tin.engine.exceptions.TINException;

/**
 * Jest to klasa reprezentuj¹ca kolektor jako kolektor danych zbieranych w hashmapie.
 * 
 */
public class HashMapCollector extends SearchableMap 
{

	/**
	 * @param collection
	 * @throws TINException
	 */
	public HashMapCollector(Map map) throws TINException
	{
		super(map);
		throw new TINException("This constructor is illegal for this class.");
	}

	/**
	 * 
	 */
	public HashMapCollector()
	{
		map = new HashMap<Object, Object>();
	}
}
