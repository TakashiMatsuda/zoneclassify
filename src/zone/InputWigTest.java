package zone;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.junit.Test;

/**
 * 
 * 
 * @author takashi
 * 
 */
public class InputWigTest {

	@SuppressWarnings("unused")
	@Test
	public void testgetWIG() {
		final boolean DEBUG = false;

		// ArrayList<ArrayList<Double>> exa = InputWig
		// .getWIG("blastula_CpGMethylationLevel_chrome=1.wig");
		//
		// for (int i = 0; i < 1000; i++) {
		// System.out.println(exa.get(0).get(1));
		// }
		ArrayList<int[]> pre_exa = InputWig.load_CpGPosition("CpGsitePosition_chrom=1.wig", 39973034);
		ArrayList<double[]> exa = InputWig.getWIG("blastula_CpGMethylationLevel_chrome=1.wig", pre_exa);
		for (int i = 0; i < 10; i++) {
			 System.out.println(exa.get(0)[i] + " " + pre_exa.get(0)[i]);
		}
	}

	@Test
	public void testload_CpGPosition() {
		ArrayList<int[]> exa = InputWig.load_CpGPosition(
				"CpGsitePosition_chrom=1.wig", 11222712);
		for (int i = 0; i < 10; i++) {
			System.out.println(exa.get(0)[i]);
		}

	}
}