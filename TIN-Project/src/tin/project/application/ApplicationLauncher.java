/**
 * 
 */
package tin.project.application;

import java.util.Vector;

import tin.engine.exceptions.TINException;

/**
 * Jest to klasa s³u¿¹ca do uruchamiania aplikacji, trzymania referencji na nie, pobierania ich strumieni wejœciwych i wyjœciowych. 
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
	 * Metoda ta uruchamia aplikacjê i dodaje j¹ do zbioru uruchomionych aplikacji.
	 * @param String path - œcie¿ka do uruchomianego programu
	 * @param String[] args - tablica argumentów dla uruchamianego programu
	 * @throws TINException - wyj¹tek rzucany gdy tablica jest nullem, lub apliacja nie uruchomi sie.
	 */
	public void launchApplication(String path, String[] args) throws TINException {
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
