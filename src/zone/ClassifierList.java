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

	/**
	 * 完成しています。
	 * 
	 * @param n
	 * @return
	 */
	public CisEList get_intense_classifier(int n, ClassifierRanking memberlist) {
		CisEList res = new CisEList(n);
		for (int i = 0; i < n; i++) {
			res.add(get(i).get_record());
		}

		return res;
	}

}
