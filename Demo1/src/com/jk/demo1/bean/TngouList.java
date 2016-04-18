package com.jk.demo1.bean;

import java.util.List;

public class TngouList extends BaseBean {

	private List<Tngou> tngou;

	public List<Tngou> getTngou() {
		return tngou;
	}

	public void setTngou(List<Tngou> tngou) {
		this.tngou = tngou;
	}

	@Override
	public String toString() {
		return "TngouList [tngou=" + tngou + "]";
	}

}
