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
	
	/*
	 * MyPointの数
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
	 * 仕様変更があります、型を配列に変えたので変更をうけます　
	 * @param zones, filename
	 * @return
	 * recordsに格納する作業です。
	 */
	public boolean load(List<List<int[]>> zones, String filename){
		// 下流につなげることを考えよう
		// this.recordsに記録するところまで。
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
						
						
						
					}
					
					// ここ変更かな。
					// この場でもう対象区間の切り出しをおこなってしまう設計にしましょう
					this.records.add(String.valueOf(onePlace));
					onePlace.delete(0, onePlace.length());// チューニングの成果？
					tagCount++;
				}
			}
				// 指定位置を読むにはどうするか
				//まずはじめに断片ごとに読んで記録
			br.close();
			return true;
		} catch(Exception e){
			e.printStackTrace();
			return false;
		}
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
	 * 一から順番にラベルごとに分類器をつくり、エラー率が0.5より大きければその分類器を返します。
	 * 完成しています
	 * @return
	 */
	public Classifier weakLearn(){
		Classifier tmp = new Classifier(LS, (byte) 0);
		while(LS < PATTERN){
			if (errorRatio(tmp) < (1.0 / 2.0))
				break;
			else{	/**
				 * あるn-merがsequenceに含まれる回数が、期待値よりも大きいかを判定します。
				 * @param factor
				 * @param sequence
				 * @return
				 */
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
	 * Constructer
	 * open to change
	 */
	DataSet(){// 訓練データの入力我必要
		// その形式はBoostくらすが決めること、ここで考える必要はない
		
		LS = 0;
	}
	
}
