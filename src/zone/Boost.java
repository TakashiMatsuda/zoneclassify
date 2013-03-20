package zone;

import java.util.ArrayList;

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
	public ArrayList<String> mers;

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
		mers = createMers();
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
			dataset.load(SegmentSetsExtracter.extract(1, 1000,
					"chromosome_blastula_CpGmethylationLevel.wig",
					"chromosome_CpGsitePosition.wig"), "chromosomeMEDAKA.fa");

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
		
//		TODO ? 根本設計の変更　methyl_predictionを全merに対して用いて正解率リストをだす。
//		正解率に大きく影響するものがcis_element.
//		すでに分類器集合の中にその情報が計算済みで格納されていると思われる。
		
		for(String record : mers){
			
		}
		
		if (finalclassifier != null) {
//			CisEList ciselementlist = finalclassifier.suggest_cis(CIS_NUM);
			dataset.get_intense_classifier(0.05);
//			ciselementlist.write();
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

		if (finalclassifier != null) {			
			byte[] sample = trans_str_byte(record);
			return this.finalclassifier.lastprediction(sample);

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
	
	
	
	public static Boost run(){
		System.out.println("-------------MinMethyl-------------");
		Boost singleton = new Boost();
		singleton.analyse();
		return singleton;
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
	 * 与えられた文字列から、それに含まれる各2-5merの出現頻度と期待値との大小比較の結果のベクトルを返す。
	 * @param record
	 * @return
	 */
	private byte[] trans_str_byte(String record) {
		/*
		 * 1:A 2;T 3:C 4;G
		 */
//		byte[] res = new byte[record.length()];
		
//		切り出し解析に使ったアルゴリズムが使える。
		byte[] res= new byte[PATTERN];
		for (int j = 0; j < PATTERN; j++) {
			res[j] = judgeEXP(mers.get(j), record);
		}
//		for (int i = 0; i < record.length(); i++) {
//			switch (record.codePointAt(i)) {
//			case 'A':
//				res[i] = 1;
//				break;
//			case 'T':
//				res[i] = 2;
//				break;
//			case 'C':
//				res[i] = 3;
//				break;
//			case 'G':
//				res[i] = 4;
//				break;
//			default:
//				break;
//			}
//		}
		return res;

	}
	
	

	/**
	 * targetにsearchwordが現れる関数を返します。ただし重複し数えない場合の度数です。
	 * 
	 * @param target
	 * @param searchWord
	 * @return
	 */
	private int countStringInString(String searchWord, String target) {
		return (target.length() - target.replaceAll(searchWord, "").length())
				/ searchWord.length();
	}

	/**
	 * あるn-merがsequenceに含まれる回数が、期待値よりも大きいかを判定します。
	 * 
	 * @param factor	検索クエリ
	 * @param sequence　対象
	 * @return
	 */
	public byte judgeEXP(String factor, String sequence) {		
		int l = factor.length();
		double ne = ((sequence.length() - l) * (1.0 / Math.pow(4.0, (double) l)))
				* sequence.length();
		/*
		 * factorがsequenceに含まれる回数
		 */
		int dosu = countStringInString(factor, sequence);
		if (dosu >= ne)
			return 1;
		else
			return 0;
	}

	/**
	 * コンストラクタから呼び出されます。 Create and store all patterns for 2-5mer Coding
	 * completed. The calculation speed is fast. テスト済、完動しています
	 * 
	 * @return List of all patterns for 2-5mer.
	 */
	private static ArrayList<String> createMers() {
		/*
		 * nucleotide : ヌクレオチド4文字のインデックス two, ...five digits:
		 * 各桁数のmersのパターン数、for文を回すために用意 各パターンをfruitに格納して返します
		 */
		char[] nucleotide = { 'A', 'T', 'C', 'G' };
		int twodigits = (int) Math.pow(4, 2);
		int threedigits = (int) Math.pow(4, 3);// 4であってる？
		int fourdigits = (int) Math.pow(4, 4);
		int fivedigits = (int) Math.pow(4, 5);
		ArrayList<String> fruit = new ArrayList<String>();
		/*
		 * 2merの処理
		 */
		// 以下ぴったりの設計です。植木算法を間違えているとArrayOutOfExceptionです。
		for (int i = 0; i < twodigits; i++) {
			/*
			 * 10進数iから4進数への変換
			 */
			char[] twomer = { nucleotide[i / 4], nucleotide[i % 4] };// 頭の中で暗算して書いた行です
			fruit.add(String.valueOf(twomer));
		}
		/*
		 * 同様に3-5merも処理
		 */
		int d3 = twodigits + threedigits;// 脳内暗算記述行
		for (int i = twodigits; i < d3; i++) {
			int tmpi = i - twodigits;
			char[] threemer = { nucleotide[tmpi / (4 * 4)],
					nucleotide[(tmpi % (4 * 4) / 4)], nucleotide[(tmpi % 4)] };
			fruit.add(String.valueOf(threemer));
		}
		int d4 = d3 + fourdigits;
		for (int i = d3; i < d4; i++) {
			int tmpi = i - d3;
			char[] fourmer = { nucleotide[tmpi / (4 * 4 * 4)],
					nucleotide[(tmpi % (4 * 4 * 4)) / (4 * 4)],
					nucleotide[(tmpi % (4 * 4)) / 4], nucleotide[tmpi % 4] };
			fruit.add(String.valueOf(fourmer));
		}
		int d5 = d4 + fivedigits;
		for (int i = d4; i < d5; i++) {
			int tmpi = i - d4;
			// 記述が長くなってしまったのでミス確認
			char[] fivemer = { nucleotide[tmpi / (4 * 4 * 4 * 4)],
					nucleotide[tmpi % (4 * 4 * 4 * 4) / (4 * 4 * 4)],
					nucleotide[(tmpi % (4 * 4 * 4) / (4 * 4))],
					nucleotide[(tmpi % (4 * 4) / 4)], nucleotide[tmpi % 4] };
			fruit.add(String.valueOf(fivemer));
		}
		return fruit;
	}

	
}