package com.jk.earn.activity;

import java.io.IOException;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.jk.earn.R;
import com.jk.earn.constant.SpKey;
import com.jk.earn.net.NetURL;
import com.jk.earn.net.RequestTool;
import com.jk.earn.net.bean.BaseBean;
import com.jk.earn.net.bean.QQGoods;
import com.jk.earn.net.bean.UserInfo;
import com.jk.earn.net.utils.CallBackJson;
import com.jk.earn.net.utils.NetClient;
import com.jk.earn.utils.AppUtils;
import com.jk.earn.utils.SPUtils;
import com.jk.earn.view.MyProgressDialog;
import com.jk.earn.view.MyToast;
import com.squareup.okhttp.Request;

public class QQGoodInfoActivity extends BaseActivity implements OnClickListener {

	private static final String TAG = QQGoodInfoActivity.class.getSimpleName();

	private ImageView titleBackImgView;
	private ImageView goodImgView;

	private TextView goodNameTv;
	private TextView goodUnitTv;
	private TextView goodPointTv;
	private TextView totalPointTv;

	private EditText acountEt;
	private EditText buyNumEt;

	private Button buyNowBtn;

	private QQGoods good;

	private UserInfo mLoginInfo;

	private String account;

	private String buyNum;

	TextWatcher buyNumWatcher = new TextWatcher() {

		@Override
		public void onTextChanged(CharSequence s, int start, int before, int count) {
			if (!TextUtils.isEmpty(s)) {
				int buyNum = Integer.valueOf(s.toString().trim());
				totalPointTv.setText(String.valueOf(good.getPoint() * buyNum));
			} else {
				totalPointTv.setText("");
			}

		}

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count, int after) {

		}

		@Override
		public void afterTextChanged(Editable s) {

		}
	};

	CallBackJson<BaseBean> rechargeBackJson = new CallBackJson<BaseBean>(BaseBean.class) {

		@Override
		public void onError(Request req, IOException exception) {
			MyProgressDialog.closeProgressDialog();
			MyToast.showToast(QQGoodInfoActivity.this, R.string.toast_submit_rechare_error, Toast.LENGTH_SHORT);
		}

		@Override
		public void onScucces(BaseBean response) {
			MyProgressDialog.closeProgressDialog();
			handleResponse(response);

		}

	};

	private void handleResponse(BaseBean response) {
		switch (response.getCode()) {
		case 200:
			MyToast.showToast(QQGoodInfoActivity.this, R.string.toast_submit_rechare_ok, Toast.LENGTH_SHORT);
			break;

		case 401:
			MyToast.showToast(QQGoodInfoActivity.this, R.string.toast_submit_rechare_point_un_enough,
					Toast.LENGTH_SHORT);
			break;
		case 102:
			MyToast.showToast(QQGoodInfoActivity.this, R.string.toast_user_login, Toast.LENGTH_SHORT);
			startActivity(new Intent(QQGoodInfoActivity.this, UserLoginActivity.class));
			break;

		default:
			MyToast.showToast(QQGoodInfoActivity.this, R.string.toast_submit_rechare_point_exception,
					Toast.LENGTH_SHORT);
		}
	}

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_qq_good_info);
		initView();
		initData();
	}

	private void initData() {
		good = (QQGoods) this.getIntent().getSerializableExtra("qqGood");

		goodUnitTv.setText(good.getF() + good.getU());
		goodPointTv.setText(String.valueOf(good.getPoint()));
		goodNameTv.setText(good.getB());
		Glide.with(this).load(NetURL.Host + good.getImgurl()).diskCacheStrategy(DiskCacheStrategy.ALL)
				.placeholder(R.drawable.ic_app_img_default).into(goodImgView);
	}

	private void initView() {
		titleBackImgView = (ImageView) findViewById(R.id.ac_qq_info_title_back_img_view);
		titleBackImgView.setOnClickListener(this);
		goodImgView = (ImageView) findViewById(R.id.ac_qq_info_img_view);

		goodNameTv = (TextView) findViewById(R.id.ac_qq_info_good_name);
		goodUnitTv = (TextView) findViewById(R.id.ac_qq_info_good_unit);
		goodPointTv = (TextView) findViewById(R.id.ac_qq_info_good_point);
		totalPointTv = (TextView) findViewById(R.id.ac_qq_info_good_total_point);

		acountEt = (EditText) findViewById(R.id.ac_qq_info_good_acount_etd_view);
		acountEt.setHint(Html.fromHtml("<small>" + getString(R.string.hint_account_input) + "</small>"));
		buyNumEt = (EditText) findViewById(R.id.ac_qq_info_good_buy_number_edt_view);

		buyNumEt.setHint(Html.fromHtml("<small>" + getString(R.string.hint_buyNum_input) + "</small>"));
		buyNumEt.addTextChangedListener(buyNumWatcher);

		buyNowBtn = (Button) findViewById(R.id.ac_qq_info_buy_now_btn);
		buyNowBtn.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.ac_qq_info_title_back_img_view:
			finshThisActivity();
			break;
		case R.id.ac_qq_info_buy_now_btn:
			opRecharge();
			break;
		}
	}

	private void opRecharge() {
		if (!checkInput()) {
			return;
		}

		mLoginInfo = AppUtils.checkIsLogin(this);
		if (null == mLoginInfo) {
			MyToast.showToast(this, R.string.toast_user_login, Toast.LENGTH_SHORT);
			startActivity(new Intent(this, UserLoginActivity.class));
			return;
		}

		submitRecharge();
	}

	private void submitRecharge() {
		String channelId = (String) SPUtils.get(this, SpKey.CHANNELID, "local");
		MyProgressDialog.showProgressDialog(this, getString(R.string.dialog_qq_ac_good_info_recharge));

		RequestTool.qqSubmitRecharge(mLoginInfo.getUserName(), channelId, mLoginInfo.getToken(), good.getI(),
				good.getB(), buyNum, String.valueOf(good.getP16()), totalPointTv.getText().toString().trim(), account,
				rechargeBackJson, TAG);
	}

	private boolean checkInput() {
		account = acountEt.getText().toString().trim();
		buyNum = buyNumEt.getText().toString().trim();

		if (TextUtils.isEmpty(account) || TextUtils.isEmpty(buyNum) || Integer.valueOf(buyNum) < 1) {
			MyToast.showToast(this, R.string.toast_input_info_error, Toast.LENGTH_SHORT);
			return false;
		}

		return true;
	}

	private void finshThisActivity() {
		finish();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		NetClient.getInstance().cancelRequest(TAG);
	}
}
