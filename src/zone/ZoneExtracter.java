package zone;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

// 構成比で書き換える
// TODO ZoneExtracterを完成させる、それでこの仕事はおしまい

/**
 * 最小メチル化領域の抽出を担うクラス
 * @author tks
 *
 */
public class ZoneExtracter {
	static boolean DEBUG = false;
	String genome;
	int[] sitepos;
	List<ArrayList<Double>> methyllevel;
	
	
	/**
	 * harvest ArrayList<double[]>
	 * 正負反転
	 * -0.5
	 * 関数
	 * 必要なくなった
	 * @return
	 */
	private List<ArrayList<Double>> mezo(){
		String filename = "blastula_CpGMethylationLevel_chrom=1.wig";
		System.out.println("READING CpGMethylationLevel DATA...  :  " + filename);
		List<ArrayList<Double>> tmp = InputWig.getWIG(filename);
		System.out.println(tmp.size());
		double p = 0;
		ArrayList<Double> bunch = new ArrayList<Double>();
		for (int i = 0; i < tmp.size(); i++){
			System.out.println("読み込んだwigファイルを処理可能な形に変形しています・・・・    ( " + i + " / " + tmp.size() + " )");
			bunch.ensureCapacity(100000);
			for (int j = 0; j < tmp.get(i).size(); j++){
				p = (tmp.get(i).get(j) - 0.5) * (-1);
				bunch.add(p);
			}
			System.out.println(bunch.size());
			tmp.set(i, bunch);
			
//			下のコメントはおかしい
			bunch.clear();// clearでやるのは逆効果かもしれない。メモリ領域確保のオーバーヘッドをとるか、それとも無駄に消費されるメモリ領域をとるか
		}
		return tmp;
	}
	
	
	/**
	 * 重み最大のm区間をもとめる
	 * 
	 * 
	 * 主要関数。これを外から呼び出してください。
	 * @param 	m
	 * @return  maxzones
	 */
	public List<List<int[]>> subZone(int m){
		System.out.println("EXTRACTING subZones....");
		List<List<int[]>> alldata = new ArrayList<List<int[]>>();
		List<int[]> maxzones = new LinkedList<int[]>();
		
		//for(int i = 0; i < 100; i++){
			//System.out.print(methyllevel.get(0)[i]);
			//System.out.print(' ');
		//}
		
		String filename = "blastula_CpGMethylationLevel_chrome=1.wig";
		System.out.println("READING CpGMethylationLevel DATA...  :  " + filename);
		methyllevel = InputWig.getWIG(filename);
		// もらったものが空じゃないか確認
		int al = methyllevel.size();
		System.out.println(al);
		/*
		 * tagcountは遺伝子断片の序数
		 */
		int tagcount = 0;
		/*
		 * targetの意味書け
		 * 遺伝子断片の中身、wigファイルの成れの果て
		 */
		ArrayList<Double> target = null;
		while(tagcount < methyllevel.size()){// tagcountについて並列化したい
			// methyllevel.get(tagcount) != null
			target = methyllevel.get(tagcount);
			maxzones.clear();
			
			/*
			 *  この下、区間推定アルゴリズム
			 */
			/*
			 * methyllevelの各要素(double[])について下の作業を行います
			 */
			System.out.println("methyllevel.size:  " + methyllevel.size() + "||  tagcount: " + tagcount);
			for(int r = 0; r < al; r++){
				/*
				 * 正区間と負区間への分割
				 * 
				 * とりあえず正区間だけ抽出します。（反転しているので）
				 */
				int l = target.size();
				/*
				 * k: 抽出区間の長さ<-必要？
				 */
				int k = 1;
				int s = 0;
				int[] tmp = new int[2];
				tmp[0] = -1;
				// tmp[0]が正区間のはじまり
				// tmp[1]が正区間のおわり
				// target[j]が正なら登録開始
				for (int j = 0; j < l; j++){
					/**
					 * tmpの登録が始まっていない場合
					 */
					if (tmp[0] == -1){
						if (target.get(j) > 0){
							tmp[0] = j;
						}
					}
					else{
						if (target.get(j - 1) * target.get(j) >= 0)
							k++;
						else{
							/**
							 * 条件: 正・負区間<.今はこっちではない
							 */
							/**
							 * 条件：正区間・負区間が継続
							 */
							if (target.get(j) > 0)
							{
								// 区間継続
							}
							else{
								// 負区間抽出のための区間は消してしまったけどここだった
								tmp[1] = j - 1;//j - 1でエラー含みだがちゃんと動いていればj == 0となることはないはず
								maxzones.add(tmp);
								tmp = new int[2];
								tmp[0] = -1;
							}
						}
					}
				}
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
				// int sugnum2 = 0;使わない、たぶん
				int d0 = 0;
				int d1 = 0;
				if (m > M){
					System.out.println("何もしない");
					// ここに全部入ってくる
					// 何もしない
				}
				else{
					/*
			i		 * 並列化できない
					 */
					// 実際には、子のアルゴリズムを使うのであれば区間の用意のやり方が変わってくる
					// 遅い
					for(int u = M - 1; u >= m; u= u -2){// 実験的に。アルゴリズムとしては間違っています <- どういうこと？？
						//if ((u % 100000) == 0){
							System.out.println("現在の区間数・・・　" + u  +"　　区間数を減らしています・・・・");
						//}
						for(int kk = 0; kk < u; kk++){// 要素数がぴったり一致しているならば動作する、管理についてのあそびがない設計部分です
							summin2 = 0;
							for(int rr = maxzones.get(kk)[0]; rr < maxzones.get(kk)[1]; rr++){// IndexOutOfBoundsException
								summin2 += target.get(rr);
							}
							if (sug3 > Math.abs(summin2)){
								sug3 = Math.abs(summin2);
								sugnum1 = kk;
							}
						}
						/*
						 * Deletion step
						 */
						// 	sugnum1: この番目の区間を中心として前後3つを削除
						// sugnum1 - 1: ここに再び投入する
						// sugunum1が0で流れてきています
						
						// 3つ減らして1お歯科入れてない、-2になっているのだからuも-2しなければいけない
						
						// sugnum -1: sugnumが端数で合った場合は別処理をしなければいけない
						// アルゴリズムを再検討して下さい
						d0 = maxzones.get(sugnum1 - 1)[0];
						d1 = maxzones.get(sugnum1 + 1)[1];
						for(int kkk = 0; kkk < 3; kkk++){
							maxzones.remove(sugnum1 - 1);
						}
						int[] newtmp = {d0, d1};
						maxzones.add(sugnum1 - 1, newtmp);
					}
				alldata.add(maxzones);
				// tagcount++; tagcountの場所が違う気がする
				}
			}
			tagcount++;
		}
		return alldata;
	}
	
	ZoneExtracter(){
	}
}