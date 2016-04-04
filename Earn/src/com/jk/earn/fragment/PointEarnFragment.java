package com.jk.earn.fragment;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jk.earn.R;
import com.jk.earn.adapter.PointsRecordAdapter;
import com.jk.earn.net.RequestTool;
import com.jk.earn.net.bean.UserInfo;
import com.jk.earn.net.bean.UserPoints;
import com.jk.earn.net.bean.UserPointsList;
import com.jk.earn.net.utils.CallBackJson;
import com.jk.earn.net.utils.NetClient;
import com.jk.earn.pulltorefresh.widget.XListView;
import com.jk.earn.pulltorefresh.widget.XListView.IXListViewListener;
import com.jk.earn.utils.AppUtils;
import com.jk.earn.view.MyToast;
import com.squareup.okhttp.Request;

public class PointEarnFragment extends Fragment {

	private static final String TAG = PointEarnFragment.class.getSimpleName();

	private View rootView;
	private PointsRecordAdapter mAdapter;
	private List<UserPoints> pointsList;
	private XListView mXListView;

	private int page = 0;
	private int limit = 50;

	CallBackJson<UserPointsList> backJson = new CallBackJson<UserPointsList>(UserPointsList.class) {

		@Override
		public void onError(Request req, IOException exception) {
			onLoadOrRefreshFinish();
		}

		@Override
		public void onScucces(UserPointsList response) {
			onLoadOrRefreshFinish();
			handleResponse(response);
		}
	};

	private void onLoadOrRefreshFinish() {
		mXListView.stopRefresh();
		mXListView.stopLoadMore();
	}

	protected void handleResponse(UserPointsList response) {
		switch (response.getCode()) {
		case 100:
			pointsList.clear();
			pointsList.addAll(response.getPointsList());
			mAdapter.notifyDataSetChanged();
			break;

		case 103:

			break;
		}
	}

	IXListViewListener xListener = new IXListViewListener() {

		@Override
		public void onRefresh() {
			getData();
		}

		@Override
		public void onLoadMore() {
		}
	};

	private void getData() {
		UserInfo userInfo = AppUtils.checkIsLogin(getActivity());
		if (null == userInfo) {
			MyToast.showToast(getActivity(), R.string.toast_user_no_login);
			return;
		}

		RequestTool.queryPointsRecoder(userInfo.getUserName(), userInfo.getToken(), page, limit, backJson, TAG);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		if (null == rootView) {
			rootView = inflater.inflate(R.layout.fragment_point_earn, null);
			initView(rootView);

		}
		return rootView;
	}

	private void initView(View rootView) {
		mXListView = (XListView) rootView.findViewById(R.id.fr_point_earn_xlv);
		mXListView.setXListViewListener(xListener);
		// 隐藏底部分隔线
		mXListView.setFooterDividersEnabled(false);

		mXListView.setPullRefreshEnable(true);
		mXListView.setPullLoadEnable(false);
		mXListView.setAutoLoadEnable(false);

		pointsList = new ArrayList<UserPoints>();
		mAdapter = new PointsRecordAdapter(pointsList, getActivity());
		mXListView.setAdapter(mAdapter);
	}

	@Override
	public void onResume() {
		super.onResume();
		getData();
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
		NetClient.getInstance().cancelRequest(TAG);
	}

}
