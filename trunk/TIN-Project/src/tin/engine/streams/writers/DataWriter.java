/**
 * 
 */
package tin.engine.streams.writers;

import java.io.OutputStream;
import java.io.PrintWriter;

import tin.engine.data.Data;

/**
 * 
 *
 */
public class DataWriter extends StreamWriter
{

	protected PrintWriter printer;
	
	/**
	 * @param outputStream
	 */
	public DataWriter(OutputStream outputStream)
	{
		super(outputStream);
		this.printer = new PrintWriter(outputStream);
	}

	/* (non-Javadoc)
	 * @see tin.engine.streams.writers.StreamWriter#writeDataPacketToStream(tin.engine.data.Data)
	 */
	@Override
	protected void writeDataPacketToStream(Data data) throws Exception
	{
		this.printer.print(data.toString());
		this.printer.flush();
	}

}
