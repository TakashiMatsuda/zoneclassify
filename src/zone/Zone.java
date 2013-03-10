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
	private int fasta_start;
	private int fasta_end;
	
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
	
	/**
	 * 
	 * @return
	 */
	public int get_start(){
		return start;
	}
	
	/**
	 * 
	 * @return
	 */
	public int get_end(){
		return end;
	}
	
	public void set_fasta_value(int[] pos_indicator){
		this.fasta_start = pos_indicator[start];
		this.fasta_end = pos_indicator[end];
	}
	
	public int get_start_fasta(){
		return fasta_start;
	}
	
	public int get_end_fasta(){
		return fasta_end;
	}
	
}
