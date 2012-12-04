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
	// 何もharvestに入ってない
	public static ArrayList<ArrayList<Double>> getWIG(String filename){
		
		// ArrayList<double[]> harvest = new ArrayList<double[]>();
		ArrayList<ArrayList<Double>>harvest = new ArrayList<ArrayList<Double>>();
		try{
			System.out.println("READING COVERAGE DATA...");
			String line = null;
			BufferedReader br = new BufferedReader(new FileReader(filename));
			Pattern chromtag = Pattern.compile("^fixed");
			int i = 0;
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
			while((line = br.readLine()) != null){
				if ((count % 10000000) == 0)
					System.out.println((count / 10000000) + "* 10000000lines  wigデータ取得中....");
				// if (j > 100000)
					// break;// デバッグ中 <- 重いので手元のPCでテストする時には長さ制限をかけています
				if (chromtag.matcher(line).find())
				{
					harvest.add(gallon);
					// freeしたい
					gallon.clear();
					// j = 0;
				}
				else{
					/*
					 * 登録時に同時に変換してから格納
					 */
					gallon.add((Double.parseDouble(line) - 0.5) * (-1));// 変更点、あとでワークステーション側とマージして下さい
					// j++;
				}
				count++;
			}
			br.close();
		} catch(Exception e){
			e.printStackTrace();
		}
		return harvest;
	}
}
