/**
 * 
 */
package tin.engine.streams.readers;

import java.io.InputStream;

import tin.engine.data.Data;
import tin.engine.exceptions.TINException;

/**
 * Abstrakcyjna klasa bazowa dla Readerow, ktore musza dodawac do produkowanych paczek
 * danych swoj wlasny numer ID, ktory jest Readerowi nadawany podczas tworzenia.
 */
public abstract class ReaderWithID<ID> extends StreamReader {

	/**
	 * Numer ID nadawany instancji Readera podczas tworzenia.
	 */
	protected ID readerID;
	
	/**
	 * Ten konstruktor nie moze byc wywolywany, bo nie przekazuje numeru ID.
	 * @param inputStream
	 * @throws TINException
	 */
	public ReaderWithID(InputStream inputStream) throws TINException {
		super(inputStream);
		throw new TINException("This constructor can not be used in this class.");
	}
	
	/**
	 * @param InputStream inputStream - strumie� wej�ciowy
	 * @param int id - unikalny numer ID nadawany instancji Readera
	 */
	public ReaderWithID(InputStream inputStream, ID id) {
		super(inputStream);
		readerID = id;
	}

	/* (non-Javadoc)
	 * @see tin.engine.streams.readers.StreamReader#readDataPacketFromStream()
	 */
	@Override
	protected abstract Data readDataPacketFromStream() throws Exception;

}
