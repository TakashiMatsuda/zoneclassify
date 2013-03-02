package zone;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

public class ZoneExtracterTest {
	// とりあえずテストしてそれから考えよう
	@Test
	public void testSubZone() {
		ZoneExtracter exa = new ZoneExtracter();
		List<List<int[]>> t = exa.subZone(10);
		System.out.print(t.size());
	}

	@Test
	public void testZoneExtracter() {
		ZoneExtracter exa = new ZoneExtracter();
	}

}