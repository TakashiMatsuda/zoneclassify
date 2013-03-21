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
		long t1 = System.nanoTime();
		Boost exa = Boost.run();
		long t2 = System.nanoTime();
		System.out.println(t2 - t1);
	}
	
	@Test
	public void testWrite_cis_element(){
		long t1 = System.nanoTime();
		Boost exa = Boost.run();
		long t2 = System.nanoTime();
		System.out.println("ATAT  : " + exa.methyl_prediction("ATAT"));
		exa.write_cis_element();		
	}
	
	

}
