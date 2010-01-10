/**
 * 
 */
package klient.view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JOptionPane;
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
 * Klasa ta odpowiada za malowanie planszy. Wywo³uje równie¿ odpowiednie metody
 * kontrolera klawiatury.
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
			g.setColor(Color.GREEN);
			g.fillRect(0, 0, w*rectSize, h*rectSize);
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
						} else if(fields[i][j] instanceof Coin) {
							g.setColor(Color.YELLOW);
							g.fillOval(i*rectSize, j*rectSize, rectSize, rectSize);
						} else if(fields[i][j] instanceof Player) {
							if(((Player)fields[i][j]).getId() == root.getModel().getLocalPlayerId())
								g.setColor(Color.ORANGE);
							else
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
		setLocation(400, 200);
		setTitle("Plansza");
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
		this.addKeyListener(this);
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

	@Override
	public void keyPressed(KeyEvent e) {
//		System.out.println("Key pressed!");
		if(e.getKeyCode() == KeyEvent.VK_W) {
			root.getKeyboardController().onClickedUp();
		} else if(e.getKeyCode() == KeyEvent.VK_S) {
			root.getKeyboardController().onClickedDown();
		} else if(e.getKeyCode() == KeyEvent.VK_A) {
			root.getKeyboardController().onClickedLeft();
		} else if(e.getKeyCode() == KeyEvent.VK_D) {
			root.getKeyboardController().onClickedRight();
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
//		System.out.println("Key released!");
	}

	@Override
	public void keyTyped(KeyEvent e) {
//		System.out.println("Key typed!");
	}
	
	/**
	 * Metoda odmalowuje planszê.
	 */
	public void repaintBoard() {
		try {
			if(this.root.getModel().getLm().getHeight() != h ||
					this.root.getModel().getLm().getWidth() != w) {
				h = this.root.getModel().getLm().getHeight();
				w = this.root.getModel().getLm().getWidth();
				setSize(w*rectSize+10, h*rectSize+20);
			}
		} catch (IllegalOperation e) {
			e.printStackTrace();
		}
		panel.invalidate();
		panel.repaint();
//		panel.paintComponent(getGraphics());
	}
}
