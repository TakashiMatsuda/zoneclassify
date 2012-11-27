package zone;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author tks
 *
 */
public class DataSet {
	// とりあえずかたちは整いました、
	// あとはIOを整備して
	// 細かい調整、これはIOが整ったら上流から順番に行います
	// そしてデバッグ
	// テスト
	// 完成
	// という手順を通りましょう。
	
	/*
	 * 判定器リスト
	 */
	ArrayList<Classifier> boxes;
	
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
	 * 入力ファイルからrecordをloadしてknowledgesに格納
	 * 
	 * 仕様変更があります、型を配列に変えたので変更をうけます　
	 * @param zones, filename
	 * @return
	 */
	public void load(List<int[]> zones, String filename){
		
		
	}
	
	/**
	 * targetにsearchwordが現れる関数を返します。ただし重複し数えない場合の度数です。
	 * @param target
	 * @param searchWord
	 * @return
	 */
	private int countStringInString(String target, String searchWord){
		return (target.length() - target.replaceAll(searchWord, "").length()) / searchWord.length();
	}

	/**
	 * あるn-merがsequenceに含まれる回数が、期待値よりも大きいかを判定します。
	 * @param factor
	 * @param sequence
	 * @return
	 */
	private int judgeEXP(String factor, String sequence){
		int l = factor.length();
		double ne = ((sequence.length() - l) * (1.0 / Math.pow(4.0, (double)l))) * sequence.length();
		/*
		 * faactorがsequenceに含まれる回数
		 */
		int dosu = countStringInString(sequence, factor);
		if (dosu >= ne)
			return 1;
		else
			return 0;
	}
	
	
	/**
	 * Initialize weight
	 */
	public void initWeight(){
		int l = knowledges.length;
		for (int i = 0; i < l; i++){
			knowledges.set(i, knowledges.get(i).setW(1.0 / (double)l));// ここあとで
		}
	}
	
	/**
	 * classifierによるpredictionのエラー率を計算
	 * 完成しています
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
	 * 完成しています
	 * @return
	 */
	public Classifier weakLearn(){
		Classifier tmp = new Classifier(LS, (byte) 0);
		while(LS < PATTERN){
			if (errorRatio(tmp) < (1.0 / 2.0))
				break;
			else{	/**
				 * あるn-merがsequenceに含まれる回数が、期待値よりも大きいかを判定します。
				 * @param factor
				 * @param sequence
				 * @return
				 */
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
	 * 完成しています
	 */
	public void reviseWeight(Classifier h){
		double beta;
		double e = errorRatio(h);
		beta = e /(1 - e);
		for(int i = 0; i < M; i++){
			knowledges[i][PATTERN + 1] = (byte) (knowledges[i][PATTERN + 1] * Math.pow(beta, (1 - Math.abs(h.prediction() - knowledges[i][PATTERN]))));
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
