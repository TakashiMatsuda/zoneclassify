package zone;


/**
 * 単位クラス分類器
 * @author takashi
 *
 */
public class Classifier {
	
	/**
	 * law[0]: ラベルの番号
	 * law[1]: 目標属性
	 */
	byte[] law;
	
	public byte prediction(){
		return law[1];
	}
	
	Classifier(){
		this.law = new byte[2];
	}
}
