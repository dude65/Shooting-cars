package objects;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

import cars.CantLoadException;
import cars.GameArea;

//This is common class for all of the objects in the game
public abstract class Object {
	//Coordinates
	//gridX, gridY - They point together the square where the object is set
	//squareX, squareY - They point together the precise point on the square where the object is set
	public int gridX;
	public int gridY;
	public float squareX;
	public float squareY;
	
	//Images
	//imgList - It is a list for all of the images of the object
	//img - This is the image which is being used
	public ArrayList<BufferedImage> imgList = new ArrayList<BufferedImage>();
	public BufferedImage img;
	
	GameArea a;
	
	public Object(int x, int y, GameArea a) throws CantLoadException {
		this.a = a;
		
		gridX = (int) Math.floor(x/64);
		gridY = (int) Math.floor(y/64);
		
		squareX = x - gridX * 64;
		squareY = y - gridY * 64;
	}
	
	public Object(int gX, int sX, int gY, int sY, GameArea a) throws CantLoadException {
		this.a = a;
		
		gridX = gX;
		squareX = sX;
		
		gridY = gY;
		squareY = sY;
	}
	
	protected void setImage(int number) {
		img = imgList.get(number);
	}
	
	abstract void loadImages() throws CantLoadException;
}
