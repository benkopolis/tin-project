/**
 * 
 */
package klient.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.util.HashMap;

import javax.swing.JLabel;
import javax.swing.JPanel;

import sun.java2d.loops.FontInfo;

import klient.controller.ViewsController;
import klient.model.fields.Player;

/**
 * @author zby
 *
 */
public class GameInfoView extends GameView {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3984532223389483756L;
	
	protected boolean labelsShowed = false;
	
	protected ScorePanel panel = new ScorePanel();
	protected HashMap<Player, JLabel> labels = new HashMap<Player, JLabel>();

	public GameInfoView(ViewsController viewsController) {
		super(viewsController);
		setLocation(100, 200);
		setTitle("Punktacja");
	}
	
	public void addPlayer(Player p) {
		JLabel l = new JLabel(p.getNick()+" ma "+p.getScore()+" puntkow.");
		labels.put(p, l);
	}
	
	public void refresh() {
		if(labelsShowed == false) {
			setSize(150, 40*labels.size()+40);
			panel.setSize(150, 40*labels.size());
			setVisible(true);
			panel.setVisible(true);
			labelsShowed = true;
		}
		invalidate();
		repaint();
	}

	@Override
	public void init() {
		add(panel);
	}
	
	protected class ScorePanel extends JPanel {
		
		public ScorePanel() {
			
		}
		
		@Override
		public void paintComponent(Graphics g) {
			g.setColor(Color.WHITE);
			g.fillRect(0, 0, getWidth(), getHeight());
			g.setColor(Color.BLACK);
			int yPos = 50;
			for(Player p: labels.keySet()) {
				g.drawString(p.getNick()+" ma "+p.getScore()+" puntkow.", 10, yPos);
				yPos = yPos + 30;
			}

		}
	}

}
