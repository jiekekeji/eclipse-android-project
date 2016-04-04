package com.jk.earn.net.bean;

import java.io.Serializable;

public class AppVersion extends BaseBean implements Serializable {

	/**
	 * 渠道号
	 */
	private String channelId;
	/**
	 * app版本号
	 */
	private Double versionCode;

	/**
	 * 版本号
	 */
	private String versionDesc;
	/**
	 * 下载的文件名
	 */
	private String fileName;

	public String getChannelId() {
		return channelId;
	}

	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}

	public Double getVersionCode() {
		return versionCode;
	}

	public void setVersionCode(Double versionCode) {
		this.versionCode = versionCode;
	}

	public String getVersionDesc() {
		return versionDesc;
	}

	public void setVersionDesc(String versionDesc) {
		this.versionDesc = versionDesc;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	@Override
	public String toString() {
		return "AppVersion [channelId=" + channelId + ", versionCode="
				+ versionCode + ", versionDesc=" + versionDesc + ", fileName="
				+ fileName + "]";
	}

}
