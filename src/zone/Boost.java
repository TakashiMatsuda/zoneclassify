package zone;

import java.util.LinkedList;
import java.util.List;

/**
 * メインクラス
 * @author takashi
 *
 */
public class Boost {
	static int M = 10;// あとで仕様変更を受けます.
	static int T = 50;// 最終的に用いる分類器の数
	
	/**
	 * 訓練データを読み込む
	 * 訓練データに大してadaboostアルゴリズムを適用しclassifierのリストを計算
	 * テストデータのも好評属性をadaboostで計算したclassifierを使って予測し、error率を計算
	 * 全部datasetに入る
	 * 
	 * これが全体のメインクラス
	 */
	
	/**
	 * 
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
		dataset.load(farmer.subZone(M), "coverage.wig");// Mは区間数、ゆくゆくは区間長の制限に変えたいですね
		
		// 最終classifierのリストを作成する
		List<Classifier> teacher = new LinkedList<Classifier>();
		
		
		/*
		 * 引数を与えられる設計にする。
		 * DataSetはカプセル化されている。
		 */
		// 下の関数をパブリックにする必要性を感じない
		// 一つ叩いたらあとは流れて結果が帰ってくるイメージ
		// 結果とは、分類器の集合体
		// 正解率が高い分類器を上位100断片くらい返してほしい
		dataset.initWeight();
		dataset.weakLearn();
		dataset.reviseWeight(null);//
		
		/*
		 * 最終的なクラス分類器を作成する
		 */
		
		
		
	}
	
}
