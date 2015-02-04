package cars;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

public class TerrainDecoder {
	protected Map<String,String> codes = new HashMap<String,String>();
	
	public TerrainDecoder() throws CantLoadException{
		init();
	}
	
	//set terrain codes and path to them
	//Example: codes.get("wa"); returns "./img/terrain/way/asphalt/"
	protected void init() throws CantLoadException {
		String path = "./img/terrain/";
		
		Map<String,String> typeDir = getDirectoryList(path);
		
		for (Map.Entry<String, String> entry : typeDir.entrySet()) {
			String type = entry.getKey();
			String innerPath = entry.getValue();
			
			Map <String, String> varDir = getDirectoryList(innerPath);
			
			if (varDir.isEmpty()) {
				codes.put(type + "0", innerPath);
			} else {
				for (Map.Entry<String, String> innerEntry : varDir.entrySet()) {
					String terVariable = innerEntry.getKey();
					String terPath = innerEntry.getValue();
					
					codes.put(type + terVariable, terPath);
				}
			}
		}
	}
	
	//Returns image path of the specific type of the terrain
	//Terrain code: XX-YYYY
	//XX = type of the terrain
	//YYYY = specific variation of this terrain
	//Example: wa-horizontal
	//wa = asphalt way
	//horizontal - horizontal variation	
	public String getImagePath(String code) throws CantLoadException {
		String key = code.substring(0, 2);
		String val = code.substring(3);
		
		String path;
		
		if (codes.containsKey(key)) {
			path = codes.get(key) + val + ".png";
		} else {
			throw new CantLoadException ("Can't read data", "//Can't read data" + '\n' + "Class: TerrainDecoder" + '\n' + "Method: getImagePath(String code)" + '\n' + "Description: List of codes doesn't contain the key: "+ key);
		}
		
		return path;
	}	
	
	//Return Image of the specific terrain
	protected BufferedImage getImage(String code) throws CantLoadException {
		String path = getImagePath(code);
		
		try {
			BufferedImage img = ImageIO.read(new File(path));
			return img;
		} catch (IOException e) {
			throw new CantLoadException("Can't load terrain image","Class: TerrainDecoder"+'\n'+"Method: getImage(String code)"+'\n'+"Description (IOException):" + e);
		}
		
		
		
	}

	//returns Map list of directories in the director, the key is the first letter of the directory, the value is the path of the directory
	protected Map<String,String> getDirectoryList (String path) throws CantLoadException {
		if (path.charAt(path.length() - 1) != '/') path += "/";
		File dir = new File(path);
		Map<String, String> returnList = new HashMap<String,String>();
		
		if (dir.isDirectory()) {
			String[] list = dir.list();
			
			for (int i = 0; i < list.length; i++) {
				String filePath = path + list[i];
				File f = new File(filePath);
				String code = list[i].substring(0,1);
				
				if (f.isDirectory() && !filePath.equals(path + "background")) returnList.put(code, filePath + "/");
			}
		} else {
			throw new CantLoadException ("Can't read data", "//Can't read data" + '\n' + "Class: TerrainDecoder" + '\n' + "Method: getDirectoryList(String path)" + '\n' + "Description: The file "+ path + "is not a directory.");
		}
		
		return returnList;
	}
}
	
