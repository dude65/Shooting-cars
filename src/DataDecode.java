import java.io.*;
import java.util.*;
import java.util.regex.*;


public class DataDecode {
	File file;
	
	DataDecode(String path) {
		file = new File(path);
	}
	
	public ArrayList<String> getDataFromBlock(String key, ArrayList<String> variables) throws IOException {
		//Content of the file and preparation of a list
		ArrayList<String> listOfGainedValues = new ArrayList<String>();
		BufferedReader content = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));
		
		//Preparations of regexp
		boolean in = false;
		Pattern getIn = Pattern.compile("^\\s*"+ key + "\\s*\\{\\s*$");
		Pattern getVal = Pattern.compile("\"([^\"]*)\"");
		Pattern getOut = Pattern.compile("^\\s*\\}\\s*");
		Pattern getRow;
		Matcher mIn, mVal, mOut, mRow;
		
		String row;
		
		while ((row = content.readLine()) != null) {
			if (!in) {
				//1. step = getting into the block according to the key
				mIn = getIn.matcher(row);
				
				if (mIn.find()) {
					in = true;
				}
			} else {
				for (int i = 0; i < variables.size(); i++) {
					//2. step = reading data
					getRow = Pattern.compile("^\\s*"+variables.get(i)+"\\s*=\\s*\".+\";");
					mRow = getRow.matcher(row);
					
					if (mRow.find()) {
						//extract data from the row
						mVal = getVal.matcher(row);

						if (mVal.find()) {
							listOfGainedValues.add(mVal.group(1));
						}
					}
					
					//3. step = get out of the block
					mOut = getOut.matcher(row);
					
					if (mOut.find()) {
						content.close();
						break;
					}
				}
			}
			
		}
		
		
		return listOfGainedValues;
	}
}
