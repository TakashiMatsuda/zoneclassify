package zone;

import java.util.LinkedList;
import java.util.List;

/**
 * メインクラス
 * @author takashi
 *
 */
public class Boost {
	private static int M = 10;// あとで仕様変更を受けます.
	private static int T = 50;// 最終的に用いる分類器の数
	private static int PATTERN = (int) Math.pow(4, 2) + (int) Math.pow(4, 3) + (int) Math.pow(4, 4) + (int) Math.pow(4, 5);
	
	
	/**
	 * 訓練データを読み込む
	 * 訓練データに大してadaboostアルゴリズムを適用しclassifierのリストを計算
	 * テストデータの目標属性をadaboostで計算したclassifierを使って予測し、error率を計算
	 * 全部datasetに入る
	 */
	/**
	 * Main function
	 * @param args
	 */
	public static void main(char[] args){
		ZoneExtracter farmer = new ZoneExtracter();
		
		/*
		 * 読み込み(IO処理)
		 * : DataSetをコンストラクトする
		 */
		
		/*
		 * DataSet
		 */
		DataSet dataset = new DataSet();
		// メチル化度の定義だが、最初は自分の好きなようにやって、あとで論文を呼んでチェックすればよい。
		dataset.load(farmer.subZone(M), "coverage.wig");
		// Mは区間数、ゆくゆくは区間長の制限に変えたいですね

//		各レコードの重みを正規化した分布を計算
		dataset.initWeight();
		
//		newclassifierはどういうものか？
//		weakLearnを呼び、エラー率が1/2未満クラス分類器htを作成
//		各レコードの重みを更新　正解だと軽くし、不正解だとそのまま
//		Classifier newclassifier = null;
//		二回ループをまわしてしまっている（内部と、外部で）
		
		dataset.adaboost();
		
//		最終のクラス分類器hf
		FinalClassifier finalclassifier = new FinalClassifier(dataset, T);
		
		
//		FIXME このあと何をしなければいけないか考えよう。
//		cis-elementの場所を返す。
//		cis-elementの塩基配列を返す。
//		(出力する)
		
		
		
//		最終的な分類器を作成する。
//		入力待ち状態
//		テストからの呼出を受付 : lastprediction
		
		
//		何らかの塩基配列を受け取って、それがcis-elementかどうかを返す。
		
		
		
		
		
	}	
	
	
	
	private void write_cis(CisEList ciselmlist){
		
		
		
	}
	
}