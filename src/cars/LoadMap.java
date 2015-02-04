package cars;

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

//This class loads all the images and other data for each map.
public class LoadMap {

	//To do: gather all the terrain, buildings and other images to the ArrayList of the future class "StaticObject"
	
	GameArea a;
	Map<String, BufferedImage> images;
	Maps m;
	TerrainDecoder e;
	Window w;
	
	ArrayList<MovingObject> moveAble = new ArrayList<MovingObject>();
	Timer time = new Timer(25,new MoveObjects());
	
	LoadMap(GameArea a, Map<String, BufferedImage> i, Maps m, TerrainDecoder e, Window w) {
		this.a = a;
		this.images = i;
		this.m = m;
		this.e = e;
		this.w = w;
	}
	
	protected void load() throws CantLoadException {
		//background
		images.put("bg", loadImage("./img/terrain/background/"+m.getMediaData().get("€background")+".png"));
		
		//start line
		loadLine();
		
		//buildings
		loadBuildings();
		
		//terrain
		loadTerrain();
		
		//lights
		loadLights();
		
		//movable objects
		loadCars();
		
		time.start();
	}
	
	private void loadCars() throws CantLoadException {
		moveAble.add(new PCPlayer(1,300,300,a,"lorry",w));
		moveAble.add(new PCPlayer(2,450,450,a,"lorry",w));
		//moveAble.add(new PCPlayer(3,150,150,a,"lorry",w));
		//moveAble.add(new PCPlayer(4,500,500,a,"lorry",w));
	}
	
	private void loadLine() throws CantLoadException {
		String direction = m.getLine().get("€direction");
		String position;
		
		if (direction.equals("left") || direction.equals("right")) {
			position = "vertical";
		} else {
			position = "horizontal";
		}
		
		images.put("line", loadImage("./img/route/finish_line-"+position+".png"));
	}
	
	private void loadBuildings() throws CantLoadException {
		for (Map.Entry<String, String> entry : m.getBuildingsData().entrySet()) {
			String key = entry.getKey().substring(1);
			
			if (!images.containsKey(key)) images.put("building_"+key, loadImage("./img/buildings/"+key+".png"));
		}
	}
	
	private void loadTerrain() throws CantLoadException {
		for (int i = 0; i < 150; i++) {
			putTerrainImage(m.getTerrainData().get(i));
		}
	}
	
	protected void putTerrainImage(String code) throws CantLoadException {
		if (!images.containsKey(code) && !code.equals("0")) {
			images.put(code, e.getImage(code));
		}
	}
	
	private void loadLights() throws CantLoadException {
		images.put("lights_nothing", loadImage("./img/route/lights_nothing.png"));
		images.put("lights_red", loadImage("./img/route/lights_red.png"));
		images.put("lights_yellow", loadImage("./img/route/lights_yellow.png"));
		images.put("lights_green", loadImage("./img/route/lights_green.png"));
		images.put("lights_all", loadImage("./img/route/lights_all.png"));
	}
	
	private BufferedImage loadImage(String path) throws CantLoadException {
		try {
			return ImageIO.read(new File(path));
		} catch (IOException e) {
			throw new CantLoadException("Can't load an image","Class:LoadMap"+'\n'+"Method: loadImage(String path)"+'\n'+"Description: Can't load the image:"+path+'\n'+"IOException:"+e);
		}
	}
	
	private class MoveObjects implements ActionListener {
		//When it is called, it moves all the moveable object
		
		@Override
		public void actionPerformed(ActionEvent e) {
			for (int i = 0; i < moveAble.size(); i++) {
				moveAble.get(i).move();
			}
		}
		
	}
}
