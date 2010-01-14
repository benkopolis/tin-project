package klient.controller;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import javax.swing.JOptionPane;

import klient.controller.keyboardctrl.KeyboardController;
import klient.controller.networkctrl.NetworkController;
import klient.model.IllegalOperation;
import klient.model.Model;
import klient.model.fields.Player;
import klient.view.GameBoardView;
import klient.view.GameInfoView;
import klient.view.GameOptionsView;

/**
 * 
 * @author zby
 *
 * G��wny kontroler programu. Odpala widok pocz�tkowy.
 * TODO Widoki gry, powinny byc odpalane na zadanie watku TCP
 * TODO Widok punktacji, powinien byc dostepny tylko dla jednego watku - tcp sie na nim chyba zawiesza
 * TODO Model powinien byc Observable, a ViewsController Observer
 * TODO UDPSender powinien byc klasa tworzona i uzywana przez ViewsControllera, wysylanie pakietow UDP tylko w reakcji na odpowiednie zmiany modelu
 */
public class ViewsController {

	protected GameOptionsView gameOptionsView = null;
	protected GameInfoView gameInfoView = null;
	protected GameBoardView gameBoardView = null;
	protected NetworkController networkController = null;
	protected KeyboardController keyboardController = null;
	protected Model model = new Model();
	protected RefreshingThread rt = null;
	
	protected class RefreshingThread extends Thread {
		protected LinkedBlockingQueue<Object> stoper = new LinkedBlockingQueue<Object>();
		
		public RefreshingThread() {
			super();
			setDaemon(true);
			start();
		}
		
		public void run() {
			while(!isInterrupted()) {
				try {
					stoper.poll(10L, TimeUnit.MILLISECONDS);
					refreshBoardView();
//					refreshInfoView();
				} catch (InterruptedException e) {
					e.printStackTrace();
				} //catch (IllegalOperation e) {
					//e.printStackTrace();
				//}
				
			}
		}
		
	}
	
	
	public ViewsController() {
		getModel().getLm().createNewLevel(10, 10, false);
		gameBoardView = new GameBoardView(this);
		gameInfoView = new GameInfoView(this);
		gameOptionsView = new GameOptionsView(this);
		keyboardController =  new KeyboardController(this);
		gameOptionsView.init();
	}
	
	public void initNetworkController(String ip, String nick) {
		model.setLocalPlayerNick(nick);
		try {
			networkController = new NetworkController(ip, 666, model, this);
		} catch (UnknownHostException e) {
			JOptionPane.showMessageDialog(null, "Blad polaczenia - nie ma takiego serwera", "Error", JOptionPane.ERROR_MESSAGE);
			System.exit(0);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Blad polaczenia - nie ma takiego serwera", "Error", JOptionPane.ERROR_MESSAGE);
			System.exit(0);
		}
	}

	public void startPlayViews() {
		gameBoardView.init();
		gameInfoView.init();
		gameBoardView.toFront();
		rt = new RefreshingThread();
	}

	public  GameOptionsView getGameOptionsView() {
		return gameOptionsView;
	}

	public  void setGameOptionsView(GameOptionsView gameOptionsView) {
		this.gameOptionsView = gameOptionsView;
	}

	public  GameInfoView getGameInfoView() {
		return gameInfoView;
	}

	public  void setGameInfoView(GameInfoView gameInfoView) {
		this.gameInfoView = gameInfoView;
	}

	public  GameBoardView getGameBoardView() {
		return gameBoardView;
	}

	public  void setGameBoardView(GameBoardView gameBoardView) {
		this.gameBoardView = gameBoardView;
	}

	public  NetworkController getNetworkController() {
		return networkController;
	}

	public  void setNetworkController(
			NetworkController networkController) {
		this.networkController = networkController;
	}

	public  Model getModel() {
		return model;
	}

	public  void setModel(Model model) {
		this.model = model;
	}

	public  KeyboardController getKeyboardController() {
		return keyboardController;
	}

	public  void setKeyboardController(
			KeyboardController keyboardController) {
		this.keyboardController = keyboardController;
	}
	
	/**
	 * Dodaje gracza, do listy graczy wy�wietlanej u�ytkownikowi.
	 * Wywolanie tej funkcji nie powinny wystapic po rozpoczeciu gry.
	 * @param p
	 */
	public void addPlayerToInfoView(Player p) throws IllegalOperation {
		if(model.isGameOn())
			throw new IllegalOperation("Nie wolno dodawac nowych graczy, po starcie gry");
		gameInfoView.addPlayer(p);
	}
	
	/**
	 * Od�wierza wy�wietlane informacje o graczach.
	 * Pierwsze wywo�anie powinno nast�pic dopiero po starcie gry - 
	 * gdy wszyscy gracze sa juz dodani.
	 */
	public  void refreshInfoView() throws IllegalOperation {
		if(model.isGameOn()==false)
			throw new IllegalOperation("Nie mozna odswierzac, zanim zaczela sie rozgrywka");
		gameInfoView.refresh();
	}
	
	/**
	 * Powoduje przemalowanie planszy, wed�ug informacji zawartych w modelu.
	 */
	public synchronized void refreshBoardView() {
//		try {
//			System.out.print(model.getLm().levelToString(false));
//		} catch (IllegalOperation e) {
//			e.printStackTrace();
//		}
		gameBoardView.repaintBoard();
	}
	
	/**
	 * Konczy dzialanie programu
	 */
	public synchronized void closeApplication() {
		this.gameBoardView.setVisible(false);
		this.gameInfoView.setVisible(false);
		this.gameOptionsView.setVisible(false);
		System.exit(0);
	}
}
