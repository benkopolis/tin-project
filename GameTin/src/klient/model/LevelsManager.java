/**
 * 
 */
package klient.model;

import java.io.BufferedReader;
//import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Vector;

import klient.model.fields.Coin;
import klient.model.fields.Field;
import klient.model.fields.Grass;
import klient.model.fields.Wall;



/**
 * Klasa udost�pniaj�ca poziom gry. Umo�liwia za�adowanie go 
 * z zewn�trznego pliku, jak i utworzenie w czasie dzia�ania aplikacji.
 * <br><br>
 * Jest zbudowany w oparciu o wzorzec singletone'a.
 * <br><br>
 * Nie jest on "thread-safe".
 * @author Szaman
 *
 */
public class LevelsManager {
	
	/**
	 * Tablica dwuwymiarowa przechowuj�ca plansz�.
	 */
	private Field[][] level;
	
	/**
	 * Szeroko�� planszy.
	 */
	private int width;	
	/**
	 * Wysoko�� planszy.
	 */
	private int height;
	
	/**
	 * Singletonowa instancja obiektu.
	 */
	private static LevelsManager instance = null;
	
	/**
	 * Prywatny (ze wzgl�du na singleton) konstruktor klasy.
	 */
	private LevelsManager() {
		createNewLevel(10, 10, true);
	}
	
	/**
	 * Singletonowy geter instancji obiektu.
	 * @return instancja obiektu LevelsManager
	 */
	public static LevelsManager getInstance() {
		if (instance == null) instance = new LevelsManager();
		return instance;
	}
	
	/**
	 * Tworzy now� plansz� (usuwaj�c star�, je�li by�a za�adowana do pami�ci).
	 * @param width - szeroko�� (w polach)
	 * @param height - wysoko�� (w polach)
	 * @param borders - czy nale�y od razu utworzy� obramowanie ?
	 */
	public void createNewLevel(int width, int height, boolean borders) {
		level = new Field[width][height];
		this.width = width;
		this.height = height;
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				if ((borders == true) && ((x == 0) || (y == 0) || (x == width-1) || (y == height-1))) {
					level[x][y] = new Wall();
				} else {
					level[x][y] = new Grass();
				}
			}
		}
	}
	
	/**
	 * Zwraca plansz� w postaci tekstowej.
	 * @return String - zapis planszy w postaci tekstowej
	 * @throws IllegalOperation - gdy poziom nie zosta� utworzony/wczytany 
	 */
	public String levelToString(boolean showColsAndRows) throws IllegalOperation {
		if (level == null) throw new IllegalOperation("Level is not created !");
		StringBuffer sb = new StringBuffer();
		if (showColsAndRows == true) {
			sb.append("\t ");
			for (int x = 0; x < getWidth(); x++)
				sb.append("[" + x + "\t]");
			
			sb.append("\n");
			for (int y = 0; y < getHeight(); y++) {
				sb.append("" + y + "\t");
				for (int x = 0; x < getWidth(); x++) {
					sb.append("  " + level[x][y].getSign() + "\t");
				}	
				sb.append("\n");
			}
		} else {
			 for (int y = 0; y < getHeight(); y++) {
				for (int x = 0; x < getWidth(); x++) {
					sb.append(level[x][y].getSign());
				}	
				sb.append("\n");
			}
		}
		return sb.toString();
	}
	
	/**
	 * Metoda, kt�ra zapisuje zawarto�� planszy do Stringa, kt�ry nadaje si�
	 * bezpo�rednio do wys�ania przez sie�.
	 * <br><br>
	 * Format: <br>
	 * szeroko��;wysoko��;kolejne pola zapisane wierszami od 0 do getHeight()-1;
	 * @return String
	 * @throws IllegalOperation - gdy plansza nie jest utworzona
	 */
	public String levelToMessage() throws IllegalOperation {
		StringBuffer sb = new StringBuffer();
		
		sb.append(this.getWidth()); sb.append(';');
		sb.append(this.getHeight()); sb.append(';');
		for (int y = 0; y < getHeight(); y++) {
			for (int x = 0; x < getWidth(); x++) {
				sb.append(level[x][y].getSign());
			}
		}
		sb.append(';');
		
		return sb.toString();
	}
	
	/**
	 * Wczytuje zawarto�� planszy ze stringa wygenerowanego poprzez u�ycie metody levelToMessage().
	 * @param String message - string z zapisana plansza
	 * @throws IllegalOperation 
	 */
	public void loadFromMessage(String message) throws IllegalOperation {
		if (message == null) throw new IllegalArgumentException("Message is null.");

		String[] tokens = message.split(";");
		for(String s: tokens)
			System.out.println(s);
		if (tokens == null || tokens.length != 3) throw new IllegalArgumentException("Message is not a valid level.");
		try {
			Integer.parseInt(tokens[0]);
			Integer.parseInt(tokens[1]);
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException("Message contains invalid width/height values.");
		}
		level = new Field[Integer.parseInt(tokens[0])][Integer.parseInt(tokens[1])];
		width = Integer.parseInt(tokens[0]);
		height = Integer.parseInt(tokens[1]);
		for (int y = 0; y < getHeight(); y++) {
			for (int x = 0; x < getWidth(); x++) {
				switch (tokens[2].charAt(y*getWidth() + x)) {
				case '#':
					level[x][y] = new Wall();
					break;
				case '.': 
					level[x][y] = new Grass();
					break;
				case '$':
					level[x][y] = new Coin();
					break;
				default:
					System.err.println("Char \'" + tokens[2].charAt(y*getWidth() +x) + "\' at position " + y*getWidth()+x + " is not a valid field type !");
					level[x][y] = new Grass();
					break;
				}
			}				
		}
	}

	/**
	 * @return wysoko�� planszy
	 * @throws IllegalOperation - gdy poziom nie zosta� utworzony/wczytany
	 */
	public int getHeight() throws IllegalOperation {
		if (level == null) throw new IllegalOperation("Level is not created !");
		return height;
	}

	/**
	 * @return szeroko�� planszy
	 * @throws IllegalOperation - gdy poziom nie zosta� utworzony/wczytany
	 */
	public int getWidth() throws IllegalOperation {
		if (level == null) throw new IllegalOperation("Level is not created !");
		return width;
	}
	
	/**
	 * Umo�liwia pobranie pola o wsp�rz�dnych podanych w argumentach.
	 * @param x - wsp. X
	 * @param y - wsp. Y
	 * @param loop - czy wsp�rz�dne nale�y najpierw "potraktowa�": mod width (mod height) ?
	 * @return pole z planszy
	 * @throws IllegalOperation - gdy plansza nie zosta�a utworzona, bad� nie istnieje pole o okre�lonych wsp�rz�dnych
	 */
	public Field getField(int x, int y, boolean loop) throws IllegalOperation {
		//if (level == null) throw new IllegalOperation("Level is not created !");
		
		if (loop == true) {
			x = x % getWidth();
			y = y % getHeight();
		}
		
		if ((x < 0) || (x >= getWidth()) || (y < 0) || (y >= getHeight())) throw new IllegalOperation("Bad coordinates: [" + x + "," + y + "]. Widht = " + getWidth() + ", height = " + getHeight() + ".");
		
		return level[x][y];
	}
	
	/**
	 * Ustawia pole na planszy.
	 * @param x - wsp. X
	 * @param y - wsp. Y
	 * @param field - rodzaj pola (�ciana, trawa, etc.)
	 * @throws IllegalOperation - gdy field == null, gdy poziom nie zosta� utworzony/za�adowany, gdy podano z�e wsp�rz�dne.
	 */
	public void setField(int x, int y, Field field) throws IllegalOperation {
		if (field == null) throw new IllegalOperation("Field can not be null !");
		if (level == null) throw new IllegalOperation("Level is not created !");
		
		if ((x < 0) || (x >= getWidth()) || (y < 0) || (y >= getHeight())) throw new IllegalOperation("Bad coordinates: [" + x + "," + y + "]. Widht = " + getWidth() + ", height = " + getHeight() + ".");
		
		level[x][y] = field;
	}
	
	/**
	 * �aduje level z pliku tekstowego.
	 * @param filename - �cie�ka do pliku
	 * @throws FileNotFoundException - gdy okre�lonego pliku nie mo�na znale��
	 * @throws IOException - gdy wyst�pi� b��d w readLine()
	 */
	public void loadFromFile(String filename) throws FileNotFoundException, IOException {
		FileReader fr = new FileReader (filename);//new File("SampleLevel.txt"));
		BufferedReader br = new BufferedReader (fr);
		
		int lineNr = 0;
		int rowLength = 0;
		Vector<Field[]> tempLevel = new Vector<Field[]>(); // tymczasowa tablica pol
		Field[] temp;
		String line = br.readLine();
		while (line != null) {
			if (lineNr == 0) { // wczytywanie szeroko�ci planszy
				rowLength = line.length();
			} else {
				if (line.length() != rowLength) {
					System.err.println("Line " + lineNr + " has invalid length. Line ignored.");
					line = br.readLine();
					continue;
				}
			}
			temp = new Field[rowLength];
			for (int i = 0; i < rowLength; i++) {
				switch (line.charAt(i)) {
				case '#':
					temp[i] = new Wall();
					break;
				case '.': 
					temp[i] = new Grass();
					break;
				case '$':
					temp[i] = new Coin();
					break;
				default:
					System.err.println("Line " + lineNr + ": Char \'" + line.charAt(i) + "\' at position " + i + " is not a valid field type !");
					temp[i] = new Grass();
					break;
				}
			}
			
			tempLevel.add(temp);
			line = br.readLine();
			lineNr++;
		}
		br.close();
		fr.close();
		//System.out.println("" + tempLevel.size() + " lines loaded.");
		level = new Field[rowLength][tempLevel.size()];
		width = rowLength;
		height = tempLevel.size();
		int y = 0;
		for (Field[] t : tempLevel ) {
			for (int x = 0; x < rowLength; x++) {
				level[x][y] = t[x];
			}
			y++;
		}
		tempLevel.clear();
	}
	
	/**
	 * Zwraca wektor wype�niony pozycjami monet na planszy.
	 * @return
	 */
	public Vector<CoinPosition> getCoinsPositions() {
		Vector<CoinPosition> positions = new Vector<CoinPosition>();
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				if (level[x][y] instanceof Coin) {
					positions.add(new CoinPosition(x,y));
				}
			}
		}
		return positions;
	}
	
	/**
	 * Zmienia wszystkie monety na planszy w traw�.
	 */
	public void changeAllCoinsToGrass() {
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				if (level[x][y] instanceof Coin) {
					level[x][y] = new Grass();
				}
			}
		}		
	}

	public boolean isWallBetweenV(int x, int y, int x2, int y2) {
		int j = 0;
		int end = 0;
		if(y < y2) {
			j = y;
			end = y2;
		} else {
			j = y2;
			end = y;
		}
		for(; j<end; j++) {
			try {
				if(this.getField(x, j, false) instanceof Wall)
					return false;
			} catch (IllegalOperation e) {
			}
		}
		return true;
	}

	public boolean isWallBetweenH(int x, int y, int x2, int y2) {
		int j = 0;
		int end = 0;
		if(x < x2) {
			j = x;
			end = x2;
		} else {
			j = x2;
			end = x;
		}
		for(; j<end; j++) {
			try {
				if(this.getField(j, y, false) instanceof Wall)
					return false;
			} catch (IllegalOperation e) {
			}
		}
		return true;
	}
	
//	public synchronized Field[][] getLevel() {
//		return level;
//	}
}



//package klient.model;
//
//import java.io.BufferedReader;
//import java.io.File;
//import java.io.FileNotFoundException;
//import java.io.FileReader;
//import java.io.IOException;
//import java.util.Vector;
//
//import klient.model.fields.Coin;
//import klient.model.fields.Field;
//import klient.model.fields.Grass;
//import klient.model.fields.Wall;
//
///**
// * Klasa udost�pniaj�ca poziom gry. Umo�liwia za�adowanie go 
// * z zewn�trznego pliku, jak i utworzenie w czasie dzia�ania aplikacji.
// * <br><br>
// * Jest zbudowany w oparciu o wzorzec singletone'a.
// * <br><br>
// * Nie jest on "thread-safe".
// * @author Szaman
// *
// */
//public class LevelsManager {
//	
//	/**
//	 * Tablica dwuwymiarowa przechowuj�ca plansz�.
//	 */
//	private Field[][] level;
//	
//	/**
//	 * Szeroko�� planszy.
//	 */
//	private int width;	
//	/**
//	 * Wysoko�� planszy.
//	 */
//	private int height;
//	
//	/**
//	 * Singletonowa instancja obiektu.
//	 */
//	private static LevelsManager instance = null;
//	
//	/**
//	 * Prywatny (ze wzgl�du na singleton) konstruktor klasy.
//	 */
//	private LevelsManager() {
//		//...
//	}
//	
//	/**
//	 * Singletonowy geter instancji obiektu.
//	 * @return instancja obiektu LevelsManager
//	 */
//	public static LevelsManager getInstance() {
//		if (instance == null) instance = new LevelsManager();
//		return instance;
//	}
//	
//	/**
//	 * Tworzy now� plansz� (usuwaj�c star�, je�li by�a za�adowana do pami�ci).
//	 * @param width - szeroko�� (w polach)
//	 * @param height - wysoko�� (w polach)
//	 * @param borders - czy nale�y od razu utworzy� obramowanie ?
//	 */
//	public void createNewLevel(int width, int height, boolean borders) {
//		level = new Field[width][height];
//		this.width = width;
//		this.height = height;
//		for (int x = 0; x < width; x++) {
//			for (int y = 0; y < height; y++) {
//				if ((borders == true) && ((x == 0) || (y == 0) || (x == width-1) || (y == height-1))) {
//					level[x][y] = new Wall();
//				} else {
//					level[x][y] = new Grass();
//				}
//			}
//		}
//	}
//	
//	/**
//	 * Zwraca plansz� w postaci tekstowej.
//	 * @return String - zapis planszy w postaci tekstowej
//	 * @throws IllegalOperation - gdy poziom nie zosta� utworzony/wczytany 
//	 */
//	public String levelToString(boolean showColsAndRows) throws IllegalOperation {
//		if (level == null) throw new IllegalOperation("Level is not created !");
//		StringBuffer sb = new StringBuffer();
//		if (showColsAndRows == true) {
//			sb.append("\t ");
//			for (int x = 0; x < getWidth(); x++)
//				sb.append("[" + x + "\t]");
//			
//			sb.append("\n");
//			for (int y = 0; y < getHeight(); y++) {
//				sb.append("" + y + "\t");
//				for (int x = 0; x < getWidth(); x++) {
//					sb.append("  " + level[x][y].getSign() + "\t");
//				}	
//				sb.append("\n");
//			}
//		} else {
//			 for (int y = 0; y < getHeight(); y++) {
//				for (int x = 0; x < getWidth(); x++) {
//					sb.append(level[x][y].getSign());
//				}	
//				sb.append("\n");
//			}
//		}
//		return sb.toString();
//	}
//	
//	/**
//	 * Metoda, kt�ra zapisuje zawarto�� planszy do Stringa, kt�ry nadaje si�
//	 * bezpo�rednio do wys�ania przez sie�.
//	 * <br><br>
//	 * Format: <br>
//	 * szeroko��;wysoko��;kolejne pola zapisane wierszami od 0 do getHeight()-1;
//	 * @return String
//	 * @throws IllegalOperation - gdy plansza nie jest utworzona
//	 */
//	public String levelToMessage() throws IllegalOperation {
//		StringBuffer sb = new StringBuffer();
//		
//		sb.append(this.getWidth()); sb.append(';');
//		sb.append(this.getHeight()); sb.append(';');
//		for (int y = 0; y < getHeight(); y++) {
//			for (int x = 0; x < getWidth(); x++) {
//				sb.append(level[x][y].getSign());
//			}
//		}
//		sb.append(';');
//		
//		return sb.toString();
//	}
//	
//	/**
//	 * Wczytuje zawarto�� planszy ze stringa wygenerowanego poprzez u�ycie metody levelToMessage().
//	 * @param String message - string z zapisana plansza
//	 * @throws IllegalOperation 
//	 */
//	public void loadFromMessage(String message) throws IllegalOperation {
//		if (message == null) throw new IllegalArgumentException("Message is null.");
//
//		String[] tokens = message.split(";");
//		if (tokens == null || tokens.length != 3) throw new IllegalArgumentException("Message is not a valid level.");
//		try {
//			Integer.parseInt(tokens[0]);
//			Integer.parseInt(tokens[1]);
//		} catch (NumberFormatException e) {
//			throw new IllegalArgumentException("Message contains invalid width/height values.");
//		}
//		level = new Field[Integer.parseInt(tokens[0])][Integer.parseInt(tokens[1])];
//		width = Integer.parseInt(tokens[0]);
//		height = Integer.parseInt(tokens[1]);
//		for (int y = 0; y < getHeight(); y++) {
//			for (int x = 0; x < getWidth(); x++) {
//				switch (tokens[2].charAt(y*getWidth() + x)) {
//				case '#':
//					level[x][y] = new Wall();
//					break;
//				case '.': 
//					level[x][y] = new Grass();
//					break;
//				case '$':
//					level[x][y] = new Coin();
//					break;
//				default:
//					System.err.println("Char \'" + tokens[2].charAt(y*getWidth() +x) + "\' at position " + y*getWidth()+x + " is not a valid field type !");
//					level[x][y] = new Grass();
//					break;
//				}
//			}				
//		}
//	}
//
//	/**
//	 * @return wysoko�� planszy
//	 * @throws IllegalOperation - gdy poziom nie zosta� utworzony/wczytany
//	 */
//	public int getHeight() throws IllegalOperation {
//		if (level == null) throw new IllegalOperation("Level is not created !");
//		return height;
//	}
//
//	/**
//	 * @return szeroko�� planszy
//	 * @throws IllegalOperation - gdy poziom nie zosta� utworzony/wczytany
//	 */
//	public int getWidth() throws IllegalOperation {
//		if (level == null) throw new IllegalOperation("Level is not created !");
//		return width;
//	}
//	
//	/**
//	 * Umo�liwia pobranie pola o wsp�rz�dnych podanych w argumentach.
//	 * @param x - wsp. X
//	 * @param y - wsp. Y
//	 * @param loop - czy wsp�rz�dne nale�y najpierw "potraktowa�": mod width (mod height) ?
//	 * @return pole z planszy
//	 * @throws IllegalOperation - gdy plansza nie zosta�a utworzona, bad� nie istnieje pole o okre�lonych wsp�rz�dnych
//	 */
//	public Field getField(int x, int y, boolean loop) throws IllegalOperation {
//		if (level == null) throw new IllegalOperation("Level is not created !");
//		
//		if (loop == true) {
//			x = x % getWidth();
//			y = y % getHeight();
//		}
//		
//		if ((x < 0) || (x >= getWidth()) || (y < 0) || (y >= getHeight())) throw new IllegalOperation("Bad coordinates: [" + x + "," + y + "]. Widht = " + getWidth() + ", height = " + getHeight() + ".");
//		
//		return level[x][y];
//	}
//	
//	/**
//	 * Ustawia pole na planszy.
//	 * @param x - wsp. X
//	 * @param y - wsp. Y
//	 * @param field - rodzaj pola (�ciana, trawa, etc.)
//	 * @throws IllegalOperation - gdy field == null, gdy poziom nie zosta� utworzony/za�adowany, gdy podano z�e wsp�rz�dne.
//	 */
//	public void setField(int x, int y, Field field) throws IllegalOperation {
//		if (field == null) throw new IllegalOperation("Field can not be null !");
//		if (level == null) throw new IllegalOperation("Level is not created !");
//		
//		if ((x < 0) || (x >= getWidth()) || (y < 0) || (y >= getHeight())) throw new IllegalOperation("Bad coordinates: [" + x + "," + y + "]. Widht = " + getWidth() + ", height = " + getHeight() + ".");
//		
//		level[x][y] = field;
//	}
//	
//	/**
//	 * �aduje level z pliku tekstowego.
//	 * @param filename - �cie�ka do pliku
//	 * @throws FileNotFoundException - gdy okre�lonego pliku nie mo�na znale��
//	 * @throws IOException - gdy wyst�pi� b��d w readLine()
//	 */
//	public void loadFromFile(String filename) throws FileNotFoundException, IOException {
//		FileReader fr = new FileReader (new File("SampleLevel.txt"));
//		BufferedReader br = new BufferedReader (fr);
//		
//		int lineNr = 0;
//		int rowLength = 0;
//		Vector<Field[]> tempLevel = new Vector<Field[]>(); // tymczasowa tablica pol
//		Field[] temp;
//		String line = br.readLine();
//		while (line != null) {
//			if (lineNr == 0) { // wczytywanie szeroko�ci planszy
//				rowLength = line.length();
//			} else {
//				if (line.length() != rowLength) {
//					System.err.println("Line " + lineNr + " has invalid length. Line ignored.");
//					line = br.readLine();
//					continue;
//				}
//			}
//			temp = new Field[rowLength];
//			for (int i = 0; i < rowLength; i++) {
//				switch (line.charAt(i)) {
//				case '#':
//					temp[i] = new Wall();
//					break;
//				case '.': 
//					temp[i] = new Grass();
//					break;
//				case '$':
//					temp[i] = new Coin();
//					break;
//				default:
//					System.err.println("Line " + lineNr + ": Char \'" + line.charAt(i) + "\' at position " + i + " is not a valid field type !");
//					temp[i] = new Grass();
//					break;
//				}
//			}
//			
//			tempLevel.add(temp);
//			line = br.readLine();
//			lineNr++;
//		}
//		br.close();
//		fr.close();
//		//System.out.println("" + tempLevel.size() + " lines loaded.");
//		level = new Field[rowLength][tempLevel.size()];
//		width = rowLength;
//		height = tempLevel.size();
//		int y = 0;
//		for (Field[] t : tempLevel ) {
//			for (int x = 0; x < rowLength; x++) {
//				level[x][y] = t[x];
//			}
//			y++;
//		}
//		tempLevel.clear();
//	}
//

//}
