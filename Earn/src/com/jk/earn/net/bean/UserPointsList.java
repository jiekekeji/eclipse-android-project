package com.jk.earn.net.bean;

import java.util.List;

public class UserPointsList extends BaseBean {

	private List<UserPoints> pointsList;

	public List<UserPoints> getPointsList() {
		return pointsList;
	}

	public void setPointsList(List<UserPoints> pointsList) {
		this.pointsList = pointsList;
	}

	@Override
	public String toString() {
		return "UserPointsList [pointsList=" + pointsList + "]";
	}

}
