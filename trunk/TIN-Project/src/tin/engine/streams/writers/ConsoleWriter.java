/**
 * 
 */
package tin.engine.streams.writers;

import java.io.OutputStream;

import tin.engine.data.Data;
import tin.engine.exceptions.TINException;

/**
 * @author zby
 *
 */
public class ConsoleWriter extends WriterWithID
{

	/**
	 * @param outputStream
	 * @throws TINException
	 */
	public ConsoleWriter(OutputStream outputStream) throws TINException
	{
		super(outputStream);
		throw new TINException("This constructor is illegal in this class");
	}

	/**
	 * @param outputStream
	 * @param id
	 */
	public ConsoleWriter(OutputStream outputStream, int id)
	{
		super(outputStream, id);
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see tin.engine.streams.writers.WriterWithID#writeDataPacketToStream(tin.engine.data.Data)
	 */
	@Override
	protected void writeDataPacketToStream(Data data) throws Exception
	{
		// TODO Auto-generated method stub

	}

}
