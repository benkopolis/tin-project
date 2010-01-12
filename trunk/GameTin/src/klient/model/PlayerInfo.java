package klient.model;

import klient.model.fields.Field;
import klient.model.fields.Player;

/**
 * 
 * @author zby
 *
 */
public class PlayerInfo {
	
	protected Player player;
	
	protected boolean onCoin = false;
	
	int x;
	
	int y;
	
	public PlayerInfo(Player p, int x, int y) {
		this.player = p;
		this.x = x;
		this.y = y;
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public boolean isOnCoin() {
		return onCoin;
	}

	public void setOnCoin(boolean onCoin) {
		this.onCoin = onCoin;
	}
	
	public boolean isVisible(int x, int y) {
		if(x == this.x || y == this.y)
			return true;
		else 
			return false;
	}
	
}
