package com.jk.earn.net.bean;

import java.io.Serializable;

/**
 * 话费充值产品
 * 
 * @author Administrator
 * 
 */
public class PhoneProduct extends BaseBean implements Serializable {

	/**
	 * 面额 :1、2、5...
	 */
	private Integer cardWorth;

	/**
	 * 对应的积分
	 */
	private Float point;

	/**
	 * 产品Id
	 */
	private String productId;

	public Integer getCardWorth() {
		return cardWorth;
	}

	public void setCardWorth(Integer cardWorth) {
		this.cardWorth = cardWorth;
	}

	public Float getPoint() {
		return point;
	}

	public void setPoint(Float point) {
		this.point = point;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	@Override
	public String toString() {
		return "PhoneProduct [cardWorth=" + cardWorth + ", point=" + point + ", productId=" + productId + "]";
	}

}
