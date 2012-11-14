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
	 * 重み最大のm区間をもとめる主要関数。これを外から呼び出してください。
	 * @return
	 */
	public List<String> subZone(int m){
		List<String> maxzone = new ArrayList<String>();
		
		// 重み最大m区間をもとめるアルゴリズムを実装して下さい。
		return maxzone;
	}
}