/**
 * 
 */
package tin.engine.streams.readers;

import java.io.InputStream;

import tin.engine.data.Data;
import tin.engine.exceptions.TINException;

/**
 * @author zby
 *
 */
public class ConsoleReader extends ReaderWithID
{

	/**
	 * @param inputStream
	 * @throws TINException
	 */
	public ConsoleReader(InputStream inputStream) throws TINException
	{
		super(inputStream);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param inputStream
	 * @param id
	 */
	public ConsoleReader(InputStream inputStream, int id)
	{
		super(inputStream, id);
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see tin.engine.streams.readers.ReaderWithID#readDataPacketFromStream()
	 */
	@Override
	protected Data readDataPacketFromStream() throws Exception
	{
		// TODO Auto-generated method stub
		return null;
	}

}
