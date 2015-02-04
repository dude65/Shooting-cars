package cars;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JOptionPane;

public class NewGameListener implements ActionListener {
	Window window;
	Maps map;
	
	NewGameListener (Window w) {
		window = w;
	}
	
	@Override
	public void actionPerformed(ActionEvent ae) {
		
		try {
			map = new Maps("./maps/Farmer_circuit.map");
			window.con.removeAll();
			window.con.add(new GameArea(map, window), BorderLayout.CENTER);
			window.con.add(new JButton("Bottom"), BorderLayout.SOUTH);
			window.setContentPane(window.con);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(window, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			
			System.err.println(e);
		}
		
	}

}
