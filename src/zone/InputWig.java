package zone;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

public class InputWig {
	public static ArrayList<double> read(String filename){
		ArrayList<Double> harvest = new ArrayList<Double>();
		try{
			// ArrayListで読み取るのが早いか、それとも
			// 配列で読むために一度回して行数を取得したほうが早いか
			String line = null;
			BufferedReader br = new BufferedReader(new FileReader(filename));
			while((line = br.readLine()) != null){
				harvest.add(Double.parseDouble(line));
			}
			br.close();
		} catch(Exception e){
			e.printStackTrace();
		}
		return harvest;
	}
}
