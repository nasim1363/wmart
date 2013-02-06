/**
 *
 */
package org.wmart.core;

import java.util.*;
import java.util.Map.*;

/**
 * 約定明細を扱うクラス。商品名×スロット番号で指定される表形式の数値データを持つ
 * 
 * @author Ikki Fujiwara, NII
 * 
 */
public class WOutcomeTable1 {

	/** 値がないときのデフォルト値 */
	private double fDefaultValue;
	/** スロット数 */
	private int fSlots;
	/** 商品ごと・スロットごとの表 */
	private TreeMap<String, double[]> fTable = null;

	/**
	 * コンストラクタ
	 * 
	 * @param slots
	 *            スロット数
	 * @param defaultValue
	 *            デフォルト値
	 */
	public WOutcomeTable1(int slots, double defaultValue) {
		fDefaultValue = defaultValue;
		fSlots = slots;
		fTable = new TreeMap<String, double[]>();
	}

	/**
	 * 全データを文字列として取得
	 * 
	 * @return 数値表 "goodA:10:11:12...;goodB:21:22:23...;"
	 */
	public String encode() {
		StringBuilder result = new StringBuilder();
		for (Iterator<Entry<String, double[]>> itr = fTable.entrySet().iterator(); itr.hasNext();) {
			Entry<String, double[]> entry = itr.next();
			String key = entry.getKey();
			double[] a = entry.getValue();
			result.append(key);
			for (int i = 0; i < a.length; i++) {
				result.append(":").append(a[i]);
			}
			result.append(";");
		}
		return result.toString();
	}

	/**
	 * 指定商品の全スロットのデータを CSV で取得
	 * 
	 * @param good
	 * @return
	 */
	public String csv(String good) {
		StringBuilder result = new StringBuilder();
		double[] a = fTable.get(good);
		for (int i = 0; i < a.length; i++) {
			result.append(a[i]).append(",");
		}
		return result.toString();
	}

	/**
	 * 指定商品の全スロットの合計値を取得
	 */
	public double sum(String good) {
		double sum = 0.0;
		double[] a = fTable.get(good);
		for (int i = 0; i < a.length; i++) {
			sum += a[i];
		}
		return sum;
	}

	/**
	 * 全商品・全スロットの合計値を取得
	 */
	public double sum() {
		double sum = 0.0;
		for (Iterator<double[]> itr = fTable.values().iterator(); itr.hasNext();) {
			double[] a = itr.next();
			for (int i = 0; i < a.length; i++) {
				sum += a[i];
			}
		}
		return sum;
	}

	/**
	 * セルに値を加算
	 * 
	 * @param good
	 * @param slot
	 * @param value
	 */
	public void add(String good, int slot, double value) {
		assert fDefaultValue == 0.0 : "The default value must be 0.0 to add another value.";
		if (!fTable.containsKey(good)) {
			double[] a = new double[fSlots];
			for (int i = 0; i < a.length; i++) {
				a[i] = fDefaultValue;
			}
			fTable.put(good, a);
		}
		fTable.get(good)[slot] += value;
	}

	/**
	 * セルに値を設定
	 * 
	 * @param good
	 * @param slot
	 * @param value
	 */
	public void put(String good, int slot, double value) {
		if (!fTable.containsKey(good)) {
			double[] a = new double[fSlots];
			for (int i = 0; i < a.length; i++) {
				a[i] = fDefaultValue;
			}
			fTable.put(good, a);
		}
		fTable.get(good)[slot] = value;
	}

	/**
	 * セルの値を取得
	 * 
	 * @param good
	 * @param slot
	 * @return
	 */
	public double get(String good, int slot) {
		if (!fTable.containsKey(good)) {
			return fDefaultValue;
		}
		return fTable.get(good)[slot];
	}

	/**
	 * セルの値を取得
	 * 
	 * @param good
	 * @param slot
	 * @param defaultValue
	 * @return
	 */
	public double get(String good, int slot, double defaultValue) {
		if (!fTable.containsKey(good)) {
			return defaultValue;
		}
		return fTable.get(good)[slot];
	}

	/**
	 * 商品名のイテレータを取得
	 */
	public Iterator<String> goodsIterator() {
		return fTable.keySet().iterator();
	}

}
