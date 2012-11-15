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
	List<List<Double>> methyllevel;
	
	private List<List<Double>> mezo(){
		List<List<Double>> tmp = InputWig.getWIG("blastula_coverage.wig");
		List<List<Double>> harvest = new ArrayList<List<Double>>();
		double p = 0;
		for (int i = 0; i < tmp.size(); i++){
			ArrayList<Double> part = new ArrayList<Double>();
			for (int j = 0; j < tmp.get(i).size(); j++){
				p = (tmp.get(i).get(j) - 0.5) * (-1);// setを用いて書きなおしたほうがstandard?
				part.add(p);
			}
			harvest.add(part);
		}
		return harvest;
	}
	
	
	/**
	 * 重み最大のm区間をもとめる
	 * 
	 * 主要関数。これを外から呼び出してください。
	 * @return
	 */
	public List<int[]> subZone(int m){
		List<int[]> maxzones = new ArrayList<int[]>();
		methyllevel = mezo();
		int i = 0;
		List<Double> target = null;
		while(methyllevel.get(i) != null){
			target = methyllevel.get(i);
			// この下、推定アルゴリズムを記述してください
			
			i++;
		}
		
		return maxzones;
	}
}