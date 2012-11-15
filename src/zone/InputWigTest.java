package zone;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.junit.Test;

/**
 * JUnitも同期しなければいけないようです。
 * @author takashi
 *
 */
public class InputWigTest {

	@Test
	public void testgetWIG() {
		try{
			// ArrayListで読み取るのが早いか、それとも
			// 配列で読むために一度回して行数を取得したほうが早いか
			String line = null;
			BufferedReader br = new BufferedReader(new FileReader("blastula_coverage.wig"));
			int i = 0;
			while((line = br.readLine()) != null){
				i++;
			}
			System.out.println(i);
			br.close();
		} catch(Exception e){
			e.printStackTrace();
		}
		
		
		ArrayList<List<Double>> exa = InputWig.getWIG("blastula_coverage.wig");
		assertEquals(0.0, exa.get(1).get(1), 0.01);
	}

}
