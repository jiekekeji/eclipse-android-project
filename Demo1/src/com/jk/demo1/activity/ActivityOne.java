package com.jk.demo1.activity;

import java.util.ArrayList;
import java.util.List;

import android.R.raw;
import android.os.Bundle;
import android.util.Log;
import cn.finalteam.okhttpfinal.BaseHttpRequestCallback;
import cn.finalteam.okhttpfinal.HttpRequest;
import cn.finalteam.okhttpfinal.RequestParams;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnLastItemVisibleListener;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.jk.demo1.R;
import com.jk.demo1.adapter.TngouListAdapter;
import com.jk.demo1.bean.Tngou;
import com.jk.demo1.bean.TngouList;
import com.jk.demo1.net.URLApi;

public class ActivityOne extends BaseActivity implements OnRefreshListener2, OnLastItemVisibleListener {

	protected static final String TAG = ActivityOne.class.getSimpleName();
	private PullToRefreshListView listview;
	private TngouListAdapter mAdapter;
	private List<Tngou> tngous;

	private int page = 1;// 请求页码
	private int rows = 20;// 每页返回的条数

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.activity_one);
		initView();
	}

	private void initView() {
		listview = (PullToRefreshListView) findViewById(R.id.listview);

		tngous = new ArrayList<Tngou>();
		mAdapter = new TngouListAdapter(this, tngous, R.layout.adapter_tngou);

		listview.setAdapter(mAdapter);
		// 下拉刷新监听
		listview.setOnRefreshListener(this);
		// 滑动到底部的监听
		listview.setOnLastItemVisibleListener(this);
		// 正在刷新的过程中允许滚动
		listview.setScrollingWhileRefreshingEnabled(true);
		// 设置刷新的模式
		listview.setMode(Mode.BOTH);
	}

	@Override
	public void onPullDownToRefresh(PullToRefreshBase refreshView) {
		// 下拉刷新
		page = 0;
		requestData();
	}

	@Override
	public void onPullUpToRefresh(PullToRefreshBase refreshView) {
		requestData();
	};

	@Override
	public void onLastItemVisible() {
		// 滑动到底部加载更多
		requestData();
	}

	/**
	 * 请求数据
	 */
	private void requestData() {
		RequestParams params = new RequestParams();
		params.addFormDataPart("page", page);
		params.addFormDataPart("rows", rows);
		// params.addFormDataPart("id", "");
		HttpRequest.post(URLApi.SERVER + URLApi.IMG_LIST, params, requestDataCallback);
	}

	/**
	 * 请求数据的监听
	 */
	BaseHttpRequestCallback requestDataCallback = new BaseHttpRequestCallback<TngouList>() {
		@Override
		protected void onSuccess(TngouList bean) {
			super.onSuccess(bean);
			listview.onRefreshComplete();
			Log.i(TAG, "t=" + bean.toString());
		}

		@Override
		public void onStart() {
			super.onStart();
			Log.i(TAG, "onStart");
		}

		@Override
		public void onFailure(int errorCode, String msg) {
			super.onFailure(errorCode, msg);
			listview.onRefreshComplete();
			Log.i(TAG, "onFailure");
		}
	};

	/*** Activity生命周期回调函数 ****/
	@Override
	protected void onResume() {
		super.onResume();
		listview.setRefreshing();
	}

}
