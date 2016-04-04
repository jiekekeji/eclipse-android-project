package com.jk.earn.fragment;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.jk.earn.R;
import com.jk.earn.activity.QQGoodInfoActivity;
import com.jk.earn.adapter.QQProductAdapter;
import com.jk.earn.net.RequestTool;
import com.jk.earn.net.bean.QQGoods;
import com.jk.earn.net.bean.QQProducts;
import com.jk.earn.net.utils.CallBackJson;
import com.jk.earn.net.utils.NetClient;
import com.jk.earn.view.MyProgressDialog;
import com.jk.earn.view.MyToast;
import com.squareup.okhttp.Request;

public class QQFragment extends Fragment implements OnItemClickListener, OnClickListener {

	private static final String TAG = QQFragment.class.getSimpleName();

	private View rootView;
	private GridView qqProductGv;
	private QQProductAdapter mAdapter;
	private List<QQGoods> qqGoodList;

	CallBackJson<QQProducts> callBackJson = new CallBackJson<QQProducts>(QQProducts.class) {

		@Override
		public void onError(Request req, IOException exception) {
			MyProgressDialog.closeProgressDialog();
			MyToast.showToast(getActivity(), R.string.sync_qq_product_error, Toast.LENGTH_SHORT);
		}

		@Override
		public void onScucces(QQProducts response) {
			MyProgressDialog.closeProgressDialog();
			updateUI(response);
		}
	};

	protected void updateUI(QQProducts response) {
		String name = Thread.currentThread().getName();
		System.out.println(name);
		if (100 == response.getCode()) {
			qqGoodList.addAll(response.getGoods());
			mAdapter.notifyDataSetChanged();
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		if (null == rootView) {
			rootView = inflater.inflate(R.layout.fragment_qq, null);
			initView(rootView);
			initData();
		}
		return rootView;
	}

	private void initData() {
		getData();
	}

	private void initView(View view) {
		qqProductGv = (GridView) view.findViewById(R.id.fr_qq_product_gv);
		addEmptyView2qqProductGv(qqProductGv);

		qqGoodList = new ArrayList<QQGoods>();

		mAdapter = new QQProductAdapter(getActivity(), qqGoodList);
		qqProductGv.setAdapter(mAdapter);
		qqProductGv.setOnItemClickListener(this);
	}

	private void addEmptyView2qqProductGv(GridView qqProductGv) {
		ViewGroup emptyVg = (ViewGroup) qqProductGv.getParent();
		View view = (ViewGroup) LayoutInflater.from(getActivity()).inflate(R.layout.view_qq_empty, null);

		TextView emptyTv = (TextView) view.findViewById(R.id.fr_qq_empty_text_view);
		emptyTv.setText(Html.fromHtml("<small>" + getString(R.string.hint_qq_fr_empty_text) + "</small>"));
		emptyTv.setOnClickListener(this);

		emptyVg.addView(view, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		qqProductGv.setEmptyView(view);
	}

	private void getData() {
		MyProgressDialog.showProgressDialog(getActivity(), getString(R.string.dialog_qq_fr_sync_product));
		RequestTool.getQQProduct(callBackJson, TAG);
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int position, long id) {
		skipToQqGoodInfoActivity(qqGoodList.get(position));
	}

	private void skipToQqGoodInfoActivity(QQGoods qqGood) {
		Intent intent = new Intent(getActivity(), QQGoodInfoActivity.class);
		Bundle bundle = new Bundle();
		bundle.putSerializable("qqGood", qqGood);

		intent.putExtras(bundle);
		getActivity().startActivity(intent);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.fr_qq_empty_text_view:
			getData();
			break;

		}
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
		NetClient.getInstance().cancelRequest(TAG);
	}
}
