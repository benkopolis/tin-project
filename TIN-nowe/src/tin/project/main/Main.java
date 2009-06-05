/**
 * 
 */
package tin.project.main;


import java.util.HashMap;
import java.util.Vector;

import tin.engine.data.MessageTypes;
import tin.engine.interactive.Interactive;
import tin.engine.interactive.collectors.HashMapCollector;
import tin.engine.interactive.decoders.Decoder;
import tin.engine.interactive.tranformers.InfoFromDataExtractor;
import tin.engine.streams.TargetType;
import tin.engine.interactive.collectors.SearchableMap;
import tin.engine.interactive.tranformers.DataUnpackerTransformer;
import tin.engine.interactive.tranformers.RawTransformer;
import tin.engine.streams.writers.DataWriter;
import tin.engine.streams.writers.LineWriter;
import tin.engine.streams.writers.LogWriter;
import tin.engine.streams.writers.UnpackedDataWriter;
import tin.engine.interactive.tranformers.AuthorWorkTransformer;
import tin.project.main.menu.Menu;

/**
 * @author Szaman
 *
 */
public class Main {
	
	private static HashMap<String, Interactive> graphElements;
	
	public static void makeFlowGraph()
	{
		// kolektor z elementami czytajacymi
		HashMapCollector<Integer, LineReaderWithID> readersCollector = new HashMapCollector<Integer, LineReaderWithID>();
		// kolektor z elementami piszacymi
		HashMapCollector<Integer, LineWriterWithID> writersCollector = new HashMapCollector<Integer, LineWriterWithID>();
		// ekstraktor danych wygenerowanych przez aplikacje
		InfoFromDataExtractor infoExtractor = new InfoFromDataExtractor();
		// decoder kierujacy ruchem na podstawie typu komunikatu
		Decoder<MessageTypes> messageTypeDecoder = new Decoder<String>();
		// handler obslugujacy komunikaty bledow
		ErrorHandler errorHandler = new ErrorHandler();
		// handler obslugujacy komunikaty systemowe
		SystemMessagesHandler sysMsgHandler = new SystemMessagesHandler();
		// warunkowy dekoder z Base64
		CoditionalBase64Decoder condB64Decoder = new ConditionalBase64Decoder();
		// decoder kierujacy ruchem na podstawie typu celu pakietu
		Decoder<TargetType> targetTypeDecoder = new Decoder<TargetType>();
		// decoder wysylajacy dane na odpowiednia konsole uzytkownika
		Decoder<Integer> userOutputDecoder = new Decoder<Integer>();
		// decoder wysylajacy dane do odpowiedniej aplikacji
		Decoder<Integer> appOutputDecoder = new Decoder<Integer>();
		// decoder wysylajacy dane do odpowiedniego gniazda sieciowego
		Decoder<Integer> socketOutputDecoder = new Decoder<Integer>();
		// koder Base64
		Base64Encoder b64Encoder = new Base64Encoder();
		
		graphElements.put("readersCollector", readersCollector);
		graphElements.put("writersCollector", writersCollector);
		graphElements.put("infoExtractor", infoExtractor);
		graphElements.put("messageTypeDecoder", messageTypeDecoder);
		graphElements.put("errorHandler", errorHandler);
		graphElements.put("sysMsgHandler", sysMsgHandler);
		graphElements.put("condB64Decoder", condB64Decoder);
		graphElements.put("targetTypeDecoder", targetTypeDecoder);
		graphElements.put("userOutputDecoder", userOutputDecoder);
		graphElements.put("appOutputDecoder", appOutputDecoder);
		graphElements.put("socketOutputDecoder", socketOutputDecoder);
		graphElements.put("b64Encoder", b64Encoder);
		
		infoExtractor.addTarget(messageTypeDecoder);
		messageTypeDecoder.addTarget(MessageTypes.Normal, condB64Decoder);
		messageTypeDecoder.addTarget(MessageTypes.System, sysMsgHandler);
		condB64Decoder.addTarget(targetTypeDecoder);
		targetTypeDecoder.addTarget(TargetType.User, userOutputDecoder);
		targetTypeDecoder.addTarget(TargetType.Application, appOutputDecoder);
		targetTypeDecoder.addTarget(TargetType.Socket, b64Encoder);
		b64Encoder.addTarget(socketOutputDecoder);
		
		infoExtractor.addErrorTarget(errorHandler);
		messageTypeDecoder.addErrorTarget(errorHandler);
		condB64Decoder.addErrorTarget(errorHandler);
		sysMsgHandler.addErrorTarget(errorHandler);
		targetTypeDecoder.addErrorTarget(errorHandler);
		b64Encoder.addErrorTarget(errorHandler);
		socketOutputDecoder.addErrorTarget(errorHandler);
		appOutputDecoder.addErrorTarget(errorHandler);
		userOutputDecoder.addErrorTarget(errorHandler);
		
		
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Menu m = new Menu();
		makeFlowGraph();
	}

}
