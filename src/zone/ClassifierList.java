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
 * @author takashi
 *
 */
public class ClassifierList extends ArrayList<Classifier> {

	/**
	 * 
	 */
	public ClassifierList() {
		// TODO 自動生成されたコンストラクター・スタブ
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
	
	public CisEList get_intense_classifier(int n){
//		FIXME not yet implemented
	}
	
}
