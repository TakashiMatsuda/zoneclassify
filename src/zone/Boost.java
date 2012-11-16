package zone;

/**
 * メインクラス
 * @author tks
 *
 */
public class Boost {
	static int M = 10;// あとで仕様変更を受けます.
	
	
	/**
	 * 訓練データを読み込む
	 * 訓練データに大してadaboostアルゴリズムを適用しclassifierのリストを計算
	 * テストデータのも好評属性をadaboostで計算したclassifierを使って予測し、error率を計算
	 * 全部datasetに入る
	 * 
	 * これが全体のメインクラス
	 */
	public static void main(char[] args){
		
		ZoneExtracter farmer = new ZoneExtracter();
		
		/*
		 * 読み込み(IO処理)
		 * : DataSetをコンストラクトする
		 */
		
		DataSet prince = new DataSet();
		/*
		 * パースしてDataSetが扱える形にしたい
		 * それはDataSetがやること？
		 */
		prince.load(farmer.subZone(M), "coverage.wig");
		
		/*
		 * 引数を与えられる設計にする。
		 * DataSetはカプセル化されている。
		 */
		prince.initWeight();
		prince.weakLearn();
		prince.errorRatio();
		prince.reviseWeight();
		
	}
	
}
