/**
 * 
 */
package tin.engine.data;

import java.util.HashMap;

import tin.engine.exceptions.TINException;

/**
 * Klasa s�u��ca do przechowywania danych przesy�anych pomi�dzy modu�ami.
 */
public class Data {
	
	/**
	 * Hashmapa s�u��ca do przechowywania par: [nazwa atrybutu]:[warto�� atrybutu]
	 */
	private HashMap<String, Object> data = new HashMap<String, Object>(8);
	
	/**
	 * Metoda s�u��ca do dodawania nowych par [nazwa]:[warto��].
	 * Metoda rzuca wyj�tkiem TINException, gdy dany atrybut ju� istnieje w zbiorze.
	 * @param String attrName - nazwa atrybutu
	 * @param Object object - warto�� atrybutu
	 * @throws TINException
	 */
	public void addObject(String attrName, Object object) throws TINException 
	{
		
		if (data.containsKey(attrName) == true) throw new TINException("Attribute already exists (" + attrName + ").");
		
		data.put(attrName, object);
		return;
	}
	
	/**
	 * Metoda zwracaj�ca dane powi�zane z podan� nazw� atrybutu lub rzucaj�ca wyj�tek,
	 * je�li takiego atrybutu brakuje w zbiorze.
	 * @param String attrName - nazwa atrybutu
	 * @return Object - zwr�cone dane
	 * @throws TINException
	 */
	public Object getData(String attrName) throws TINException 
	{
		if (data.containsKey(attrName) == false) throw new TINException("No such attribute in set (" + attrName + ").");
		
		return data.get(attrName);
	}
	
	/**
	 * Metoda s�u��ca do zamieniania warto�ci powi�zanej z dan� nazw� atrybutu.
	 * Je�li danego atrybutu brakuje w zbiorze, to rzucany jest wyj�tek.
	 * @param String attrName - nazwa atrybutu
	 * @param Object object - nowa warto�� atrybutu
	 * @throws TINException
	 */
	public void setData(String attrName, Object object) throws TINException 
	{
		if (data.containsKey(attrName) == false) throw new TINException("No such attribute in set.");
		
		data.put(attrName, object);
		return;
	}
	
	/**
	 * Metoda drukujaca dokladnie cala zawartosc Date'y
	 */
	@Override
	public String toString()
	{
		StringBuffer ret = new StringBuffer("\nData:\n");
		for(String s: this.data.keySet())
		{
			ret.append("\t" + s + ":" + this.data.get(s) + "\n");
		}
		ret.append("\n");
		return ret.toString();
	}
	
	/**
	 * Metoda sprawdza czy w zbiorze atrybutow znajduje sie szukany atrybut i zwraca true jezeli to prawda, ablo false jezeli go nie ma.
	 * @param s
	 * @return
	 */
	public boolean checkPresenceOfAttribute(String s)
	{
		return this.data.keySet().contains(s);
	}
}
