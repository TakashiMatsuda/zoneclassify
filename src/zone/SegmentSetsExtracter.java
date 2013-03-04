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

	/**
	 * staticに用いられるクラスのためコンストラクタは特に使われない、空です
	 */
	public SegmentSetsExtracter() {
	}

	// FIXME ゆくゆくは型を変更する。DataSetとあわせて変更が必要。配列化して高速化したい。
	// FIXME 最短区間長制限問題をといているが、そもそもの「メチル化率」の定義をしていない
	// 区間に対するメチル化率という言葉の定義が必要。
	/**
	 * 
	 * @param filename
	 * @return
	 */
	public static List<ZoneList> extract(int m, String filename) {
		System.out.println("EXTRACTING subZones....");
		List<ZoneList> alldata = new ArrayList<ZoneList>();
		ZoneList maxzones = new ZoneList();
		
		System.out.println("READING CpGMethylationLevel DATA...  :  "
				+ filename);
		List<ArrayList<Double>> methyllevel = InputWig.getWIG(filename);
		
		if (methyllevel.size() == 0){
			System.err.println(methyllevel.size());
		}
		
		/*
		 * tagcountは遺伝子断片の序数
		 */
		int tagcount = 0;
		/*
		 * targetの意味を各　遺伝子断片の中身、wigファイルの成れの果て
		 */
		ArrayList<Double> target = null;
		while (tagcount < methyllevel.size()){
			target = methyllevel.get(tagcount);
			maxzones.clear();
			
			/*
			 * この下、区間推定アルゴリズム
			 * methyllevelの各要素(double[])について下の作業を行います
			 */
			System.out.println("methyllevel.size: " + methyllevel.size()
					+ "||  tagcount: " + tagcount);
			
			for (int r = 0; r < methyllevel.size(); r++){
				/*
				 * 正区間と負区間への分割
				 * 
				 * とりあえず正区間だけ抽出します。(反転しているので)
				 */
				int start = 0;
				int end = 0;
				
				for (int j = 0; j < target.size(); j++){
					/*
					 * start, endの登録が始まっていない場合
					 */
					if (start == -1){
						if (target.get(j) > 0){
							start = j;
						}
					} else {
						if (target.get(j - 1) * target.get(j) >= 0){
							
						} else {
							/*
							 * 上
							 * 条件: 正・負区間<. 今こっちではない
							 */
							/*
							 * 条件：正区間・負区間
							 */
							if (target.get(j) > 0){
								// 区間継続
							} else {
//								負区間抽出のためのの区間は消してしまったけどここだった
								end = j - 1;
//								j - 1でエラー含みだがちゃんとうごいていれば
//								j == 0となることはないはず
								maxzones.add(new Zone(start, end));
								start = -1;
								end = -1;
								
							}
						}
					}
				}
				int M = maxzones.size();
				
				/*
				 * m区間の抽出アルゴリズム
				 */
//				何回か手直し、もとのコードは使い回しなので
//				バグが混入している可能性が
				/*
				 * 区間数を減らしていく
				 */
				System.out.println("CUTTING DOWN ZONES....");
				double summin2 = 1.0 / 0.0;
				double sug3 = 1.0 / 0.0;
				int sugnum1 = 0;
				
				int d0 = 0;
				int d1 = 0;
				if (m > M){
					System.out.println("何もしない");
				} else {
					for(int u = M - 1; u >= m; u = u - 2){
						System.out.println("現在の区間数・・・" + u + "   区間数を減らしています・・・・");
						for (int kk = 0; kk < u; kk++){
							summin2 = 0;
							
							for(int rr = maxzones.get(kk).get_start(); rr < maxzones.get(kk).get_end(); rr++){
								summin2 += target.get(rr);
							}
							if (sug3 > Math.abs(summin2)){
								sug3 = Math.abs(summin2);
								sugnum1 = kk;
							}
						}
						d0 = maxzones.get(sugnum1 - 1).get_start();
						d1 = maxzones.get(sugnum1 + 1).get_end();
						
						for(int kkk = 0; kkk < 3; kkk++){
							maxzones.remove(sugnum1 - 1);
						}
						maxzones.add(sugnum1 - 1, new Zone(d0, d1));
					}
					alldata.add(maxzones);
				}
				
			}
			tagcount++;
			
		}
		return alldata;
		
	}

}
