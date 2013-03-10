/**
 * 
 */
package zone;

import java.util.Collection;
import java.util.LinkedList;

/**
 * @author tks
 *
 */
public class ZoneList extends LinkedList<Zone> {
	
	private int[] pos_indicator;

	/**
	 * 
	 */
	public ZoneList() {

	}

	/**
	 * @param c
	 */
	public ZoneList(Collection<? extends Zone> c) {
		super(c);

	}
	
	public void trans_cpg_fasta(int[] indicator){
		this.pos_indicator = indicator;
		for(int i = 0; i < size(); i++){
			get(i).set_fasta_value(pos_indicator);
		}
		
	}
	
	
}
