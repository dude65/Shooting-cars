package objects;

import cars.CantLoadException;
import cars.GameArea;

//This is common class for all of the moving objects
public abstract class MovingObject extends Object {
	//speed - how many pixels the object is able to move in the second
	//maxSpeed - can be changed
	int speed = 0;
	int maxSpeed = 80;
	int speedChange = 1;
	
	public MovingObject(int x, int y, GameArea a) throws CantLoadException {
		super(x, y, a);
	}
	
	//move the object
	public void move() {
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
		repaintObject(old,newImg);
		
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
			if (relativeChange > 0) {
				 setImage(getFieldNumber(imgN - 1));
			 } else {
				 setImage(getFieldNumber(imgN + 1));
			 }
			 
			 speed = (int) Math.ceil(speed/10 * 9);
		} else if (Math.abs(relativeChange) == 4) {
			//slowing down
			speed -= 2 * speedChange * speedSign();
		}
		
	}
	
	//This method change the position according to the direction
	private void changePosition(int[] old) {
		int newImg = imgList.indexOf(img);
		float change = (float) speed/20;

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
		if (Math.abs(speed) > maxSpeed) speed = speedSign() * maxSpeed;
	}
	
	//It counts a 'dirty area' and repaints it
	private void repaintObject(int[] oldImage, int[] newImage) {
		if (!oldImage.equals(newImage)) {
			float change = speed/20;
			int topX = Math.min(a.getX(0) + oldImage[1],a.getX(0) + newImage[1]);
			int topY = Math.min(a.getY(0) + oldImage[2], a.getY(0) +  newImage[2]);
			
			int width;
			if (topX == oldImage[1]) width = (int) Math.ceil(a.coef * img.getWidth() + Math.abs(change));
			else width = (int) Math.ceil(a.coef * oldImage[3] + Math.abs(change));
			
			int height;
			if (topY == oldImage[2]) height = (int) Math.ceil(a.coef * img.getHeight() + Math.abs(change));
			else height = (int) Math.ceil(a.coef * oldImage[4] + Math.abs(change));
			
			a.repaint(topX,topY,width,height);
		}
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
