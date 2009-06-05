/**
 * 
 */
package tin.project.application;

import java.util.Vector;

import tin.engine.exceptions.TINException;

/**
 * Jest to klasa s�u��ca do uruchamiania aplikacji, trzymania referencji na nie, pobierania ich strumieni wej�ciwych i wyj�ciowych. 
 *
 */
public class ApplicationLauncher
{
	/**
	 * wektor referencji na uruchomione programy
	 */
	private Vector<Process> processes;

	public ApplicationLauncher() {
		processes = new Vector<Process>(10);
	}
	
	/**
	 * Metoda ta uruchamia aplikacj� i dodaje j� do zbioru uruchomionych aplikacji.
	 * @param String path - �cie�ka do uruchomianego programu
	 * @param String[] args - tablica argument�w dla uruchamianego programu
	 * @throws TINException - wyj�tek rzucany gdy tablica jest nullem, lub apliacja nie uruchomi sie.
	 */
	public void launchApplication(String path, String[] args) throws TINException {
		System.out.println("Otrzymalem: " + path + args);
		Runtime r = Runtime.getRuntime();
		try {
		String[] temp = new String[args.length + 1];
		temp[0] = path;
		for (int i = 0; i < args.length; i++)
			temp[i+1] = args[i];
		Process p = r.exec(temp);
		processes.add(p);
		} catch (Exception e) {
			throw new TINException("Error during launching an application:" + e);
		}
		// TODO dodawanie do sieci streamow - moze zarzadca odgory powinien to robic.
	}

}
