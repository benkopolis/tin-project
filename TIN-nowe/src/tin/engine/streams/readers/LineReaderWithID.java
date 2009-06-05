/**
 * 
 */
package tin.engine.streams.readers;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import tin.engine.data.Data;
import tin.engine.data.DataType;
import tin.engine.data.MessageTypes;
import tin.engine.data.Reason;
import tin.engine.exceptions.TINException;

/**
 * @author zby
 *
 */
public class LineReaderWithID<ID> extends ReaderWithID {

	/**
	 * 
	 */
	private ReaderType type;

	private BufferedReader buffReader;
	
	/**
	 * @param inputStream
	 * @throws TINException
	 */
	public LineReaderWithID(InputStream inputStream) throws TINException {
		super(inputStream);
		throw new TINException("This constructor in illegal for this class!");
	}
	
	/**
	 * @param inputStream
	 * @param id
	 * @throws TINException
	 */
	public LineReaderWithID(InputStream inputStream, ID id) throws TINException {
		super(inputStream);
		throw new TINException("This constructor in illegal for this class!");
	}

	/**
	 * @param inputStream
	 * @param id
	 * @param type
	 */
	public LineReaderWithID(InputStream inputStream, ID id, ReaderType type) {
		super(inputStream, id);
		this.type = type;
		this.buffReader = new BufferedReader(new InputStreamReader(inputStream));
	}

	/**
	 * Funkcja czyta pakiety ze strumienia. Po otrzymaniu pakietu ze strumienia decyduje co zrobic - czy nadac mu status
	 * systemowy czy normalny - i w zaleznosci od tego kontruowana jest odpowiednia paczka Danych (opisana w dokumentacji).
	 * @note pakiet systemowy moze byc tez wyslany przez aplikacje - nasz system na tym poziomie (czyli na poziomie Readera) 
	 * nie ingeruje w jego zawartosc - bedzie to rozpatrywane w dalszej czesci sieci przeplywowej.
	 */
	/* (non-Javadoc)
	 * @see tin.engine.streams.readers.ReaderWithID#readDataPacketFromStream()
	 */
	@Override
	protected Data readDataPacketFromStream() throws Exception {
		Data d = new Data();
		Data d2 = new Data();
		String s = buffReader.readLine();
		d.addObject("ReaderId", readerID);
		d.addObject("ReaderType", type);
		if (s != null) 
		{ // tworzymy paczke normalna
			d.addObject("MessageType", MessageTypes.Normal);
			d.addObject("Data", d2);
			d2.addObject("DataType", DataType.Text);
			d2.addObject("DataLenght", new Integer(s.length()));
			d2.addObject("Base64Encoded", new Boolean(false));
			d2.addObject("Data", s);
			return d;
		} else 
		{ // tworzymy paczke systemowa - wiadomosc dla odpowiednich aplikacji bedzie generowana przez SystemMessageHandlera
			d.addObject("MessageType", MessageTypes.Normal);
			d.addObject("Data", null); 
			d.addObject("MessageContent", d2);
			if(type == ReaderType.ApplicationReader) 
				d2.addObject("Reason", Reason.ApplicationTerminated);
			 else if(type == ReaderType.SocketReader)
				d2.addObject("Reason", Reason.ConnectionClosed);
			else if(type == ReaderType.UserInputReader)
				d2.addObject("Reason", Reason.Unknown);
			d2.addObject("MessageText", "StreamClosed"); // TODO zamkniety strumien tekst wiadomosci
			return d;
		}
	}

}
