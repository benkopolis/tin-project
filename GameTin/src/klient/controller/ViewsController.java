package klient.controller;

import java.io.IOException;
import java.net.UnknownHostException;

import klient.controller.networkctrl.NetworkController;
import sun.net.NetworkClient;
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

	protected GameOptionsView gov = new GameOptionsView(this);
	protected GameInfoView giv = new GameInfoView(this);
	protected GameBoardView gbv = new GameBoardView(this);
	protected NetworkController nc = null;
	protected Model m = new Model();
	
	public ViewsController() {
		gov.init();
	}
	
	public void play(String ip, String nick) {
		try {
			nc = new NetworkController(nick, 0, m);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		giv.init();
		gbv.init();
	}
}
