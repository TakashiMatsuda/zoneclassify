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
	int[] coverage;
	int[] methellevel;
	
	/**
	 * 
	 * @return
	 */
	private double[] calclevel(){
		// 入力形式に勘違いがあったようなので、実装停止
		// 入力は[0, 1]なので、正負対応対応への変換と正負反転変換をかける
		int l = genome.length();
		double[] m = new double[l];
		for(int i = 0; i < l; i++){
			if (sitepos[i] == 1)
				m[i] = (double)coverage[i] / (double)methellevel[i];
			else
				m[i] = -1;//
		}
		return m;
	}
	
	
	/**
	 * 重み最大のm区間をもとめる主要関数。これを外から呼び出してください。
	 * @return
	 */
	public List<String> subZone(int m){
		List<String> maxzone = new ArrayList<String>();
		
		// 重み最大m区間をもとめるアルゴリズムを実装して下さい。
		return maxzone;
	}
}