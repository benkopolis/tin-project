/**
 * 
 */
package klient.controller;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import klient.model.Move;

/**
 * @author zby
 *
 */
public class Main {

	private class TestThread extends Thread {
		
		protected LinkedBlockingQueue<Move> moves = null;
		
		public TestThread(LinkedBlockingQueue<Move> m) {
			setDaemon(true);
			moves = m;
			start();
		}
		
		@Override
		public synchronized void run() {
			Move  m = null;
			while(!isInterrupted()) {
//				try {
					try {
						m = moves.poll(10000L, TimeUnit.MILLISECONDS);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			}
		}
	}
	
	public TestThread tt = null;
	
	public Main(LinkedBlockingQueue<Move> moves) {
		TestThread tt = new TestThread(moves);
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ViewsController viewscontrl = new ViewsController();
		Main m = new Main(new LinkedBlockingQueue<Move>());
		System.out.println("Loool");
	}

	/**
	 * 
	 */
	public static void test() {
		LinkedBlockingQueue<Move> moves = new LinkedBlockingQueue<Move>(Integer.MAX_VALUE);
		Main main = new Main(moves);
		int i=0;
		while(i<10) {
			try {
				Thread.sleep(50L);
				if(moves.offer(new Move(0,0,0,0)))
					System.out.println("Inserted!");
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			++i;
		}
	}

}
