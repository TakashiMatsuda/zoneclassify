package zone;

/**
 *	one column and one target.
 * @author tks
 */
public class MyPoint {
	/*
	 * PATTERN 2-5merのパターン数
	 * すなわちcolumnの数
	 * weight: 重み
	 * column: 各2-5merの出現頻度に対応する配列
	 */
	static int PATTERN = (int)Math.pow(4, 2) + (int)Math.pow(4, 3) + (int)Math.pow(4, 4) + (int)Math.pow(4, 5);
	public double weight;
	private byte[] column;
	// Listで挿入した順番が保持されるなら、columnをprivateとしても問題ない
//	コメントはもっとわかりやすくなるように書こう
	
	/**
	 * これで本当に内容が変更されるのか不安
	 * あとで試しコード書いて調べてみよう
	 * 
	 * Reset weight
	 * @param w
	 */
	public MyPoint changeWeight(double w){
		MyPoint tmp = new MyPoint(this.column);
		tmp.weight = w;
		return tmp;
	}
	
	
	/**
	 * 
	 * @param w
	 */
	public void setWeight(double w){
		this.weight = w;
	}
	
	
	/**
	 * 
	 * @return
	 */
	public byte[] getColumn(){
		return this.column;
	}
	
	
	/**
	 * 
	 * @return
	 */
	public double getWeight(){
		return this.weight;
	}
	
	
	/**
	 * targetの属性を返します。(1 or 0)
	 * column[column.length - 2]を返しています。
	 * columnの実装記録が失われているため、
	 * 自信はありません。
	 * 
	 * @return このmypointの目標属性
	 */
	public byte getTarget(){
		return this.column[column.length - 2];
	}
	
	/**
	 * 
	 * @param core
	 */
	MyPoint(byte[] core){
		if (core.length == PATTERN)
			this.column = core;
		else
			System.out.println("PARAMETER LENGTH ERROR     -- MyPoint() --");
	}	
}