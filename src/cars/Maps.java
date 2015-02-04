package cars;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;


public class Maps {	
	public String id;
	protected Map<String,Map<String,String>> data;
	protected String lightsColor = "red";
	
	protected ArrayList<String> terrain = new ArrayList<String>();
	
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
	
	public Map<String,String> getLine() {
		return data.get("finishline");
	}
	
	public String lightsPath() {
		return "./img/route/lights_"+lightsColor+".png";
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