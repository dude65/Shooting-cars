package cars;

public class CantLoadException extends Exception{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String description;
	
	public CantLoadException () {
		description = "File cannot be loaded";
	}
	
	public CantLoadException (String dsc) {
		description = dsc;
	}
	
	public CantLoadException (String dsc, String data) {
		description = dsc;
		
		System.err.println(data);
	}
	
	public String toString() {
		return "CantLoadException: [" + description + "]";
	}
}