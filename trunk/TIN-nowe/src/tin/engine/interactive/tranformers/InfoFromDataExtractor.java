/**
 * 
 */
package tin.engine.interactive.tranformers;

import java.util.StringTokenizer;

import tin.engine.data.Data;
import tin.engine.data.MessageTypes;
import tin.engine.exceptions.TINException;
import tin.engine.streams.TargetType;

/**
 * Klasa ta dziedziczy po transformerze, ktory rozsyla niezmieniane dane do swoich celow - dzieki czemu
 * implementacja metosy propagateData() w tej klasie jest bezcelowa. Klasa ta ma z otrzymanej od LineReadera (tylko i 
 * wylacznie typu aplikacyjnego) Daty wyluskac date bezposrednio odczytana z aplikacji i przetransformowac caly pakiet tak,
 * zeby kontekst pakietu aplikacji stal sie kontekstem zewnetrznym i zastapil stary konteskt - ale tylko w przypadku gdy 
 * kontekst zewnetrzny nie jest systemowy - bo reader przekazuje pakiet o kontekscie systemowym tylko w przypadku zakonczenia 
 * sie strumienia - jedyne co trzeba wtedy zrobic to przekazac taki pakiet dalej (w koncu trafi on do SystemMessageHandler, 
 * ktory to wygeneruje dla zaintersowanych aplikacji odpowiedni pakiet).
 */
/**
 * @author zby
 *
 */
public class InfoFromDataExtractor extends RawTransformer {

	/**
	 * 
	 */
	public InfoFromDataExtractor() {
		super();
	}

	/**
	 * Metoda wyluskuje kontekst aplikacji na wierzch. Jezeli pakiet jest zle skonstruowany ze wzgledu na 
	 * zgodnosc z protokolem, to metoda generuje pakiet bledu - tworzy go na podstawie numeru (przerabianego w chwili 
	 * napotkania bledu) kroku i dotychczas wyluskanych informacji.
	 */
	/* (non-Javadoc)
	 * @see tin.engine.interactive.Interactive#processData(tin.engine.data.Data)
	 */
	@Override
	public Data processData(Data data) {
		try {
			if(data.getData("MessageType") == MessageTypes.Normal) {
				StringTokenizer st = new StringTokenizer((String) data.getData("Data"), ":");
				String userInfo;
				String tmp=null, text = null, dane = new String();
				TargetType tt;
				MessageTypes mt = MessageTypes.Normal;
				Integer id, size;
				boolean throwError = false;
				int i=0;
				
				while(st.hasMoreTokens())
				{
					tmp = st.nextToken();
					if(i == 0) {	
						mt = extractMessageType(tmp);
					} else if(i == 1) {
						if(mt == MessageTypes.Normal) {
							tt = extractTargetType(tmp);
						} else if(mt == MessageTypes.System) {
							text = extractText(tmp);
						}
					} else if(i == 2) {
						if(mt == MessageTypes.Normal) {
							size = extractInteger(tmp, "Invalid info size.", 0, Integer.MAX_VALUE);
						} else if(mt == MessageTypes.System) {
							if(text.equals("rc") || text.equals("dc"))
								id = extractInteger(tmp, "Invalid reader ID.", 0, Integer.MAX_VALUE);
						}
					} else if(mt == MessageTypes.Normal && i>2)
						dane = dane + tmp;
					else
						break;
					++i;
				}
			}
		} catch (TINException e) {
			if(e.getMessage().equals("Invalid message type.")) {
				;// TODO wygeneruj pakiet bledu
			} else if(e.getMessage().equals("Invalid target type.")) {
				; // TODO wygeneruj pakiet bledu
			} else if(e.getMessage().equals("Invalid system message text")) {
				; // TODO wygeneruj pakiet bledu
			} else if(e.getMessage().equals("Invalid info size.")) {
				; // TODO wygeneruj pakiet bledu
			} else if(e.getMessage().equals("Invalid reader ID.")) {
				; // TODO wygeneruj pakiet bledu
			}
			
		}
		return data;
	}

	/**
	 * Metoda wyluskuje Integera z podanego stringa - jak sie nie uda to rzuca wyjatek o tresci
	 * podanej w error. Na podstawie lower i upper -Bound sprawdza czy miesci sie w
	 * w wymaganej przez sytuacje dziedzinie.
	 * @param tmp
	 * @param error
	 * @param lowerBound
	 * @param upperBound
	 * @return
	 * @throws TINException 
	 */
	private Integer extractInteger(String tmp, String error, int lowerBound, int upperBound) throws TINException {
		Integer i = null;
		try {
			i = Integer.parseInt(tmp);
			if(i < lowerBound || i > upperBound)
				throw new TINException(error);
		} catch(NumberFormatException e) {
			throw new TINException(error);
		}
		return i;
	}

	/**
	 * Jezeli tekst niezgadza sie z nizej wyspecyfikowanymi to rzuca wyjatek,
	 * w normalnym wypadku zwraca dobry tekst.
	 * @param tmp
	 * @return
	 * @throws TINException
	 */
	private String extractText(String tmp) throws TINException {
		if(tmp != null) {
			if(!tmp.equals("rc") && !tmp.equals("nt") && !tmp.equals("at") && !tmp.equals("dc"))
				throw new TINException("Invalid system message text");
		}
		return tmp;
	}

	/**
	 * Na podstawie podanego parametru probuje ustalic typ celu, jezeli sie uda,
	 * to go zwraca, jezeli nie, to rzuca wyjatkiem.
	 * @param tmp
	 * @return
	 */
	private TargetType extractTargetType(String tmp) throws TINException {
		TargetType tt = null;
		try {
			int val = Integer.parseInt(tmp);
			if(val == -1)
				tt = TargetType.User;
			else if(val >= 0)
				tt = TargetType.Socket;
			else 
				throw new TINException("Invalid target type.");
		} catch(NumberFormatException e) {
			if(tmp.equals("*"))
				tt = TargetType.Socket;
		}
		return tt;
	}

	/**
	 * Na podstawie podanego patrametru prubuje ustalic typ wiadomosci, jezeli sie 
	 * uda to zwraca odpowiednia wartosc enumeracji, jezeli nie to rzuca wyjatek
	 * @param tmp
	 * @param mt
	 * @return
	 */
	private MessageTypes extractMessageType(String tmp) throws TINException {
		MessageTypes mt;
		if(tmp.equals("N"))
			mt = MessageTypes.Normal;
		else if(tmp.equals("S"))
			mt = MessageTypes.System;
		else
			throw new TINException("Invalid message type.");
		return mt;
	}

}
