package objects;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import cars.CantLoadException;
import cars.EditImages;
import cars.GameArea;


//Class for the player
public abstract class Player extends MovingObject {
	int id;
	String vehicle;
	EditImages ed;
	
	public Player(int id, int x, int y, GameArea a, String car) throws CantLoadException {
		super(x, y, a);
		initiate(id, car);
	}
	
	public Player(int id, int gX, int sX, int gY, int sY, GameArea a, String car) throws CantLoadException {
		super(gX, sX, gY, sY, a);
		initiate(id, car);
	}
	
	private void initiate(int id, String car) throws CantLoadException {
		this.id = id;
		
		ed = new EditImages();
		vehicle = car;
		loadImages();
	}

	@Override
	void loadImages() throws CantLoadException {
		imgList.add(ed.reverseImage(getCarImage("horizontal"), true));
		imgList.add(ed.reverseImage(getCarImage("oblique-up"), true));
		imgList.add(ed.reverseImage(getCarImage("vertical"), false));
		imgList.add(getCarImage("oblique-up"));
		imgList.add(getCarImage("horizontal"));
		imgList.add(getCarImage("oblique-down"));
		imgList.add(getCarImage("vertical"));
		imgList.add(ed.reverseImage(getCarImage("oblique-down"), true));
		
		setImage(4);
	}
	
	private BufferedImage getCarImage(String direction) throws CantLoadException {
		try {
			return ed.colorImage(ImageIO.read(new File("./img/cars/"+vehicle+"-"+direction+".png")), id);
		} catch (IOException e) {
			throw new CantLoadException("Can't load a car image","Class:"+ this.getClass().getName() + '\n' + "Method: getCarImage(String direction)" + '\n' + "Description: Can't load the car image: ./img/cars/"+vehicle+"-"+direction+".png" + '\n' + e);
		}
	}
	
	abstract int getEventNumber();
	abstract int arrayElements();
	

}
