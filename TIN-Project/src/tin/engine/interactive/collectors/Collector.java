/**
 * 
 */
package tin.engine.interactive.collectors;

import java.util.Collection;

import tin.engine.data.Data;
import tin.engine.exceptions.TINException;
import tin.engine.interactive.Interactive;

/**
 * Klasa gromadz¹ca nap³ywaj¹ce do niej dane w okreœlon¹ kolekcjê a nastêpnie 
 * wysy³aj¹ca pakiet z t¹ kolekcj¹ dalej.
 * Format danych:<br>
 * Aby dodaæ nowy obiekt: <br>
 * "command":"add"<br>
 * "data":[obiekt]<br>
 * Aby wys³aæ kolekcjê dalej: <br>
 * "command":"send"<br>
 * Aby wyczyœciæ dane w kolekcji:<br>
 * "command":"clear"<br>
 * Zwraca:<br>
 * "type":[nazwa typu kolekcji]
 * "collection":[obiekt typu Collection] 
 */
public class Collector extends Interactive {

	/**
	 * Referencja na kolekcjê obiektów typu data.
	 */
	protected Collection collector;
	
	/**
	 * Konstruktor.
	 * @param Collection collection - referencja na kolekcjê, która bêdzie u¿ywana
	 * @throws TINException - gdy referencja == null
	 */
	public Collector(Collection collection) throws TINException {
		super();
		if (collection == null) throw new TINException("Collection can not be null !");
		collector = collection;
	}
	
	/**
	 * Konstruktor, po którym musi nast¹piæ wywo³anie setCollection(). 
	 * Chyba, ¿e jest u¿ywany w klasie potomnej, która ma wbudowan¹ okreœlon¹ kolekcjê (np wektor)
	 */
	public Collector() {
		super();
	}
	
	/**
	 * Ustawia referencjê na jak¹œ zewnêtrzn¹ kolekcjê.
	 * @param Collection collection - zewnêtrzna kolekcja
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
			// nic nie wysy³amy	
		} else {
			for (Interactive i: targets) {
				i.addToBuffer(data);
			}
		}
	}

}
