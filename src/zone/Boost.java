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
		dataset.load(farmer.subZone(M), "coverage.wig");// Mは区間数、ゆくゆくは区間長の制限に変えたいですね
				
//		各レコードの重みを正規化した分布を計算
		dataset.initWeight();
		
//		FIXME Boost.javaのweaklearnの呼出部分を書き上げる
//		newclassifierはどういうものか？
//		weakLearnを呼び、エラー率が1/2未満クラス分類器htを作成
//		各レコードの重みを更新　正解だと軽くし、不正解だとそのまま
		for(int i = 0; i < t; i++){
			newclassifier = dataset.weakLearn(i);
			dataset.reviseWeight(newclassifier);
		}		
		
//		最終のクラス分類器hf
		FinalClassifier finalclassifier = new FinalClassifier(dataset);
		
		
		
	}
	
	
	/**
	 * 
	 * @author takashi
	 *	最終的に獲得する分類器のクラス
	 *	DataSetと一対一に対応
	 */
	private class FinalClassifier {
		DataSet dataset;
		/**
		 * DataSetと一対一の関係をもつクラス
		 * @param ls
		 * @param t
		 */
		FinalClassifier(DataSet dataset) {
			this.dataset = dataset;
		}
		
		
		/**
		 * 内装はmajorityRuleによる。
		 * 重み付き多数決による結果を返す。
		 * 完成しました。
		 * @param a
		 * @return
		 */
		public byte lastprediction(byte[] sample){
//			重みつき多数決をするメソッド
//			名前微妙。かぶっている。わざとでないなら変えるべき。
			if(majorityRule(sample)){
				return 1;
			}
			else
				return 0;
		}
		
		
		/**
		 * この結果を再計算するのはもったいないので、このクラスの中にフィールドを作って格納したい。
		 * 完成しています。
		 * @param greatTeachers
		 * @return
		 */
		private boolean majorityRule(byte[] sample){
			double prob = 0;
			double aver = 0;
			double beta = 0;
			// NOW
			for(int t = 0; t < T; t++){
//				左辺の計算
				beta = dataset.boxes.get(t).omomikeisu();
				prob += (- Math.log(beta)) * (dataset.boxes.get(t).prediction(sample));
				
//				右辺の計算
				aver += (- Math.log(beta)) / 2.0;
			}		
			
			if(prob >= aver){
				return true;
			}
			else
				return false;
		}
	}
}