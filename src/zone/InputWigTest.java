package zone;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class InputWigTest {

	@Test
	public void testgetWIG() {
		ArrayList<Double[]> exa = InputWig.getWIG("blastula_coverage.wig");
		
		assertEquals(0.0, exa.get(0)[3], 0.01);
	}

}
