package cars;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.util.Arrays;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class GameArea extends JPanel{
	private static final long serialVersionUID = 1L;

	Maps map;
	TerrainDecoder tDec = new TerrainDecoder();
	float coef;
	int square;
	
	public GameArea(Maps m) {

		map = m;
		this.setPreferredSize(new Dimension(900,640));;
		this.setBackground(Color.decode("#a7835f"));
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		setCoeficient();
		setBackground(g);
		loadTerrain(g);
		loadBuildings(g);
		
	}
	
	//Sets coefficient for counting image sizes and size of one square
	protected void setCoeficient() {
		float[] coefList = {(float) this.getWidth()/960, (float) this.getHeight()/640, (float) 1.0};
		Arrays.sort(coefList);
		square = (int) Math.floor(64.0 * coefList[0]);
		coef = (float) square/64;
	}
	
	//Sets background of the map
	protected void setBackground(Graphics g) {
		ImageIcon background = new ImageIcon("./img/terrain/background/"+map.getMediaData().get("€background")+".png");
		Image bgImage = background.getImage();
		g.drawImage(bgImage, getX(1), getY(1), 15 * square, 10 * square, null);
	}
	
	//Loads terrain images
	protected void loadTerrain(Graphics g) {
		int x = 1;
		int y = 1;
		
		for (int i = 0; i < 150; i++) {
			if (x > 15) {
				y++;
				x = 1;
			}
			
			if (!map.getTerrainData().get(i).equals("0")) {
				try {
					
					ImageIcon tImage = tDec.getImage(map.getTerrainData().get(i));
					g.drawImage(tImage.getImage(), getX(x), getY(y), square, square, null);
				} catch (CantLoadException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			
			x++;
		}
	}
	
	//load buildings images
	protected void loadBuildings(Graphics g) {
		for (Map.Entry<String,String> entry : map.getBuildingsData().entrySet()) {
		    String key = entry.getKey().substring(1);
		    String coordinates = entry.getValue();
		    
		    String[] field = coordinates.split(":");
		    int x = Integer.parseInt(field[0]);
		    int y = Integer.parseInt(field[1]);
		    
		    ImageIcon buildingIcon = new ImageIcon("./img/buildings/"+key+".png");
		    Image building = buildingIcon.getImage();
		    g.drawImage(building, getX(x), getY(y), square * 2, square * 2, null);
		    
		    
		}
	}
	
	//returns x coordinate of the image in the game area
	protected int getX(int x) {
		return  (this.getWidth()/2 - (int) Math.floor(960 * coef / 2)) + (x - 1) * square;
	}
	
	//returns y coordinate of the image in the game area
	protected int getY(int y) {
		return  (this.getHeight()/2 - (int) Math.floor(640 * coef / 2)) + (y - 1) * square;
	}
	
	//returns size of the image
	protected int getSize(int size) {
		return (int) Math.round(size*coef);
	}
}
