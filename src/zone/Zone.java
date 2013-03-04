/**
 * 
 */
package zone;

/**
 * @author tks
 *
 */
public class Zone {
	private int start;
	private int end;
	private String record;
	
	/**
	 * 
	 */
	public Zone(int s, int e) {
		start = s;
		end = e;

	}
	
	/**
	 * 
	 * @param rec
	 */
	public void set_record(String rec){
		record = rec;
	}
	
	
	
}
