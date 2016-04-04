package com.jk.earn.net.bean;

public class UserInfo extends BaseBean {

	private String userName;
	private String email;
	private String points;
	private String token;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPoints() {
		return points;
	}

	public void setPoints(String points) {
		this.points = points;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	@Override
	public String toString() {
		return "UserInfo [userName=" + userName + ", email=" + email + ", points=" + points + ", token=" + token + "]";
	}

}
