package com.jk.earn.net.bean;

public class LoginInfo extends BaseBean {

	private String token;

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	@Override
	public String toString() {
		return "LoginInfo [token=" + token + "]";
	}
}
