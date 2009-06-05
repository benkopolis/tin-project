/**
 * 
 */
package tin.engine.streams.writers;

import java.io.OutputStream;

import tin.engine.data.Data;
import tin.engine.exceptions.TINException;

/**
 * Klasa pisz¹ca na socketa
 *
 */
public class SocketWriter extends WriterWithID
{

	/**
	 * @param outputStream
	 * @throws TINException
	 */
	public SocketWriter(OutputStream outputStream) throws TINException
	{
		super(outputStream);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param outputStream
	 * @param id
	 */
	public SocketWriter(OutputStream outputStream, int id)
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
