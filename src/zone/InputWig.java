package zone;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.regex.Pattern;

public class InputWig {
	public static ArrayList<Double[]> getWIG(String filename){
		ArrayList<Double[]> harvest = new ArrayList<Double[]>();
		try{
			// ArrayListで読み取るのが早いか、それとも
			// 配列で読むために一度回して行数を取得したほうが早いか
			String line = null;
			BufferedReader br = new BufferedReader(new FileReader(filename));
			Pattern chromtag = Pattern.compile("^>fixed");
			ArrayList<Double> gallon = new ArrayList<Double>();
			while((line = br.readLine()) != null){
				if (chromtag.matcher(line).find())
				{
					harvest.add((Double[]) gallon.toArray());
					gallon.clear();
				}
				else{
					gallon.add(Double.parseDouble(line));
				}
			}
			br.close();
		} catch(Exception e){
			e.printStackTrace();
		}
		return harvest;
	}
}
