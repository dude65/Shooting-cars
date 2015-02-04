package cars;

import java.util.HashMap;
import java.util.Map;

//This class contains all of the shortcuts that occur in the game
public class KeyShortCuts {
	public Map<String, Integer[]> shortcuts = new HashMap<String, Integer[]>();
	
	public KeyShortCuts() {
		Integer[] player1 = {37, 38, 39, 40};
		Integer[] player2 = {83, 69, 70, 68};
		Integer[] player3 = {74, 73, 76, 75};
		Integer[] player4 = {100, 104, 102, 101};
		
		shortcuts.put("player1", player1);
		shortcuts.put("player2", player2);
		shortcuts.put("player3", player3);
		shortcuts.put("player4", player4);
	}
}
