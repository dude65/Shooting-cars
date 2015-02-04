package cars;

import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;

public class EditImages {
	
	//redraw pink color of the image to another according to the given number
	//1 - red
	//2 - blue
	//3 - green
	//4 - yellow
	//other - grey
	public BufferedImage colorImage(BufferedImage img, int c) {
		int width = img.getWidth();
		int height = img.getHeight();
		WritableRaster raster = img.getRaster();
		
		//loads list of shades
		int[][] colorList = new int[4][4];
		for (int i = 0; i <= 3; i++) {
			colorList[i] = getRGB(c,i);
		}		
		
		
		//It checks all of the pixels
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				int pixel[] = raster.getPixel(x, y, (int[]) null);
				int i = -1;
				
				//It controls whether it is shade of pink color
				if (pixel[0] == 88 && pixel[1] == 7 && pixel[2] == 93) {
					i = 0;
				}
				if (pixel[0] == 160 && pixel[1] == 13 && pixel[2] == 170) {
					i = 1;
				}
				if (pixel[0] == 199 && pixel[1] == 73 && pixel[2] == 207) {
					i = 2;
				}
				if (pixel[0] == 236 && pixel[1] == 132 && pixel[2] == 243) {
					i = 3;
				}
				
				//sets another color for the pixel
				if (i != -1) {					
					pixel = colorList[i];
					raster.setPixel(x,y,pixel);
				}
			}
		}
		
		img.setData(raster);
		return img;
	}
	
	//It's a method that reverse Image, it can reverse the picture horizontally or vertically
	public BufferedImage reverseImage(BufferedImage img, boolean xReversed, boolean yReversed) {
		int width = img.getWidth();
		int height = img.getHeight();
		WritableRaster imgRaster = img.getRaster();
		
		BufferedImage reversed = new BufferedImage(width, height, BufferedImage.TYPE_4BYTE_ABGR);
		WritableRaster reversedRaster = reversed.getRaster();
		
		//It runs through all the pixels of the former image
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				int[] pixel = imgRaster.getPixel(x, y, (int[]) null);
				
				int xPixel;
				int yPixel;
				
				if (xReversed) {
					xPixel = width - x - 1;
				} else {
					xPixel = x;
				}
				if (yReversed) {
					yPixel = height - y - 1;
				} else {
					yPixel = y;
				}
				
				//It sets the pixel to the right position in the new picture
				reversedRaster.setPixel(xPixel, yPixel, pixel);
			}
		}
		
		
		return reversed;
	}
	
	public BufferedImage reverseImage(BufferedImage img) {
		//reverse horizontally
		return reverseImage(img,true,false);
	}
	
	public BufferedImage reverseImage(BufferedImage img, boolean xReverse) {
		//true = reverse horizontally
		//false = reverse vertically
		return reverseImage(img, xReverse, !xReverse);
	}
	
	//This method returns a specific color of the specific shade
	private int[] getRGB(int color, int shade) {
		int[] rgb = new int[4];
		rgb[3] = 255;
		switch (color) {
		case 1:
			switch(shade) {
				case 0: rgb[0] = 143; rgb[1] = 18; rgb[2] = 12; break;
				case 1: rgb[0] = 255; rgb[1] = 47; rgb[2] = 0; break;
				case 2: rgb[0] = 255; rgb[1] = 70; rgb[2] = 28; break;
				case 3: rgb[0] = 255; rgb[1] = 116; rgb[2] = 84; break;
				default: rgb[0] = 0; rgb[1] = 0; rgb[2] = 0;
			} break;
		case 2:
			switch(shade) {
				case 0: rgb[0] = 0; rgb[1] = 0; rgb[2] = 149; break;
				case 1: rgb[0] = 55; rgb[1] = 55; rgb[2] = 149; break;
				case 2: rgb[0] = 111; rgb[1] = 111; rgb[2] = 255; break;
				case 3: rgb[0] = 172; rgb[1] = 172; rgb[2] = 255; break;
				default: rgb[0] = 0; rgb[1] = 0; rgb[2] = 0;
			} break;
		case 3:
			switch(shade) {
				case 0: rgb[0] = 24; rgb[1] = 79; rgb[2] = 8; break;
				case 1: rgb[0] = 40; rgb[1] = 130; rgb[2] = 13; break;
				case 2: rgb[0] = 53; rgb[1] = 172; rgb[2] = 17; break;
				case 3: rgb[0] = 133; rgb[1] = 245; rgb[2] = 24; break;
				default: rgb[0] = 0; rgb[1] = 0; rgb[2] = 0;
			} break;
		case 4:
			switch(shade) {
				case 0: rgb[0] = 189; rgb[1] = 159; rgb[2] = 18; break;
				case 1: rgb[0] = 238; rgb[1] = 207; rgb[2] = 0; break;
				case 2: rgb[0] = 248; rgb[1] = 240; rgb[2] = 56; break;
				case 3: rgb[0] = 248; rgb[1] = 240; rgb[2] = 113; break;
				default: rgb[0] = 0; rgb[1] = 0; rgb[2] = 0;
			} break;
		default:
			switch(shade) {
			case 0: rgb[0] = 14; rgb[1] = 14; rgb[2] = 14; break;
			case 1: rgb[0] = 62; rgb[1] = 62; rgb[2] = 63; break;
			case 2: rgb[0] = 135; rgb[1] = 134; rgb[2] = 136; break;
			case 3: rgb[0] = 208; rgb[1] = 207; rgb[2] = 210; break;
			default: rgb[0] = 0; rgb[1] = 0; rgb[2] = 0;
		}
		}
		return rgb;
	}
}
