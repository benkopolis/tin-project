/**
 * 
 */
package tin.project.main;


import java.util.HashMap;
import java.util.Vector;

import tin.engine.exceptions.NullPointerTINException;
import tin.engine.exceptions.TINException;
import tin.engine.interactive.Interactive;
import tin.engine.interactive.collectors.SearchableMap;
import tin.engine.interactive.tranformers.DataUnpackerTransformer;
import tin.engine.interactive.tranformers.RawTransformer;
import tin.engine.streams.readers.ApplicationReader;
import tin.engine.streams.readers.LineReader;
import tin.engine.streams.writers.DataWriter;
import tin.engine.streams.writers.LineWriter;
import tin.engine.streams.writers.LogWriter;
import tin.engine.streams.writers.UnpackedDataWriter;
import tin.engine.interactive.tranformers.AuthorWorkTransformer;
import tin.project.main.menu.Menu;

/**
 * @author Szaman
 *
 */
public class Main {
	
	private static HashMap<String, Interactive> graphElements;
	
	public static void makeFlowGraph()
	{
		// dodajemy DataUnpackerTransformer - wyluskuje Date z przesylki transportowej
		// przesylke systemowa przesyla dalej
		graphElements.put("DataUnpackerTransformer", new DataUnpackerTransformer());
		// kolektor w ktorym trzymamy pary - reader+writer - i na tej podstawie wyszukujemy polaczenia
		graphElements.put("ReaderAndWriterAssociationCollector", new SearchableMap());
		//szukamy wlasciwego celu, jezeli celow jest wiecej to dostajemy vector i szukamy w nim wlasciwego obiektu
		//docelowego sprawdzajac jego id z id celu.
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Menu m = new Menu();
		makeFlowGraph();
	}

}
