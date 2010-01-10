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

	public synchronized int getOldX() {
		return oldX;
	}

	public synchronized void setOldX(int oldX) {
		this.oldX = oldX;
	}

	public synchronized int getOldY() {
		return oldY;
	}

	public synchronized void setOldY(int oldY) {
		this.oldY = oldY;
	}

	public synchronized int getNewX() {
		return newX;
	}

	public synchronized void setNewX(int newX) {
		this.newX = newX;
	}

	public synchronized int getNewY() {
		return newY;
	}

	public synchronized void setNewY(int newY) {
		this.newY = newY;
	}
	
	
}
