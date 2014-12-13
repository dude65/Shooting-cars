import java.io.*;
import java.util.*;
import java.util.regex.*;


public class DataDecode {
	File file;
	String DataType;
	ArrayList<Map<String, String>> DataValues = new ArrayList<Map<String, String>>();
	
	DataDecode(String path, String type) {
		file = new File(path);
		DataType = type;
	}
	
	//returns data to another object
	public ArrayList<Map<String, String>> getData() throws IOException, CantLoadException {
		if (DataValues.isEmpty()) {
			DataValues = getDataFromBlocks();
		}
		
		return DataValues;
	}
	
	//reads data
	private ArrayList<Map<String, String>> getDataFromBlocks() throws IOException, CantLoadException {
		//Content of the file and declarations of lists
		ArrayList<Map<String, String>> listOfGainedValues = new ArrayList<Map<String, String>>();
		ArrayList<String> allowedKeys = allowedKeys(DataType);
		ArrayList<String> variables = new ArrayList<String>();
		@SuppressWarnings("resource")
		BufferedReader content = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));
		
		//Preparations of regexp
		boolean in = false;
		Pattern getIn = Pattern.compile("^\\s*([^\"\\{\\}]*)\\s*\\{\\s*$");
		Pattern getKey = Pattern.compile("([^\"\\{\\}\\s]*)");
		Pattern getVar = Pattern.compile("€([^\"\\{\\}\\s]*)");
		Pattern getVal = Pattern.compile("\"([^\"]*)\"");
		Pattern getOut = Pattern.compile("^\\s*\\}\\s*");
		Pattern getRow = Pattern.compile("^\\s*€([^\"\\{\\}\\s]+)\\s*=\\s*\".+\";");
		Matcher mIn, mKey, mVar, mVal, mOut, mRow;
		
		String row, key;
		int i = 0;
		
		//reading the file
		while ((row = content.readLine()) != null) {
			if (!in) {
				//1. step = getting into the block
				mIn = getIn.matcher(row);
				
				if (mIn.find()) {
					mKey = getKey.matcher(row);
					
					//extract the name of the key
					if (mKey.find()) {
						key = mKey.group();
						in = true;
						
						//finding out whether the key is correct, if so, set variables
						if (allowedKeys.contains(key)) {
							variables = getVariables(key);
							listOfGainedValues.add(new HashMap <String, String>());
							listOfGainedValues.get(i).put("key", key);
						} else {
							throw new CantLoadException("Invalid file","Class: Datadecode" + '\n' + "Method: getDataFromBlocks" + '\n' + "Description: List of allowed keys doesn't contain the value \"" + key + "\"");
						}
					}
				}
			} else {
				//2. step = reading data
				mRow = getRow.matcher(row);
				
				//Finding the right row
				if (mRow.find()) {
					mVar = getVar.matcher(row);
					
					if (mVar.find()) {
						String variable = mVar.group();
						
						//Check whether the variable is correct
						if (variables.contains(variable)) {
							
							mVal = getVal.matcher(row);
	
							//extract data from the row
							if (mVal.find()) {
								listOfGainedValues.get(i).put(variable, mVal.group(1));
							}
						} else {
							System.err.println("DataDecode(String DataType).getDataFromBlocks: The variable \"" + variable + "\" is not allowed.");
						}
					}
				} else {
					//3. step - getting out of the block
					mOut = getOut.matcher(row);
					
					if (mOut.find()) {
						in = false;
						i++;
					}
				}

			}
			
		}
		
		return listOfGainedValues;
	}
	
	//gets the list of allowed keys according to the type of the file
	private ArrayList<String> allowedKeys(String type) {
		ArrayList<String> allowedKeys = new ArrayList<String>();
		
		switch (type) {
			case "map": String[] add = {"data","background","terrain"}; allowedKeys.addAll(Arrays.asList(add)); break;
			default:  break;
		}
		
		return allowedKeys;
		
	}
	
	//gets the list of variables according to the keys
	private ArrayList<String> getVariables(String key) {
		ArrayList<String> v = new ArrayList<String>();
		
		switch (key) {
			case "data": v.add("€id"); v.add("€name"); v.add("€description"); break;
			case "background": v.add("€background"); v.add("€music"); break;
			case "terrain":
				String[] mapField = new String[150];
				
				int x = 97;
				int y = 1;
				
				for (int i = 0; i < 150; i++) {
					if (y > 15) {
						x++;
						y = 1; 
					}
					
					mapField[i] = "€" + Character.toString((char) x) + y;
					y++;
				}
				
				v.addAll(Arrays.asList(mapField));
				
				break;
		}
		
		return v;
	}
}
