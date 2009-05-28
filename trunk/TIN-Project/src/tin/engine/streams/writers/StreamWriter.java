/**
 * 
 */
package tin.engine.streams.writers;

import java.io.OutputStream;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import tin.engine.data.Data;
import tin.engine.exceptions.TINException;
import tin.engine.interactive.Interactive;

/**
 * Abstrakcyjna klasa bazowa dla klas zapisuj¹cych dane do strumieni.
 */
public abstract class StreamWriter extends Interactive {
	// TODO: byc moze nalezy wywalic to write... i ten watek i wykorzystac propagateData i watek z Interactive

	/**
	 * Referencja do strumienia docelowego, do którego maj¹ byæ zapisywane dane.
	 */
	private OutputStream outputStream;
	
	/**
	 * Konstruktor.
	 * @param OutputStream outputStream - strumieñ wyjœciowy, na który bêd¹ zapisywane dane.
	 */
	public StreamWriter(OutputStream outputStream) {
		this.outputStream = outputStream;
	}
	
	/**
	 * Abstrakcyjna metoda, która zapisuje podany w parametrze pakiet danych
	 * do strumienia. 
	 * @param Data data - dane do zapisania
	 * @throws Exception
	 */
	protected abstract void writeDataPacketToStream(Data data) throws Exception;
	
	/**
	 * Prze³adowana metoda dodawania targetów - nie robi nic, bo StreamWriter nie u¿ywa targetów. 
	 */
	public void addTarget(Interactive target) {
		// nie ma sensu target dla writera - j.w.
	}
	
	/**
	 * nic nie zmienia. TODO pomyslec nad tym
	 */
	@Override
	protected Data processData(Data data)
	{
		return data;
	}
	
	/**
	 * Przeladowana metoda do propagowania danych - wysyla na strumien.
	 * @throws Exception 
	 */
	@Override
	protected void propagateData(Data data) throws Exception
	{
		this.writeDataPacketToStream(data);
	}
}
