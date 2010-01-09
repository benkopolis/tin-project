package controller;

import view.GameBoardView;
import view.GameInfoView;
import view.GameOptionsView;

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
