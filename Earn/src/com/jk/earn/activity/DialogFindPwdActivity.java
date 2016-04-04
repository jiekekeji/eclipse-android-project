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
import com.jk.earn.net.RequestTool;
import com.jk.earn.net.bean.BaseBean;
import com.jk.earn.net.utils.CallBackJson;
import com.jk.earn.net.utils.NetClient;
import com.jk.earn.utils.AppUtils;
import com.jk.earn.view.MyProgressDialog;
import com.jk.earn.view.MyToast;
import com.squareup.okhttp.Request;

public class DialogFindPwdActivity extends BaseActivity implements OnClickListener {

	private static final String TAG = DialogFindPwdActivity.class.getSimpleName();

	private EditText emailEt;
	private EditText namelEt;

	CallBackJson<BaseBean> backJson = new CallBackJson<BaseBean>(BaseBean.class) {

		@Override
		public void onError(Request req, IOException exception) {
			MyProgressDialog.closeProgressDialog();
			MyToast.showToast(DialogFindPwdActivity.this, R.string.toast_pwd_modify_error);
		}

		@Override
		public void onScucces(BaseBean response) {
			MyProgressDialog.closeProgressDialog();
			handleResult(response);
		}
	};

	protected void handleResult(BaseBean response) {
		switch (response.getCode()) {
		case 100:
			MyToast.showToast(DialogFindPwdActivity.this, R.string.toast_ac_dialog_find_pwd_scucces);
			finish();
			break;
		case 103:
			MyToast.showToast(DialogFindPwdActivity.this, R.string.toast_ac_dialog_find_pwd_fail);
			break;
		case 102:
			MyToast.showToast(DialogFindPwdActivity.this, R.string.toast_ac_dialog_find_pwd_name_error);
			break;
		case 104:
			MyToast.showToast(DialogFindPwdActivity.this, R.string.toast_ac_dialog_find_pwd_email_error);
			break;
		default:
			MyToast.showToast(DialogFindPwdActivity.this, R.string.toast_ac_dialog_find_pwd_fail);
			break;
		}
	}

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setFinishOnTouchOutside(false);
		setContentView(R.layout.activity_dialog_find_pwd);
		initView();
	}

	private void initView() {
		emailEt = (EditText) findViewById(R.id.ac_dialog_find_pwd_email_et);
		emailEt.setHint(Html.fromHtml("<small>" + getString(R.string.hint_ac_find_pwd_user_email) + "</small>"));

		namelEt = (EditText) findViewById(R.id.ac_dialog_find_pwd_name_et);
		namelEt.setHint(Html.fromHtml("<small>" + getString(R.string.hint_ac_find_pwd_user_name) + "</small>"));

		findViewById(R.id.ac_dialog_find_pwd_confire_tv).setOnClickListener(this);
		findViewById(R.id.ac_dialog_find_pwd_cancel_tv).setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.ac_dialog_find_pwd_confire_tv:
			modifyPassWd();
			break;
		case R.id.ac_dialog_find_pwd_cancel_tv:
			DialogFindPwdActivity.this.finish();
			break;
		}
	}

	private void modifyPassWd() {
		String email = emailEt.getText().toString().trim();
		String name = namelEt.getText().toString().trim();

		if (TextUtils.isEmpty(name) || name.length() < 3 || name.length() > 10) {
			MyToast.showToast(this, R.string.toast_user_name_enpty);
			return;
		}

		if (TextUtils.isEmpty(email) || !AppUtils.checkRegular(AppUtils.EMAIL, email)) {
			MyToast.showToast(this, R.string.toast_user_email_error);
			return;
		}

		MyProgressDialog.showProgressDialog(this, "请稍等");
		RequestTool.getBackPasswd(name, email, backJson, TAG);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		NetClient.getInstance().cancelRequest(TAG);
	}
}
