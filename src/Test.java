import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Test {
	public void testReg() {
		String text =   " €ahoj = \"€beran\";";
		Pattern p = Pattern.compile("^\\s*€([^\"\\{\\}\\s]+)\\s*=\\s*\".+\";");
		Matcher m = p.matcher(text);
		
		System.out.println(m.find());
		
		Pattern p1 = Pattern.compile("€([^\"\\{\\}\\s]*)");
		Matcher m1 = p1.matcher(text);
		
		if (m1.find()) {
			System.out.println(m1.group());
		}
	}
	
	public void testList() {
		ArrayList<String> list = new ArrayList<String>();

		for (int i = 0; i < 6; i++) {
			list.add("");
		}
		
		list.set(2, "blázne");
		list.set(0, "ahoj");
		
		System.out.println(list.toString());
	}
	
	public void testArr() {
		String[] ar = null;
		
		for (int i = 0; i < 2; i++) {
			if (i == 0) {
				ar = new String[3];
				ar[0] = "initialize";
			} else {
				ar[i] = "blaazen";
			}
		}
		
		
		System.out.println("{"+ar[0]+","+ar[1]+"}");
		
		ar = new String[2];
		System.out.println("{"+ar[0]+","+ar[1]+"}");
	}
	
	public void HashMap() {
		Map<String, String> map = new java.util.HashMap<String, String>();
		map.put("promenna", "hodnota");
		map.put("dalsi", "slunce");
		System.out.println(map.get("promenna"));
		
		ArrayList<Map<String, String>> a = new ArrayList<Map<String, String>>();
		a.add(map);
		
		Map<String, String> zpet = a.get(0);
		
		System.out.println(zpet.get("promenna"));
		System.out.println(zpet.get("dalsi"));
		
	}
	
	public void testMap() {
		DataDecode d = new DataDecode("./maps/Farmer_circuit.map", "map");

		try {
			ArrayList<Map<String, String>> list = d.getData();
			
			/*for (int i = 0; i < list.size(); i++) {
				Map<String, String> m = list.get(i);
				
				String key = m.get("key");
				System.out.print(key + " + ");
				
				switch (key) {
				case "data": System.out.println(m.get("€id") + "," + m.get("€name") + "," + m.get("€description")); break;
				case "background": System.out.println(m.get("€background") + "," + m.get("€music")); break;
				default: System.out.println("a další");
				}
			}*/
		} catch (IOException e) {
			System.err.println(e.getLocalizedMessage());
		} catch (CantLoadException e) {
			System.err.println(e);
		}
		
	}
}
