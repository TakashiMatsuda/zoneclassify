package zone;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

public class SegmentSetsExtracterTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Ignore
	public void testSegmentSetsExtracter() {
	}

	@Test
	public void testExtract() {
		List<ZoneList> exa = SegmentSetsExtracter.extract(1, 2000,
				"chromosome_blastula_CpGmethylationLevel.wig", "chromosome_CpGsitePosition.wig");
		System.out.println("テスト終了");
	}
	
//	@Test
//	public void testSubZone() {
//		ZoneExtracter exa = new ZoneExtracter();
//		List<List<int[]>> t = exa.subZone(10);
//		System.out.print(t.size());
//	}
//
//	@Test
//	public void testZoneExtracter() {
//		@SuppressWarnings("unused")
//		ZoneExtracter exa = new ZoneExtracter();
//	}

}
