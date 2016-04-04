package com.jk.earn.net.bean;

public class PhonePoints extends BaseBean {

	/**
	 * 兑换该面额所需的积分
	 */
	private int point;

	/**
	 * 实际金额，单位元
	 */
	private float price;
	/**
	 * 充值面额
	 */
	private int card_worth;
	/**
	 * 手机号码
	 */
	private String phone_number;
	/**
	 * 归属地信息
	 */
	private String area;
	/**
	 * 运营商类型
	 */
	private String platform;

	/**
	 * 签名串
	 */
	private String sign;

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public int getPoint() {
		return point;
	}

	public void setPoint(int point) {
		this.point = point;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public int getCard_worth() {
		return card_worth;
	}

	public void setCard_worth(int card_worth) {
		this.card_worth = card_worth;
	}

	public String getPhone_number() {
		return phone_number;
	}

	public void setPhone_number(String phone_number) {
		this.phone_number = phone_number;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getPlatform() {
		return platform;
	}

	public void setPlatform(String platform) {
		this.platform = platform;
	}

	@Override
	public String toString() {
		return "HFDProduct [point=" + point + ", price=" + price
				+ ", card_worth=" + card_worth + ", phone_number="
				+ phone_number + ", area=" + area + ", platform=" + platform
				+ ", sign=" + sign + "]";
	}

}
