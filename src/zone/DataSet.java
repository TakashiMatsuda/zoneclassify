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
	 * 入力ファイルからrecordをloadしてknowledgesに格納
	 * @param zones, filename
	 * @return
	 */
	public void load(List<int[]> zones, String filename){
		
		
		
	}
	
	/**
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
