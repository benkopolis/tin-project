/**
 * 
 */
package tin.engine.interactive.collectors;

import java.util.Map;
import java.util.Vector;

import tin.engine.data.Data;
import tin.engine.exceptions.TINException;
import tin.engine.interactive.Interactive;

/**
 * Jest to abstrakcyjna klasa bazowa, równoleg³a do klasy Collector, przechowuj¹ca  
 * dane w mapie.
 */
public abstract class MapCollector extends Interactive
{
	
	protected Map map;

	/**
	 * @param map
	 */
	public MapCollector(Map map)
	{
		this.map = map;
	}
	
	/**
	 * Domyœlny kontruktor.
	 */
	public MapCollector()
	{
		super();
	}

	/* (non-Javadoc)
	 * @see tin.engine.interactive.Interactive#processData(tin.engine.data.Data)
	 */
	@Override
	protected Data processData(Data data)
	{
		String cmd;
		Object object;
		try {
			cmd = (String) data.getData("command");
			if (cmd.equalsIgnoreCase("add")) {
				if(map.containsKey(data.getData("key"))) {
					Vector<Object> v = (Vector<Object>) (map.get(data.getData("key")));
					v.add(data.getData("value"));
				} else {
					Vector<Object> v = new Vector<Object>(3);
					v.add(data.getData("value"));
					map.put(data.getData("key"), v);
				}
				return null;
			} else 
			if (cmd.equalsIgnoreCase("send")) {
				Data d = new Data();
				d.addObject("type", map.getClass().getName());
				d.addObject("map", map);
				return d;
			} else 
			if (cmd.equalsIgnoreCase("clear")) {
				map.clear();
				return null;
			} else return null;					
		} catch (TINException e) {
			return null; // TODO na pewno ? ;p
		}
	}

	/* (non-Javadoc)
	 * @see tin.engine.interactive.Interactive#propagateData(tin.engine.data.Data)
	 */
	@Override
	protected void propagateData(Data data) throws Exception
	{
		if(data == null)
			return;
		else
		{
			for(Interactive i: targets)
				i.addToBuffer(data);
		}		
	}

}
