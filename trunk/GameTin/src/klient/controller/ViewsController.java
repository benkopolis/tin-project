package klient.controller;

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
	
	
	public ViewsController() {
		gov.init();
	}
	
	public void play() {
		giv.init();
		gbv.init();
	}
}
