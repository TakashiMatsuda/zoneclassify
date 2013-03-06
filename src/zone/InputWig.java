package zone;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * 
 * このクラスは愚直にInputするだけ。他のことをしてはいけない。 テスト通過しました。
 * 読み取り機構に問題なし。その後の標準化の方法を考えなければいけない。 
 * @author tks
 * 
 */
public class InputWig {
	static boolean DEBUG = true;
	
	/**
	 * max_boundはgrepで得ておいて下さい。（中からgrepを走らせてもいいけど、遅いし、、、)
	 * 完成しています。
	 * 
	 * @param filename
	 * @param max_bound
	 * @return
	 */
	public static ArrayList<int[]> load_CpGPosition(String filename, int max_bound) {
		ArrayList<int[]> res = new ArrayList<int[]>();
		try {
			System.out.println("READING CpG_POSITION DATA...." + filename);
			String line = null;
			BufferedReader br = new BufferedReader(new FileReader(filename));
			Pattern tag_chrom = Pattern.compile("^fixed");
			Pattern tag_pos = Pattern.compile("^1");
			
			int[] array_pos = new int[max_bound];// 大したオーバーヘッドではないので気にしない、メモリも足りてるし
			int count = 0;
			int tag_count = 0;
			int pos_count = 0;
			
			br.readLine();
			while ((line = br.readLine()) != null) {
//				CAUTION countの値は正確でなくてはいけない
				if ((count % 1000000) == 0)
					System.out.println((count / 1000000)
							+ "* 10000000lines  CpgPositionデータ取得中....");
				/*
				 * nametag一致
				 */
				if (tag_pos.matcher(line).find()) {
					array_pos[pos_count] = count;
					count++;
					pos_count++;
				} else if (tag_chrom.matcher(line).find()) {
					tag_count++;
					count = 0;
					System.out.println("Hit: " + line);
					res.add(array_pos);
					array_pos = new int[max_bound];
				} else {
					count++;
					continue;
				}
				
			}
			res.add(array_pos);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return res;

	}

	
	
	/**
	 * 
	 * @param filename
	 * @return
	 */
	public static ArrayList<double[]> getWIG(String filename, ArrayList<int[]> pos_indicator) {
//		TODO 型変更しました。対応して下さい
		// ArrayList<double[]> harvest = new ArrayList<double[]>();// こっちに戻すべきかもしれない。
		ArrayList<ArrayList<Double>> harvest = new ArrayList<ArrayList<Double>>();
		ArrayList<double[]> res = new ArrayList<>();
		
		try {
			System.out.println("READING COVERAGE DATA..." + filename);
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

			// ArrayListに変えてみよう
			// double[] gallon = new double[i / 100];// 足りるかは未知数
//			ArrayList<Double> gallon = new ArrayList<Double>();
			// int j = 0;
			long count = 0;
			
			
			
			
			
//			座標と、値を結び付けなければいけない。
			
			
//			新しい形
			double[] gallon2 = null;
			for(int[] site:pos_indicator){
				int zahyo = 0;
				int now = 0;
				gallon2 = new double[site.length];
				while((line = br.readLine()) != null){
					if ((zahyo % 1000000) == 0)
						System.out.println((zahyo / 1000000)
								+ "* 10000000lines  methylationlevelデータ取得中....");
					
					if (!(zahyo == site[now])){
						zahyo++;
						continue;
					} else {
//						FIXME ここの修正が必要かもしれない。
						gallon2[now] = (Double.parseDouble(line) - 0.5) * (-1);
						now++;
						zahyo++;
						 /* 1断片の解析が終了したか確認します */
						if (site[now] == 0){
							res.add(gallon2);
							break;
						}	
					}
				}
			}
			
			
//			while ((line = br.readLine()) != null) {
				/*
				 * 実行状況表示
				 */
//				if ((count % 1000000) == 0)
//					System.out.println((count / 1000000)
//							+ "* 10000000lines  wigデータ取得中....");
				/*
				 * nametag一致
				 */
//				if (chromtag.matcher(line).find()) {
//					System.out.println("Hit: " + line);
//					harvest.add((ArrayList<Double>) gallon);// キャストは高速化のため、今後配列に移行したい
//					 freeしたい
//					gallon = null;// clearの仕様がよくない
					// nullで書き換え
					// j = 0;
//					gallon = new ArrayList<Double>();
//				} else {
					/*
					 * 加工
					 */	
//					FIXME メチル化度の読み取り方法とその標準化についてここを修正する
//					gallon.add((Double.parseDouble(line) - 0.5) * (-1));
//					 j++;
//				}
//				count++;
//			}
//			harvest.add((ArrayList<Double>) gallon);
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return res;
	}
}
