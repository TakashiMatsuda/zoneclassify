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
	
	public void setWeight(double w){
		this.weight = w;
	}
	
	public byte[] getColumn(){
		return this.column;
	}
	
	public double getWeight(){
		return this.weight;
	}
	
	MyPoint(byte[] core){
		if (core.length == PATTERN)
			this.column = core;
		else
			System.out.println("PARAMETER LENGTH ERROR     -- MyPoint() --");
	}	
}