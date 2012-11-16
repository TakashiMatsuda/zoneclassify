package zone;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author tks
 *
 */
public class DataSet {
	
	
	/*
	 * 教師データ集合
	 */
	List<MyPoint> knowledges;
	
	
	/**
	 * あるn-merがsequenceに含まれる回数が、期待値よりも大きいかを判定します。
	 * @param factor
	 * @param sequence
	 * @return
	 */
	private int judgeEXP(String factor, String sequence){
		int l = factor.length();
		double ne = (sequence.length() - l) * (1.0 / Math.pow(4.0, (double)l));
		
		
		// 出現回数を数えるライブラリが用意されていると聞いたことがあります。
		// 重複を許すかどうか微妙なところ
		// dosu(kari)
		double dosu = 0;
		if (dosu >= ne)
			return 1;
		else
			return 0;
	}
	
	/**
	 * 入力ファイルからrecordをloadしてknowledgesに格納
	 * @param zones, filename
	 * @return
	 */
	public void load(List<int[]> zones, String filename){
		
		
		
	}
	
	/**a
	 * Initialize weight
	 */
	public void initWeight(){
		int l = knowledges.size();
		for (int i = 0; i < l; i++){
			knowledges.set(i, knowledges.get(i).setW(1.0 / (double)l));
		}
	}
	
	/**
	 * classifierによるpredictionのエラー率を計算
	 * @return
	 */
	public double[] errorRatio(){
		return null;
	}
	
	/**
	 * 最もerror率の低いclassifierを計算
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
	
	/**
	 * Constructer
	 * open to change
	 */
	DataSet(){
		this.knowledges = new ArrayList<MyPoint>();
	}
	
}
