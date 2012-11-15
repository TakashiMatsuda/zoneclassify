package zone;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * 動作の確認終わっています
 * かなり時間がかかります
 * @author tks
 *
 */
public class InputWig {
	public static ArrayList<List<Double>> getWIG(String filename){
		ArrayList<List<Double>> harvest = new ArrayList<List<Double>>();
		try{
			// ArrayListで読み取るのが早いか、それとも
			// 配列で読むために一度回して行数を取得したほうが早いか
			String line = null;
			BufferedReader br = new BufferedReader(new FileReader(filename));
			Pattern chromtag = Pattern.compile("^fixed");
			ArrayList<Double> gallon = new ArrayList<Double>();
			int i = 0;
			int j = 0;
			while((line = br.readLine()) != null){
				if (i % 100000 == 0)
					System.out.println(i);
				else if(i > 1000000)// 安全ピン
					break;
				if (j % 10000 == 0)
					System.out.println(j);
				if (chromtag.matcher(line).find())
				{
					harvest.add(gallon);
					gallon.clear();
					j = 0;
				}
				else{
					gallon.add(Double.parseDouble(line));
				}
				i++;
				j++;
			}
			br.close();
		} catch(Exception e){
			e.printStackTrace();
		}
		return harvest;
	}
}
