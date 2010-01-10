/**
 * 
 */
package klient.view;

import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.util.HashMap;

import javax.swing.JLabel;
import javax.swing.JPanel;

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
	
	protected JPanel panel = new JPanel();
	protected HashMap<Player, JLabel> labels = new HashMap<Player, JLabel>();

	public GameInfoView(ViewsController viewsController) {
		super(viewsController);
		setLocation(100, 200);
	}
	
	public void addPlayer(Player p) {
		JLabel l = new JLabel(p.getNick()+" ma "+p.getScore()+" puntkow.");
		labels.put(p, l);
	}
	
	public void refresh() {
		if(labelsShowed == false) {
			setSize(150, 40*labels.size()+40);
			panel.setSize(150, 40*labels.size());
			panel.setLayout(new GridLayout(labels.size(), 1));
			setVisible(true);
			panel.setVisible(true);
		}
		for(Player p: labels.keySet()) {
			if(labelsShowed == false) {
				panel.add(labels.get(p));
			}
			labels.get(p).setText(p.getNick()+" ma "+p.getScore()+" puntkow.");
		}
	}

	@Override
	public void init() {
		add(panel);
	}

}
