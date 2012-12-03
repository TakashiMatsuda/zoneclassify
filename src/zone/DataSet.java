package zone;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * 
 * @author tks
 *
 */
public class DataSet {
	// とりあえずかたちは整いました、
	// あとはIOを整備して
	// 細かい調整、これはIOが整ったら上流から順番に行います
	// そしてデバッグ
	// テスト
	// 完成
	// という手順を通りましょう。
	
	// 先生のクラス構成微妙なんじゃないかな...
	
	/*
	 * 判定器リスト
	 */
	private ArrayList<Classifier> boxes;
	private ArrayList<MyPoint> teachers;
	private ArrayList<String> records;
	private ArrayList<String> mers;
	
	/*
	 * MyPointの数 <- 意味がわからなくなってしまいました。
	 */
	private int M;
	
	private int PATTERN = (4 ^ 2) * (5 ^ 3);
	/*
	 * 教師データ集合
	 */
	private byte[][] knowledges;
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
	public boolean load(List<List<int[]>> zones, String filename){// booleanはわかりにくい, 実際はknowledgesまで?
		// 下流につなげることを考えよう
		// this.recordsに記録するところまで。<- knowledgesまでじゃないか？
		try{
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
			while((line = br.readLine()) != null){
				/*
				 * nametagごとに対象区間族の切り出しを行います
				 * tagcountで管理します
				 */
				if (nametag.matcher(line).find() != true){// 綺麗な否定の方法を勉強したい
					onePlace.append(line);
				}
				else{
					List<int[]> tmpZones = zones.get(tagCount);// これって速度とスコープ的にどうなんだろうか、
					// 今まで避けてきた書き方ですけど・・・
					// 毎回初期化される!
					
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
			 * recordsできあがり
			 */
			
			/*
			 * 一つの低メチル化領域について全2-5merについてのカラムを作成します。
			 * 全部使いつくすまでjudgeEXPを実行します
			 */
			int recordsize = records.size();
			for(int i = 0; i < recordsize; i++){
				String lowMethylZone = records.get(i);
				/*
				 * 各2-5merについてjudgeExpを実行
				 */
				MyPoint newColumn = new MyPoint();// MyPointであってたか？
				// 2-5merのパターンを収めたリストがほしい<- つくりましょう！あとで遅くなったらチューニングするということで。
				// コンストラクタの中に内蔵します。
				
				
				
			}
			
			
			
			return true;
		} catch(Exception e){
			e.printStackTrace();
			return false;
		}
		
		// これではまだknowledgesに格納できていない
		// recordsからknowledgesへ格納する作業を書き込む必要がある
		// 低メチル化領域しかピックあぷしていないから、学習データの目標属性は全部1です。。後で修復。ZoneExtracterのほう。
		
		
		
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
	private int judgeEXP(String factor, String sequence){
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
		int l = knowledges.length;
		for (int i = 0; i < l; i++){
			knowledges.set(i, knowledges.get(i).setW(1.0 / (double)l));// ここあとで
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
		int p = x.prediction();
		for (int i = 0; i < M; i++){
			if (knowledges[i][q] == 1){
				sum++;
				if (knowledges[i][M] == p)
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
		Classifier tmp = new Classifier(LS, (byte) 0);
		while(LS < PATTERN){
			if (errorRatio(tmp) < (1.0 / 2.0))
				break;
			else{
				// judgeEXPを使える
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
			knowledges[i][PATTERN + 1] = (byte) (knowledges[i][PATTERN + 1] * Math.pow(beta, (1 - Math.abs(h.prediction() - knowledges[i][PATTERN]))));
		}
	}
	
	/**
	 * Create and store all 2-5mer Patterns
	 * @return
	 */
	private List<String> createMers(){
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
		for(int i = 0; i < twodigits; i++){
			/*
			 * 10進数iから4進数への変換
			 */
			
			char[] twomer = {nucleotide[i / 4], nucleotide[i % 4]};// 頭の中で暗算して書いた行です
			fruit.add(String.valueOf(twomer));
		}
		int d3 = twodigits + threedigits;// 脳内暗算記述行
		for(int i = twodigits; i < d3; i++){
			
		}
		int d4 = d3 + fourdigits;
		for(int i = d3; i < d4; i++){
			
		}
		int d5 = d4 + fivedigits;
		for(int i = d4; i < d5; i++){
			
		}
		
		return fruit;
	}
	
	/**
	 * Constructer
	 * open to change
	 */
	DataSet(){// 訓練データの入力我必要
		// その形式はBoostくらすが決めること、ここで考える必要はない<- その方針は変更したほうがよいのでは？
		
		/*
		 * 各2-5merの作成
		 */
		this.mers = new ArrayList<String>();
		// AA ==> GGGGGまでのながれを考えよう
		// 桁数ずつに順番に愚直にやりましょう
		// 場合によってはprivateメソッドを作成したほうがいいかもしれませんね
		
		this.mers = createMers();
		LS = 0;
	}
	
}
