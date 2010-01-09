/**
 * 
 */
package klient.view;

import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

import klient.controller.ViewsController;
import klient.model.IllegalOperation;

/**
 * @author zby
 *
 */
public class GameBoardView extends GameView {
	/**
	 * 
	 */
	private static final long serialVersionUID = 276597651225478479L;
	
	protected JPanel panel = new JPanel();
	
	protected int rectSize = 40;
	protected int w;
	protected int h;

	public GameBoardView(ViewsController viewsController) {
		super(viewsController);
		try {
			h = this.root.getModel().getLm().getHeight();
			w = this.root.getModel().getLm().getWidth() + 1;
		} catch (IllegalOperation e) {
			e.printStackTrace();
		}
	}

	@Override
	public void init() {
		panel.setLayout(new GridLayout(h, w));
		panel.setSize(w*rectSize, h*rectSize);
		
	}
}
