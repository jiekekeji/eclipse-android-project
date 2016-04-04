package com.jk.earn.net.bean;

import java.io.Serializable;

/**
 * 手机的归属地和运营商信息
 * 
 * @author Administrator
 * 
 */
public class PhoneNumInfo extends BaseBean implements Serializable {

	private Integer id;

	private String phoneNum;
	private String mobileArea;

	private String mobileType;
	private Integer areaCode;
	private Integer postCode;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getPhoneNum() {
		return phoneNum;
	}

	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}

	public String getMobileArea() {
		return mobileArea;
	}

	public void setMobileArea(String mobileArea) {
		this.mobileArea = mobileArea;
	}

	public String getMobileType() {
		return mobileType;
	}

	public void setMobileType(String mobileType) {
		this.mobileType = mobileType;
	}

	public Integer getAreaCode() {
		return areaCode;
	}

	public void setAreaCode(Integer areaCode) {
		this.areaCode = areaCode;
	}

	public Integer getPostCode() {
		return postCode;
	}

	public void setPostCode(Integer postCode) {
		this.postCode = postCode;
	}

	@Override
	public String toString() {
		return "PhoneInfo [id=" + id + ", phoneNum=" + phoneNum + ", mobileArea=" + mobileArea + ", mobileType="
				+ mobileType + ", areaCode=" + areaCode + ", postCode=" + postCode + "]";
	}

}
