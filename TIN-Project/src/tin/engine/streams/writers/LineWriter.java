/**
 * 
 */
package tin.engine.streams.writers;

import java.io.OutputStream;
import java.io.PrintWriter;

import tin.engine.data.Data;
import tin.engine.exceptions.TINException;

/**
 * Klasa, która do bufora otrzymuje dane postaci:<br>
 * <b>Data:</b><br>
 * &nbsp;&nbsp;&nbsp;"line":[String] - dane do zapisania<br>
 * i zapisuje je do strumienia w postaci linii tekstu.
 */
public class LineWriter extends StreamWriter {

	/**
	 * Jest to obiekt, który bêdzie wykorzystywany do zapisu linii tekstu do strumienia.
	 */
	private PrintWriter printWriter;
	
	/**
	 * Konstruktor.
	 * @param outputStream
	 */
	public LineWriter(OutputStream outputStream) {
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
			String s = (String) data.getData("line");
			printWriter.println(s);
			printWriter.flush();
		} catch (TINException e) {
			throw new TINException("Invalid data packet (no 'line' attribute).");
		}
		//System.out.println("Linewriter wypisuje cos, ale nie to co mu kazalizmy");
	}


}
