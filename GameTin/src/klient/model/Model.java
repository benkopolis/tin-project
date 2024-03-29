package klient.model;

import java.util.LinkedList;
import java.util.List;
import java.util.Observable;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import klient.model.fields.Coin;
import klient.model.fields.Grass;
import klient.model.fields.Player;

public class Model extends Observable {
	
	protected LevelsManager lm = LevelsManager.getInstance();
	
	protected int localPlayerId;
	
	protected String localPlayerNick;
	
	protected PlayerInfo localPlayerInfo;
	
	protected List<PlayerInfo> players = new LinkedList<PlayerInfo>();
	
	protected boolean gameOn = false;
	
	protected boolean gameOff = false;
	
	protected LinkedBlockingQueue<Move> moves = new LinkedBlockingQueue<Move>(Integer.MAX_VALUE);
	
	private Coin localCoin = new Coin();
	
	private Grass localGrass = new Grass();
	
	public Model() {
	}
	
	/* ustawienie id i nick'a innych graczy */
	public Player addPlayer(String nick, int id) {
		Player p = new Player(id, nick);
		PlayerInfo pi = new PlayerInfo(p , 0, 0);
		players.add(pi);
		if(id == localPlayerId)
			localPlayerInfo = pi;
		return p;
	}

	public void setActualPlayerPosition(int id, int x, int y, boolean init) {
		Player p = null;
		try {
			for(PlayerInfo i: players) {
				if(i.getPlayer().getId() == id) {
					p = i.getPlayer();
					if(init == false) {
						this.lm.setField(i.getX(), i.getY(), new Grass());
					}
					i.setX(x);
					i.setY(y);
					break;
				}
			}
			this.lm.setField(x, y, p);
		} catch (IllegalOperation e) {
			e.printStackTrace();
		}
	}

	public  LevelsManager getLm() {
		return lm;
	}

	public  void setLm(LevelsManager lm) {
		this.lm = lm;
	}

	public  List<PlayerInfo> getPlayers() {
		return players;
	}

	public  void setPlayers(List<PlayerInfo> players) {
		this.players = players;
	}

	/* funkcja ma ustawiac zmienna (ktora wskazuje czy gra jest w toku) na true */
	public  void startGame() {
		this.gameOn = true;
	}
	
	public  void endGame() {
		this.gameOff = true;
		this.gameOn = false;
	}
	
	public  boolean isGameOn() {
		return gameOn;
	}
	
	public  boolean isGameOff() {
		return gameOff;
	}

	/* ustawia aktualna liczbe punktow */
	public void setPoints(int id, int points) {
		for(PlayerInfo pi: players) {
			if(pi.getPlayer().getId() == id)
				pi.getPlayer().setScore(points);
		}
		
	}

	public  int getLocalPlayerId() {
		return localPlayerId;
	}

	public  void setLocalPlayerId(int localPlayerId) {
		this.localPlayerId = localPlayerId;
	}

	public  String getLocalPlayerNick() {
		return localPlayerNick;
	}

	public  void setLocalPlayerNick(String localPlayerNick) {
		this.localPlayerNick = localPlayerNick;
	}

	public  void setGameOn(boolean gameOn) {
		this.gameOn = gameOn;
	}


	/* ustawia wspolrzedne monety ktore zostaly odebrane od serwera */
	public void setActualCoinPosition(int x, int y) {
		try {
			this.lm.setField(x, y, localCoin);
			if((this.lm.getField(x, y, false) instanceof Coin) == false)
				System.err.println("ERROR: -Failed to add new coin to board!");
		} catch (IllegalOperation e) {
			e.printStackTrace();
		}
		
	}



	public  PlayerInfo getLocalPlayerInfo() {
		return localPlayerInfo;
	}



	public  void setLocalPlayerInfo(PlayerInfo localPlayerInfo) {
		this.localPlayerInfo = localPlayerInfo;
	}



	public  Move workOnMoves(boolean insert, Move m) throws InterruptedException, IllegalOperation {
		int s = moves.size();
		if(insert) {
			moves.put(m);
//			System.out.println("wtawiono do kolejki");
			if(s == moves.size())
				throw new IllegalOperation("Put to queue didn't work.");
			return null;
		} else {
			Move mm = moves.poll(10L, TimeUnit.MILLISECONDS);
			//if (mm != null) System.out.println("pobrano");
			//else System.out.println("pobrano null");
//			if(s == moves.size())
//				throw new IllegalOperation("Poll from queue didn't work.");
			return mm;
		}
	}



	protected  void setMoves(LinkedBlockingQueue<Move> moves) {
		this.moves = moves;
	}

	public  Coin getLocalCoin() {
		return localCoin;
	}

	public  void setLocalCoin(Coin localCoin) {
		this.localCoin = localCoin;
	}

	public  Grass getLocalGrass() {
		return localGrass;
	}

	public  void setLocalGrass(Grass localGrass) {
		this.localGrass = localGrass;
	}

}
