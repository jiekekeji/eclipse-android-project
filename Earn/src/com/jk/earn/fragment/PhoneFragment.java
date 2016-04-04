package com.jk.earn.fragment;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.TextView;

import com.jk.earn.R;
import com.jk.earn.activity.PhoneGoodInfoActivity;
import com.jk.earn.adapter.PhoneProductAdapter;
import com.jk.earn.net.RequestTool;
import com.jk.earn.net.bean.PhoneNumInfo;
import com.jk.earn.net.bean.PhoneProduct;
import com.jk.earn.net.bean.PhoneProducts;
import com.jk.earn.net.utils.CallBackJson;
import com.jk.earn.net.utils.NetClient;
import com.jk.earn.utils.AppUtils;
import com.jk.earn.view.MyProgressDialog;
import com.jk.earn.view.MyToast;
import com.jk.earn.view.NoSrocGridView;
import com.jk.earn.view.PhoneProductEmptyView;
import com.squareup.okhttp.Request;

public class PhoneFragment extends Fragment implements OnItemClickListener, OnClickListener {

	private static final String TAG = PhoneFragment.class.getSimpleName();

	private View rootView;
	private EditText phoneNumEt;
	private TextView phoneNumInfoTv;
	private NoSrocGridView cardWorthGv;

	private PhoneProductAdapter productAdapter;
	private List<PhoneProduct> productList;

	private String phoneNum;
	private PhoneNumInfo numInfo;

	CallBackJson<PhoneProducts> phoneBackJson = new CallBackJson<PhoneProducts>(PhoneProducts.class) {

		@Override
		public void onError(Request req, IOException exception) {
			MyToast.showToast(getActivity(), R.string.toast_phone_fr_sync_product_error);
			MyProgressDialog.closeProgressDialog();
		}

		@Override
		public void onScucces(PhoneProducts response) {
			MyProgressDialog.closeProgressDialog();
			updatePhonePorduct(response);
		}

	};

	protected void updatePhonePorduct(PhoneProducts response) {
		if (100 == response.getCode()) {
			productList.clear();
			productList.addAll(response.getPhoneProducts());
			productAdapter.notifyDataSetChanged();
		}
	}

	CallBackJson<PhoneNumInfo> phoneNumBackJson = new CallBackJson<PhoneNumInfo>(PhoneNumInfo.class) {

		@Override
		public void onError(Request req, IOException exception) {
			MyProgressDialog.closeProgressDialog();
			phoneNumInfoTv.setText(getString(R.string.fr_phone_num));
		}

		@Override
		public void onScucces(PhoneNumInfo response) {
			MyProgressDialog.closeProgressDialog();
			numInfo = response;
			updatePhoneNumInfoTv();
		}

	};

	protected void updatePhoneNumInfoTv() {
		if (100 != numInfo.getCode()) {
			phoneNumInfoTv.setText(getString(R.string.fr_phone_num));
			return;
		}
		phoneNumInfoTv.setText(numInfo.getMobileArea() + numInfo.getMobileType());
	}

	TextWatcher mTextWatcher = new TextWatcher() {

		@Override
		public void onTextChanged(CharSequence s, int start, int before, int count) {
			Log.i(TAG, "onTextChanged:" + s);
			String temp = s.toString();
			if (11 == temp.length() && !temp.equals(phoneNum)) {
				phoneNum = s.toString();
				getPhoneNumInfo(phoneNum);
			}

		}

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count, int after) {
			Log.i(TAG, "beforeTextChanged:" + s);
		}

		@Override
		public void afterTextChanged(Editable s) {
			Log.i(TAG, "afterTextChanged:" + s);
		}
	};

	protected void getPhoneNumInfo(String phoneNum) {
		AppUtils.hintInputMethod(getActivity());
		MyProgressDialog.showProgressDialog(getActivity(), getString(R.string.dialog_fr_phone_query_phone_num));
		RequestTool.queryPhoneNumInfo(phoneNum, phoneNumBackJson, "request_phoneNumInfo");
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		if (null == rootView) {
			rootView = inflater.inflate(R.layout.fragment_phone, null);
			initView(rootView);
			initData();
		}
		return rootView;
	}

	private void initView(View view) {
		phoneNumEt = (EditText) view.findViewById(R.id.fr_phone_number_input_et);
		phoneNumEt.setHint(Html.fromHtml("<small>" + getString(R.string.hint_fr_phone_phone_num_input) + "</small>"));
		phoneNumInfoTv = (TextView) view.findViewById(R.id.fr_phone_number_info_tv);
		cardWorthGv = (NoSrocGridView) view.findViewById(R.id.fr_phone_product_gv);

		phoneNumEt.addTextChangedListener(mTextWatcher);
		productList = new ArrayList<PhoneProduct>();
		productAdapter = new PhoneProductAdapter(getActivity(), productList);

		PhoneProductEmptyView mEmptyView = new PhoneProductEmptyView(getActivity());
		((ViewGroup) cardWorthGv.getParent()).addView(mEmptyView);
		cardWorthGv.setEmptyView(mEmptyView);

		cardWorthGv.setAdapter(productAdapter);
		cardWorthGv.setOnItemClickListener(this);
		view.findViewById(R.id.fr_phone_refresh_cardworth).setOnClickListener(this);

	}

	private void initData() {
		MyProgressDialog.showProgressDialog(getActivity(),
				getActivity().getString(R.string.dialog_phone_fr_sync_product));
		RequestTool.getPhoneProduct(phoneBackJson, "request_product");
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
		skip2PhoneGoodInfoActivity(position);
	}

	private void skip2PhoneGoodInfoActivity(int position) {
		if (null == phoneNum || phoneNum.length() != 11) {
			MyToast.showToast(getActivity(), R.string.toast_phone_fr_phone_num_error);
			return;
		}

		Intent intent = new Intent(getActivity(), PhoneGoodInfoActivity.class);
		intent.putExtra("phoneProduct", productList.get(position));
		intent.putExtra("phoneNum", phoneNum);
		if (null != numInfo) {
			intent.putExtra("numInfo", numInfo);
		}
		startActivity(intent);
	}

	@Override
	public void onResume() {
		super.onResume();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.fr_phone_refresh_cardworth:
			initData();
			break;

		default:
			break;
		}
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
		NetClient.getInstance().cancelRequest(TAG);
	}

}
