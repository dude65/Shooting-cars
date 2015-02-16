package cars;

import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.Timer;

import objects.MovingObject;
import objects.PCPlayer;
import objects.StaticObject;

//This class loads all the images and other data for each map.
public class LoadMap {
	
	GameArea a;
	Map<String, BufferedImage> images;
	Maps m;
	TerrainDecoder e;
	
	ArrayList<StaticObject> stationary = new ArrayList<StaticObject>();
	ArrayList<MovingObject> moveAble = new ArrayList<MovingObject>();
	
	public int pps = 40; 
	Timer time = new Timer(1000/pps, new MoveObjects());
	
	LoadMap(GameArea a) {
		this.a = a;
		this.images = a.images;
		this.m = a.map;
		this.e = a.tDec;
	}
	
	protected void load() throws CantLoadException {
		//background
		images.put("bg", loadImage("./img/terrain/background/"+m.getMediaData().get("€background")+".png"));
				
		//buildings
		loadBuildings();
		
		//terrain
		loadTerrain();
		
		//start line
		loadLine();
		
		//lights
		loadLights();
		
		//movable objects
		loadCars();
		
		time.start();
	}
	
	private void loadCars() throws CantLoadException {
		moveAble.add(new PCPlayer(1,300,300,a,"lorry"));
		moveAble.add(new PCPlayer(2,450,450,a,"lorry"));
		//moveAble.add(new PCPlayer(3,150,150,a,"lorry",w));
		//moveAble.add(new PCPlayer(4,500,500,a,"lorry",w));
	}
	
	private void loadLine() throws CantLoadException {
		String direction = m.getLine().get("€direction");
		int gridX = Integer.parseInt(m.getLine().get("€xline"));
		int gridY = Integer.parseInt(m.getLine().get("€yline"));
		
		int squareX = 0, squareY = 0;
		
		String position;
		
		if (direction.equals("left") || direction.equals("right")) {
			position = "vertical";
		} else {
			position = "horizontal";
		}
		
		if (direction.equals("right")) squareX = 52;
		if (direction.equals("down")) squareY = 52;
		
		String[] vals = {"line"};
		images.put("line", loadImage("./img/route/finish_line-"+position+".png"));
		
		stationary.add(new StaticObject(gridX, squareX, gridY, squareY, a, false, false, vals));
	}
	
	private void loadBuildings() throws CantLoadException {
		for (Map.Entry<String, String> entry : m.getBuildingsData().entrySet()) {
			String key = entry.getKey().substring(1);
			String code = "building_"+key;
			String coordinates = entry.getValue();
		    
			if (!images.containsKey(key)) images.put(code, loadImage("./img/buildings/"+key+".png"));
			
			String[] field = coordinates.split(":");
		    int x = Integer.parseInt(field[0]);
		    int y = Integer.parseInt(field[1]);
		    
		    String[] imgList = {code};
		    StaticObject building = new StaticObject(x, 0, y, 0, a, true, true, imgList);
		    building.xSize = 2;
		    building.ySize = 2;
		    
		    stationary.add(building);
		}
	}
	
	private void loadTerrain() throws CantLoadException {
		ArrayList<String> data = m.getTerrainData();
		int x = 1;
		int y = 1;
		
		for (int i = 0; i < 150; i++) {
			if (x > 15) {
				x = 1;
				y++;
			}
			
			String code = data.get(i);
			if (!code.equals("0")) {
				BufferedImage img;
				
				if (!images.containsKey(code)) {
					img = e.getImage(code);
					images.put(code, img);
				} else {
					img = images.get(code);
				}
				
				String[] imgNames = {code};
				
				stationary.add(new StaticObject(x, 0, y, 0, a, true, false, imgNames));
			}
			
			x++;
		}
	}
	
	private void loadLights() throws CantLoadException {
		String[] codes = {"lights_nothing", "lights_red", "lights_yellow", "lights_green", "lights_all"};
		
		for (int i = 0; i < codes.length; i++) {
			images.put(codes[i], loadImage("./img/route/"+codes[i]+".png"));
		}
		
		int x = Integer.parseInt(m.getLine().get("€xlights"));
		int y = Integer.parseInt(m.getLine().get("€ylights"));
		
		stationary.add(new StaticObject(x, 0, y, 0, a, true, true, codes));
	}
	
	private BufferedImage loadImage(String path) throws CantLoadException {
		try {
			return ImageIO.read(new File(path));
		} catch (IOException e) {
			throw new CantLoadException("Can't load an image","Class:LoadMap"+'\n'+"Method: loadImage(String path)"+'\n'+"Description: Can't load the image:"+path+'\n'+"IOException:"+e);
		}
	}
	
	private class MoveObjects implements ActionListener {
		//Timer
		int repeating = 0;
		Rectangle r = new Rectangle();
		
		@Override
		public void actionPerformed(ActionEvent e) {
			//move all the moveable object
			for (int i = 0; i < moveAble.size(); i++) {
				r.add(moveAble.get(i).move());
			}
			
			//animate static object in 200 milliseconds
			if ((float) 1000/pps *  repeating >= 200) {
				for (int i = 0; i < stationary.size(); i++) {
					r.add(stationary.get(i).animate());					
				}
				
				repeating = 0;
			}
			
			repeating++;
			
			a.repaint(r);
		}
		
		
		
	}
}
