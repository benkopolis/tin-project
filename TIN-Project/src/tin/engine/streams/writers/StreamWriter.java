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
 * Abstrakcyjna klasa bazowa dla klas zapisuj�cych dane do strumieni.
 */
public abstract class StreamWriter extends Interactive {
	// TODO: byc moze nalezy wywalic to write... i ten watek i wykorzystac propagateData i watek z Interactive

	/**
	 * Referencja do strumienia docelowego, do kt�rego maj� by� zapisywane dane.
	 */
	private OutputStream outputStream;
	
	/**
	 * Konstruktor.
	 * @param OutputStream outputStream - strumie� wyj�ciowy, na kt�ry b�d� zapisywane dane.
	 */
	public StreamWriter(OutputStream outputStream) {
		this.outputStream = outputStream;
	}
	
	/**
	 * Abstrakcyjna metoda, kt�ra zapisuje podany w parametrze pakiet danych
	 * do strumienia. 
	 * @param Data data - dane do zapisania
	 * @throws Exception
	 */
	protected abstract void writeDataPacketToStream(Data data) throws Exception;
	
	/**
	 * Prze�adowana metoda dodawania target�w - nie robi nic, bo StreamWriter nie u�ywa target�w. 
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
