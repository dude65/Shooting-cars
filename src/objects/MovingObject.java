package objects;

import java.awt.Rectangle;

import cars.CantLoadException;
import cars.GameArea;

//This is common class for all of the moving objects
public abstract class MovingObject extends Object {
	//speed - how many pixels the object is able to move in the second
	//maxSpeed - can be changed
	int speed = 0;
	int maxSpeed = 80;
	int speedChange = 1;
	
	boolean slow = true;
	
	public MovingObject(int x, int y, GameArea a) throws CantLoadException {
		super(x, y, a);
	}
	
	public MovingObject(int gX, int sX, int gY, int sY, GameArea a) throws CantLoadException {
		super(gX, sX, gY, sY, a);
	}
	
	//move the object
	public Rectangle move() {
		//e - It is the number of the direction in which the object should move according the keys etc. (direction number - explanation in the method changeSpeed(int))
		int e = getEventNumber();
		//Informations about the image before move
		int[] old = {imgList.indexOf(img), getCoordinate(gridX,squareX), getCoordinate(gridY,squareY), img.getWidth(), img.getHeight()};
		
		//Changing the speed and images if it is needed
		if (e != -1) {
			changeSpeed(e);
			checkSpeed();
		}
		
		//Change position
		if (speed != 0) changePosition(old);
		
		//Informations about the image after move and redrawing
		int[] newImg = {imgList.indexOf(img), getCoordinate(gridX,squareX), getCoordinate(gridY,squareY), img.getWidth(), img.getHeight()};
		return repaintObject(old,newImg);
		
	}
	
	//This method change speed and images eventually
	private void changeSpeed(int e) {
		//Value is the number of the direction in which the object moves.
		//0 - west
		//1 - north-west
		//2 - north
		//etc..
		int imgN = imgList.indexOf(img);
		int value;
		if (speed >= 0) {
			value = imgN;
		} else {
			value = getFieldNumber(imgN+4);
		}
		
		//relativeChange - it is a number of the distance between direction number and "e" number
		int relativeChange;
		int leftDistance = keyDistance(value, e, false);
		int rightDistance = keyDistance(value, e, true);
		relativeChange = Math.min(leftDistance, rightDistance);
		
		if (leftDistance == relativeChange) relativeChange *= -1;
		
		if (relativeChange == 0) {
			//acceleration
			speed += speedChange * speedSign();
		} else if (Math.abs(relativeChange) > 0 && Math.abs(relativeChange) <= 2) {
			//turning right or left 
			int n;
			if (relativeChange > 0) {
				n = getFieldNumber(imgN - 1);
			 } else {
				n = getFieldNumber(imgN + 1);
			 }
			
			float coef;
			if (Math.round((float) n/2) == (float) n/2) {
				coef = (float) 1.41;
			} else {
				coef = (float) (1/1.41);
			}
			
			speed = (int) Math.ceil(coef * speed/10 * 7);
			setImage(n);
		} else if (Math.abs(relativeChange) == 4) {
			//slowing down
			speed -= 2 * speedChange * speedSign();
		}
		
	}
	
	//This method change the position according to the direction
	private void changePosition(int[] old) {
		int newImg = imgList.indexOf(img);
		float change = (float) speed/a.loading.pps * 2;
		checkColision(newImg,change);
		
		switch (newImg) {
		case 0: squareX -= change; break;
		case 1: squareX -= change; squareY -= change; break;
		case 2: squareY -= change; break;
		case 3: squareX += change; squareY -= change; break;
		case 4: squareX += change; break;
		case 5: squareX += change; squareY += change; break;
		case 6: squareY += change; break;
		case 7: squareX -= change; squareY += change; break;
		}
		
		checkGrid();
	}
	
	//This method is going to check collisions
	private void checkColision(int imgN, float change) {
		
	}
	
	//This method controls whether the object is still on the same square
	//It doesn't work for very quick object probably
	private void checkGrid() {
		if (squareX < 0) {
			gridX--;
			squareX += 64;
		}
		
		if (squareX >= 64) {
			gridX++;
			squareX -= 64;
		}
		
		if (squareY < 0) {
			gridY--;
			squareY += 64;
		}
		
		if (squareY >= 64) {
			gridY++;
			squareY -= 64;
		}
	}
	
	//This method controls whether the speed is not higher than maximum
	private void checkSpeed() {
		int d = imgList.indexOf(img);
		if (Math.round((float) d/2) == (float) d/2) {
			if (Math.abs(speed) > maxSpeed) speed = speedSign() * maxSpeed;
		} else {
			if (Math.abs(speed) > (maxSpeed * (1/1.41))) speed = (int) (speedSign() * (maxSpeed* (1/1.41)));
		}
		
	}
	
	//It counts a 'dirty area' and returns its bounds
	private Rectangle repaintObject(int[] oldImage, int[] newImage) {
		Rectangle r = new Rectangle();
		if (!oldImage.equals(newImage)) {
			float change = speed/20;
			int direction = imgList.indexOf(img);
			
			int topX = Math.min(a.getX(0) + oldImage[1],a.getX(0) + newImage[1]) - 1;
			int topY = Math.min(a.getY(0) + oldImage[2], a.getY(0) +  newImage[2]) - 1;
			
			int width;
			if (direction != 2 && direction != 6) {
				width = (int) Math.ceil(a.coef * (oldImage[3] + newImage[3] - Math.abs(change)));
			} else {
				width = Math.max(oldImage[3], newImage[3]);
				width = (int) Math.ceil(a.coef * width);
			}
			width += 2;
			
			int height;
			if (direction != 0 && direction != 4) {
				height = (int) Math.ceil(a.coef * (oldImage[4] + newImage[4] - Math.abs(change)));
			} else {
				height = Math.max(oldImage[4], newImage[4]);
				height = (int) Math.ceil(a.coef * height);
			}
			height += 2;
			
			r.add(new Rectangle(topX, topY, width, height));
			//a.repaint(topX,topY,width,height);
		}
		return r;
	}
	
	//It counts a distance between the direction and "e" from
	// a) add = false; -> left side
	// b) add = true; -> right side
	private int keyDistance(int imgNumber, int keyEvent, boolean add) {
		int distance = 0;
		int adder;
		
		if (add) {
			adder = 1;
		} else {
			adder = -1;
		}
		
		while(imgNumber != keyEvent) {
			keyEvent = getFieldNumber(keyEvent+adder);
			
			distance++;
		}
		
		return distance;
	}
	
	//Method that returns sign of the speed. Very useful
	private int speedSign() {
		int sign;
		if (speed >= 0) {
			sign = 1;
		} else {
			sign = -1;
		}
		return sign;
	}
	
	//returns the correct number of the image
	//examples:
	//getFieldNumber(-2) = 6; (south)
	//getFieldNumber(2) = 2; (north)
	//getFieldNumber(12) = 4; (east)
	private int getFieldNumber(int f) {
		if (f >= 8) f-=8;
		if (f < 0) f+= 8;
		return f;
	}
	
	//It returns a coordinate on the map (for redrawing purposes)
	private int getCoordinate(int grid, float square) {
		if (square >= 64) {
			grid++;
			square -= 64;
		}
		
		if (square < 0) {
			grid--;
			square += 64;
		}
		
		return (int) Math.floor((float) grid * a.square + square * a.coef);
	}
	
	protected void setSpeedChange(int change) {
		speedChange = change;
	}
	
	
	
	abstract void loadImages() throws CantLoadException;
	abstract int getEventNumber();
	abstract int arrayElements();
}
