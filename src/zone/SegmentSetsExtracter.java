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
	private static final int MAX_BOUND = 39973034;

	/**
	 * staticに用いられるクラスのためコンストラクタは特に使われない、空です
	 */
	public SegmentSetsExtracter() {
	}
	
//	TODO まず、InputWigによってい得られたものからCpGPosition値ベースの区間を推定する。
//	その座標を塩基対座標に変換すればよい。
	
	// FIXME 最短区間長制限問題をといているが、そもそもの「メチル化率」の定義をしていない
	// 区間に対するメチル化率という言葉の定義が必要。
	/**
	 * 
	 * @param filename
	 * @param sign
	 * @param m
	 * @return
	 */
	public static List<ZoneList> extract(int sign, int m, String filename) {
		System.out.println("EXTRACTING subZones....");
		List<ZoneList> alldata = new ArrayList<ZoneList>();
		// kouho
		ZoneList maxzones = new ZoneList();

		// wig shutoku
		System.out.println("READING CpGMethylationLevel DATA...  :  "
				+ filename);
		ArrayList<int[]> pos_indicator = InputWig.load_CpGPosition(filename, MAX_BOUND);
		List<double[]> list_methyllevelarray = InputWig.getWIG(filename, pos_indicator));

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
		// 拡張forへの切り替え
		for (double[] target : list_methyllevelarray) {
			// target = methyllevel.get(tagcount);
			maxzones.clear();

			/*
			 * この下、区間推定アルゴリズム methyllevelの各要素(double[])について下の作業を行います
			 */
			System.out.println("methyllevel.size: " + list_methyllevelarray.size()
					+ "||  tagcount: " + tagcount);

			// naniositeiru?
			// for (int r = 0; r < methyllevel.size(); r++) {// ここ違うのではないか。
			/*
			 * 正区間と負区間への分割
			 * 
			 * とりあえず正区間だけ抽出します。(反転しているので)
			 */
			int start = -1;
			int end = 0;

			for (int j = 0; j < target.length; j++) {
				/*
				 * start, endの登録が始まっていない場合
				 */
				if (start == -1) {
					if (target[j] > 0) {
						start = j;
					}
				} else {
					if (target[j - 1] * target[j] >= 0) {

					} else {
						/*
						 * 上 条件: 正・負区間<. 今こっちではない
						 */
						/*
						 * 条件：正区間・負区間
						 */
						if (target[j] > 0) {
							// 区間継続
						} else {
							// 負区間抽出のためのの区間は消してしまったけどここだった
							end = j - 1;
							// j - 1でエラー含みだがちゃんとうごいていれば
							// j == 0となることはないはず
							maxzones.add(new Zone(start, end));
							start = -1;
							end = -1;

						}
					}
				}
				// }
			}
			// 仮登録ここまで
			
			int M = maxzones.size();

			/*
			 * m区間の抽出アルゴリズム
			 */
			// 何回か手直し、もとのコードは使い回しなので
			// バグが混入している可能性が
			/*
			 * 区間数を減らしていく
			 */
			System.out.println("CUTTING DOWN ZONES....");
			double summin2 = 1.0 / 0.0;
			double sug3 = 1.0 / 0.0;
			int sugnum1 = 0;

			int d0 = 0;
			int d1 = 0;
			if (m > M) {
				System.out.println("何もしない");
			} else {
				
				
				
				double abs_sum2 = 0;
				for (int u = M - 1; u >= m; u = u - 2) {
					System.out.println("現在の区間数・・・" + u + "   区間数を減らしています・・・・");
					
					// FIXME アルゴリズムのミス
					for (int kk = 0; kk < u; kk++) {// TODO ここを並列化して下さい。
						if ((kk % 1000) == 0){
							System.err.println(kk);
						}
						summin2 = 0;
										
//						FIXME この下、とても遅い
//						-> 処理数が多くなっているのは、CpGMethylationSiteの乗法を生かしていないため。
						for (int rr = maxzones.get(kk).get_start(); 
								rr < maxzones.get(kk).get_end(); rr++) {
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
		
//		TODO コード改良中。この上まではpositionベースの区間を求めておき、ここから下で変換する。
//		最終格納時にpos_indicatorで座標変換してもよい。		
		return alldata;

	}
	
}
