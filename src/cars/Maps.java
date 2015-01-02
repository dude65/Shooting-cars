package cars;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;


public class Maps {	
	public String id;
	private Map<String,Map<String,String>> data;
	
	private ArrayList<String> terrain = new ArrayList<String>();
	
	public Maps (String path) throws IOException, CantLoadException {
		DataDecode decoder = new DataDecode(path, "map");
		data = decoder.getData();
		id = data.get("data").get("€id");
	}
	
	public Map<String,String> getMainData() {
		return data.get("data");
	}
	
	public Map<String,String> getMediaData() {
		return data.get("background");
	}
	
	public Map<String,String> getBuildingsData() {
		return data.get("buildings");
	}
	
	public ArrayList<String> getTerrainData() {
		if (terrain.isEmpty()) {
			sortTerrainData();
		}
		
		return terrain;
	}
	
	private void sortTerrainData() {
		Map<String, String> rawData = data.get("terrain");
		
		int x = 97;
		int y = 1;
		
		for (int k = 0; k < 150; k++) {
			if (y > 15) {
				x++;
				y = 1; 
			}
			
			terrain.add(rawData.get("€" + Character.toString((char) x) + y));
			y++;
		}
	}
}