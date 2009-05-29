/**
 * 
 */
package tin.engine.streams.readers;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Vector;

import tin.engine.data.Data;
import tin.engine.exceptions.TINException;

/**
 * Klasa, kt�ra s�u�y do odczytywania danych ze strumienia aplikacji.
 * Przyjmuje dane w postaci: [target]:[length]:{data}, gdzie:<br>
 * target - { U | N } (U - User, N - Net)<br>
 * length - liczba oznaczaj�ca ilo�� bajt�w do przeczytania (w danych binarnych) lub 0, gdy s� to dane tekstowe i nale�y wczyta� je a� do wyst�pienia \0.<br>
 * data - dane - binarne lub tekstowe.
 * <br>
 * Zwraca Data:
 * "readerID":Integer<br>
 * "message type":"System"|"Data"
 * "message content":"Stream closed"|"Data corrupted"|Data (gdy typ to "Data")
 */
// TODO: dokonczyc komentarz
public class ApplicationReader extends ReaderWithID {

	/**
	 * Ten konstruktor nie moze byc uzyty w tej klasie, bo nie podaje sie unikalnego ID.
	 * @param inputStream
	 * @throws TINException
	 */
	public ApplicationReader(InputStream inputStream) throws TINException {
		super(inputStream);
		// wyjatek zostanie rzucony przez klase "ojca"
	}

	/**
	 * Konstruktor.
	 * @param InputStream inputStream
	 * @param int id - unikalne ID dla Readera.
	 */
	public ApplicationReader(InputStream inputStream, int id) {
		super(inputStream, id);
	}

	/* (non-Javadoc)
	 * @see tin.engine.streams.readers.ReaderWithID#readDataPacketFromStream()
	 */
	@Override
	protected Data readDataPacketFromStream() throws Exception 
	{
		StringBuffer sb = new StringBuffer();
		int temp;
		Data data = new Data();
		data.addObject("readerID", readerID);			
		temp = inputStream.read(); // Czytamy pierwszy znak ( U | N )
		if(temp == (int)'\n')
			temp = inputStream.read(); // jezeli wczytany znak to nie U ani nie N to prawdopodobnie
									// transmisja byla potwierdzona enterem - dlatego na poczatku wczytywania
							// pomijamy znak nowej lini
		if (temp == -1) 
		{
			System.out.println("temp == -1");
			data.addObject("message type", "System");
			data.addObject("message content", new String("Stream closed"));
			this.streamReaderThread.rawRuns = true;
			return data;
		}
		temp = inputStream.read();
		if (temp != (int)':') 
		{
			System.out.println("temp != (int)':'");
			System.out.println("temp: " + (char)temp);
			data.addObject("message type", "System");
			data.addObject("message content", "Data corrupted");
			return data;
		}
		Data content = new Data();
		
		while ((temp = inputStream.read()) != -1 && temp != (int)':') {
			sb.append((char)temp);
		} // readuje do konca strumienia albo do pojawienia sie znaku ':' - gdy znak ten sie pojawi
			// to przy nastepnym wczytywaniu nie bedzie brany pod uwage TODO sprawdzic czy na pewno
		
		try {
			if ((temp = Integer.parseInt(sb.toString())) == 0) 
			{
				// mamy do czynienia z danymi tekstowymi
				BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
				String s = br.readLine();
				content.addObject("data type", "text");
				content.addObject("base64Encoded", "false");
				content.addObject("data", s);
			} else
			if (temp < 0) 
			{
				// dane uszkodzone
				System.out.println("temp < 0");
				data.addObject("message type", "System");
				data.addObject("message content", "Data corrupted");
				return data;
			} else 
			{
				// mamy dane binarne
				Vector<Byte> byteArray = new Vector<Byte>();
				int t;
				for (int i = 0; i < temp; i++) 
				{
					t = inputStream.read();
					System.out.println("Numer: " + i + " wartosc: " + t);
					if (t == -1) // TODO czy strumien konczy sie -1 ?
					{ 
						System.out.println("temp == -1 wtf");
						data.addObject("message type", "System");
						data.addObject("message content", "Stream closed");
						return data;
					}
					byteArray.add(new Byte((byte)t));
				}
				content.addObject("data type", "binary");
				content.addObject("base64Encoded", "false");
				content.addObject("data", byteArray);
			}
			
			
			data.addObject("message type", "Data");
			System.out.println("add(Data)");
			data.addObject("message content", content);		
			return data;
		} catch (NumberFormatException e) {
			System.out.println(temp);
			data.addObject("message type", "System");
			data.addObject("message content", "Data corrupted");
			return data;			
		}
	}

}
