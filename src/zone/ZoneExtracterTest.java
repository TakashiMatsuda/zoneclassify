package zone;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

public class ZoneExtracterTest {

	@Test
	public void testSubZone() {
		// デバッグ成功、テスト失敗
		ZoneExtracter exa = new ZoneExtracter();
		List<List<int[]>> t = exa.subZone(100);
		System.out.print(t.size());
	}

	@Test
	public void testZoneExtracter() {
		ZoneExtracter exa = new ZoneExtracter();
	}

}
