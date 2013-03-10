package zone;

/**
 * メインクラス 完成しています。
 * 
 * @author takashi
 * 
 */
public class Boost {
	private static int M = 10;// あとで仕様変更を受けます.
	private static int T = 50;// 最終的に用いる分類器の数 <- 全部使えばいいのではないか、大丈夫ならそのままいれこむ
	private static int PATTERN = (int) Math.pow(4, 2) + (int) Math.pow(4, 3)
			+ (int) Math.pow(4, 4) + (int) Math.pow(4, 5);
	private static int CIS_NUM = 100;

	// private ZoneExtracter farmer;

	private DataSet dataset;
	private FinalClassifier finalclassifier;

	private boolean sign_analysed;

	/**
	 * コンストラクタ。singletonの初期化に用います。
	 */
	private Boost() {
		// farmer = new ZoneExtracter();
		dataset = new DataSet();
		sign_analysed = false;
	}

	/**
	 * 解析の実行メソッドです。解析終了、最終的な分類器を作成するところまで。
	 */
	public void analyse() {
		if (sign_analysed) {
			System.err.println("already analysed(@analyse_error)");
		} else {
			System.out.println("starting analysis...");

			// dataset.load(farmer.subZone(M), "coverage.wig");
			dataset.load(SegmentSetsExtracter.extract(1, 2000,
					"chromosome_blastula_CpGmethylationLevel.wig",
					"chromosome_CpGsitePosition.wig"), "shortMEDAKA.fa");

			// Mは区間数、ゆくゆくは区間長の制限に変えたいですね
			// メモリは足りているので、高速化したい
			// 並列処理もできるとよい

			// 各レコードの重みを正規化した分布を計算
			dataset.initWeight();

			// newclassifierはどういうものか？
			// weakLearnを呼び、エラー率が1/2未満クラス分類器htを作成
			// 各レコードの重みを更新　正解だと軽くし、不正解だとそのまま
			// Classifier newclassifier = null;
			// 二回ループをまわしてしまっている（内部と、外部で）

			dataset.adaboost();

			// 最終のクラス分類器finalclassiierを作成する。
			finalclassifier = new FinalClassifier(dataset, T);
			sign_analysed = true;
		}

	}

	/**
	 * cis-elementのリストを出力する。
	 */
	public void write_cis_element() {
		// if (sign_analysed) {
		// cis-elementの場所を返す。
		// cis-elementの塩基配列を返す。
		// (出力する)
		if (finalclassifier != null) {
			CisEList ciselementlist = finalclassifier.suggest_cis(CIS_NUM);
			ciselementlist.write();
			// }
		} else {
			System.err.println("not yet analysed(@write_error)");
		}
	}

	/**
	 * 高メチル化なら1, 低メチル化なら0を返します。
	 * 
	 * @param record
	 * @return
	 */
	public byte methyl_prediction(String record) {
		// if (sign_analysed) {
		if (finalclassifier != null) {
			byte[] sample = trans_str_byte(record);
			return this.finalclassifier.lastprediction(sample);
			// }
		} else {
			System.err.println("not yet analysed(@methyl_prediction:error)");
			return -1;
		}

	}

	/**
	 * 訓練データを読み込む 訓練データに大してadaboostアルゴリズムを適用しclassifierのリストを計算
	 * テストデータの目標属性をadaboostで計算したclassifierを使って予測し、error率を計算 全部datasetに入る
	 */
	/**
	 * Main function 大部分をanalyseに移転しました。
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println("-------------MinMethyl-------------");
		Boost singleton = new Boost();
		singleton.analyse();

	}

	/**
	 * 
	 * recordをもらって、その判定を与えられた最終分類器を用いて行う。 (overloaded) 使わない予定
	 * 
	 * @param record
	 * @param finalclassifier
	 * @return
	 */
	private byte prediction(String record, FinalClassifier finalclassifier) {
		byte[] sample = trans_str_byte(record);
		return finalclassifier.lastprediction(sample);
	}

	/**
	 * 他のところとの整合性チェックはテストの時におこなって下さい。
	 * 
	 * @param record
	 * @return
	 */
	private static byte[] trans_str_byte(String record) {
		/*
		 * 1:A 2;T 3:C 4;G
		 */
		byte[] res = new byte[record.length()];

		for (int i = 0; i < record.length(); i++) {
			switch (record.codePointAt(i)) {
			case 'A':
				res[i] = 1;
				break;
			case 'T':
				res[i] = 2;
				break;
			case 'C':
				res[i] = 3;
				break;
			case 'G':
				res[i] = 4;
				break;
			default:
				break;
			}
		}
		return res;

	}

}