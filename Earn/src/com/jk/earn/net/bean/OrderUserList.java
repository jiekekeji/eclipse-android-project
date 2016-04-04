package com.jk.earn.net.bean;

import java.util.List;

public class OrderUserList extends BaseBean {

	private List<OrderUser> list;

	public List<OrderUser> getList() {
		return list;
	}

	public void setList(List<OrderUser> list) {
		this.list = list;
	}

	@Override
	public String toString() {
		return "OrderUserList [list=" + list + "]";
	}

}
