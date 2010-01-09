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
		
		/**
		 * Wype�nia plansz� na pocz�tek.
		 */
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			g.setColor(Color.BLACK);
			g.fillRect(0, 0, w*rectSize, h*rectSize);
			g.setColor(Color.YELLOW);
			for(int i=0; i<w; ++i) {
				for(int j=0; j<h; ++j) {
					g.drawLine(i*rectSize, 0, i*rectSize, h*rectSize);
					g.drawLine(0, j*rectSize, w*rectSize, j*rectSize);
				}
			}
			
		}
	}
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 276597651225478479L;
	
	protected Board panel = new Board();
	
	protected int rectSize = 40;
	protected int w;
	protected int h;

	public GameBoardView(ViewsController viewsController) {
		super(viewsController);
		setLocation(200, 200);
		try {
			h = this.root.getModel().getLm().getHeight();
			w = this.root.getModel().getLm().getWidth() + 1;
			setSize(w*rectSize+10, h*rectSize+20);
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
	
	/**
	 * Metoda wypelnia plansze pobierajac dane z modelu
	 */
	public void fillBoard() {
		
	}
	
	/**
	 * Metoda wypelnia tylko zadane pole, pobierajac dane z modelu
	 * @param x - musi sie miescic w granicach
	 * @param y - musi sie miescic w granicach
	 * @throws rzuca wyj�tek, je�eli x, lub y nie mieszcz� si� na planszy.
	 */
	public void fillField(int x, int y) throws IllegalOperation {
		
	}
	
	/**
	 * Metoda wypelnia zadane pola, pobierajac dane z modelu.
	 * @param x - musi miec ten sam rozmiar co y
	 * @param y - musi miec ten sam rozmiar co x
	 * @throws rzuca wyj�tek, je�eli jakakolwiek warto�c z tabeli x lub y
	 * nie mie�ci si� w granicach, lub je�eli ich rozmiary s� r�ne.
	 */
	public void fillFields(int[] x, int[] y) throws IllegalOperation {
		
	}
}
