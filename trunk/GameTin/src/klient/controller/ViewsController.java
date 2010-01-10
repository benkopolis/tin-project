package klient.controller;

import java.io.IOException;
import java.net.UnknownHostException;

import klient.controller.keyboardctrl.KeyboardController;
import klient.controller.networkctrl.NetworkController;
import klient.model.Model;
import klient.view.GameBoardView;
import klient.view.GameInfoView;
import klient.view.GameOptionsView;

/**
 * 
 * @author zby
 *
 */
public class ViewsController {

	protected GameOptionsView gameOptionsView = null;
	protected GameInfoView gameInfoView = null;
	protected GameBoardView gameBoardView = null;
	protected NetworkController networkController = null;
	protected KeyboardController keyboardController = null;
	protected Model model = new Model();
	
	public ViewsController() {
		getModel().getLm().createNewLevel(10, 10, false);
		gameBoardView = new GameBoardView(this);
		gameInfoView = new GameInfoView(this);
		gameOptionsView = new GameOptionsView(this);
		keyboardController =  new KeyboardController(this);
		gameOptionsView.init();
	}
	
	public void play(String ip, String nick) {
//		try {
//			networkController = new NetworkController(ip, 666, model);
//		} catch (UnknownHostException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
		gameInfoView.init();
		gameBoardView.init();
	}

	public synchronized GameOptionsView getGameOptionsView() {
		return gameOptionsView;
	}

	public synchronized void setGameOptionsView(GameOptionsView gameOptionsView) {
		this.gameOptionsView = gameOptionsView;
	}

	public synchronized GameInfoView getGameInfoView() {
		return gameInfoView;
	}

	public synchronized void setGameInfoView(GameInfoView gameInfoView) {
		this.gameInfoView = gameInfoView;
	}

	public synchronized GameBoardView getGameBoardView() {
		return gameBoardView;
	}

	public synchronized void setGameBoardView(GameBoardView gameBoardView) {
		this.gameBoardView = gameBoardView;
	}

	public synchronized NetworkController getNetworkController() {
		return networkController;
	}

	public synchronized void setNetworkController(
			NetworkController networkController) {
		this.networkController = networkController;
	}

	public synchronized Model getModel() {
		return model;
	}

	public synchronized void setModel(Model model) {
		this.model = model;
	}

	public synchronized KeyboardController getKeyboardController() {
		return keyboardController;
	}

	public synchronized void setKeyboardController(
			KeyboardController keyboardController) {
		this.keyboardController = keyboardController;
	}
	
}
