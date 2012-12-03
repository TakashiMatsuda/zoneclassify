package zone;

import static org.junit.Assert.*;

import org.junit.Test;

public class DataSetTest {

	@Test
	public void testLoad() {

		ZoneExtracter farmer = new ZoneExtracter();
		DataSet exa = new DataSet();
		exa.load(farmer.subZone(100), "coverage.wig");// そんな名前のファイルはないので変更しなければ
		
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
