package cars;
import java.awt.*;

import javax.swing.*;



public class Window extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Container con;
	BorderLayout srb;
	MainMenu menu;
	
	public Window () {
		super ("Shooting cars");
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setVisible(true);
		
		con = getContentPane();
		srb = new BorderLayout();
		con.setLayout(srb);
		setContentPane(con);
		
	}
	

	public void initialize() {
		menu = new MainMenu(this);
		menu.setUp();
	}
	
	
}
