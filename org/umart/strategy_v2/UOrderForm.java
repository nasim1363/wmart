package org.umart.strategy_v2;

/**
 * �����[�N���X
 * @author isao
 *
 */
public class UOrderForm {
	
	/** �����������Ȃ����Ƃ�\���萔 */
	public static final int NONE = 0;
	
	/** ��������\���萔 */
	public static final int SELL = 1;
	
	/** ��������\���萔 */
	public static final int BUY = 2;
	
	/** �����敪(�����������Ȃ�:UOrderForm.NONE(=0), ������:UOrderForm.SELL(=1), ������:UOrderForm.BUY(=2)) */
	private int fBuySell;
	
	/** �������i������������������Ă��Ȃ����Ƃ�\���萔 */
	public static final int INVALID_PRICE = -1;
	
	/** �������i */
	private int fPrice;
	
	/** �������ʂ�����������������Ă��Ȃ����Ƃ�\���萔 */
	public static final int INVALID_QUANTITY = -1;

	/** �������� */
	private int fQuantity;
	
	/**
	 * �R���X�g���N�^
	 *
	 */
	public UOrderForm() {
		fBuySell = UOrderForm.NONE;
		fPrice = UOrderForm.INVALID_PRICE;
		fQuantity = UOrderForm.INVALID_QUANTITY;
	}
	
	/**
	 * �R�s�[�R���X�g���N�^
	 * @param src �R�s�[��
	 */
	public UOrderForm(UOrderForm src) {
		fBuySell = src.fBuySell;
		fPrice = src.fPrice;
		fQuantity = src.fQuantity;
	}
	
	/**
	 * �R�s�[����D
	 * @param src �R�s�[��
	 * @return �R�s�[��̎������g
	 */
	public UOrderForm copyFrom(UOrderForm src) {
		fBuySell = src.fBuySell;
		fPrice = src.fPrice;
		fQuantity = src.fQuantity;
		return this;
	}

	/**
	 * �����敪��Ԃ��D
	 * @return buySell �������Ȃ��ꍇ�F(UOrderForm.NONE=0)�C����̏ꍇ�F(UOrderForm.SELL=1)�C�����̏ꍇ�F(UOrderForm.BUY=2)
	 */
	public int getBuySell() {
		return fBuySell;
	}
	
	/**
	 * �����敪��ݒ肷��D
	 * @param buySell �����敪(�����������Ȃ�:UOrderForm.NONE(=0), ������:UOrderForm.SELL(=1), ������:UOrderForm.BUY(=2))
	 */
	public void setBuySell(int buySell) {
		fBuySell = buySell;
	}

	/**
	 * �������i��Ԃ��D
	 * @return price �������i
	 */
	public int getPrice() {
		return fPrice;
	}

	/**
	 * �������i��ݒ肷��D
	 * @param price �������i
	 */
	public void setPrice(int price) {
		fPrice = price;
	}

	/**
	 * �������ʂ�Ԃ��D
	 * @return quantity ��������
	 */
	public int getQuantity() {
		return fQuantity;
	}

	/**
	 * �������ʂ�ݒ肷��D
	 * @param quantity ��������
	 */
	public void setQuantity(int quantity) {
		fQuantity = quantity;
	}
	
	/**
	 * �����敪�𕶎���ŕԂ��D
	 * @return �����敪��\��������
	 */
	public String getBuySellByString() {
		if (fBuySell == UOrderForm.BUY) {
			return "Buy";
		} else if (fBuySell == UOrderForm.SELL) {
			return "Sell";
		} else {
			return "None";
		}
	}
	
}