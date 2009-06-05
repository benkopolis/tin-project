/**
 * 
 */
package tin.engine.interactive.decoders;

import java.util.HashMap;
import java.util.Vector;

import tin.engine.data.Data;
import tin.engine.exceptions.TINException;
import tin.engine.interactive.Interactive;

/**
 * Jest to dekoder - ma jedno wejscie i wysla dane na jedno z wielu wyjsc.
 * Jest klas¹ generyczn¹ - kluczem mo¿e byæ dowolny obiekt.
 */
public final class Decoder<T> extends Interactive
{

	
	/**
	 * mapa z wektorami targetow
	 */
	private HashMap<T, Vector<Interactive>> targetsMap = new HashMap<T, Vector<Interactive>>();
	
	private T key = null;
	
	/**
	 * 
	 */
	public Decoder()
	{
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see tin.engine.interactive.Interactive#processData(tin.engine.data.Data)
	 */
	@SuppressWarnings("unchecked")
	@Override
	protected Data processData(Data data)
	{
		Data d = new Data();
		try
		{
			key = ((T)data.getData("target"));
			d.addObject("data", data.getData("data"));
			
		} catch (TINException e)
		{
			Data error = new Data();
			try
			{
				error.addObject("cause", "Can not find \"target\" in Data.");
				sendError(error);
			} catch (TINException e1)
			{
				e1.printStackTrace();
			}
			return null;
		}
		return d;
	}

	/* (non-Javadoc)
	 * @see tin.engine.interactive.Interactive#propagateData(tin.engine.data.Data)
	 */
	@Override
	protected void propagateData(Data data) throws Exception
	{
		if (data == null) return;
		for (Interactive i : targetsMap.get(key)) {
			i.addToBuffer(data);
		}
	}

}
