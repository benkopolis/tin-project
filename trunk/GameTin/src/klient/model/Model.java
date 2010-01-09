package klient.model;

import java.util.Observable;

import klient.model.fields.Player;

public class Model extends Observable {
	
	protected LevelsManager lm = LevelsManager.getInstance();
	
	protected Player[] players = null;
	
	/* ustawienie id klienta */
	public void setPlayerID(int id) {
		// TODO Auto-generated method stub
		
	}
	
	/* funkcja obslugujaca odrzucenie gracza */
	public void connectionDenied(String string) {
		// TODO Auto-generated method stub
		
	}
	
	/* ustawienie id i nick'a innych graczy */
	public void addPlayer(String nick, int id) {
		// TODO Auto-generated method stub
		
	}

	public void setActualPlayerPosition(int id, int x, int y) {
		// TODO Auto-generated method stub
		
	}

	public void countDown(int parseInt) {
		// TODO Auto-generated method stub
		
	}

	public synchronized LevelsManager getLm() {
		return lm;
	}

	public synchronized void setLm(LevelsManager lm) {
		this.lm = lm;
	}

	public synchronized Player[] getPlayers() {
		return players;
	}

	public synchronized void setPlayers(Player[] players) {
		this.players = players;
	}

}
