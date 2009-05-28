/**
 * 
 */
package tin.engine.interactive.tranformers;

import tin.engine.data.Data;
import tin.engine.exceptions.TINException;
import tin.engine.interactive.Interactive;

/**
 * Transformer s�u��cy do wypakowania klasy Data (z ID) z Data.content i przes�ania jej dalej.
 * <br>Pobiera i zwraca:<br>
 * Data z "message type" (musi byc rowny "Data") i zwraca Data.attribs["content"] (typu Data).
 */
public class DataUnpackerTransformer extends Interactive {

	/**
	 * Konstruktor.
	 */
	public DataUnpackerTransformer() {

	}

	/* (non-Javadoc)
	 * @see tin.engine.interactive.Interactive#processData(tin.engine.data.Data)
	 */
	@Override
	protected Data processData(Data data) {
		try {
			if (  (  (String)(data.getData("message type"))  ).equals("Data") == true   ) {
				Data d = (Data) data.getData("message content");
				return d;
			} else
			if (  (  (String)(data.getData("message type"))  ).equals("System") == true   ) {
				return data;
			} 				
		} catch (TINException e) 
		{
			System.out.println(e.getMessage());
			e.printStackTrace();
			return null; // TODO ?
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see tin.engine.interactive.Interactive#propagateData(tin.engine.data.Data)
	 */
	@Override
	protected void propagateData(Data data) throws Exception {
		if (data == null) throw new TINException("Data packet not correct.");
		for (Interactive i : targets) {
			i.addToBuffer(data);
		}
	}

}
