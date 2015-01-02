package cars;

import javax.swing.ImageIcon;

public class TerrainDecoder {
	
	//Returns image path of the specific type of the terrain
	//Terrain code: XX-YYYY
	//XX = type of the terrain
	//YYYY = specific variation of this terrain
	//Example: wa-horizontal
	//wa = asphalt way
	//horizontal - horizontal variation
	public String getImagePath(String code) throws CantLoadException {
		String path = "./img/terrain/";
		String key = code.substring(0, 2);
		String val = code.substring(3);
		
		switch (key) {
		case "wa": path += "way/asphalt/"; break;
		case "wu": path += "way/unpaved/"; break;
		case "wi": path += "way/interface-asphalt-unpaved/";  break;
		case "c0": path += "cliff/"; break;
		case "pt": path += "plants/trees/"; break;
		default: throw new CantLoadException();
		}
		
		path += val + ".png";
		
		
		return path;
	}
	
	//Returns ImageIcon of the specific terrain
	public ImageIcon getImage(String code) throws CantLoadException {
		String path = getImagePath(code);
		
		ImageIcon image = new ImageIcon(path);
		
		return image;
	}
}
