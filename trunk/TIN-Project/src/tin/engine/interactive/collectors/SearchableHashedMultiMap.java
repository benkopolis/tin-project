/**
 * 
 */
package tin.engine.interactive.collectors;

import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import tin.engine.data.Data;
import tin.engine.exceptions.TINException;
import tin.engine.interactive.Interactive;

/**
 * Multi mapa służąca do wyszukiwania par klucz-wartość w dwóch hashmapach. To co jest kluczem w pierwszej hashmapie jest wartością w
 * drugiej, analogicznie w drugą stronę. Realizuje ona funkcje dodawania i wyszukiwania.
 * @note to jest cholerny bydlak, implementacja tego to masochizm
 */
public final class SearchableHashedMultiMap extends Interactive
{

	HashMap<Object, Vector<Object>> keyToVal = new HashMap<Object, Vector<Object>>();
	HashMap<Object, Vector<Object>> valToKey = new HashMap<Object, Vector<Object>>();

	/**
	 * 
	 */
	public SearchableHashedMultiMap()
	{
		// TODO Auto-generated constructor stub
	}

	@Override
	protected Data processData(Data data)
	{
		String cmd;
		Object object;
		Data temp = new Data();
		try {
			temp.addObject("readerID", data.getData("readerID"));
			cmd = (String) data.getData("command");
			if (cmd.equalsIgnoreCase("add")) { // dodawanie pary <K, V> do asoscjacji * do *
				// najpierw do klucz - wartosc dodajemy pod
				// odpowiedni klucz podan� wartosc do wektora wartosci
				if(keyToVal.containsKey(data.getData("key"))) { 
					Vector<Object> v = ((Vector<Object>) (keyToVal.get(data.getData("key"))));
					v.add(data.getData("value"));
				} else {
					Vector<Object> v = new Vector<Object>(3);
					v.add(data.getData("value"));
					keyToVal.put(data.getData("key"), v);
				}
				// nastepnie do pary wartosc - klucz dodajemy pod odpowiednia
				// wartosc podany klucz do wektora kluczy
				if(valToKey.containsKey(data.getData("value"))) { 
					Vector<Object> v = (Vector<Object>) (valToKey.get(data.getData("value")));
					v.add(data.getData("key"));
				} else {
					Vector<Object> v = new Vector<Object>(3);
					v.add(data.getData("key"));
					valToKey.put(data.getData("value"), v);
				}
				return null;
			} else if (cmd.equalsIgnoreCase("clear")) {
				keyToVal.clear();
				valToKey.clear();
				return null;
			} else if (cmd.equalsIgnoreCase("getByKey")) { // wyszukiwanie po kluczu
				Vector<Object> v = keyToVal.get(data.getData("key"));
				temp.addObject("result", v); // dodaje referencje na wektor do obiektu data
				return temp;
			} else if(cmd.equalsIgnoreCase("getByValue")) { // wyszukiwanie po wartosciach
				Vector<Object> v = valToKey.get(data.getData("value"));
				temp.addObject("result", v); // dodaje referencje na wektor do obiektu data
				return temp;
			} else return null;	
		} catch (TINException e) {
			e.printStackTrace();
			return null; // TODO na pewno ? ;p
		}
	}

	@Override
	protected void propagateData(Data data) throws Exception
	{
		// TODO Auto-generated method stub
		
	}

}
