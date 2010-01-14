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
		/*if(x == this.x && y == this.y)
			return true;
		if (this.x == x) {
			return (LevelsManager.getInstance().isWallBetweenV(this.x,this.y, x,y));
		}
		if (this.y == y){
			return (LevelsManager.getInstance().isWallBetwee1nH(this.x, this.y, x,y));
		}
		return false;*/
		
		if ((Math.abs(this.x - x) <= 2) && (Math.abs(this.y - y) <= 2)) {
				if(x == this.x && y == this.y)
					return true;
			/*	boolean res = false;
//				for (int temp = Math.abs(this.x - x)-2; temp < Math.abs(this.x - x) + 2; temp++) {
				for (int temp = -2; temp < 2; temp++) {
					res = (LevelsManager.getInstance().isWallBetweenV(this.x+temp,this.y, x+temp,y));
					if (res == false) return false;
				}
				if (res == true) {
//				for (int temp = Math.abs(this.y - y)-2; temp < Math.abs(this.y - y) + 2; temp++) {
					for (int temp = -2; temp < 2; temp++) {
						res = (LevelsManager.getInstance().isWallBetwee1nH(this.x, this.y+temp, x,y+temp));
						if (res == false) return false;
					}
					
				}
				return true;*/
				
				if ((Math.abs(this.x - x) <= 1) && (Math.abs(this.y - y) <= 1)) return true;
				
				if (this.x == x) {
					if (!LevelsManager.getInstance().isWallBetweenV(this.x, this.y, x, y)) return false; else return true;
				} else 
				if (this.y == y) {
					if (!LevelsManager.getInstance().isWallBetweenH(this.x, this.y, x, y)) return false; else return true;
				}
				
		}
		return false;
	}
	
}
