/**
 * 
 */
package tin.engine.streams.writers;

import java.io.OutputStream;

import tin.engine.data.Data;
import tin.engine.exceptions.NullPointerTINException;

/**
 * Writer wypisuj�cy do strumienia dane z pakietu danych rozpakowanego przez "DataUnpackerTransformer".
 */
public class UnpackedDataWriter extends NamedWriter {

	/**
	 * @param outputStream
	 */
	public UnpackedDataWriter(OutputStream outputStream, String name, int id) throws NullPointerTINException 
	{
		super(outputStream, name, id);	
	}

	/* (non-Javadoc)
	 * @see tin.engine.streams.writers.StreamWriter#writeDataPacketToStream(tin.engine.data.Data)
	 */
	@Override
	protected void writeDataPacketToStream(Data data) throws Exception 
	{
		super.writeDataPacketToStream(data);
		Object obj = null;
		if(data.checkPresenceOfAttribute("data"))
		{
				obj = data.getData("data");
			if(obj.getClass().getName() == "tin.engine.data.Data")
				printer.println(((Data)obj).getData("data"));
			else
				printer.println(obj);
		} else printer.println(data.getData("message content") + " chujowy printer");
		printer.flush();
	}

}

