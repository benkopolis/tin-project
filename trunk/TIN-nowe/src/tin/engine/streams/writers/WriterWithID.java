package tin.engine.streams.writers;

import java.io.OutputStream;

import tin.engine.data.Data;
import tin.engine.exceptions.TINException;

/**
 * 
 * @author zby
 *
 * Klasa ta to zwykły writer ze swoim własnym id - tworząc go, trzeba je ustalić
 */
public abstract class WriterWithID extends StreamWriter
{
	/**
	 * Id writera
	 */
	private int writerID;

	/**
	 * Niedozwolony kostruktor
	 * @param outputStream
	 * @throws TINException
	 */
	public WriterWithID(OutputStream outputStream) throws TINException
	{
		super(outputStream);
		throw new TINException("This constructor is illegal for this class");
	}

	/**
	 * Konstriktor dozwolony - trzeba podac odgornie id
	 * @param outputStream
	 * @param id
	 */
	public WriterWithID(OutputStream outputStream, int id)
	{
		super(outputStream);
		this.writerID = id;
	}
	

	/**
	 * Metoda abstrakcyjna - tak jak u klasy bazowej dla tej klasy
	 * @see tin.engine.streams.writers.StreamWriter
	 */
	protected abstract void writeDataPacketToStream(Data data) throws Exception;
	
	/**
	 * pobeira id writera
	 * @return
	 */
	protected synchronized int getId()
	{
		return writerID;
	}
	
	/**
	 * ustawia id writera
	 * @param id
	 */
	protected synchronized void setId(int id)
	{
		this.writerID = id;
	}
	
}
