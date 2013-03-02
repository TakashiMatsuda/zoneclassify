/**
 * 
 */
package zone;

/**
 * 最終的に獲得する分類器のクラス DataSetと一対一に対応
 * 完成しています。
 * @author takashi 
 */
public class FinalClassifier {
	DataSet dataset;
	int T;

	/**
	 * DataSetと一対一の関係をもつクラス
	 * 
	 * @param ls
	 * @param t
	 */
	FinalClassifier(DataSet dataset, int t) {
		this.T = t;
		this.dataset = dataset;
	}

	/**
	 * 内装はmajorityRuleによる。 重み付き多数決による結果を返す。 完成しました。
	 * byte[]
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
		// NOW
		for (int t = 0; t < T; t++) {
			// 左辺の計算
			beta = dataset.classifierlist.get(t).omomikeisu();
			prob += (-Math.log(beta))
					* (dataset.classifierlist.get(t).prediction(sample));

			// 右辺の計算
			aver += (-Math.log(beta)) / 2.0;
		}

		if (prob >= aver) {
			return true;
		} else
			return false;
	}
	
	
	/**
	 * cis_elementのリストを返します。
	 * @param num
	 * @return
	 */
	public CisEList suggest_cis(int num){
//		ある特定塩基配列パターンに対してそれがcis_elementであるというアノテーションをつけるというモデルが
//		科学的正当性を持たない。このことは頭にのこしておくこと。
		
//		正解率に大きく影響するレコードは、cis elementとする。
//		今回の単位分類器の作り方は、各elementごとの判断であるから、
//		単位分類器のうち、その重みの大きいものを上からnum個順番に出力すればよい。
//		上の乗法はどこに求めるべきか。 -> dataset.classifierlistの中にその演算を設ければよい。
//		ArrayList<String> dataset.classifierlist.importantlist();
		
		return dataset.get_intense_classifier(num);

	}
	
}