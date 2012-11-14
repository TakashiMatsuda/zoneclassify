package zone;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class InputWigTest {

	@Test
	public void testgetWIG() {
		ArrayList<List<Double>> exa = InputWig.getWIG("blastula_coverage.wig");
		
		assertEquals(0.0, exa.get(1).get(1), 0.01);
	}

}
