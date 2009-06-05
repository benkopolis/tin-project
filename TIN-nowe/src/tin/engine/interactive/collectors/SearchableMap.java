/**
 * 
 */
package tin.engine.interactive.collectors;

import java.util.Map;
import java.util.Vector;

import tin.engine.data.Data;
import tin.engine.exceptions.TINException;

/**
 * Kolektor w postaci mapy umo�liwiaj�cej wyszukiwanie danych po kluczu lub po wartosci.
 * Za przyjmowanie polece� wyszukiwania i ich realizacj� odpowiada funkcja processData(Data data) 
 */
public class SearchableMap extends MapCollector
{

	/**
	 * @param map
	 */
	public SearchableMap(Map map)
	{
		super(map);
	}

	/**
	 * 
	 */
	public SearchableMap()
	{
		// do nothing
	}
	
	/* (non-Javadoc)
	 * @see tin.engine.interactive.Interactive#processData(tin.engine.data.Data)
	 */
	@Override
	protected Data processData(Data data)
	{
		Data temp;
		String cmd;
		if ((temp=super.processData(data)) != null) return temp;
		
		try
		{
			cmd = (String) data.getData("command");
			temp = new Data();
			temp.addObject("readerID", data.getData("readerID"));
			if (cmd.equalsIgnoreCase("getByKey")) { // wyszukiwanie po kluczu
				temp.addObject("result", map.get(data.getData("key")));
				temp.addObject("type", "value");
				return temp;
			} else if(cmd.equalsIgnoreCase("getByValue")) { // wyszukiwanie po wartosciach
				Vector<Object> v = new Vector<Object>(5);
				temp.addObject("result", v); // dodaje referencje na wektor do obiektu data
				for(Object i: map.keySet())
				{
					for(Object j: (Vector<Object>)map.get(i))
					{
						if(data.getData("value").equals(j))
							v.add(i);
					}
				}
				temp.addObject("type", "set");
				return temp;
			}
		} catch (TINException e)
		{
			e.printStackTrace();
			return null;
		}
		return null;
	}

}
