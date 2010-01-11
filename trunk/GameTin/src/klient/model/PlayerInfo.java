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

	public synchronized Player getPlayer() {
		return player;
	}

	public synchronized void setPlayer(Player player) {
		this.player = player;
	}

	public synchronized int getX() {
		return x;
	}

	public synchronized void setX(int x) {
		this.x = x;
	}

	public synchronized int getY() {
		return y;
	}

	public synchronized void setY(int y) {
		this.y = y;
	}

	public synchronized boolean isOnCoin() {
		return onCoin;
	}

	public synchronized void setOnCoin(boolean onCoin) {
		this.onCoin = onCoin;
	}
	
	public synchronized boolean isVisible(Field[][] fields, int x, int y) {
		float dy;
		float dx;
		float rx, ry;
		int sx = this.x-x;
		int sy = this.y-y;
		return true;
	}
	
}
