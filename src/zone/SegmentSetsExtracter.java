package zone;

import java.util.ArrayList;
import java.util.List;

/**
 * ZoneExtracterに代わり区間抽出を行うクラス 基本的な部分は引き継ぐべき 最短区間長制限問題をつくアルゴリズムを実装しています。 static
 * に用いられる
 * 
 * @author takashi
 * 
 */
public class SegmentSetsExtracter {
	private static final int MAX_BOUND = 6000000;// 39973034;

	/**
	 * staticに用いられるクラスのためコンストラクタは特に使われない、空です
	 */
	public SegmentSetsExtracter() {
	}

	// その座標を塩基対座標に変換すればよい。

	// 最短区間長制限問題をといているが、そもそもの「メチル化率」の定義をしていない
	// 区間に対するメチル化率という言葉の定義が必要。
	/**
	 * 
	 * @param filename
	 * @param sign
	 * @param m
	 * @return
	 */
	public static List<ZoneList> extract(int sign, int m, String filename,
			String posfile) {

		System.out.println("EXTRACTING subZones....");
		List<ZoneList> alldata = new ArrayList<ZoneList>();
		// kouho
		ZoneList maxzones = new ZoneList();

		// wig shutoku
		System.out.println("READING CpGMethylationLevel DATA...  :  "
				+ filename);
		ArrayList<int[]> pos_indicator = InputWig.load_CpGPosition(posfile,
				MAX_BOUND);
		List<double[]> list_methyllevelarray = InputWig.getWIG(filename,
				pos_indicator);
		
		
//		デバッグのための監視コードだった
//		for (int j = 0; j < 1; j++) {
//			for (int i = 0; i < 10; i++) {
//				System.out.println(list_methyllevelarray.get(j)[i] + " "
//						+ pos_indicator.get(j)[i]);
//			}
//		}

		if (list_methyllevelarray.size() == 0) {
			System.err.println(list_methyllevelarray.size());
		}

		/*
		 * tagcountは遺伝子断片の序数 iranaiko
		 */
		int tagcount = 0;
		/*
		 * 各遺伝子断片の中身、wigファイルの成れの果て
		 */
		// ArrayList<Double> target = null;
		// while (tagcount < methyllevel.size()){
		
//		TODO ここのループを並列化したい。
//		fork/joinを見てみよう
//		入れる順番を揃えなければいけないから、ダメっぽい。
		for (double[] target : list_methyllevelarray) {
			// target = methyllevel.get(tagcount);
			maxzones.clear();

			/*
			 * この下、区間推定アルゴリズム methyllevelの各要素(double[])について下の作業を行います
			 */
			System.out.println("methyllevel.size: " + target.length
					+ "||  tagcount: " + tagcount);

			System.out.println(target.length);
			// naniositeiru?
			// for (int r = 0; r < methyllevel.size(); r++) {// ここ違うのではないか。
			/*
			 * 正区間と負区間への分割
			 * 
			 * とりあえず正区間だけ抽出します。(反転しているので)
			 */
			int start = -1;
			int end = 0;

//			負区間の抽出に変更した。ここの中身は以後動かさない。（3月)
			for (int j = 0; j < target.length; j++) {

				/*
				 * start, endの登録が始まっていない場合
				 */
				if (start == -1) {
					if (target[j] < 0) {
						start = j;
					}
				} else {
					if (target[j - 1] * target[j] >= 0) {
						// ここは？

					} else {
						/*
						 * 上 条件: 正・負区間<. 今こっちではない
						 */
						/*
						 * 条件：正区間・負区間
						 */
//						if (target[j] > 0) {
//							System.out.println(target[j] + "区間継続");
//							 区間継続
//						} else {
							// 負区間抽出のためのの区間は消してしまったけどここだった
							end = j - 1;
							// j - 1でエラー含みだがちゃんとうごいていれば
							// j == 0となることはないはず
							maxzones.add(new Zone(start, end));
							start = -1;
							end = -1;

//						}
					}
				}
			}
			// 仮登録ここまで

			int M = maxzones.size();

			/*
			 * m区間の抽出アルゴリズム
			 */
			/*
			 * 区間数を減らしていく
			 */
//			律速です。
			System.out.println("CUTTING DOWN ZONES....");
			double summin2 = 1.0 / 0.0;
			double sug3 = 1.0 / 0.0;
			int sugnum1 = 0;

			int d0 = 0;
			int d1 = 0;
			if (m > M) {
				System.out.println("何もしない" + M);
			} else {
				double abs_sum2 = 0;
				for (int u = M - 1; u >= m; u = u - 2) {
					if (((u - M + 1) % 10) == 0){
						System.out.println("現在の区間数・・・" + u + "   区間数を減らしています・・・・");
					}

//					FIXME 別アルゴリズムの実装が望まれる。
					for (int kk = 0; kk < u; kk++) {

//						if ((kk % 1000) == 0) {
//							System.out.println("区間縮小中  " + kk);
//							System.out.println("abs_sum2  " + abs_sum2);
//							System.out.println(maxzones.get(kk).get_start()
//									+ " -> " + maxzones.get(kk).get_end());
//						}

						summin2 = 0;
						for (int rr = maxzones.get(kk).get_start(); rr < maxzones
								.get(kk).get_end(); rr++) {
							summin2 += target[rr];

						}
						abs_sum2 = Math.abs(summin2);
						if (sug3 > abs_sum2) {
							sug3 = abs_sum2;
							sugnum1 = kk;
						}

					}

					d0 = maxzones.get(sugnum1 - 1).get_start();
					d1 = maxzones.get(sugnum1 + 1).get_end();

					for (int kkk = 0; kkk < 3; kkk++) {
						maxzones.remove(sugnum1 - 1);
					}
					maxzones.add(sugnum1 - 1, new Zone(d0, d1));
				}
				alldata.add(maxzones);
			}
			tagcount++;
		}

		
		//pos_indicatorで座標変換
		for(int i = 0; i < alldata.size(); i++){
			alldata.get(i).trans_cpg_fasta(pos_indicator.get(i));
		}
		
		return alldata;

	}
	
	
	

}
