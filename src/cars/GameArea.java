package cars;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
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
	
	public LoadMap loading;
	public Window w;
	
	public Map<String, BufferedImage> images = new HashMap<String, BufferedImage>();
	
	public GameArea(Maps m, Window win) throws CantLoadException{
		tDec = new TerrainDecoder();
		map = m;
		this.w = win;
		this.setPreferredSize(new Dimension(900,640));;
		this.setBackground(Color.decode("#a7835f"));
		
		loading = new LoadMap(this);
		loading.load();
		
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		setCoeficient();
		setBackground(g);
		
		ArrayList<objects.Object> objList = new ArrayList<objects.Object>();
		objList.addAll(loading.stationary);
		objList.addAll(loading.moveAble);
		
		drawObjects(g, objList);
		
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
	
	private void drawObjects(Graphics g, ArrayList<objects.Object> objectList) {
		for (int i = 0; i < objectList.size(); i++) {
			objects.Object obj = objectList.get(i);
			
			BufferedImage img = obj.img;
			
			
			int x = getX(0) + obj.gridX * square + (int) Math.floor(obj.squareX * coef);
			int y = getY(0) + obj.gridY * square + (int) Math.floor(obj.squareY * coef);
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
