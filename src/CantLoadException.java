
public class CantLoadException extends Exception{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String description;
	
	CantLoadException () {
		description = "File cannot be loaded";
	}
	
	CantLoadException (String dsc) {
		description = dsc;
	}
	
	CantLoadException (String dsc, String data) {
		description = dsc;
		
		System.err.println(data);
	}
	
	public String toString() {
		return "CantLoadException: [" + description + "]";
	}
}