package klient.model;

import klient.model.fields.Player;

/**
 * 
 * @author zby
 *
 */
public class PlayerInfo {
	
	protected Player player;
	
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
	
	
	
}
