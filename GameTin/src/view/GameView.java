/**
 * 
 */
package view;

import javax.swing.JFrame;

import controller.ViewsController;

/**
 * @author zby
 *
 */
public abstract class GameView extends JFrame {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8959215982584247420L;
	protected ViewsController parent;
	
	public GameView(ViewsController p) {
		parent = p;
	}
	
	public abstract void init();

}
