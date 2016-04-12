package com.jk.uiutils;

import java.util.ArrayList;
import java.util.List;

public class ItemData {

	/**
	 * 初始化数据
	 */
	public List<ItemBean> getData() {
		List<ItemBean> datas = new ArrayList<ItemBean>();

		ItemBean bean0 = new ItemBean();
		bean0.setTitle("PullToRefreshListView");
		bean0.setContent("实现下拉刷新上拉加载更多");
		datas.add(bean0);

		return datas;
	}
}
