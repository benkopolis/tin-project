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
		
		/**
		 * Metoda wypelnia plansze pobierajac dane z modelu
		 * @param g
		 */
		public void fillBoard(Graphics g) {
			
		}
		
		/**
		 * Metoda wypelnia tylko zadane pole, pobierajac dane z modelu
		 * @param g
		 * @param x - musi sie miescic w granicach
		 * @param y - musi sie miescic w granicach
		 * @throws rzuca wyj¹tek, je¿eli x, lub y nie mieszcz¹ siê na planszy, 
		 * albo je¿eli g jest nullem
		 */
		public void fillField(Graphics g, int x, int y) throws IllegalOperation {
			
		}
		
		/**
		 * Metoda wypelnia zadane pola, pobierajac dane z modelu.
		 * @param g
		 * @param x - musi miec ten sam rozmiar co y
		 * @param y - musi miec ten sam rozmiar co x
		 * @throws rzuca wyj¹tek, je¿eli jakakolwiek wartoœc z tabeli x lub y
		 * nie mieœci siê w granicach, lub je¿eli ich rozmiary s¹ ró¿ne, albo
		 * je¿eli g jest nullem.
		 */
		public void fillFields(Graphics g, int[] x, int[] y) throws IllegalOperation {
			
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
}
