package zone;

/**
 *	one column and one target.
 * 
 * @author tks
 * PATTERN	columnの次元数
 */
public class MyPoint {
	int PATTERN = 1020;// 2-5merのパターン数
	double weight;
	byte[] column;
	
	
	/**
	 * スレッドセーフとの兼ね合いをどう設計すればいいのか、定石がわからない
	 * このコードだと遅い、メモリ必要
	 * @param w
	 */
	public MyPoint setW(double w){
		MyPoint tmp = new MyPoint();
		tmp.weight = w;
		tmp.column = this.column;
		return tmp;
	}
	
	MyPoint(){
		this.column = new byte[PATTERN];
	}
	
	
}