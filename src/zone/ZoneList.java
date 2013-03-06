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
	
}
