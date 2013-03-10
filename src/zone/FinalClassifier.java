/**
 * 
 */
package zone;

/**
 * 最終的に獲得する分類器のクラス DataSetと一対一に対応 完成しています。
 * 
 * @author takashi
 */
public class FinalClassifier {
	private DataSet dataset;
	private int T;
	private ClassifierRanking classifierranking;
	private boolean sign_ranked;

	/**
	 * DataSetと一対一の関係をもつクラス
	 * 
	 * @param ls
	 * @param t
	 */
	FinalClassifier(DataSet dataset, int t) {
		this.T = t;
		this.dataset = dataset;
		classifierranking = null;
		sign_ranked = false;
	}

	/**
	 * 内装はmajorityRuleによる。 重み付き多数決による結果を返す。 完成しました。 byte[]
	 * 
	 * @param a
	 * @return
	 */
	public byte lastprediction(byte[] sample) {
		// 重みつき多数決をするメソッド
		// 名前微妙。かぶっている。わざとでないなら変えるべき。
		if (majorityRule(sample)) {
			return 1;
		} else
			return 0;
	}

	/**
	 * この結果を再計算するのはもったいないので、このクラスの中にフィールドを作って格納したい。 完成しています。
	 * 
	 * @param greatTeachers
	 * @return
	 */
	public boolean majorityRule(byte[] sample) {
		double prob = 0;
		double aver = 0;
		double beta = 0;

		classifierranking = new ClassifierRanking();
		for (int t = 0; t < T; t++) {
			// 左辺の計算
//			ちゃんとカプセル化したほうがよい
			beta = dataset.classifierlist.get(t).omomikeisu();

			// 重みに重複があった場合、不適切な結果が帰ります。
			if (classifierranking.containsKey(beta)) {
				System.err.println("重み係数が重複しました。結果は不適切です。");
			}
			classifierranking.put(beta, t);

			prob += (-Math.log(beta))
					* (dataset.classifierlist.get(t).prediction(sample));

			// 右辺の計算
			aver += (-Math.log(beta)) / 2.0;
		}
		sign_ranked = true;

		if (prob >= aver) {
			return true;
		} else
			return false;

	}

	/**
	 * cis_elementのリストを返します。
	 * 
	 * @param num
	 * @return
	 */
	public CisEList suggest_cis(int num) {
		// ある特定塩基配列パターンに対してそれがcis_elementであるというアノテーションをつけるというモデルが
		// 科学的正当性を持たない。このことは頭にのこしておくこと。

		// 正解率に大きく影響するレコードは、cis elementとする。
		// 今回の単位分類器の作り方は、各elementごとの判断であるから、
		// 単位分類器のうち、その重みの大きいものを上からnum個順番に出力すればよい。
		// 上の乗法はどこに求めるべきか。 -> dataset.classifierlistの中にその演算を設ければよい。
		// ArrayList<String> dataset.classifierlist.importantlist();
		if (sign_ranked) {
			return dataset.get_intense_classifier(num, classifierranking);
		} else {
			System.err.println("not yet ranked(@suggest_cis)");
			return null;
		}

	}

}