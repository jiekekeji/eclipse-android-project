package com.jk.earn.net.bean;

public class UserPoints extends BaseBean {

	private Integer id;

	/**
	 * 获得积分的用户的用户名
	 */
	private String userName;

	/**
	 * 应用名称
	 */
	private String app;

	/**
	 * 获得的积分
	 */
	private Float point;

	/**
	 * 获取积分的详情
	 */
	private String detail;

	/**
	 * 积分获得的时间
	 */
	private Long time;

	/**
	 * 每次获得积分的辨识，不重复
	 */
	private String upOrderId;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getApp() {
		return app;
	}

	public void setApp(String app) {
		this.app = app;
	}

	public Float getPoint() {
		return point;
	}

	public void setPoint(Float point) {
		this.point = point;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public Long getTime() {
		return time;
	}

	public void setTime(Long time) {
		this.time = time;
	}

	public String getUpOrderId() {
		return upOrderId;
	}

	public void setUpOrderId(String upOrderId) {
		this.upOrderId = upOrderId;
	}

	@Override
	public String toString() {
		return "UserPoints [id=" + id + ", userName=" + userName + ", app=" + app + ", point=" + point + ", detail="
				+ detail + ", time=" + time + ", upOrderId=" + upOrderId + "]";
	}

}
