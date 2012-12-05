package zone;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.regex.Pattern;

/**
 * 
 * @author tks
 *
 */
public class InputWig {
	static boolean DEBUG = false;
	
	/**
	 * 
	 * @param filename
	 * @return
	 */
	public static ArrayList<ArrayList<Double>> getWIG(String filename){
		
		// ArrayList<double[]> harvest = new ArrayList<double[]>();
		ArrayList<ArrayList<Double>>harvest = new ArrayList<ArrayList<Double>>();

		try{
			System.out.println("READING COVERAGE DATA...");
			String line = null;
			BufferedReader br = new BufferedReader(new FileReader(filename));
			Pattern chromtag = Pattern.compile("^fixed");
			// int i = 0;
			/*
			 * empty reading for the first line
			 */
			br.readLine();
			/*
			 * core, Read and change the wig format to List<double[]>.
			 */
			/*
			 * 行数取得
			 */
			System.out.println("行数取得中....");
			//while((line = br.readLine()) != null){
			//	i++;
			//}
			// System.out.println("Line: "+ i);
			// br.close();
			// br = new BufferedReader(new FileReader(filename));
			System.out.println("wigデータ取得中....");
			// ArrayListに変えてみよう
			//double[] gallon = new double[i / 100];// 足りるかは未知数
			ArrayList<Double> gallon = new ArrayList<Double>();
			// int j = 0;
			long count = 0;
			int nametag = 0;
			while((line = br.readLine()) != null){
				/*
				 * 実行状況表示
				 */
				if ((count % 10000000) == 0)
					System.out.println((count / 10000000) + "* 10000000lines  wigデータ取得中....");
				/*
				 * nametag一致
				 */
				if (chromtag.matcher(line).find())
				{
					harvest.add(gallon);
					// freeしたい
					gallon.clear();
					// j = 0;
					
					// デバッグ中
					if (DEBUG){
					nametag++;
					if (nametag > 4000){
						return harvest;// これを書いたことの影響を考えろ
					}
					}
				}
				else{
					/*
					 * 追加
					 */
					gallon.add((Double.parseDouble(line) -0.5) * (-1));
					// j++;
				}
				count++;
			}
			br.close();
		} catch(Exception e){
			e.printStackTrace();
		}
		// harvestに何も入っていない
		return harvest;
	}
}
