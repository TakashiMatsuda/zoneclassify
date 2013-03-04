/**
 * 
 */
package zone;

import java.util.Comparator;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * 重みが重複した場合は不適切な結果が帰ります。（先に挿入されていたものが忘れられてしまう）
 * 
 * @author takashi
 * 
 */
public class ClassifierRanking extends TreeMap<Double, Integer> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2047111169548698931L;

	/**
	 * 
	 */
	public ClassifierRanking() {
		
	}

	/**
	 * @param comparator
	 */
	public ClassifierRanking(Comparator<? super Double> comparator) {
		super(comparator);
		// TODO 自動生成されたコンストラクター・スタブ
	}

	/**
	 * @param m
	 */
	public ClassifierRanking(Map<? extends Double, ? extends Integer> m) {
		super(m);
		// TODO 自動生成されたコンストラクター・スタブ
	}

	/**
	 * @param m
	 */
	public ClassifierRanking(SortedMap<Double, ? extends Integer> m) {
		super(m);
		// TODO 自動生成されたコンストラクター・スタブ
	}

}
