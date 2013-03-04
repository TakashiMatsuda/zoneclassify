package zone;

/**
 * 簡易単位クラス分類器
 * 
 * @author takashi
 * 
 */
public class Classifier {

	public final int start;
	public final byte target;
	private double ratio;
	// FIXME recordをclassifierに入れておいて下さい。
	private String record;

	/**
	 * 
	 * @param a
	 * @return
	 */
	public byte prediction(byte[] a) {
		if (a[start] == 1)
			return target;
		else
			return (byte) ((target - 1) * (-1));
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
	 * @param ls
	 * @param t
	 * @param record
	 */
	Classifier(int ls, byte t, String record) {
		this.start = ls;
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
