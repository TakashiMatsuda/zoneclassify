package zone;

import static org.junit.Assert.*;

import org.junit.Test;

public class DataSetTest {
	static boolean DEBUG = false;
	
	@Test
	public void testLoad() {
		ZoneExtracter farmer = new ZoneExtracter();
		DataSet exa = new DataSet();
		exa.load(farmer.subZone(100), "Oryzias_latipes.MEDAKA1.53.dna.toplevel.fa");
	}

	@Test
	public void testInitWeight() {
		fail("まだ実装されていません");
	}

	@Test
	public void testErrorRatio() {
		fail("まだ実装されていません");
	}

	@Test
	public void testWeakLearn() {
		fail("まだ実装されていません");
	}

	@Test
	public void testReviseWeight() {
		fail("まだ実装されていません");
	}

	@Test
	public void testDataSet() {
		DataSet exa = new DataSet();
		
	}

}
