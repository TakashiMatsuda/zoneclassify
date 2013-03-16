package zone;

import static org.junit.Assert.*;

import org.junit.Ignore;
import org.junit.Test;

public class BoostTest {

	@Ignore
	public void testMain() {
		Boost.main(null);
	}
	
	@Ignore
	public void testRun(){
		Boost exa = Boost.run();
	}
	
	@Test
	public void testWrite_cis_element(){

		Boost exa = Boost.run();
		System.out.println("ATAT  : " + exa.methyl_prediction("ATAT"));
		exa.write_cis_element();
//		全部順番に出ている
//		結果を反映したものなのか確認する
//		標準の期待値の計算がおかしくなっているのか。
		
	}
	
	

}
