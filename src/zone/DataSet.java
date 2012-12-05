package zone;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * COdeing complete.
 * @author tks
 *
 */
public class DataSet {
	/*
	 * 判定器リスト -> Boost.classへ移転
	 */
	private ArrayList<Classifier> boxes;
	/*
	 * 教師データ集合
	 */
	private LinkedList<MyPoint> teachers;// LinkedかArrayか
	private ArrayList<String> records;
	private ArrayList<String> mers;
	
	/*
	 * MyPointの数 <- 意味がわからなくなってしまいました。
	 */
	private int M;
	
	private static int PATTERN = (int) Math.pow(4, 2) + (int) Math.pow(4, 3) + (int) Math.pow(4, 4) + (int) Math.pow(4, 5);
	/*
	 * 教師データ集合
	 */
	private int LS;
	
	/**
	 * 入力ファイルからrecordをloadしてknowledgesに格納
	 * filename: fastaファイルの名前
	 * fastaファイルから該当区間のList<String>と各Stringが低メチル化領域か高メチルか領域かをはきだします。
	 * 
	 * 仕様変更があります、型を配列に変えたので変更をうけます　<- なんのことだかわかっていない(12/2)
	 * @param zones, filename
	 * @return
	 * recordsに格納する作業です。
	 * coding finished.
	 */
	public boolean load(List<List<int[]>> zones, String filename){
		try{
			System.out.println("LOADING GENOME FASTA DATA.....");
			String line;
			BufferedReader br = new BufferedReader(new FileReader(filename));
			// wigとfastaでタグが一致しているかどうか。同じ遺伝子部位についてやらないと意味なし
			// 一致確認。なので各int[]について
			// 0, Nの関係でちゃんと一致しているのか自信がもてません
			// 0, Nをどうするのかの方針を固めておこう
			StringBuilder onePlace = new StringBuilder();
			Pattern nametag = Pattern.compile("^>");
			List<String> tmpRecords = new ArrayList<String>();
			/*
			 * Count the tag on the fasta file.
			 */
			int tagCount = 0;
			List<int[]> tmpZones;
			while((line = br.readLine()) != null){
				/*
				 * nametagごとに対象区間族の切り出しを行います
				 * tagcountで管理します
				 */
				if (nametag.matcher(line).find() != true){// 綺麗な否定の方法を勉強したい
					onePlace.append(line);
				}
				else{
					tmpZones = zones.get(tagCount);
					// 今まで避けてきた書き方ですけど・・・
					// 毎回初期化される!<- やっぱり良くなかった
					
					/*
					 * tmpZonesの各要素に対応するものを全部切り出す
					 */
					while(tmpZones.size() == 0){// ここはどう書くのがイディオム的に正しいのかわからない
						// for文を使っても綺麗にかける、メモリ使用量がどっちが多いのかで決めよう
						// コンパイル後は一緒かな？
						int[] mold = tmpZones.get(tmpZones.size());//末端から消去。ArrayListなのでこれが速いはず。 
						String cast = onePlace.substring(mold[0], mold[1]);// 植木算があってる確証をとっていません
						this.records.add(cast);
						tmpZones.remove(tmpZones.size());
					}
					onePlace.delete(0, onePlace.length());// クリアの方法、早い方法が何かわからなかったので自分なりに工夫した部分
					tagCount++;
				}
			}
			br.close();
			/*
			 * records created
			 */
			
			/*
			 * 一つの低メチル化領域について全2-5merについてのカラムを作成します。
			 * 全部使いつくすまでjudgeEXPを実行します
			 */
			System.out.println("CREATING SUPERVISING DATA.....");
			int recordsize = records.size();
			for(int i = 0; i < recordsize; i++){
				String lowMethylZone = records.get(i);
				/*
				 * 各2-5merについてjudgeExpを実行し、teachersに格納する
				 */
				byte[] preColumn = new byte[PATTERN];
				for(int j = 0; j < PATTERN; j++){
					preColumn[j] = judgeEXP(lowMethylZone, mers.get(j));
				}
				MyPoint tmpMP = new MyPoint(preColumn);
				teachers.add(tmpMP);
			}
			return true;
		} catch(Exception e){
			e.printStackTrace();
			return false;
		}		
		// 低メチル化領域しかピックアップしていないから、学習データの目標属性は全部1です。。後で修復。ZoneExtracterのほう。
	}
	
	
	/**
	 * targetにsearchwordが現れる関数を返します。ただし重複し数えない場合の度数です。
	 * @param target
	 * @param searchWord
	 * @return
	 */
	private int countStringInString(String target, String searchWord){
		return (target.length() - target.replaceAll(searchWord, "").length()) / searchWord.length();
	}
	
	
	/**
	 * あるn-merがsequenceに含まれる回数が、期待値よりも大きいかを判定します。
	 * @param factor
	 * @param sequence
	 * @return
	 */
	private byte judgeEXP(String factor, String sequence){
		int l = factor.length();
		double ne = ((sequence.length() - l) * (1.0 / Math.pow(4.0, (double)l))) * sequence.length();
		/*
		 * factorがsequenceに含まれる回数
		 */
		int dosu = countStringInString(sequence, factor);
		if (dosu >= ne)
			return 1;
		else
			return 0;
	}
	
	
	/**
	 * Initialize weight
	 */
	public void initWeight(){
		for (int i = 0; i < PATTERN; i++){
			// そうか、ここの関係でsetWはvoidじゃなくて何か返さなきゃいけなかったんだ
			teachers.set(i, teachers.get(i).changeWeight(1));// elementのところにMyPointで提供される関数、wを更新したMyPointを入れる
		}
	}
	
	/**
	 * classifierによるpredictionのエラー率を計算
	 * 完成しています
	 * @return
	 */
	public double errorRatio(Classifier x){
		/*
		 * xによる予測属性に対して訓練データ中の予測属性の現れる数
		 */
		int right = 0;
		int sum = 0;
		int q = x.start;
		byte p = x.prediction();
		for (int i = 0; i < M; i++){
			if (teachers.get(i).getColumn()[q] == 1){
				sum++;
				if (teachers.get(i).getTarget() == p)
					right++;
			}
		}
		return (double)right / (double)sum;
	}
	
	
	/**
	 * classifierを作成
	 * 一から順番にラベルごとに分類器をつくり、エラー率が0.5より小さければその分類器を返します。
	 * 完成しています<- 本当？
	 * @return
	 */
	public Classifier weakLearn(){
		// 作り方、歪んでいます。分類器が、ある列が1のときにどうなるか、というものしかない
		// それで結果十分なのかな？
		Classifier tmp = new Classifier(LS, (byte) 0);
		while(LS < PATTERN){
			if (errorRatio(tmp) < (1.0 / 2.0))
				break;
			else{
				tmp = new Classifier(LS, (byte) 1);
				if (errorRatio(tmp) < (1.0 / 2.0))
					break;
			}
			LS++;
		}
		return tmp;
	}
	
	
	/**
	 * AdaBoostのアルゴリズムにしたがってweightを更新
	 * 完成しています
	 */
	public void reviseWeight(Classifier h){
		double beta;
		double e = errorRatio(h);
		beta = e /(1 - e);
		for(int i = 0; i < M; i++){
			// teachers.get(i).getWeight()// これではweightに差S割れない
			// スレッドセーフ、カプセル化を破ります
			teachers.set(i, teachers.get(i).changeWeight(teachers.get(i).getWeight() * Math.pow(beta, (1 - Math.abs(h.prediction() - teachers.get(i).getTarget())))));
			// knowledges[i][PATTERN + 1] = (byte) (knowledges[i][PATTERN + 1] * Math.pow(beta, (1 - Math.abs(h.prediction() - knowledges[i][PATTERN]))));
		}
	}
	
	
	/**
	 * Create and store all 2-5mer Patterns
	 * @return
	 * Coding completed.
	 * The calculation speed is fast.
	 */
	private ArrayList<String> createMers(){
		/*
		 * nucleotide : ヌクレオチド4文字のインデックス
		 * two, ...five digits: 各桁数のmersのパターン数、for文を回すために用意
		 * 各パターンをfruitに格納して返します
		 */
		char[] nucleotide = {'A', 'T', 'C', 'G'};
		int twodigits = (int) Math.pow(4, 2);
		int threedigits = (int) Math.pow(4, 3);// 4であってる？
		int fourdigits = (int) Math.pow(4, 4);
		int fivedigits = (int) Math.pow(4, 5);
		ArrayList<String> fruit = new ArrayList<String>();
		/*
		 * 2merの処理
		 */
		// 以下ぴったりの設計です。植木算法を間違えているとArrayOutOfExceptionです。
		for(int i = 0; i < twodigits; i++){
			/*
			 * 10進数iから4進数への変換
			 */
			char[] twomer = {nucleotide[i / 4], nucleotide[i % 4]};// 頭の中で暗算して書いた行です
			fruit.add(String.valueOf(twomer));
		}
		/*
		 * 同様に3-5merも処理
		 */
		int d3 = twodigits + threedigits;// 脳内暗算記述行
		for(int i = twodigits; i < d3; i++){
			int tmpi = i - twodigits;
			char[] threemer = {nucleotide[tmpi / (4 * 4)], nucleotide[(tmpi % (4 * 4) / 4)], nucleotide[(tmpi % 4)]};
			fruit.add(String.valueOf(threemer));
		}
		int d4 = d3 + fourdigits;
		for(int i = d3; i < d4; i++){
			int tmpi = i - d3;
			char[] fourmer = {nucleotide[tmpi / (4 * 4 * 4)], nucleotide[(tmpi % (4 * 4 * 4)) / (4 * 4)], 
					nucleotide[(tmpi % (4 * 4)) / 4], nucleotide[tmpi % 4]};
			fruit.add(String.valueOf(fourmer));
		}
		int d5 = d4 + fivedigits;
		for(int i = d4; i < d5; i++){
			int tmpi = i - d4;
			// 記述が長くなってしまったのでミス確認
			char[] fivemer = {nucleotide[tmpi / (4 * 4 * 4 * 4)], nucleotide[tmpi % (4 * 4 * 4 * 4) / (4 * 4 * 4)],
			                             nucleotide[(tmpi % (4 * 4 * 4) / (4 * 4))], nucleotide[(tmpi % (4 * 4) / 4)], nucleotide[tmpi % 4]};
			fruit.add(String.valueOf(fivemer));
		}
		return fruit;
	}
	
	/**
	 * Constructer
	 * open to change
	 */
	DataSet(){
		/*
		 * 各2-5merの作成
		 */
		this.mers = createMers();
		LS = 0;
	}
	
}
