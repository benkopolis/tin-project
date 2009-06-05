package tin.project.main.menu;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;
import java.util.Vector;

import tin.engine.data.Data;
import tin.engine.exceptions.TINException;
import tin.project.application.ApplicationLauncher;

/**
 * Klasa wypisuj�ca komunikaty (co u�ytkownik ma wpisa� �eby uruchomi� poprawnie aplikacje, co mo�e jeszcze zrobi� innego itp)
 * oraz przyjmuj�ca akcje u�ytkownika z konsoli i podejmuj�ca na ich podstawie jakie� dzia�ania.
 */
public class Menu
{
	/**
	 * zmienna okreslajaca, czy menu ma dalej dzialac
	 */
	private boolean end = false;
	
	/**
	 * zmienna ktora jest klasa odpalajaca aplikacje
	 */
	private ApplicationLauncher appLauncher = new ApplicationLauncher();
	
	/**
	 * Kontruktor domyslny 
	 */
	public Menu() {
		Vector<String> cmd;
		while (!end) {
			System.out.print("Enter command: ");
			cmd = read();
			parseCmd(cmd);
		}
	}

	/**
	 * Metoda do parsowania komendy. Dla każdej komendy wywołuje odpowiednie metody do jej realizacji.
	 * @param cmd
	 */
	private void parseCmd(Vector<String> cmd) {
		if (cmd == null) 
			return;
		String fElem = cmd.get(0);
		if(fElem.equals("help"))
			help();
		else if(fElem.equals("exit"))
			exit();
		else if(fElem.equals("addclient"))
			addClientApplication(cmd);
		else if(fElem.equals("addserver"))
			addServerApplication(cmd);
		else
			write("Unknown command", 1);
	}

	/**
	 * Metoda odpalajaca program ktory jest serwerem
	 * @param Vector<String> cmd - kolejne slowa z linii komend, zawierajace informacje o tworzonej aplikacji
	 */
	private void addServerApplication(Vector<String> cmd) {
		String [] args = null;
		if(cmd.size()-3 != 0) {
			args = new String [cmd.size()-3];
			for(int i = 3; i<cmd.size(); ++i)
				args[i-3] = cmd.get(i);
		}
		try {
			appLauncher.launchApplication(cmd.get(2), args);
		} catch (TINException e) {
			e.printStackTrace();
		}
		// TODO zrobic tworzenie watku serwerowego i odpalanie go oraz dodawanie appr/w i servsockr/w
	}

	/**
	 * Metoda dodajaca nowa aplikacje kliencka
	 * @param Vector<String> cmd - kolejne slowa z linii komend, zawierajace informacje o tworzonej aplikacji
	 */
	private void addClientApplication(Vector<String> cmd) {
		String [] args = null;
		if(cmd.size()-4 != 0) {
			args = new String [cmd.size()-4];
			for(int i = 4; i<cmd.size(); ++i)
				args[i-4] = cmd.get(i);
		}
		try {
			appLauncher.launchApplication(cmd.get(3), args);
		} catch (TINException e) {
			e.printStackTrace();
		}
		// TODO dopinanie do writerow i innych smieci znaczy socket i app readerow + tworzenie socketa
	}

	/**
	 * Funkcja realizujaca konczenie programu
	 */
	private void exit()
	{
		// TODO tutaj kasujemy wszystko i zamykamy poprawnie polaczenia i aplikacje
		end = true;
		write("Trwa konczenie pracy programu...",1);
	}

	private void help()
	{
		write("HELP:",1);
		write("<path> - sciezka do uruchamianej aplikacji. W sciezce nie mozna podawac spacji - zamiast nich nalezy", 2);
		write("wstawic podkreslniki. Zamiast podkreslnikow: \\_ , a zmiast \\ trzeba \\\\ ", 3);
		
		write("exit - wyjscie z programu",1);
		
		write("addclient host port <path> <args>- polecenie dodania nowgo programu klienckiego", 1);
		write("host - czyli adres ip, do ktorego klient chce sie podlaczyc", 2);
		write("port - czyli numer portu, na ktory klient bedzie pisac", 2);
		write("<args> - argumenty ktore maja byc przekazane do uruchamianego programu, rozdzielone spacja", 2);
		
		write("addserver port <path> <args> - polecenie dodania nowego programu serwerowego", 1);
		write("port - numer portu na ktorym serwer bedzie nasluchiwac", 2);
		write("<args> - argumenty ktore maja byc przekazane do uruchamianego programu, rozdzielone spacja", 2);
	}

	/**
	 * metoda wypisujaca na ekran podanego Stringa
	 * @param String s 
	 * @param int level - poziom wciecia
	 */
	public void write(String s, int level) {
		if (level < 0) level = 0;
		for (int i = 0; i < level; i++) System.out.print('\t');
		System.out.println(s);
	}
	
	/**
	 * Wczytuje polecenie od uzytkownika i rozbija je na fragmenty a nastepnie zwraca je w wektorze
	 * @return Vector<String>
	 */
	public Vector<String> read() {
		BufferedReader br;
		br = new BufferedReader(new InputStreamReader(System.in));
		String s, pom;
		Vector<String> v = new Vector<String>(5);
		try {
			s = br.readLine();
			StringTokenizer st = new StringTokenizer(s, " ");
			while(st.hasMoreTokens()) {
				pom = st.nextToken();
				v.add(pom);
			}
				
		} catch (IOException e)	{
			e.printStackTrace();
		}
		
		return v;
	}

	/**
	 * Metoda transformuj�ca podaną przez użytkownika ścieżką, zgodnie z
	 * podan� specyfikacj� (\\ -> \   \_ -> _    _ -> " "). 
	 * W podawanej scie�ce nie mog� wyst�pi� takie znaki jak " ". 
	 * @param pom
	 * @return
	 */
	protected static String transform(String pom)
	{
		System.out.println(pom);
		pom = pom.replaceAll("_", " ");
		pom = pom.replaceAll("\\\\ ", "_");
		pom = pom.replaceAll("\\\\\\\\", "\\\\");
		System.out.println(pom);		
		return pom;
		// TODO dokumentacja - nazwa pliku nie moze zaczynac sie od spacji
		// TODO przy wpisywaniu sciezki zamiast \ robimy \\ a zmiast " " robimy "_"
		// TODO a zamiast _ robimy \_
	}
}





















