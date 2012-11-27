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
	public List<int[]> subZone(int m){
		List<int[]> maxzones = new ArrayList<int[]>();
		/*
		 * 使いやすくなったメチル化度列を得る
		 */
		methyllevel = mezo();
		int i = 0;
		double[] target = null;
		while(methyllevel.get(i) != null){
			target = methyllevel.get(i);
			
			/*
			 *  この下、区間推定アルゴリズム
			 */
			
			
			/*
			 * 正区間と負区間への分割
			 */
			int l = target.length;
			int k = 1;
			int s = 0;
			int[] tmp = new int[2];
			tmp[0] = 0;
			for (int j = 1; j < l; j++){
				if (target[j - 1] * target[j] >= 0)
					k++;
				else{
					if (target[j - 1] > 0){
						// ここ空白
					}
					else
					{
						tmp[1] = j;
						maxzones.add(tmp.clone());
						if (j < j - 1)
							tmp[0] = j + 1;
					}
				}
			}
			
			/*
			 * 最短区間長制限区間推定アルゴリズムを実装してください。
			 */
			
			
			i++;
		}
		return maxzones;
	}
	
	ZoneExtracter(){
		
	}
}