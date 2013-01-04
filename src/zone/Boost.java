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
		dataset.load(farmer.subZone(M), "coverage.wig");// Mは区間数、ゆくゆくは区間長の制限に変えたいですね
				
//		各レコードの重みを正規化した分布を計算
		dataset.initWeight();
		
//		newclassifierはどういうものか？
//		weakLearnを呼び、エラー率が1/2未満クラス分類器htを作成
//		各レコードの重みを更新　正解だと軽くし、不正解だとそのまま
//		Classifier newclassifier = null;
//		二回ループをまわしてしまっている（内部と、外部で）
		
		dataset.run();
		
//		最終のクラス分類器hf
		Boost.FinalClassifier finalclassifier = new FinalClassifier(dataset);
		
	}
	
	
	/**
	 * 
	 * @author takashi
	 *	最終的に獲得する分類器のクラス
	 *	DataSetと一対一に対応
	 */
	private static class FinalClassifier {
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