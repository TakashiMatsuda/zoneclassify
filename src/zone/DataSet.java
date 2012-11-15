package zone;

/**
 * 
 * @author tks
 *
 */
public class DataSet {
	
	MyPoint a;
	// 各列の名前
	
	/**
	 * 入力ファイルからrecordをload
	 * @param filename
	 * @return
	 */
	public String load(MyPoint a){
		
		
		return null;
	}
	
	/**
	 * Initialize weight
	 */
	public void initWeight(){
		
	}
	
	/**
	 * classifierによるpredictionのエラー率を計算
	 * @return
	 */
	public double[] errorRatio(){
		return null;
	}
	
	/**
	 * 最もeeroro率の低いclassifierを計算
	 * @return
	 */
	public Classifier weakLearn(){
		return null;
	}
	
	/**
	 * AdaBoostのアルゴリズムにしたがってweightを更新
	 */
	public void reviseWeight(){
		
	}
	
	
	public class Classifier{
		public int prediction(){
			return 0;
		}
	}
}
