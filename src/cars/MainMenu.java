package cars;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class MainMenu {
	static Window window;
	MainMenu(Window w) {
		window = w;
	}
	
	public void setUp() {
		window.con.add(Heading(), BorderLayout.NORTH);
		window.con.add(Menu(), BorderLayout.CENTER);
		window.con.add(Bottom(), BorderLayout.SOUTH);
		
		window.setContentPane(window.con);
	}
	
	public static JPanel Heading() {
		JPanel panel = new JPanel();
		
		FlowLayout f = new FlowLayout(FlowLayout.CENTER);
		panel.setLayout(f);
		
		JLabel label = new JLabel("Shooting cars");
		label.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 40));
		
		panel.add(label);
		
		return panel;
	}
	
	public static JPanel Menu() {
		JPanel panel = new JPanel();
		
		GridLayout gl = new GridLayout(0,1);
		panel.setLayout(gl);
		
		panel.setPreferredSize(new Dimension(100,500));
		//Size of the content - doesn't work!
		
		panel.setBorder(BorderFactory.createEmptyBorder(100,350,100,350));
		//Empty border around the content
		
		gl.setVgap(10);
		
		
		JButton newg = new JButton("New game");
		JButton netg = new JButton("Network game");
		JButton sett = new JButton("Settings");
		JButton auth = new JButton("Authors");
		JButton quit = new JButton("Quit");
		
		newg.addActionListener(new NewGameListener(window));
		
		panel.add(newg);
		panel.add(netg);
		panel.add(sett);
		panel.add(auth);
		panel.add(quit);
		
		
		return panel;
	}
	
	public static JPanel Bottom() {
		JPanel panel = new JPanel();
		
		FlowLayout f = new FlowLayout(FlowLayout.LEFT);
		panel.setLayout(f);
		
		JLabel label = new JLabel("© Ondøej Marek, 2014 - 15");
		
		panel.add(label);
		
		return panel;
	}
}
