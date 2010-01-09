/**
 * 
 */
package klient.controller.keyboardctrl;

import klient.controller.ViewsController;
import klient.model.IllegalOperation;
import klient.model.PlayerInfo;
import klient.model.fields.Coin;
import klient.model.fields.Field;
import klient.model.fields.Grass;



/**
 * @author zby
 *
 */
public class KeyboardController {
	
	protected ViewsController parent = null;
	
	protected PlayerInfo localPlayerInfo = null;
	
	protected int newX = 0;
	
	protected int newY = 0;
	
	public KeyboardController(ViewsController vc) {
		parent = vc;
	}
	
	public void onClickedUp() {
		if(localPlayerInfo == null)
			this.findLocalPlayer();
		try {
			newY = (localPlayerInfo.getY()+1)%parent.getModel().getLm().getHeight();
			newX = (localPlayerInfo.getX())%parent.getModel().getLm().getWidth();
			if((parent.getModel().getLm().getField(newX, newY, false) instanceof Grass) ||
				(parent.getModel().getLm().getField(newX, newY, false) instanceof Coin)) {
					parent.getModel().getLm().setField(localPlayerInfo.getX(), localPlayerInfo.getY(), new Grass());
					parent.getModel().getLm().setField(newX, newY, (Field)localPlayerInfo.getPlayer());
					//TODO poinformowac watek o wykonanym ruchu
			}
		} catch (IllegalOperation e) {
		
			e.printStackTrace();
		}
	}
	
	public void onClickedDown() {
		if(localPlayerInfo == null)
			this.findLocalPlayer();
		try {
			newY = (localPlayerInfo.getY()-1)%parent.getModel().getLm().getHeight();
			newX = (localPlayerInfo.getX())%parent.getModel().getLm().getWidth();
			if((parent.getModel().getLm().getField(newX, newY, false) instanceof Grass) ||
				(parent.getModel().getLm().getField(newX, newY, false) instanceof Coin)) {
					parent.getModel().getLm().setField(localPlayerInfo.getX(), localPlayerInfo.getY(), new Grass());
					parent.getModel().getLm().setField(newX, newY, (Field)localPlayerInfo.getPlayer());
					//TODO poinformowac watek o wykonanym ruchu
			}
		} catch (IllegalOperation e) {
		
			e.printStackTrace();
		}
	}

	public void onClickedLeft() {
		if(localPlayerInfo == null)
			this.findLocalPlayer();
		try {
			newY = (localPlayerInfo.getY())%parent.getModel().getLm().getHeight();
			newX = (localPlayerInfo.getX()-1)%parent.getModel().getLm().getWidth();
			if((parent.getModel().getLm().getField(newX, newY, false) instanceof Grass) ||
				(parent.getModel().getLm().getField(newX, newY, false) instanceof Coin)) {
					parent.getModel().getLm().setField(localPlayerInfo.getX(), localPlayerInfo.getY(), new Grass());
					parent.getModel().getLm().setField(newX, newY, (Field)localPlayerInfo.getPlayer());
					//TODO poinformowac watek o wykonanym ruchu
			}
		} catch (IllegalOperation e) {
		
			e.printStackTrace();
		}
	}

	public void onClickedRight() {
		if(localPlayerInfo == null)
			this.findLocalPlayer();
		try {
			newY = (localPlayerInfo.getY())%parent.getModel().getLm().getHeight();
			newX = (localPlayerInfo.getX()+1)%parent.getModel().getLm().getWidth();
			if((parent.getModel().getLm().getField(newX, newY, false) instanceof Grass) ||
				(parent.getModel().getLm().getField(newX, newY, false) instanceof Coin)) {
					parent.getModel().getLm().setField(localPlayerInfo.getX(), localPlayerInfo.getY(), new Grass());
					parent.getModel().getLm().setField(newX, newY, (Field)localPlayerInfo.getPlayer());
					//TODO poinformowac watek o wykonanym ruchu
			}
		} catch (IllegalOperation e) {
		
			e.printStackTrace();
		}
	}
	
	protected void findLocalPlayer() {
		for(PlayerInfo p: parent.getModel().getPlayers()) {
			if(p.getPlayer().getId() == parent.getModel().getLocalPlayerId())
				localPlayerInfo = p;
		}
	}
	
}
