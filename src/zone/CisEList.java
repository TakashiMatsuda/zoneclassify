/**
 * 
 */
package zone;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collection;

/**
 * ciselementを、byte[]の形で保存している。 実装完了
 * 
 * @author takashi
 * 
 * 
 */
public class CisEList extends ArrayList<String> {
	// 本当はCisEクラスを作った方がいい

	/**
	 * 
	 */
	public CisEList() {
		// TODO 自動生成されたコンストラクター・スタブ
	}

	/**
	 * @param initialCapacity
	 */
	public CisEList(int initialCapacity) {
		super(initialCapacity);
		// TODO 自動生成されたコンストラクター・スタブ
	}

	/**
	 * @param c
	 */
	public CisEList(Collection<? extends String> c) {
		super(c);
		// TODO 自動生成されたコンストラクター・スタブ
	}

	/**
	 * 実装終了しているが、char -> byteの対応付けに応じて修正する必要が生じているかもしれません。
	 * 
	 * @param record
	 * @return
	 */
	public static String trans_byte_str(byte[] record) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < record.length; i++) {

			switch (record[i]) {
			case 0:
				sb.append('A');
				break;
			case 1:
				sb.append('T');
				break;
			case 2:
				sb.append('C');
				break;
			case 3:
				sb.append('G');
				break;
			default:
				break;
			}
		}

		return sb.toString();
	}

	/**
	 * 実装終了
	 */
	public void write() {
		try {
			// for (int i = 0; i < CLUSTERNUM; i++) {
			/*
			 * ファイルの名前を作成
			 */
			Writer out = null;
			File output = new File("cis_element_list.txt");

			// File output = new File(CLUSTERNUM + "K_" + JIGEN + "D_"
			// + DATASIZE + "NLloyd_result" + i + ".tsv");
			out = new BufferedWriter(new FileWriter(output));

			for (int i = 0; i < size(); i++) {
				out.write(get(i) + "\n");
			}
			out.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
