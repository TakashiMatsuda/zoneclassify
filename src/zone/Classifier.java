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
	int start;
	byte target;
	
	public byte prediction(){
		return target;
	}
	
	Classifier(int ls, byte t){
		
		this.start = ls;
		this.target = t;
	}
}
