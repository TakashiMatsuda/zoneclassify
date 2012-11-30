package zone;

import java.util.ArrayList;
import java.util.List;

/**
 * 最小メチル化領域の抽出を担うクラス
 * @author tks
 *
 */
public class ZoneExtracter {
	static boolean DEBUG = false;
	String genome;
	int[] sitepos;
	List<double[]> methyllevel;
	
	
	/**
	 * harvest ArrayList<double[]>
	 * @return
	 */
	private List<double[]> mezo(){
		List<double[]> tmp = InputWig.getWIG("blastula_CpGMethylationLevel.wig");
		List<double[]> harvest = new ArrayList<double[]>();
		double p = 0;
		for (int i = 0; i < tmp.size(); i++){
			double[] bunch = new double[tmp.get(i).length];
			for (int j = 0; j < tmp.get(i).length; j++){
				p = (tmp.get(i)[j] - 0.5) * (-1);
				bunch[i] = p;
			}
			tmp.set(i, bunch);
		}
		return harvest;
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
		List<List<int[]>> alldata = new ArrayList<List<int[]>>();
		List<int[]> maxzones = new ArrayList<int[]>();
		/*
		 * 使いやすくなったメチル化度列を得る
		 */
		this.methyllevel = mezo();
		int al = methyllevel.size();
		/*
		 * iの意味がわかりませｎ
		 * iは遺伝子断片の序数
		 */
		int i = 0;
		/*
		 * targetの意味書け
		 * 遺伝子断片の中身、wigファイルの成れの果て
		 */
		double[] target = null;
		while(methyllevel.get(i) != null){// iについて並列化したいですね
			target = methyllevel.get(i);
			
			/*
			 *  この下、区間推定アルゴリズム
			 */
			/*
			 * methyllevelの各要素(double[])について下の作業を行います
			 */
			for(int r = 0; r < al; r++){
				/*
				 * 正区間と負区間への分割
				 * 
				 * とりあえず正区間だけ抽出します。（反転しているので）
				 */
				int l = target.length;
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
						if (target[j] > 0){
							tmp[0] = j;
						}
					}
					else{
						if (target[j - 1] * target[j] >= 0)
							k++;
						else{
							/**
							 * 条件: 正・負区間<.今はこっちではない
							 */
							/**
							 * 条件：正区間・負区間が継続
							 */
							if (target[j] > 0)
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
				 * 変更。m区間の抽出アルゴリズムを実装します。
				 * 定理2により、重み最大のm区間の集合を適用
				 */
				
				// 不要になった部分
				if (DEBUG)
				{
				double sug1 = 1 / 0;
				double summin = 0;
				int sugminnum1 = 0;
				double sug2 = 1 / 0;
				int sugminnum2 = 0;
				for(int o = M; o > m; o--){// Mはまだ未記入
					/*
					 * 1つめの手法から導かれる候補
					 */
					int mm = maxzones.size();
					for(int u = 0; u < mm; u++){
						summin = 0;
						for(int rr = maxzones.get(u)[0]; rr < maxzones.get(u)[1]; rr++){
							summin += target[rr];
						}
						if (sug1 > summin){
							sugminnum1 = u;
							sug1 = summin;
						}
					}
					
					/*
					 * 2つめの手法から導かれる候補
					 */
					for(int u = 0; u < mm - 1; u++){
						summin = 0;
						for(int rr = maxzones.get(u)[1]; rr < maxzones.get(u + 1)[0]; rr++){// 端数のカウントが不安
							summin += target[rr];
						}
						if (sug2 > Math.abs(summin)){
							sugminnum2 = u;
							sug2 = summin;
						}
					}
					
					/*
					 * どちらの手法が小さい損失であるか
					 */
					if (sug1 > sug2){
						maxzones.remove(sugminnum1);// あってるかどうか確認がひつようです
					}
					else{
						
						
						
						// 区間族がソートされている必要があることに気づきました。
						// データの型を変更する必要があると思います。
					}
				}
				}// ここまでデバッグ、使われないコード
				
				
				// 何回か手直し、もとのコードは使い回しなので
				// バグが混入している可能性が
				double summin2 = 1 / 0;
				double sug3 = 1 / 0;
				int sugnum1 = 0;
				// int sugnum2 = 0;
				int d0 = 0;
				int d1 = 0;
				for(int u = M - 1; u >= m; u--){
					summin2 = 0;
					for(int kk = 0; kk < u; kk++){// 要素数がぴったり一致しているならば動作する、管理についてのあそびがない設計部分です
						for(int rr = maxzones.get(kk)[0]; rr < maxzones.get(kk)[1]; rr++){
							summin2 += target[rr];
						}
						if (sug3 > summin2){
							sug3 = summin2;
							sugnum1 = kk;
						}
					}
					/*
					 * Deletion step
					 */
					// sugnum1: この番目の区間を中心として前後3つを削除
					// sugnum1 - 1: ここに再び投入する
					d0 = maxzones.get(sugnum1 - 1)[0];
					d1 = maxzones.get(sugnum1 + 1)[1];
					for(int kkk = 0; kkk < 3; kkk++){
						maxzones.remove(sugnum1 - 1);
					}
					int[] newtmp = {d0, d1};
					maxzones.add(sugnum1 - 1, newtmp);
				}// 完成では？
			}
			i++;
		}
		return null;
	}
	
	ZoneExtracter(){
	}
}