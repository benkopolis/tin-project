package networkctrl;

import java.io.IOException;
import java.io.InputStream;

/**
 * Klasa sluzaca do odczytywania wiadomosci przesylanych przez sockety.
 */
public class InputReader {
	/**
	 * Odczytuje tablicê bajtów przeslanych w pakiecie.
	 * @param is - input stream, z ktorego nalezy czytac tablice bajtow
	 * @return tablica stringow (null gdy input stream jest zamkniety)
	 * @throws IOException - gdy wystapi blad odczytu
	 */
	public static String[] getStringsFromPacket(InputStream is) throws IOException {
		byte[] tab = new byte[1024];
		int readAmount = is.read(tab);
		String[] strTab = null;
		if (readAmount != -1) {
			String str = new String(tab);
			strTab = str.substring(0, readAmount-1).split("\n");
		}
		return strTab;
	}
}
