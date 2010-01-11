/**
 * 
 */
package klient.controller.keyboardctrl;

import klient.controller.ViewsController;
import klient.model.IllegalOperation;
import klient.model.Move;
import klient.model.PlayerInfo;
import klient.model.fields.Coin;
import klient.model.fields.Field;
import klient.model.fields.Grass;



/**
 * @author zby
 *
 * Klasa kontroluj¹ca zachowanie programu wzglêdem klawiatury, podczas gry.
 * Pozwala na wykonywanie ruchów, które w czasie rzeczywistym mog¹ by aktualizowane
 * na ekranie, ka¿dy ruch ksiêgowany jest w kolejce blokuj¹cej w modelu,
 * z której korzysta mog¹ w¹tki komunikacyjne, w celu powiadomienia serwera o
 * chêci wykonania ruchu, przez gracza. 
 */
public class KeyboardController {
	
	protected ViewsController parent = null;
	
	protected PlayerInfo localPlayerInfo = null;
	
	protected int newX = 0;
	
	protected int newY = 0;
	
	protected Move move = null;
	
	public KeyboardController(ViewsController vc) {
		parent = vc;
	}
	
	public void onClickedUp() {
		if(localPlayerInfo == null)
			this.findLocalPlayer();
		try {
			newY = (localPlayerInfo.getY()-1)%parent.getModel().getLm().getHeight();
			newX = (localPlayerInfo.getX())%parent.getModel().getLm().getWidth();
			doMove();
		} catch (IllegalOperation e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void onClickedDown() {
		if(localPlayerInfo == null)
			this.findLocalPlayer();
		try {
			newY = (localPlayerInfo.getY()+1)%parent.getModel().getLm().getHeight();
			newX = (localPlayerInfo.getX())%parent.getModel().getLm().getWidth();
			doMove();
		} catch (IllegalOperation e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void onClickedLeft() {
		if(localPlayerInfo == null)
			this.findLocalPlayer();
		try {
			newY = (localPlayerInfo.getY())%parent.getModel().getLm().getHeight();
			newX = (localPlayerInfo.getX()-1)%parent.getModel().getLm().getWidth();
			doMove();
		} catch (IllegalOperation e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void onClickedRight() {
		if(localPlayerInfo == null)
			this.findLocalPlayer();
		try {
			newY = (localPlayerInfo.getY())%parent.getModel().getLm().getHeight();
			newX = (localPlayerInfo.getX()+1)%parent.getModel().getLm().getWidth();
			doMove();
		} catch (IllegalOperation e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	protected void findLocalPlayer() {
		for(PlayerInfo p: parent.getModel().getPlayers()) {
			if(p.getPlayer().getId() == parent.getModel().getLocalPlayerId())
				localPlayerInfo = p;
		}
	}
	
	/**
	 * Je¿eli to mo¿liwe, to metoda wykonuje ruch na planszy i jednoczesnie, wstawia
	 * informacje o wykonanym ruchu do kolejki w modelu.
	 * @throws IllegalOperation
	 * @throws InterruptedException
	 */
	protected void doMove() throws IllegalOperation, InterruptedException {
		if((parent.getModel().getLm().getField(newX, newY, false) instanceof Grass)) {
			parent.getModel().getLm().setField(localPlayerInfo.getX(), localPlayerInfo.getY(), new Grass());
			parent.getModel().getLm().setField(newX, newY, localPlayerInfo.getPlayer());
			move = new Move(localPlayerInfo.getX(), localPlayerInfo.getY(), newX, newY);
			localPlayerInfo.setX(newX);
			localPlayerInfo.setY(newY);
			parent.getModel().workOnMoves(true, move);
			parent.refreshBoardView();
		}
	}
}
