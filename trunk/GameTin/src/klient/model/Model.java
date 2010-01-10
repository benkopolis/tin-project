package klient.model;

import java.util.LinkedList;
import java.util.List;
import java.util.Observable;
import java.util.concurrent.LinkedBlockingQueue;

import klient.model.fields.Coin;
import klient.model.fields.Player;

public class Model extends Observable {
	
	protected LevelsManager lm = LevelsManager.getInstance();
	
	protected int localPlayerId;
	
	protected String localPlayerNick;
	
	protected PlayerInfo localPlayerInfo;
	
	protected List<PlayerInfo> players = new LinkedList<PlayerInfo>();
	
	protected boolean gameOn = false;
	
	protected boolean gameOff = false;
	
	protected LinkedBlockingQueue<Move> moves = new LinkedBlockingQueue<Move>();
	
	
	public Model() {
	}
	
	
	/* funkcja obslugujaca odrzucenie gracza */
	public void connectionDenied(String string) {
		// TODO Auto-generated method stub
		
	}
	
	/* ustawienie id i nick'a innych graczy */
	public Player addPlayer(String nick, int id) {
		Player p = new Player(id, nick);
		players.add(new PlayerInfo(p , 0, 0));
		if(nick == localPlayerNick)
			localPlayerId = id;
		return p;
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
	public void setPoints(int id, int points) {
		for(PlayerInfo pi: players) {
			if(pi.getPlayer().getId() == id)
				pi.getPlayer().setScore(points);
		}
		
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


	/* ustawia wspolrzedne monety ktore zostaly odebrane od serwera */
	public void setActualCoinPosition(int x, int y) {
		try {
			this.lm.setField(x, y, new Coin());
		} catch (IllegalOperation e) {
			e.printStackTrace();
		}
		
	}



	public synchronized PlayerInfo getLocalPlayerInfo() {
		return localPlayerInfo;
	}



	public synchronized void setLocalPlayerInfo(PlayerInfo localPlayerInfo) {
		this.localPlayerInfo = localPlayerInfo;
	}



	public synchronized LinkedBlockingQueue<Move> getMoves() {
		return moves;
	}



	protected synchronized void setMoves(LinkedBlockingQueue<Move> moves) {
		this.moves = moves;
	}

}
