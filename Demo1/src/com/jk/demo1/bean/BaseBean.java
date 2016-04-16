package com.jk.demo1.bean;

public class BaseBean {

	private String status;
	private int total;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	@Override
	public String toString() {
		return "BaseBean [status=" + status + ", total=" + total + "]";
	}

}
