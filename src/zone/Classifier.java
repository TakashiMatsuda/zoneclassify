package zone;


/**
 * 単位クラス分類器
 * @author takashi
 *
 */
public class Classifier {
	
	int start;
	byte target;
	
	
	/**
	 * 
	 * @param a
	 * @return
	 */
	public byte prediction(byte[] a){
		if (a[start] == 1)
			return target;
		else
			return (byte) ((target - 1) * (-1));
	}
	
	Classifier(int ls, byte t){
		this.start = ls;
		this.target = t;
	}
}
