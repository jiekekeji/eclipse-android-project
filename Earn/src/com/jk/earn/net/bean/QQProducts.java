package com.jk.earn.net.bean;

import java.util.List;

/**
 * QQ产品列表
 * 
 * @author Administrator
 * 
 */
public class QQProducts extends BaseBean {

	private List<QQGoods> goods;

	public List<QQGoods> getGoods() {
		return goods;
	}

	public void setGoods(List<QQGoods> goods) {
		this.goods = goods;
	}

	@Override
	public String toString() {
		return "QQProducts [goods=" + goods + "]";
	}

}
