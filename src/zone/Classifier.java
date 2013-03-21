package zone;

/**
 * 簡易単位クラス分類器
 * 
 * @author takashi
 * 
 */
public class Classifier {

	public final int start;// もとにした列・2-5mer
	public final byte target;// 目標属性
	private double ratio;
	private String record;
	
	/**
	 * 
	 * @param a
	 * @return
	 */
	public byte prediction(byte[] a) {
//		aは文字列を変換したbyte[]
//		そもそも何を想定していたのか、調べたい
//		startは何を表しているのか。
		
		if (a[start] == 1)// outofboundsエラーの跡
			return target;
		else
			return (byte) ((target - 1) * (-1));
		// 1なら0を、oなら1を
	}
	
	/**
	 * 
	 * @param r
	 * @return true, if recording successfully done.
	 */
	public boolean recordErrorRatio(double r) {
		this.ratio = r;
		return true;
	}

	/**
	 * 
	 * @return
	 */
	public double omomikeisu() {
		return ratio / (1 - ratio);
	}
	
	/**
	 * 
	 * @param start 生成材料にする2-5merが何番目の2-5merに対応するか
	 * @param t		分類先符号
	 * @param record	その2-5mer(なくても解析は可能, めんどくさかったのでここにいれた)
	 */
	Classifier(int start, byte t, String record) {
		this.start = start;
		this.target = t;
		this.record = record;
	}

	/**
	 * 
	 * @return
	 */
	public String get_record() {
		return record;
	}
}
