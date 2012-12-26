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
		
		
//		DataSetとは、リファクタリング後にでてくるものだったんだ
//		dataSetには、必ずしもメソッドはいらないんだ
		
//		各レコードの重みを正規化した分布を計算
		dataset.initWeight();
		
//		weakLearnを呼び、エラー率が1/2未満クラス分類器htを作成
//		各レコードの重みを更新　正解だと軽くし、不正解だとそのまま
		for(int i = 0; i < t; i++){
			newclassifier = dataset.weakLearn(i);
			dataset.reviseWeight(newclassifier);
		}
				
		
//		最終のクラス分類器hf
		
		
		
	}
	
}
