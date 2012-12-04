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
	public static ArrayList<double[]> getWIG(String filename){
		ArrayList<double[]> harvest = new ArrayList<double[]>();
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
			while((line = br.readLine()) != null){
				i++;
			}
			System.out.println("Line: "+ i);
			br.close();
			br = new BufferedReader(new FileReader(filename));
			System.out.println("wigデータ取得中....");
			// ArrayListに変えてみよう
			double[] gallon = new double[i / 100];// 足りるかは未知数
			int j = 0;
			while((line = br.readLine()) != null){
				// if (j > 100000)
					// break;// デバッグ中 <- 重いので手元のPCでテストする時には長さ制限をかけています
				if (chromtag.matcher(line).find())
				{
					harvest.add(gallon);
					// freeしたい
					gallon = new double[i / 100];
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
