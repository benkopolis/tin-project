/**
 * 
 */
package klient.model.fields;

import klient.model.fields.Field;

/**
 * @author zby
 *
 */
public class Player extends Field {
	
	protected int id;
	protected int score;
	protected String nick;

	public Player(int id2, String nick2) {
		sign = 'p';
		id = id2;
		nick = nick2;
	}

	public synchronized int getId() {
		return id;
	}

	public synchronized void setId(int id) {
		this.id = id;
	}

	public synchronized int getScore() {
		return score;
	}

	public synchronized void setScore(int score) {
		this.score = score;
	}

	public synchronized String getNick() {
		return nick;
	}

	public synchronized void setNick(String nick) {
		this.nick = nick;
	}
	
	
	

}
