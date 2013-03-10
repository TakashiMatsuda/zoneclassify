package zone;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;

public class DataSetTest {
	static boolean DEBUG = false;

	// 小さいデータを作成する必要がある!
	// 一つの遺伝子断片だけでやりますか
	@Test
	public void testLoad() {
		// そんなに区間数が長いのはおかしい！
		// 4000000の桁の区間数とかおかしいのでは
		// 遺伝子断片の長さを確認してください
		// ZoneExtracter farmer = new ZoneExtracter();
		DataSet exa = new DataSet();
		List<List<int[]>> a = new LinkedList<List<int[]>>();
		exa.load(SegmentSetsExtracter.extract(1, 4000,
				"chromosome_blastula_CpGmethylationLevel.wig",
				"chromosome_CpGsitePosition.wig"), "chromosomeMEDAKA.fa");
	}

	@Ignore
	public void testInitWeight() {
		ZoneExtracter farmer = new ZoneExtracter();
		DataSet exa = new DataSet();
//		exa.load(farmer.subZone(10000),
//				"Oryzias_latipes.MEDAKA1.53.dna.toplevel.fa");// もっと小さいデータでよい
//		exa.initWeight();
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
