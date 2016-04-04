package com.jk.earn.fragment;

import java.io.IOException;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.jk.earn.R;
import com.jk.earn.activity.UserLoginActivity;
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

public class RegisterFragment extends Fragment implements OnClickListener {

	private EditText accountEt;
	private EditText emailEt;
	private EditText pwdEt;
	private EditText pwdConfireEt;

	private Button registerBtn;

	private String userName;
	private String email;
	private String pwd;
	private String cofirePwd;

	private static final String REGISTER_TAG = "tag2";

	CallBackJson<BaseBean> registerBackJson = new CallBackJson<BaseBean>(BaseBean.class) {

		@Override
		public void onError(Request req, IOException exception) {
			MyProgressDialog.closeProgressDialog();
			MyToast.showToast(getActivity(), R.string.toast_user_register_error);
		}

		@Override
		public void onScucces(BaseBean response) {
			MyProgressDialog.closeProgressDialog();
			switch (response.getCode()) {
			case 100:
				skipToLogin();
				break;
			case 104:
				MyToast.showToast(getActivity(), R.string.toast_user_name_exit);
				break;
			default:
				MyToast.showToast(getActivity(), R.string.toast_user_register_error);
				break;
			}
		}
	};

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_register, null);
		initView(rootView);
		return rootView;
	}

	protected void skipToLogin() {
		SPUtils.put(getActivity(), SpKey.USERNAME, userName);
		MyToast.showToast(getActivity(), R.string.toast_user_register_ok);
		((UserLoginActivity) getActivity()).changeContentUI();
		accountEt.setText("");
		emailEt.setText("");
		pwdEt.setText("");
		pwdConfireEt.setText("");

	}

	private void initView(View rootView) {
		accountEt = (EditText) rootView.findViewById(R.id.fr_register_account_et);
		accountEt.setHint(Html.fromHtml("<small>" + getString(R.string.hint_user_name) + "</small>"));

		emailEt = (EditText) rootView.findViewById(R.id.fr_register_pwd_email);
		emailEt.setHint(Html.fromHtml("<small>" + getString(R.string.hint_user_email) + "</small>"));

		pwdEt = (EditText) rootView.findViewById(R.id.fr_register_pwd_et);
		pwdEt.setHint(Html.fromHtml("<small>" + getString(R.string.hint_user_pwd) + "</small>"));

		pwdConfireEt = (EditText) rootView.findViewById(R.id.fr_register_pwd_confire_et);
		pwdConfireEt.setHint(Html.fromHtml("<small>" + getString(R.string.hint_user_pwd_confire) + "</small>"));

		registerBtn = (Button) rootView.findViewById(R.id.fr_register_submit_btn);
		registerBtn.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {

		if (!chechInput()) {
			return;
		}
		submitRegister();
	}

	private void submitRegister() {
		MyProgressDialog.showProgressDialog(getActivity(), getString(R.string.dialog_register_fr));
		AppUtils.hintInputMethod(getActivity());
		if (!chechInput()) {// 输入校验不通过
			return;
		}

		String channelId = (String) SPUtils.get(getActivity(), SpKey.CHANNELID, "local");
		RequestTool.userRegister(channelId, AppUtils.getUniqueId(getActivity()), userName, email, pwd,
				System.currentTimeMillis(), registerBackJson, REGISTER_TAG);
	}

	private boolean chechInput() {
		userName = accountEt.getText().toString();
		email = emailEt.getText().toString();
		pwd = pwdEt.getText().toString();
		cofirePwd = pwdConfireEt.getText().toString();

		if (!checkAccount()) {// 用户名校验不通过
			MyProgressDialog.closeProgressDialog();
			MyToast.showToast(getActivity(), R.string.toast_user_name_enpty);
			return false;
		}

		if (!checkEmail()) {// 邮箱校验不通过
			MyProgressDialog.closeProgressDialog();
			MyToast.showToast(getActivity(), R.string.toast_user_email_error);
			return false;
		}

		if (!checkPwd()) {// 密码校验不通过
			MyProgressDialog.closeProgressDialog();
			MyToast.showToast(getActivity(), R.string.toast_user_pwd_error);
			return false;
		}

		return true;
	}

	private boolean checkPwd() {
		if (TextUtils.isEmpty(pwd) || pwd.length() < 6 || pwd.length() > 16 || TextUtils.isEmpty(cofirePwd)
				|| !cofirePwd.equals(pwd)) {
			return false;
		}
		return true;
	}

	private boolean checkEmail() {
		if (TextUtils.isEmpty(email) || !AppUtils.checkRegular(AppUtils.EMAIL, email)) {
			return false;
		}
		return true;

	}

	private boolean checkAccount() {
		if (TextUtils.isEmpty(userName) || userName.length() < 3 || userName.length() > 10) {
			return false;
		}
		return true;
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
		NetClient.getInstance().cancelRequest(REGISTER_TAG);
	}
}
