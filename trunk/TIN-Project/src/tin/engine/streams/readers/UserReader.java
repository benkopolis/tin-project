/**
 * 
 */
package tin.engine.streams.readers;

import java.io.OutputStream;

import tin.engine.data.Data;
import tin.engine.exceptions.TINException;
import tin.engine.streams.writers.WriterWithID;

/**
 * @author zby
 *
 */
public class UserReader extends WriterWithID
{

	/**
	 * @param outputStream
	 * @throws TINException
	 */
	public UserReader(OutputStream outputStream) throws TINException
	{
		super(outputStream);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param outputStream
	 * @param id
	 */
	public UserReader(OutputStream outputStream, int id)
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
