package com.jk.earn.fragment;

import java.io.IOException;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;

import com.jk.earn.R;
import com.jk.earn.activity.DialogFindPwdActivity;
import com.jk.earn.activity.UserLoginActivity;
import com.jk.earn.constant.SpKey;
import com.jk.earn.net.RequestTool;
import com.jk.earn.net.bean.LoginInfo;
import com.jk.earn.net.utils.CallBackJson;
import com.jk.earn.net.utils.NetClient;
import com.jk.earn.utils.AppUtils;
import com.jk.earn.utils.SPUtils;
import com.jk.earn.view.MyProgressDialog;
import com.jk.earn.view.MyToast;
import com.squareup.okhttp.Request;

public class LoginFragment extends Fragment implements OnClickListener {

	private static final String TAG = LoginFragment.class.getSimpleName();
	public static final int LOGIN_SCCCESS = 2000;

	private View rootView;
	private EditText userNameEt;
	private EditText pwdEt;

	private String userName;
	private String password;

	CallBackJson<LoginInfo> loginBackJson = new CallBackJson<LoginInfo>(LoginInfo.class) {

		@Override
		public void onError(Request req, IOException exception) {
			MyProgressDialog.closeProgressDialog();
			MyToast.showToast(getActivity(), R.string.toast_user_login_error);
		}

		@Override
		public void onScucces(LoginInfo response) {
			MyProgressDialog.closeProgressDialog();
			switch (response.getCode()) {
			case 100:
				saveLoginInfo(response);
				break;
			case 102:
				MyToast.showToast(getActivity(), R.string.toast_user_login_name_password_error);
				break;
			default:
				MyToast.showToast(getActivity(), R.string.toast_user_login_error);
				break;
			}

		}
	};

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		if (null == rootView) {
			rootView = inflater.inflate(R.layout.fragment_login, null);
			initView();
		}
		return rootView;
	}

	protected void saveLoginInfo(LoginInfo response) {
		MyToast.showToast(getActivity(), R.string.toast_user_login_ok);
		SPUtils.put(getActivity(), SpKey.TOKEN, response.getToken());
		SPUtils.put(getActivity(), SpKey.USERNAME, userName);

		getActivity().setResult(LOGIN_SCCCESS);
		((UserLoginActivity) getActivity()).finish();
	}

	private void initView() {
		userNameEt = (EditText) rootView.findViewById(R.id.fr_login_account_et);
		userNameEt.setHint(Html.fromHtml("<small>" + getString(R.string.hint_user_name) + "</small>"));

		pwdEt = (EditText) rootView.findViewById(R.id.fr_login_pwd_et);
		pwdEt.setHint(Html.fromHtml("<small>" + getString(R.string.hint_user_pwd) + "</small>"));

		rootView.findViewById(R.id.fr_login_submit_btn).setOnClickListener(this);
		rootView.findViewById(R.id.fr_login_forget_passwd).setOnClickListener(this);
	}

	@Override
	public void onResume() {
		super.onResume();
		initData();
	}

	@Override
	public void onHiddenChanged(boolean hidden) {
		super.onHiddenChanged(hidden);
		if (!hidden) {
			initData();
		}
	}

	private void initData() {
		userName = (String) SPUtils.get(getActivity(), SpKey.USERNAME, "");
		userNameEt.setText(userName);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.fr_login_submit_btn:
			submitLogin();
			break;
		case R.id.fr_login_forget_passwd:
			openDialogFindPwdActivity();
			break;
		}

	}

	private void openDialogFindPwdActivity() {
		Intent intent = new Intent(getActivity(), DialogFindPwdActivity.class);
		startActivity(intent);
	}

	private void submitLogin() {
		MyProgressDialog.showProgressDialog(getActivity(), getString(R.string.dialog_login_fr_login));
		AppUtils.hintInputMethod(getActivity());
		if (!checkInput()) {
			MyProgressDialog.closeProgressDialog();
			return;
		}
		RequestTool.userLogin(userName, password, loginBackJson, TAG);
	}

	private boolean checkInput() {
		userName = userNameEt.getText().toString();
		password = pwdEt.getText().toString();
		if (TextUtils.isEmpty(userName) || TextUtils.isEmpty(password)) {
			MyToast.showToast(getActivity(), R.string.toast_user_login_name_or_pwd_error);
			return false;
		}
		return true;
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
		NetClient.getInstance().cancelRequest(TAG);
	}
}
