package objects;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.Map;

import cars.CantLoadException;
import cars.GameArea;

public class StaticObject extends Object {
	//all the static objects. They can be solid or unstable. The can be also animated
	
	boolean solid;
	boolean animate;
	public int xSize = 1;
	public int ySize = 1;
	
	public StaticObject(int x, int y, GameArea a, boolean solid, boolean animate, String[] imgNames) throws CantLoadException {
		super(x, y, a);
		initiate(solid, animate, imgNames);
	}
	
	public StaticObject(int gX, int sX, int gY, int sY, GameArea a, boolean solid, boolean animate, String[] imgNames) throws CantLoadException {
		super(gX, sX, gY, sY, a);
		initiate(solid, animate, imgNames);
	}
	
	private void initiate(boolean solid, boolean animate, String[] imgNames) {
		this.solid = solid;
		this.animate = animate;
		
		loadImages(imgNames);
	}

	@Override
	void loadImages() throws CantLoadException {
	}
	
	void loadImages(String[] imgNames) {
		Map<String, BufferedImage> list = a.images;
		
		for (int i = 0; i < imgNames.length; i++) {
			imgList.add(list.get(imgNames[i]));
		}
		
		setImage(0);
	}
	
	public Rectangle animate() {
		int size = imgList.size();
		Rectangle r = new Rectangle();
		if (animate && size > 1) {
			int i = imgList.indexOf(img) + 1;
		
			if (i >= size) {
				i = 0;
			}
		
			setImage(i);
			
			//r = new Rectangle(a.getX(0) + (int) Math.floor(gridX), a.getY(0) + (int) Math.floor(gridY), xSize * a.square, ySize * a.square);
			r.add(new Rectangle(a.getX(gridX) + (int) Math.floor(squareX * a.coef), a.getY(gridY) + (int) Math.floor(squareY * a.coef), xSize * a.square, ySize * a.square));
		}
		
		return r;
	}

}
