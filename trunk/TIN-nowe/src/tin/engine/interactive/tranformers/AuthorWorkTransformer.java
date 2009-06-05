/**
 * 
 */
package tin.engine.interactive.tranformers;

import java.util.StringTokenizer;

import tin.engine.data.Data;
import tin.engine.exceptions.TINException;
import tin.engine.interactive.Interactive;

/**
 * @author zby
 *
 */
public class AuthorWorkTransformer extends Interactive {

	/* (non-Javadoc)
	 * @see tin.engine.interactive.Interactive#processData(tin.engine.data.Data)
	 */
	@Override
	protected Data processData(Data data) {
		try {
			StringTokenizer st = new StringTokenizer((String)data.getData("line"), ":");
			Data d = new Data();
			StringBuffer temp = new StringBuffer();
			d.addObject("author", st.nextToken());
			while (st.hasMoreTokens()) { // TODO: zmodyfikowa³em to tak, ¿eby mo¿na by³o napisaæ: "szaman:elo:zielo:melo:pelo", nie dziala nadal z podwojnym dwukropkiem ;p 
				temp.append(st.nextToken());
				if (st.hasMoreTokens()) temp.append(':');
			}
			d.addObject("data", temp.toString());
			return d;
		} catch (TINException e) {
			e.printStackTrace();
		}
		return new Data();
	}

	/* (non-Javadoc)
	 * @see tin.engine.interactive.Interactive#propagateData(tin.engine.data.Data)
	 */
	@Override
	protected void propagateData(Data data) throws Exception {
		if (targets.size() == 0) throw new TINException("No targets added.");
		for (Interactive target : targets) {
			target.addToBuffer(data); /* dodajemy dane na koniec kolejki FIFO obiektu docelowego */
		}
	}

}
