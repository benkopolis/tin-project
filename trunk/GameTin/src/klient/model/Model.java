package klient.model;

import java.util.LinkedList;
import java.util.List;
import java.util.Observable;

import klient.model.fields.Player;

public class Model extends Observable {
	
	protected LevelsManager lm = LevelsManager.getInstance();
	
	protected int localPlayerId;
	
	protected String localPlayerNick;
	
	protected List<PlayerInfo> players = new LinkedList<PlayerInfo>();
	
	protected boolean gameOn = false;
	
	protected boolean gameOff = true;
	
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
		players.add(new PlayerInfo(new Player(id, nick), 0, 0));
		if(nick == localPlayerNick)
			localPlayerId = id;
	}

	public void setActualPlayerPosition(int id, int x, int y) {
		Player p = null;
		for(PlayerInfo i: players) {
			if(i.getPlayer().getId() == id) {
				p = i.getPlayer();
				break;
			}
		}
		try {
			this.lm.setField(x, y, p);
		} catch (IllegalOperation e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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

	public synchronized List<PlayerInfo> getPlayers() {
		return players;
	}

	public synchronized void setPlayers(List<PlayerInfo> players) {
		this.players = players;
	}

	/* funkcja ma ustawiac zmienna (ktora wskazuje czy gra jest w toku) na true */
	public synchronized void startGame() {
		this.gameOff = false;
		this.gameOn = true;
	}
	
	public synchronized void endGame() {
		this.gameOff = true;
		this.gameOn = false;
	}
	
	public synchronized boolean isGameOn() {
		return gameOn;
	}
	
	public synchronized boolean isGameOff() {
		return gameOff;
	}

	/* ustawia aktualna liczbe punktow */
	public void setPoints(int points) {
		// TODO Auto-generated method stub
		
	}

	public synchronized int getLocalPlayerId() {
		return localPlayerId;
	}

	public synchronized void setLocalPlayerId(int localPlayerId) {
		this.localPlayerId = localPlayerId;
	}

	public synchronized String getLocalPlayerNick() {
		return localPlayerNick;
	}

	public synchronized void setLocalPlayerNick(String localPlayerNick) {
		this.localPlayerNick = localPlayerNick;
	}

	public synchronized void setGameOn(boolean gameOn) {
		this.gameOn = gameOn;
	}

}
