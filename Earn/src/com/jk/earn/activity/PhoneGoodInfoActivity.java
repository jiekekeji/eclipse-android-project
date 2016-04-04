package com.jk.earn.activity;

import java.io.IOException;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import com.jk.earn.R;
import com.jk.earn.constant.SpKey;
import com.jk.earn.net.RequestTool;
import com.jk.earn.net.bean.BaseBean;
import com.jk.earn.net.bean.PhoneNumInfo;
import com.jk.earn.net.bean.PhoneProduct;
import com.jk.earn.net.bean.UserInfo;
import com.jk.earn.net.utils.CallBackJson;
import com.jk.earn.net.utils.NetClient;
import com.jk.earn.utils.AppUtils;
import com.jk.earn.utils.SPUtils;
import com.jk.earn.view.MyProgressDialog;
import com.jk.earn.view.MyToast;
import com.squareup.okhttp.Request;

public class PhoneGoodInfoActivity extends BaseActivity implements OnClickListener {

	private static final String TAG = PhoneGoodInfoActivity.class.getSimpleName();
	private PhoneProduct phoneProduct;
	private String phoneNum;
	private PhoneNumInfo numInfo;

	private UserInfo mLoginInfo;

	CallBackJson<BaseBean> callBackJson = new CallBackJson<BaseBean>(BaseBean.class) {

		@Override
		public void onError(Request req, IOException exception) {
			MyProgressDialog.closeProgressDialog();
			MyToast.showToast(PhoneGoodInfoActivity.this, R.string.toast_submit_rechare_point_exception);
		}

		@Override
		public void onScucces(BaseBean response) {
			MyProgressDialog.closeProgressDialog();
			handleResponse(response);
		}
	};

	protected void handleResponse(BaseBean response) {
		switch (response.getCode()) {
		case 100:
			MyToast.showToast(PhoneGoodInfoActivity.this, R.string.toast_submit_rechare_ok);
			break;
		case 105:
			MyToast.showToast(PhoneGoodInfoActivity.this, R.string.toast_submit_rechare_point_un_enough);
			break;
		case 102:
			MyToast.showToast(PhoneGoodInfoActivity.this, R.string.toast_user_login, Toast.LENGTH_SHORT);
			startActivity(new Intent(PhoneGoodInfoActivity.this, UserLoginActivity.class));
			break;
		default:
			MyToast.showToast(PhoneGoodInfoActivity.this, R.string.toast_submit_rechare_error);
			break;
		}
	}

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_phone_good_info);
		initData();
		initView();
	}

	private void initData() {
		Intent intent = getIntent();
		phoneProduct = (PhoneProduct) intent.getSerializableExtra("phoneProduct");
		phoneNum = intent.getStringExtra("phoneNum");
		numInfo = (PhoneNumInfo) intent.getSerializableExtra("numInfo");
	}

	private void initView() {
		findViewById(R.id.ac_phone_info_title_back_img_view).setOnClickListener(this);
		findViewById(R.id.ac_phone_info_buy_now_btn).setOnClickListener(this);

		((TextView) findViewById(R.id.ac_phone_info_num)).setText(phoneNum);
		((TextView) findViewById(R.id.ac_phone_info_good_name)).setText(getString(R.string.phone_product_name));

		((TextView) findViewById(R.id.ac_phone_info_good_unit)).setText(phoneProduct.getCardWorth()
				+ getString(R.string.phone_product_adapter_carworth));

		((TextView) findViewById(R.id.ac_phone_info_good_point)).setText(String.valueOf(phoneProduct.getPoint()));
		((TextView) findViewById(R.id.ac_phone_info_good_total_point)).setText(String.valueOf(phoneProduct.getPoint()));

		String area = numInfo.getMobileArea();
		if (null != area) {
			((TextView) findViewById(R.id.ac_phone_info_eara)).setText(area + numInfo.getMobileType());
		}

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.ac_phone_info_title_back_img_view:
			finish();
			break;

		case R.id.ac_phone_info_buy_now_btn:
			submitRecharge();
			break;
		}
	}

	private void submitRecharge() {
		mLoginInfo = AppUtils.checkIsLogin(this);
		if (null == mLoginInfo) {
			MyToast.showToast(this, R.string.toast_user_login, Toast.LENGTH_SHORT);
			startActivity(new Intent(this, UserLoginActivity.class));
			return;
		}

		MyProgressDialog.showProgressDialog(this, getString(R.string.dialog_phone_ac_good_info_recharge));
		String channelId = (String) SPUtils.get(this, SpKey.CHANNELID, "local");

		RequestTool.phSubmitRecharge(mLoginInfo.getUserName(), mLoginInfo.getToken(), phoneNum,
				phoneProduct.getCardWorth(), phoneProduct.getPoint(), phoneProduct.getProductId(), channelId,
				callBackJson, TAG);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		NetClient.getInstance().cancelRequest(TAG);
	}
}
