/**
 * 
 */
package tin.engine.streams.writers;

import java.io.OutputStream;
import java.io.PrintWriter;

import tin.engine.data.Data;
import tin.engine.exceptions.TINException;

/**
 * @author zby
 *
 */
public class LogWriter extends StreamWriter {

	/**
	 * Jest to obiekt, który bêdzie wykorzystywany do zapisu linii tekstu do strumienia.
	 */
	private PrintWriter printWriter;
	
	/**
	 * Konstruktor.
	 * @param outputStream
	 */
	public LogWriter(OutputStream outputStream) {
		super(outputStream);
		printWriter = new PrintWriter(outputStream);
	}

	/* (non-Javadoc)
	 * @see tin.engine.streams.writers.StreamWriter#writeDataPacketToStream(tin.engine.data.Data)
	 */
	@Override
	protected void writeDataPacketToStream(Data data) throws TINException {
		if (data == null) throw new TINException("Data can not be null.");
		try {
			String s1 = (String) data.getData("author");
			String s2 = (String) data.getData("data");
			printWriter.println(s1 + " napisal "+ s2);
			printWriter.flush();
		} catch (TINException e) {
			throw new TINException("Invalid data packet (no 'author' or 'data' attribute).");
		}
		//System.out.println("Linewriter wypisuje cos, ale nie to co mu kazalizmy");
	}

}
