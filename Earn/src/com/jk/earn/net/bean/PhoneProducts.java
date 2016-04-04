package com.jk.earn.net.bean;

import java.util.List;

public class PhoneProducts extends BaseBean {

	private List<PhoneProduct> phoneProducts;

	public List<PhoneProduct> getPhoneProducts() {
		return phoneProducts;
	}

	public void setPhoneProducts(List<PhoneProduct> phoneProducts) {
		this.phoneProducts = phoneProducts;
	}

	@Override
	public String toString() {
		return "PhoneProducts [phoneProducts=" + phoneProducts + "]";
	}

}
