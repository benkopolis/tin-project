/**
 * 
 */
package tin.project.main;


import tin.engine.exceptions.NullPointerTINException;
import tin.engine.exceptions.TINException;
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

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO musimy miec socketContainera, IN/OUT streamContainera i appContainera
		/*ApplicationReader appReader = new ApplicationReader(System.in, 666);
		DataWriter dw = new DataWriter(System.err);
		UnpackedDataWriter udw = null;
		try
		{
			udw = new UnpackedDataWriter(System.out, "udw", 777);
		} catch (NullPointerTINException e1)
		{
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		DataUnpackerTransformer unpack = new DataUnpackerTransformer();

		
		try {
			appReader.addTarget(unpack); // stream reader --> interactive (...)
			appReader.addTarget(dw);
			unpack.addTarget(udw); // (...) interactive --> stream writer
		} catch (TINException e) {
			e.printStackTrace();
		} 
		udw.startThread();
		unpack.startThread();
		appReader.startThread();
		dw.startThread();
		
		
		int i = 101;
		while (i-- > 0) {
			System.out.println("W�tek g��wny: jeszcze dzia�am ;p Zosta�o Ci: " + i * 10  + " sekund ;)");
			try {
				Thread.sleep(10000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}*/
		// TODO dokumentacja - nazwa pliku nie moze zaczynac sie od spacji
		// TODO przy wpisywaniu sciezki zamiast \ robimy \\ a zmiast " " robimy "_"
		// TODO a zamiast _ robimy \_
		String s1 = Menu.transform("C:\\\\Lol\\\\\\_a.out"); // \Loool_nb  lol
	}

}
