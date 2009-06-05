/**
 * 
 */
package tin.engine.interactive.tranformers;

import java.awt.TrayIcon.MessageType;
import java.util.StringTokenizer;

import tin.engine.data.Data;
import tin.engine.data.MessageTypes;
import tin.engine.exceptions.TINException;
import tin.engine.interactive.Interactive;
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
public class InfoFromDataExtractor extends RawTransformer {

	/**
	 * 
	 */
	public InfoFromDataExtractor() {
		// TODO Auto-generated constructor stub
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
				String tmp, text;
				TargetType tt;
				MessageTypes mt = MessageTypes.Normal;
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
					}
					
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
			}
			
		}
		return data;
	}

	private String extractText(String tmp) throws TINException {
		if(tmp != null) {
			if(!tmp.equals("rc") && !tmp.equals("nt") && !tmp.equals("at") && !tmp.equals("dc"))
				throw new TINException("Invalid system message text");
		}
		return tmp;
	}

	/**
	 * @param tmp
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
