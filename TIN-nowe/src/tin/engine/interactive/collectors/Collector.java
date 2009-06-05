/**
 * 
 */
package tin.engine.interactive.collectors;

import java.util.Collection;

import tin.engine.data.Data;
import tin.engine.exceptions.TINException;
import tin.engine.interactive.Interactive;

/**
 * Klasa gromadz�ca nap�ywaj�ce do niej dane w okre�lon� kolekcj� a nast�pnie 
 * wysy�aj�ca pakiet z t� kolekcj� dalej.
 * Format danych:<br>
 * Aby doda� nowy obiekt: <br>
 * "command":"add"<br>
 * "data":[obiekt]<br>
 * Aby wys�a� kolekcj� dalej: <br>
 * "command":"send"<br>
 * Aby wyczy�ci� dane w kolekcji:<br>
 * "command":"clear"<br>
 * Zwraca:<br>
 * "type":[nazwa typu kolekcji]
 * "collection":[obiekt typu Collection] 
 */
public class Collector extends Interactive {

	/**
	 * Referencja na kolekcj� obiekt�w typu data.
	 */
	protected Collection collector;
	
	/**
	 * Konstruktor.
	 * @param Collection collection - referencja na kolekcj�, kt�ra b�dzie u�ywana
	 * @throws TINException - gdy referencja == null
	 */
	public Collector(Collection collection) throws TINException {
		super();
		if (collection == null) throw new TINException("Collection can not be null !");
		collector = collection;
	}
	
	/**
	 * Konstruktor, po kt�rym musi nast�pi� wywo�anie setCollection(). 
	 * Chyba, �e jest u�ywany w klasie potomnej, kt�ra ma wbudowan� okre�lon� kolekcj� (np wektor)
	 */
	public Collector() {
		super();
	}
	
	/**
	 * Ustawia referencj� na jak�� zewn�trzn� kolekcj�.
	 * @param Collection collection - zewn�trzna kolekcja
	 * @throws TINException
	 */
	public void setCollection(Collection collection) throws TINException {
		if (collection == null) throw new TINException("Collection can not be null !");
		collector = collection;
		return;
	}
	
	/* (non-Javadoc)
	 * @see tin.engine.interactive.Interactive#processData(tin.engine.data.Data)
	 */
	@Override
	protected Data processData(Data data) {
		String cmd;
		Object object;
		try {
			cmd = (String) data.getData("command");
			if (cmd.equalsIgnoreCase("add")) { 
				collector.add(data.getData("data"));
				return null;
			} else 
			if (cmd.equalsIgnoreCase("send")) {
				Data d = new Data();
				d.addObject("type", collector.getClass().getName());
				d.addObject("collection", collector);
				return d;
			} else 
			if (cmd.equalsIgnoreCase("clear")) {
				collector.clear();
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
	protected void propagateData(Data data) throws Exception {
		if (data == null) {
			// nic nie wysy�amy	
		} else {
			for (Interactive i: targets) {
				i.addToBuffer(data);
			}
		}
	}

}
