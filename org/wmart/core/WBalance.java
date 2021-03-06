package org.wmart.core;

import java.io.*;

/**
 * 収支情報を扱うクラスです． 収支情報としては，初期所持金，借入金，未実現損益，預託証拠金， 総支払い手数料，総支払い金利，保有現金，実現損益を扱っています．
 * 
 * @author 川部 裕司
 * @author 森下 領平
 * @author 小野 功
 * @author Ikki Fujiwara, NII
 */
public class WBalance {

	/** 初期所持金 */
	private long fInitialCash;

	/** 借入金 */
	private long fLoan;

	/** 未実現損益 */
	private long fUnrealizedProfit;

	/** 預託証拠金 */
	private long fMargin;

	/** 総支払い手数料 */
	private long fSumOfFee;

	/** 総支払い金利 */
	private long fSumOfInterest;

	/** 保有現金 */
	private long fCash;

	/** 実現損益 */
	private long fProfit;

	/**
	 * 収支情報を作成する．
	 */
	public WBalance() {
		fInitialCash = 0;
		fLoan = 0;
		fUnrealizedProfit = 0;
		fMargin = 0;
		fSumOfFee = 0;
		fSumOfInterest = 0;
		fCash = 0;
		fProfit = 0;
	}

	/**
	 * 複製を返す．
	 * 
	 * @return 複製
	 */
	public Object clone() {
		WBalance result = new WBalance();
		result.fInitialCash = fInitialCash;
		result.fLoan = fLoan;
		result.fUnrealizedProfit = fUnrealizedProfit;
		result.fMargin = fMargin;
		result.fSumOfFee = fSumOfFee;
		result.fSumOfInterest = fSumOfInterest;
		result.fCash = fCash;
		result.fProfit = fProfit;
		return result;
	}

	/**
	 * 内部情報を出力する．
	 * 
	 * @param pw
	 *            出力先
	 */
	public void printOn(PrintWriter pw) {
		try {
			pw.println("fInitialCash = " + fInitialCash);
			pw.println("fCash = " + fCash);
			pw.println("fProfit = " + fProfit);
			pw.println("fUnrealizedProfit = " + fUnrealizedProfit);
			pw.println("fMargin = " + fMargin);
			pw.println("fSumOfFee = " + fSumOfFee);
			pw.println("fLoan = " + fLoan);
			pw.println("fSumOfInterest = " + fSumOfInterest);
		} catch (Exception e) {
			System.out.println("Exception: " + e);
		}
	}

	/**
	 * 初期所持金を返す．
	 * 
	 * @return 初期所持金
	 */
	public long getInitialCash() {
		return fInitialCash;
	}

	/**
	 * 借入金を返す．
	 * 
	 * @return 借入金
	 */
	public long getLoan() {
		return fLoan;
	}

	/**
	 * 未実現損益を返す．
	 * 
	 * @return 未実現損益
	 */
	public long getUnrealizedProfit() {
		return fUnrealizedProfit;
	}

	/**
	 * 預託証拠金を返す．
	 * 
	 * @return 預託証拠金
	 */
	public long getMargin() {
		return fMargin;
	}

	/**
	 * 総支払い手数料を返す．
	 * 
	 * @return 総支払い手数料
	 */
	public long getSumOfFee() {
		return fSumOfFee;
	}

	/**
	 * 総支払金利を返す．
	 * 
	 * @return 総支払い金利
	 */
	public long getSumOfInterest() {
		return fSumOfInterest;
	}

	/**
	 * 保有現金を返す．
	 * 
	 * @return 保有現金
	 */
	public long getCash() {
		return fCash;
	}

	/**
	 * 実現損益を返す．
	 * 
	 * @return 実現損益
	 */
	public long getProfit() {
		return fProfit;
	}

	/**
	 * 初期所持金を設定する．
	 * 
	 * @param initialCash
	 *            初期所持金
	 */
	public void setInitialCash(long initialCash) {
		fInitialCash = initialCash;
	}

	/**
	 * 借入金を設定する．
	 * 
	 * @param loan
	 *            借入金
	 */
	public void setLoan(long loan) {
		fLoan = loan;
	}

	/**
	 * 未実現損益を設定する．
	 * 
	 * @param unrealizedProfit
	 *            未実現損益
	 */
	public void setUnrealizedProfit(long unrealizedProfit) {
		fUnrealizedProfit = unrealizedProfit;
	}

	/**
	 * 預託証拠金を設定する．
	 * 
	 * @param margin
	 *            預託証拠金
	 */
	public void setMargin(long margin) {
		fMargin = margin;
	}

	/**
	 * 総支払い手数料を設定する．
	 * 
	 * @param sumOfFee
	 *            総支払い手数料
	 */
	public void setSumOfFee(long sumOfFee) {
		fSumOfFee = sumOfFee;
	}

	/**
	 * 総支払い金利を設定する．
	 * 
	 * @param interest
	 *            総支払い金利
	 */
	public void setSumOfInterest(long interest) {
		fSumOfInterest = interest;
	}

	/**
	 * 実現損益を設定する．
	 * 
	 * @param profit
	 *            実現損益
	 */
	public void setProfit(long profit) {
		fProfit = profit;
	}

	/**
	 * 保有現金を更新する．
	 */
	public void updateCash() {
		fCash = fInitialCash + fUnrealizedProfit + fProfit + fLoan - fMargin - fSumOfFee - fSumOfInterest;
	}

}
