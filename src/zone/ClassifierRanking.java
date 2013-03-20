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
 * 重みとその序数のマッピング(Stringをvalueにしてもいいかもしれない)
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

	}

	/**
	 * @param m
	 */
	public ClassifierRanking(Map<? extends Double, ? extends Integer> m) {
		super(m);

	}

	/**
	 * @param m
	 */
	public ClassifierRanking(SortedMap<Double, ? extends Integer> m) {
		super(m);

	}

}
