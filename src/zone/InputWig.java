package zone;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.regex.Pattern;

/**
 * 動作の確認終わっています
 * かなり時間がかかります
 * @author tks
 *
 */
public class InputWig {
	/**
	 * 途中で何も書かなかったせいで、なにがかいてあるのかわからない
	 * 
	 * @param filename
	 * @return
	 */
	public static ArrayList<double[]> getWIG(String filename){
		ArrayList<double[]> harvest = new ArrayList<double[]>();
		try{
			String line = null;
			BufferedReader br = new BufferedReader(new FileReader(filename));
			Pattern chromtag = Pattern.compile("^fixed");
			int i = 0;
			/*
			 * empty read
			 */
			br.readLine();
			/*
			 * core, Read and change the wig format to List<double[]>.
			 */
			while((line = br.readLine()) != null){
				if (i > 1000000)//
					break;
				i++;
			}
			double[] gallon = new double[i / 20];
			int j = 0;
			while((line = br.readLine()) != null){
				if (chromtag.matcher(line).find())
				{
					harvest.add(gallon);
					gallon = new double[i / 20];
					j = 0;
				}
				else{
					gallon[j] = Double.parseDouble(line);
					j++;
				}
			}
			br.close();
		} catch(Exception e){
			e.printStackTrace();
		}
		return harvest;
	}
}
