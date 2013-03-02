package zone;

import java.util.List;

/**
 * ZoneExtracterに代わり区間抽出を行うクラス 基本的な部分は引き継ぐべき 最短区間長制限問題をつくアルゴリズムを実装しています。 static
 * に用いられる
 * 
 * @author takashi
 * 
 */
public class SegmentSetsExtracter {

	/**
	 * staticに用いられるクラスのためコンストラクタは特に使われない、空です
	 */
	public SegmentSetsExtracter() {
	}

	// FIXME ゆくゆくは型を変更する。DataSetとあわせて変更が必要。配列化して高速化したい。
	// FIXME 最短区間長制限問題をといているが、そもそもの「メチル化率」の定義をしていない
	// 区間に対するメチル化率という言葉の定義が必要。
	/**
	 * 
	 * @param filename
	 * @return
	 */
	public static List<List<int[]>> extract(String filename) {

		return null;

	}

}
