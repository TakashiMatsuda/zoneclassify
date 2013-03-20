/**
 * 
 */
package zone;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collection;

/**
 * 完成しています。
 * 
 * @author takashi
 * 
 */
public class ClassifierList extends ArrayList<Classifier> {

	/**
	 * 
	 */
	public ClassifierList() {

	}

	/**
	 * @param initialCapacity
	 */
	public ClassifierList(int initialCapacity) {
		super(initialCapacity);

	}

	/**
	 * @param c
	 */
	public ClassifierList(Collection<? extends Classifier> c) {
		super(c);

	}

	/**
	 * 完成しています。
	 * 
	 * @param n
	 * @return
	 */
	public CisEList get_intense_classifier(double d) {
//		dより大きい・小さいものを取り出す
		
		CisEList res = new CisEList();
		for (int i = 0; i < size(); i++) {
			if (get(i).omomikeisu() < d){
				res.add(get(i).get_record());
			}
		}

		return res;
	}

}
