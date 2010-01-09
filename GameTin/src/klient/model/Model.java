package klient.model;

import java.util.Observable;

public class Model extends Observable {
	
	public LevelsManager lm = LevelsManager.getInstance();
	
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

	public void setActualPlayersPosition(int id, int x, int y) {
		// TODO Auto-generated method stub
		
	}

}
