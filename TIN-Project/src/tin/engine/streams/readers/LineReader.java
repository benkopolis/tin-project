/**
 * 
 */
package tin.engine.streams.readers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import tin.engine.data.Data;
import tin.engine.exceptions.TINException;

/**
 * Klasa czytaj¹ca ze strumienia wejœciowego linie tekstu a nastêpnie
 * pakuj¹ca je w paczki postaci:<br>
 * <b>Data:</b><br>
 * &nbsp;&nbsp;&nbsp;&nbsp;"line":[String]- przeczytana linia
 */
public class LineReader extends StreamReader {

	/**
	 * BufferedReader do odczytywania linii tekstu ze strumienia.
	 */
	private BufferedReader buffReader;
	
	/**
	 * Konstruktor.
	 * @param InputStream inputStream - strumieñ, z którego maj¹ byæ odczytywane linie tekstu
	 */
	public LineReader(InputStream inputStream) {
		super(inputStream);
		buffReader = new BufferedReader(new InputStreamReader(inputStream));
	}

	/* (non-Javadoc)
	 * @see tin.engine.streams.readers.SourceReader#readDataPacketFromStream()
	 */
	@Override
	protected Data readDataPacketFromStream() throws IOException, TINException {
		Data d = new Data();
		String s = buffReader.readLine();
		if (s != null) 
		{
			d.addObject("line", s);
			return d;
		} else 
		{
			throw new TINException("LineReader::readDataPacketFromStream() - null line recieved");
		}
	}

}
