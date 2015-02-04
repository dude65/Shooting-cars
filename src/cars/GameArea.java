package cars;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JPanel;

public class GameArea extends JPanel{
	private static final long serialVersionUID = 1L;

	Maps map;
	TerrainDecoder tDec;
	public float coef;
	public int square;
	
	LoadMap loading;
	Window w;
	
	Map<String, BufferedImage> images = new HashMap<String, BufferedImage>();
	
	public GameArea(Maps m, Window win) throws CantLoadException{
		tDec = new TerrainDecoder();
		map = m;
		this.w = win;
		this.setPreferredSize(new Dimension(900,640));;
		this.setBackground(Color.decode("#a7835f"));
		
		loading = new LoadMap(this, images, map, tDec, w);
		loading.load();
		
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		setCoeficient();
		setBackground(g);
		drawTerrain(g);
		drawBuildings(g);
		drawLine(g);
		drawLights(g);
		drawMovingObjects(g);
		
	}
	
	//Sets coefficient for counting image sizes and size of one square
	private void setCoeficient() {
		float[] coefList = {(float) this.getWidth()/960, (float) this.getHeight()/640, (float) 1.0};
		Arrays.sort(coefList);
		square = (int) Math.floor(64.0 * coefList[0]);
		coef = (float) square/64;
	}
	
	//Sets background of the map
	private void setBackground(Graphics g) {
		BufferedImage bgImage = images.get("bg");
		g.drawImage(bgImage, getX(1), getY(1), 15 * square, 10 * square, null);
	}
	
	//draw terrain images
	private void drawTerrain(Graphics g) {
		int x = 1;
		int y = 1;
		
		for (int i = 0; i < 150; i++) {
			if (x > 15) {
				y++;
				x = 1;
			}
			
			if (!map.getTerrainData().get(i).equals("0")) {
				BufferedImage tImage = images.get(map.getTerrainData().get(i));
				g.drawImage(tImage, getX(x), getY(y), square, square, null);
			}
			
			
			x++;
		}
	}
	
	//draw buildings images
	private void drawBuildings(Graphics g) {
		for (Map.Entry<String,String> entry : map.getBuildingsData().entrySet()) {
		    String key = entry.getKey().substring(1);
		    String coordinates = entry.getValue();
		    
		    String[] field = coordinates.split(":");
		    int x = Integer.parseInt(field[0]);
		    int y = Integer.parseInt(field[1]);
		    
		    g.drawImage(images.get("building_"+key), getX(x), getY(y), square * 2, square * 2, null);
		    
		    
		}
	}
	
	//draw start line
	private void drawLine(Graphics g) {
		String direction = map.getLine().get("€direction");
		int x = Integer.parseInt(map.getLine().get("€xline"));
		int y = Integer.parseInt(map.getLine().get("€yline"));
		int xSize, ySize;
		
		int addX = 0;
		int addY = 0;
		
		if (direction.equals("left") || direction.equals("right")) {
			xSize = (int) Math.floor(12 * coef);
			ySize = square;
		} else {
			xSize = square;
			ySize = (int) Math.floor(12 * coef);
		}
		
		if (direction.equals("right")) addX = square - xSize;
		if (direction.equals("down")) addY = square - ySize;
		
		
		g.drawImage(images.get("line"), getX(x) + addX, getY(y) + addY, xSize, ySize, null);
		
	}
	
	//load lights
	private void drawLights(Graphics g) {
		int x = Integer.parseInt(map.getLine().get("€xlights"));
		int y = Integer.parseInt(map.getLine().get("€ylights"));
		
		g.drawImage(images.get("lights_"+map.lightsColor), getX(x), getY(y), square, square, null);
	}
	
	//draw movingObjects
	private void drawMovingObjects(Graphics g) {
		for (int i = 0; i < loading.moveAble.size(); i++) {
			BufferedImage img = loading.moveAble.get(i).img;
			int x = getX(0) + loading.moveAble.get(i).gridX * square + (int) Math.floor(loading.moveAble.get(i).squareX * coef);
			int y = getY(0) + loading.moveAble.get(i).gridY * square + (int) Math.floor(loading.moveAble.get(i).squareY * coef);
			int width = (int) Math.ceil(img.getWidth() * coef);
			int height = (int) Math.ceil(img.getHeight() * coef);
			
			g.drawImage(img, x, y, width, height, null);
		}
	}
	
	//returns x coordinate of the image in the game area
	public int getX(int x) {
		return  (this.getWidth()/2 - (int) Math.floor(960 * coef / 2)) + (x - 1) * square;
	}
	
	//returns y coordinate of the image in the game area
	public int getY(int y) {
		return  (this.getHeight()/2 - (int) Math.floor(640 * coef / 2)) + (y - 1) * square;
	}
	
	//returns size of the image
	protected int getSize(int size) {
		return (int) Math.round(size*coef);
	}
}
