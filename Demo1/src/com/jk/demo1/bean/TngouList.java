package com.jk.demo1.bean;

import java.util.List;

public class TngouList extends BaseBean {

	private List<Tngou> tngous;

	public List<Tngou> getTngous() {
		return tngous;
	}

	public void setTngous(List<Tngou> tngous) {
		this.tngous = tngous;
	}

	@Override
	public String toString() {
		return "TngouList [tngous=" + tngous + "]";
	}
}
