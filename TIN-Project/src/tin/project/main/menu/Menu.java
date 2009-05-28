package tin.project.main.menu;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;
import java.util.Vector;

/**
 * Klasa wypisuj¹ca komunikaty (co u¿ytkownik ma wpisaæ ¿eby uruchomiæ poprawnie aplikacje, co mo¿e jeszcze zrobiæ innego itp)
 * oraz przyjmuj¹ca akcje u¿ytkownika z konsoli i podejmuj¹ca na ich podstawie jakieœ dzia³ania.
 */
public class Menu
{
	public Menu() {
		
	}

	public void write(String s) {
		System.out.println(s);
	}
	
	public Vector<String> read() {
		BufferedReader br;
		br = new BufferedReader(new InputStreamReader(System.in));
		String s, pom;
		Vector<String> v = new Vector<String>(5);
		try {
			s = br.readLine();
			StringTokenizer st = new StringTokenizer(s, " ");
			while((pom = st.nextToken()) != null) {
				pom = transform(pom);
				v.add(pom);
			}
		} catch (IOException e)	{
			e.printStackTrace();
		}
		
		return null;
	}

	/**
	 * Metoda transformuj¹ca podan¹ przez u¿ytkownika œcie¿kê, zgodnie z
	 * podan¹ specyfikacj¹ (\\ -> \   \_ -> _    _ -> " "). 
	 * W podawanej scie¿ce nie mog¹ wyst¹piæ takie znaki jak " ". 
	 * @param pom
	 * @return
	 */
	public static String transform(String pom)
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





















