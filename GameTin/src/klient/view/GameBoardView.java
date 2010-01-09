/**
 * 
 */
package klient.view;

import java.awt.Color;
import java.awt.Graphics;
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
	
	private class Board extends JPanel {
	
		
		/**
		 * 
		 */
		private static final long serialVersionUID = 8531461779069085507L;

		public Board() {
			
		}
		
		public void paintComponent(Graphics g) {
			g.setColor(Color.BLACK);
			super.paintComponent(g);
			g.setColor(Color.YELLOW);
			for(int i=0; i<w; ++i) {
				for(int j=0; j<h; ++j) {
					g.drawLine(i*rectSize, 0, rectSize, i*rectSize);
					g.drawLine(0, j*rectSize, j*rectSize, w*rectSize);
				}
			}
			
		}
	}
	
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 276597651225478479L;
	
	protected Board panel = new Board();
	
	protected int rectSize = 20;
	protected int w;
	protected int h;

	public GameBoardView(ViewsController viewsController) {
		super(viewsController);
		try {
			h = this.root.getModel().getLm().getHeight();
			w = this.root.getModel().getLm().getWidth() + 1;
			setSize(w*rectSize+10, h*rectSize+10);
		} catch (IllegalOperation e) {
			e.printStackTrace();
		}
	}

	@Override
	public void init() {
		this.setVisible(true);
		panel.setLayout(new GridLayout(h, w));
		panel.setSize(w*rectSize, h*rectSize);
		this.add(panel);
		panel.setVisible(true);
		panel.paintComponent(this.getGraphics());
		
		
	}
}
