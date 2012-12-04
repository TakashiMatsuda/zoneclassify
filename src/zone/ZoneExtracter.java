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
		// エラー含みっぽい
		// そもそもInputWIGにエラーがある可能性も
		System.out.println("READING CpGMethylationLevel DATA...");
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
		// データ型が異なる恐れ
		// 大きくなりすぎた。もっと分割して書き直したい。
		// サイズが0!!どこに問題があるのか確認するべき
		List<List<int[]>> alldata = new ArrayList<List<int[]>>();
		List<int[]> maxzones = new ArrayList<int[]>();
		/*
		 * 使いやすくなったメチル化度列を得る
		 */
		this.methyllevel = mezo();
		//for(int i = 0; i < 100; i++){
			//System.out.print(methyllevel.get(0)[i]);
			//System.out.print(' ');
		//}
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
		double[] target = null;
		while(methyllevel.size() < tagcount){// iについて並列化したいですね--エラー
			// methyllevel.get(tagcount) != null
			target = methyllevel.get(tagcount);
			maxzones.clear();
			
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
				 * m区間の抽出アルゴリズム
				 */
				// 何回か手直し、もとのコードは使い回しなので
				// バグが混入している可能性が
				/*
				 * 区間数を減らしていく
				 */
				double summin2 = 1 / 0;
				double sug3 = 1 / 0;
				int sugnum1 = 0;
				// int sugnum2 = 0;使わない、たぶん
				int d0 = 0;
				int d1 = 0;
				if (m > M){
				
				}
				else{
					/*
			i		 * 
					 */
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
						// 	sugnum1: この番目の区間を中心として前後3つを削除
						// sugnum1 - 1: ここに再び投入する
						d0 = maxzones.get(sugnum1 - 1)[0];
						d1 = maxzones.get(sugnum1 + 1)[1];
						for(int kkk = 0; kkk < 3; kkk++){
							maxzones.remove(sugnum1 - 1);
						}
						int[] newtmp = {d0, d1};
						maxzones.add(sugnum1 - 1, newtmp);
					}// 完成では？
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