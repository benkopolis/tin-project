package klient.controller.networkctrl;

import java.io.IOException;
//import java.io.InputStream;
import java.io.OutputStream;

/**
 * Klasa sluzaca do wypisywania stringow na wyjscie.
 */
public class OutputWriter {
	/**
	 * Metoda wypisuj¹ca podanego Stringa (dorzucaj¹c na koniec znak nowej linii) na OutputStream w postaci ci¹gu bajtów.
	 * @param s - string do wypisania
	 * @param os - outputstream
	 * @throws IOException - gdy cos jest nie tak ;p
	 */
	public static void sendStringAsPacket(String s, OutputStream os) throws IOException {
		byte[] tab = s.concat("\n").getBytes();
		os.write(tab);
		return;
	}
}
