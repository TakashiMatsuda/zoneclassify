package zone;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.regex.Pattern;

/**
 * 
 * このクラスは愚直にInputするだけ。他のことをしてはいけない。 テスト通過しました。
 * 
 * @author tks
 * 
 */
public class InputWig {
	static boolean DEBUG = true;

	// 愚直に全部読む必要はないのでは。CpGPositionデータを使ってそこだけよめば良い
	// 実装変更をしようとしている？

	// まずCpGPositionデータを読んで場所データを得る
	// 場所が一致するところのメチル化率を記録していく

	/**
	 * 
	 * @param filename
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static ArrayList<ArrayList<Double>> getWIG(String filename) {
		// ArrayList<double[]> harvest = new ArrayList<double[]>();
		ArrayList<ArrayList<Double>> harvest = new ArrayList<ArrayList<Double>>();

		try {
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
			 * 行数取得 現在使われていません
			 */
			// System.out.println("行数取得中....");
			// while((line = br.readLine()) != null){
			// i++;
			// }
			// System.out.println("Line: "+ i);
			// br.close();
			// br = new BufferedReader(new FileReader(filename));
			System.out.println("wigデータ取得中....");
			// ArrayListに変えてみよう
			// double[] gallon = new double[i / 100];// 足りるかは未知数
			ArrayList<Double> gallon = new ArrayList<Double>();
			// int j = 0;
			long count = 0;
			int nametag = 0;
			while ((line = br.readLine()) != null) {
				/*
				 * 実行状況表示
				 */
				if ((count % 1000000) == 0)
					System.out.println((count / 1000000)
							+ "* 10000000lines  wigデータ取得中....");
				/*
				 * nametag一致
				 */
				if (chromtag.matcher(line).find()) {
					System.out.println("Hit: " + line);
					harvest.add((ArrayList<Double>) gallon);// キャストは高速化のため、今後配列に移行したい
					// freeしたい
					gallon = null;// clearの仕様がよくない
					// nullで書き換え
					// j = 0;
					gallon = new ArrayList<Double>();
				} else {
					/*
					 * 加工
					 */
					gallon.add((Double.parseDouble(line) - 0.5) * (-1));
					// j++;
				}
				count++;
			}
			harvest.add((ArrayList<Double>) gallon);
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return harvest;
	}
}
