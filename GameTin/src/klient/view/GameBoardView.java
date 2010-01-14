/**
 * 
 */
package klient.view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JPanel;

import klient.controller.ViewsController;
import klient.model.IllegalOperation;
import klient.model.fields.Coin;
import klient.model.fields.Field;
import klient.model.fields.Player;
import klient.model.fields.Wall;

/**
 * @author zby
 *
 * Klasa ta odpowiada za malowanie planszy. Wywo�uje r�wnie� odpowiednie metody
 * kontrolera klawiatury.
 */
public class GameBoardView extends GameView implements KeyListener {
	
	/**
	 * @author zby
	 * 
	 * Klasa b�d�ca rysowan� plansz�.
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
		 * Wype�nia plansz� na pocz�tek.
		 */
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			g.setColor(new Color(120, 150, 120));
			g.fillRect(0, 0, w*rectSize, h*rectSize);
			this.fillBoard(g);
		}
		
		public void fillBoard(Graphics g) {
			try {
				if (root.getModel().getLocalPlayerInfo() == null)
					return;
//				System.out.println("fillBoord");
				int x=0;
				int y=0;
				Field f = null;
				int w=root.getModel().getLm().getWidth();
				int h=root.getModel().getLm().getHeight();
				for(x = 0; x<w; ++x) {
					for(y=0; y<h; ++y) {
						f = root.getModel().getLm().getField(x, y, false);
						if (f == null) System.out.println("AAAAA"); 
						if(f instanceof Wall) {
							g.setColor(Color.GRAY);
							g.fillRect(x*rectSize, y*rectSize, rectSize, rectSize);
							System.out.println("WALL");
						} else {
								if(root.getModel().getLocalPlayerInfo().isVisible(x, y)) {
								
									if(f instanceof Coin) {
										System.out.println("coin");
										g.setColor(Color.YELLOW);
										g.fillOval(x*rectSize, y*rectSize, rectSize, rectSize);
									} else 
									if(f instanceof Player) {
										System.out.println("player !");
										if(((Player)f).getId() == root.getModel().getLocalPlayerId())
											g.setColor(Color.BLUE);
										else
											g.setColor(Color.RED);
										g.fillOval(x*rectSize, y*rectSize, rectSize, rectSize);
									}
									
								}
						}
//							g.setColor(Color.BLACK);
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
	
	protected int rectSize = 15;
	protected int w;
	protected int h;

	public GameBoardView(ViewsController viewsController) {
		super(viewsController);
		setLocation(400, 200);
		setVisible(false);
	}

	@Override
	public void init() {
		setTitle("Plansza");
		try {
			h = this.root.getModel().getLm().getHeight();
			w = this.root.getModel().getLm().getWidth();
			setSize(w*rectSize+10, h*rectSize+20);
		} catch (IllegalOperation e) {
			e.printStackTrace();
		}
		panel.setLayout(new GridLayout(h, w));
		panel.setSize(w*rectSize, h*rectSize);
		add(panel);
		panel.addKeyListener(this);
		addKeyListener(this);
		panel.setVisible(true);
		setVisible(true);
		invalidate();
		repaint();
	}

	@Override
	public void keyPressed(KeyEvent e) {
//		System.out.println("Key pressed!");
		if(e.getKeyCode() == KeyEvent.VK_W || e.getKeyCode() == KeyEvent.VK_UP) {
			root.getKeyboardController().onClickedUp();
		} else if(e.getKeyCode() == KeyEvent.VK_S || e.getKeyCode() == KeyEvent.VK_DOWN) {
			root.getKeyboardController().onClickedDown();
		} else if(e.getKeyCode() == KeyEvent.VK_A || e.getKeyCode() == KeyEvent.VK_LEFT) {
			root.getKeyboardController().onClickedLeft();
		} else if(e.getKeyCode() == KeyEvent.VK_D || e.getKeyCode() == KeyEvent.VK_RIGHT) {
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
	 * Metoda odmalowuje plansz�.
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
//		System.out.println("RepaintBoard before invalidate.");
		panel.invalidate();
		panel.repaint();
//		panel.paintComponent(getGraphics());
	}
}
