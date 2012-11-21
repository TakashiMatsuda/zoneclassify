package zone;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author tks
 *
 */
public class DataSet {
	ArrayList<Classifier> boxes;// 要素数Tこまで。それ以上はいらない
	// エラー率を計算して、優秀なもののみここに登録
	
	/*
	 * MyPointの数
	 */
	int M;
	
	int PATTERN = (4 ^ 2) * (5 ^ 3);
	/*
	 * 教師データ集合
	 */
	byte[][] knowledges;
	
	int LS;
	
	/**
	 * あるn-merがsequenceに含まれる回数が、期待値よりも大きいかを判定します。
	 * @param factor
	 * @param sequence
	 * @return
	 */
	private int countStringInString(String target, String searchWord){
		return (target.length() - target.replaceAll(searchWord, "").length()) / searchWord.length();
	}
	
	private int judgeEXP(String factor, String sequence){
		int l = factor.length();
		double ne = (sequence.length() - l) * (1.0 / Math.pow(4.0, (double)l));
		
		// 出現回数を数えるライブラリが用意されていると聞いたことがあります。
		// 重複を許すかどうか微妙なところ
		// とりあえず許して計算する。動いたら後で改良すること。
		// dosu(kari)
		double dosu = 0;
		if (dosu >= ne)
			return 1;
		else
			return 0;
	}
	
	/**
	 * 入力ファイルからrecordをloadしてknowledgesに格納
	 * 
	 * 仕様変更があります、型を配列に変えたので変更をうけます　
	 * @param zones, filename
	 * @return
	 */
	public void load(List<int[]> zones, String filename){
		
		
		
	}
	
	/**a
	 * Initialize weight
	 */
	public void initWeight(){
		int l = knowledges.length;
		for (int i = 0; i < l; i++){
			knowledges.set(i, knowledges.get(i).setW(1.0 / (double)l));
		}
	}
	
	/**
	 * classifierによるpredictionのエラー率を計算
	 * @return
	 */
	public double errorRatio(Classifier x){
		/*
		 * xによる予測属性に対して訓練データ中の予測属性の現れる数
		 */
		int right = 0;
		int sum = 0;
		int q = x.start;
		int p = x.prediction();
		for (int i = 0; i < M; i++){
			if (knowledges[i][q] == 1){
				sum++;
				if (knowledges[i][M] == p)
					right++;
			}
		}
		return (double)right / (double)sum;
	}
	
	/**
	 * classifierを作成
	 * 一から順番にラベルごとに分類器をつくり、エラー率が0.5より大きければその分類器を返します。
	 * @return
	 */
	public Classifier weakLearn(){
		Classifier tmp = new Classifier(LS, (byte) 0);
		while(LS < PATTERN){
			if (errorRatio(tmp) < (1.0 / 2.0))
				break;
			else{
				tmp = new Classifier(LS, (byte) 1);
				if (errorRatio(tmp) < (1.0 / 2.0))
					break;
			}
			LS++;
		}
		return tmp;
	}
	
	/**
	 * AdaBoostのアルゴリズムにしたがってweightを更新
	 */
	public void reviseWeight(Classifier h){
		double beta;
		double e = errorRatio(h);
		beta = e /(1 - e);
		for(int i = 0; i < M; i++){
			knowledges[i][PATTERN + 1] = (byte) (knowledges[i][PATTERN + 1] * Math.pow(beta, (1 - Math.abs(h.prediction() - knowledges[i][PATTERN]))));
			// 絶対値の実装とは
		}
	}
	
	/**
	 * Constructer
	 * open to change
	 */
	DataSet(){// 訓練データの入力我必要
		// その形式はBoostくらすが決めること、ここで考える必要はない
		
		LS = 0;
	}
	
}
