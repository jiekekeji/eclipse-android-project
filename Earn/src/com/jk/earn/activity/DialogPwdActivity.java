package com.jk.earn.activity;

import java.io.IOException;

import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.EditText;

import com.jk.earn.R;
import com.jk.earn.constant.SpKey;
import com.jk.earn.net.RequestTool;
import com.jk.earn.net.bean.BaseBean;
import com.jk.earn.net.utils.CallBackJson;
import com.jk.earn.net.utils.NetClient;
import com.jk.earn.utils.AppUtils;
import com.jk.earn.utils.SPUtils;
import com.jk.earn.view.MyProgressDialog;
import com.jk.earn.view.MyToast;
import com.squareup.okhttp.Request;

public class DialogPwdActivity extends BaseActivity implements OnClickListener {

	private static final String TAG = DialogPwdActivity.class.getSimpleName();
	public static final int MODIFY_PWD_SCUCCES = 2700;

	private EditText pwdEt;
	private EditText pwdConfireEt;

	private String pwd;
	private String pwdConfire;

	CallBackJson<BaseBean> backJson = new CallBackJson<BaseBean>(BaseBean.class) {

		@Override
		public void onError(Request req, IOException exception) {
			MyProgressDialog.closeProgressDialog();
			MyToast.showToast(DialogPwdActivity.this, R.string.toast_pwd_modify_error);
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
			clearLoginInfo();
			break;
		case 102:
			MyToast.showToast(this, R.string.toast_pwd_modify_error);
			break;
		case 104:
			MyToast.showToast(this, R.string.toast_pwd_modify_error);
			break;
		}
	}

	private void clearLoginInfo() {
		AppUtils.clearLoginInfo(this);
		MyToast.showToast(this, R.string.toast_pwd_modify_scucces);

		AppUtils.hintInputMethod(this);
		setResult(MODIFY_PWD_SCUCCES);
		finish();
	}

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setFinishOnTouchOutside(false);
		setContentView(R.layout.activity_dialog_pwd);
		initView();
	}

	private void initView() {
		pwdEt = (EditText) findViewById(R.id.ac_dialog_pwd_pwd_et);
		pwdConfireEt = (EditText) findViewById(R.id.ac_dialog_pwd_pwd_confire_et);

		pwdEt.setHint(Html.fromHtml("<small>" + getString(R.string.hint_user_pwd) + "</small>"));
		pwdConfireEt.setHint(Html.fromHtml("<small>" + getString(R.string.hint_user_pwd_confire) + "</small>"));

		findViewById(R.id.ac_dialog_pwd_confire_modify_btn).setOnClickListener(this);
		findViewById(R.id.ac_dialog_pwd_cancel_modify_btn).setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.ac_dialog_pwd_confire_modify_btn:
			requestModifyPwd();
			break;
		case R.id.ac_dialog_pwd_cancel_modify_btn:
			finish();
			break;
		}
	}

	protected void requestModifyPwd() {
		if (!checkPwd()) {
			MyToast.showToast(this, R.string.toast_user_pwd_error);
			return;
		}

		MyProgressDialog.showProgressDialog(this, getString(R.string.dialog_modify_pwd));
		String userName = (String) SPUtils.get(this, SpKey.USERNAME, "");
		String token = (String) SPUtils.get(this, SpKey.TOKEN, "");

		RequestTool.modifyPasswd(userName, pwdConfire, token, backJson, TAG);
	}

	private boolean checkPwd() {
		pwd = pwdEt.getText().toString();
		pwdConfire = pwdConfireEt.getText().toString();

		if (TextUtils.isEmpty(pwd) || pwd.length() < 6 || pwd.length() > 16 || TextUtils.isEmpty(pwdConfire)
				|| !pwdConfire.equals(pwd)) {
			return false;
		}
		return true;
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		NetClient.getInstance().cancelRequest(TAG);
	}
}
