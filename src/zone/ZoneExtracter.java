package zone;

import java.util.ArrayList;
import java.util.List;

/**
 * 最小メチル化領域の抽出を担うクラス
 * @author tks
 *
 */
public class ZoneExtracter {
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
		int i = 0;
		double[] target = null;
		while(methyllevel.get(i) != null){
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
				int k = 1;
				int s = 0;
				int[] tmp = new int[2];
				tmp[0] = 0;
				
				
				for (int j = 1; j < l; j++){
					/**
					 * tmpの登録が始まっていない場合
					 */
					if (tmp[0] < -3){
						if (target[j] < 0)
							tmp[0] = target[j];// ここわからない
					}
					
					/**
					 * 条件：正区間・負区間が継続
					 */
					if (target[j - 1] * target[j] >= 0)
						k++;
					else{
						/**
						 * 条件: 正・負区間
						 */
						if (target[j] > 0){
							// ここ空白
						}
						else
						{
							tmp[1] = j;
							maxzones.add(tmp);
							tmp = new int[2];
							tmp[0] = (-1 / 0);
						}
					}
				}
				
				/*
				 * 最短区間長制限区間推定アルゴリズムを実装してください。
				 */
				/*
				 * 変更。m区間の抽出アルゴリズムを実装します。
				 * 定理2により、重み最大のm区間の集合を適用
				 */
				
				double sugmin = 0;
				double summin = 0;
				int sugminnum = 0;
				double sug2 = 0;
				for(int o = M; o > m; o--){
					// 1つめの手法から導かれる候補
					int mm = maxzones.size();
					for(int r = 0; r < mm; r++){
						for(int rr = maxzones.get(r)[0]; rr < maxzones.get(r)[1]; rr++){
							summin += target[rr];
						}
						if (sugmin > summin){
							sugminnum = r;
							sugmin = summin;
						}
					}
				}
				
				
				
				
			}
		}
		return null;
	}
	
	ZoneExtracter(){
		
	}
}