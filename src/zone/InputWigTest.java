package zone;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.junit.Test;

/**
 * JUnitも同期しなければいけないようです。
 * 応急的にJUnit 4のビルドパスを通しています。
 * @author takashi
 *
 */
public class InputWigTest {

	@SuppressWarnings("unused")
	@Test
	public void testgetWIG() {
		final boolean DEBUG = false;
		
		ArrayList<double[]> exa = InputWig.getWIG("blastula_CpGMethylationLevel.wig");
		for(int i = 0; i < 1000; i++){
			System.out.println(exa.get(0)[i]);
		}
	}
}