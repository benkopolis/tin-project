package klient.model;

public class Move {

	protected int oldX;
	
	protected int oldY;
	
	protected int newX;
	
	protected int newY;
	
	public Move(int ox, int oy, int nx, int ny) {
		oldX = ox;
		oldY = oy;
		newY = ny;
		newX = nx;
	}

	public int getOldX() {
		return oldX;
	}

	public void setOldX(int oldX) {
		this.oldX = oldX;
	}

	public int getOldY() {
		return oldY;
	}

	public void setOldY(int oldY) {
		this.oldY = oldY;
	}

	public int getNewX() {
		return newX;
	}

	public void setNewX(int newX) {
		this.newX = newX;
	}

	public int getNewY() {
		return newY;
	}

	public void setNewY(int newY) {
		this.newY = newY;
	}
	
	
}
