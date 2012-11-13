package zone;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

public class InputWig {
	public static ArrayList<Byte> read(String filename){
		ArrayList<Byte> harvest = new ArrayList<Byte>();
		try{
			// ArrayListで読み取るのが早いか、それとも
			// 配列で読むために一度回して行数を取得したほうが早いか
			String line = null;
			BufferedReader br = new BufferedReader(new FileReader(filename));
			while((line = br.readLine()) != null){
				harvest.add((byte)line.toCharArray()[0]);// ここ怪しい、文法最チェック
			}
			br.close();
		} catch(Exception e){
			e.printStackTrace();
		}
		return harvest;
	}
}
