/**
 * 
 */
package tin.engine.streams.writers;

import java.io.OutputStream;
import java.io.PrintWriter;

import tin.engine.data.Data;
import tin.engine.exceptions.NullPointerTINException;
import tin.engine.exceptions.TINException;

/**
 * @author zby
 *
 * Klasa Writer która dostaje nazwe - metoda pobierająca nazwe jest protected
 */
public class NamedWriter extends WriterWithID {

	/**
	 * nazwa przpisana dla writera
	 */
	private String name;
	
	/**
	 * wirter ma printera - moze drukowac
	 */
	protected PrintWriter printer;
	
	/**
	 * Niedozwolony kontruktor
	 * @param outputStream
	 * @throws TINException
	 */
	public NamedWriter(OutputStream outputStream) throws TINException 
	{
		super(outputStream);
		throw new TINException("This constructor is illegal for this class");
	}
	
	/**
	 * Konstruktor który pobiera nazwe, id i strumien - używa kontruktora klasy bazowej.
	 * Od razu tworzony jest printer na podstawie przekazanego outputStream.
	 * Parametry name i outputStream nie mogą być równe null. 
	 * @param outputStream
	 * @param name
	 * @param id
	 * @throws NullPointerTINException
	 */
	public NamedWriter(OutputStream outputStream, String name, int id) throws NullPointerTINException
	{
		super(outputStream, id);
		if(name == null)
			throw new NullPointerTINException("Param \"name\" for NamedWriter constructor is null");
		this.name = name;
		if(outputStream == null)
			throw new NullPointerTINException("Param \"outputStream\" for NamedWriter constructor is null");
		this.printer = new PrintWriter(outputStream);
	}

	/* (non-Javadoc)
	 * @see tin.engine.streams.writers.StreamWriter#writeDataPacketToStream(tin.engine.data.Data)
	 */
	@Override
	protected void writeDataPacketToStream(Data data) throws Exception
	{
		this.printer.print(this.name + " >> ");
	}
	

}
