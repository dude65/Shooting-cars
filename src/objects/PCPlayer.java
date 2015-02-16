package objects;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Arrays;

import cars.CantLoadException;
import cars.GameArea;
import cars.KeyShortCuts;
import cars.Window;

//Class for the player that is controlled by human
public class PCPlayer extends Player {
	
	//array that shows which keys are pressed
	boolean[] key = {false, false, false, false};
	Window w;
	
	public PCPlayer(int id, int x, int y, GameArea a, String car) throws CantLoadException {
		super(id, x, y, a, car);
		initiate();
	}
	
	public PCPlayer(int id, int gX, int sX, int gY, int sY, GameArea a, String car) throws CantLoadException {
		super(id, gX, sX, gY, sY, a, car);
		initiate();
	}
	
	private void initiate() {
		this.w = a.w;
		w.addKeyListener(new KListener());
	}
	
	//Returns a number of the direction according the pressed keys
	@Override
	int getEventNumber() {
		int n = -1;
		int el = arrayElements();
		
		if (el == 1) {
			if (key[0]) n = 0;
			if (key[1]) n = 2;
			if (key[2]) n = 4;
			if (key[3]) n = 6;
		} else if (el == 2) {
			if (key[0] && key[1]) n = 1;
			if (key[1] && key[2]) n = 3;
			if (key[2] && key[3]) n = 5;
			if (key[0] && key[3]) n = 7;
			
		}
		return n;
	}
	
	//Returns a number of how many keys are pressed
	@Override
	int arrayElements() {
		int el = 0;
		
		
		for (int i = 0; i < key.length; i++) {
			if (key[i]) el++;
		}
		
		return el;
	}
	
	private class KListener implements KeyListener {
		KeyShortCuts k = new KeyShortCuts();
		ArrayList<Integer> shortcuts = new ArrayList<Integer>();
		KListener() {
			shortcuts.addAll(Arrays.asList(k.shortcuts.get("player"+id)));
		}
		
		@Override
		public void keyPressed(KeyEvent e) {
			/*if (e.getKeyCode() == 10 && slow) {
				speed /= 2;
				slow = false;
			}
			if (e.getKeyCode() == 16) slow = true;*/
			if (shortcuts.contains(e.getKeyCode())) key[shortcuts.indexOf(e.getKeyCode())] = true;
			
		}

		@Override
		public void keyReleased(KeyEvent e) {
			if (shortcuts.contains(e.getKeyCode())) {
				key[shortcuts.indexOf(e.getKeyCode())] = false;
			}
		}

		@Override
		public void keyTyped(KeyEvent e) {
		}
		
	}
}
