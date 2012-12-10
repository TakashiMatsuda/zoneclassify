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
		
		
		ArrayList<ArrayList<Double>> exa = InputWig.getWIG("blastula_CpGMethylationLevel.wig");
		// exaに何も入ってない
		// for(int i = 0; i < 10; i++){
			//System.out.println(exa.get(0).get(i));
		//}
	}
}