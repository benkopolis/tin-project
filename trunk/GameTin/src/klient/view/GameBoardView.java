/**
 * 
 */
package klient.view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JPanel;

import klient.controller.ViewsController;
import klient.model.IllegalOperation;
import klient.model.fields.Coin;
import klient.model.fields.Field;
import klient.model.fields.Grass;
import klient.model.fields.Player;
import klient.model.fields.Wall;

/**
 * @author zby
 *
 */
public class GameBoardView extends GameView implements KeyListener {
	
	/**
	 * @author zby
	 * 
	 * Klasa bêd¹ca rysowan¹ plansz¹.
	 *
	 */
	protected class Board extends JPanel {
		
		/**
		 * 
		 */
		private static final long serialVersionUID = 8531461779069085507L;

		public Board() {
			
		}
		
		/**
		 * Wype³nia planszê na pocz¹tek.
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
			this.fillBoard(g);
		}
		
		public void fillBoard(Graphics g) {
			Field[][] fields = root.getModel().getLm().getLevel();
			try {
				for(int i=0; i<root.getModel().getLm().getWidth(); ++i) { //i -x, w
					for(int j=0; j<root.getModel().getLm().getHeight(); ++j) { // j -y, h
						if(fields[i][j] instanceof Wall) {
							g.setColor(Color.GRAY);
							g.fillRect(i*rectSize, j*rectSize, rectSize, rectSize);
						} else if(fields[i][j] instanceof Grass) {
							g.setColor(Color.GREEN);
							g.fillRect(i*rectSize, j*rectSize, rectSize, rectSize);
						} else if(fields[i][j] instanceof Coin) {
							g.setColor(Color.YELLOW);
							g.fillOval(i*rectSize, j*rectSize, rectSize, rectSize);
						} else if(fields[i][j] instanceof Player) {
							g.setColor(Color.RED);
							g.fillOval(i*rectSize, j*rectSize, rectSize, rectSize);
						}
						g.setColor(Color.BLACK);
					}
				}
			} catch (IllegalOperation e) {
				e.printStackTrace();
			}
		}
		
		public void fillField(Graphics g, int x, int y) throws IllegalOperation {
			
		}
		
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
			w = this.root.getModel().getLm().getWidth();
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
		panel.addKeyListener(this);
		panel.setVisible(true);
		panel.paintComponent(this.getGraphics());
		//panel.fillBoard(this.getGraphics());
	}
	
	/**
	 * Metoda wypelnia plansze pobierajac dane z modelu
	 */
	public void fillBoard() {
		panel.fillBoard(this.getGraphics());
	}
	
	/**
	 * Metoda wypelnia tylko zadane pole, pobierajac dane z modelu
	 * @param x - musi sie miescic w granicach
	 * @param y - musi sie miescic w granicach
	 * @throws rzuca wyj¹tek, je¿eli x, lub y nie mieszcz¹ siê na planszy.
	 */
	public void fillField(int x, int y) throws IllegalOperation {
		panel.fillField(this.getGraphics(), x, y);
	}
	
	/**
	 * Metoda wypelnia zadane pola, pobierajac dane z modelu.
	 * @param x - musi miec ten sam rozmiar co y
	 * @param y - musi miec ten sam rozmiar co x
	 * @throws rzuca wyj¹tek, je¿eli jakakolwiek wartoœc z tabeli x lub y
	 * nie mieœci siê w granicach, lub je¿eli ich rozmiary s¹ ró¿ne.
	 */
	public void fillFields(int[] x, int[] y) throws IllegalOperation {
		panel.fillFields(this.getGraphics(), x, y);
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_UP) {
			root.getKeyboardController().onClickedUp();
		} else if(e.getKeyCode() == KeyEvent.VK_DOWN) {
			root.getKeyboardController().onClickedDown();
		} else if(e.getKeyCode() == KeyEvent.VK_LEFT) {
			root.getKeyboardController().onClickedLeft();
		} else if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
			root.getKeyboardController().onClickedRight();
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
}
