import java.awt.*;
import javax.swing.*;



public class Window extends JFrame {
	Container con;
	BorderLayout srb;
	
	public Window () {
		super ("Shooting cars");
	}
	
	@SuppressWarnings("static-access")
	public void initialize() {
		
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setVisible(true);
		
		con = getContentPane();
		srb = new BorderLayout();
		con.setLayout(srb);
		setContentPane(con);
		
		con.add(Heading(), srb.NORTH);
		con.add(Menu(), srb.CENTER);
		con.add(Bottom(), srb.SOUTH);
		
		setContentPane(con);
		
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
		
		JLabel label = new JLabel("© Ondøej Marek, 2014");
		
		panel.add(label);
		
		return panel;
	}
}
