package com.jk.earn.fragment;

import java.io.IOException;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.jk.earn.R;
import com.jk.earn.activity.DialogPwdActivity;
import com.jk.earn.activity.PiontHistoryAcitivity;
import com.jk.earn.activity.UserLoginActivity;
import com.jk.earn.activity.WebViewActivity;
import com.jk.earn.net.NetURL;
import com.jk.earn.net.RequestTool;
import com.jk.earn.net.bean.BaseBean;
import com.jk.earn.net.bean.UserInfo;
import com.jk.earn.net.utils.CallBackJson;
import com.jk.earn.net.utils.NetClient;
import com.jk.earn.utils.AppUtils;
import com.jk.earn.view.MyProgressDialog;
import com.jk.earn.view.MyToast;
import com.squareup.okhttp.Request;

public class UserFragment extends Fragment implements OnClickListener {

	private static final String TAG = UserFragment.class.getSimpleName();
	private static final String TAG_LOGOUT = "tag_logout";

	private static final int USER_REQUEST_LOGIN = 2500;
	private static final int USER_REQUEST_MODIFY_PWD = 2600;

	public static final int POINT_EARN_FLAG = 2601;
	public static final int POINT_EXCHANGE_FLAG = 2602;

	private View rootView;
	private TextView userNameTv;
	private TextView totalPointTv;
	private TextView emailTv;
	private TextView loginStatusTv;

	private UserInfo mInfo;

	CallBackJson<UserInfo> callBackJson = new CallBackJson<UserInfo>(UserInfo.class) {

		@Override
		public void onError(Request req, IOException exception) {
			MyProgressDialog.closeProgressDialog();
		}

		@Override
		public void onScucces(UserInfo response) {
			MyProgressDialog.closeProgressDialog();
			if (100 == response.getCode()) {
				updateUserInfo(response);
			}

		}
	};

	protected void updateUserInfo(UserInfo response) {
		userNameTv.setText(response.getUserName());
		totalPointTv.setText(String.valueOf(response.getPoints()));
		emailTv.setText(response.getEmail());
	}

	CallBackJson<BaseBean> logoutBackJson = new CallBackJson<BaseBean>(BaseBean.class) {

		@Override
		public void onError(Request req, IOException exception) {
			Log.i(TAG, "logoutBackJson error" + exception.toString());
			MyProgressDialog.closeProgressDialog();
			MyToast.showToast(getActivity(), R.string.toat_user_fr_logout_error);
		}

		@Override
		public void onScucces(BaseBean response) {
			Log.i(TAG, "logoutBackJson scucces" + response);
			MyProgressDialog.closeProgressDialog();
			handleLogout(response);
		}
	};

	private void handleLogout(BaseBean response) {
		switch (response.getCode()) {
		case 100:
			clearLoginInfo();
			break;
		case 102:
			clearLoginInfo();
			break;

		default:
			MyToast.showToast(getActivity(), R.string.toat_user_fr_logout_error);
			break;
		}
	}

	protected void clearLoginInfo() {
		AppUtils.clearLoginInfo(getActivity());
		loginStatusTv.setText(getActivity().getString(R.string.fr_user_login_now));
		MyToast.showToast(getActivity(), R.string.toat_user_fr_logout_ok);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		if (null == rootView) {
			rootView = inflater.inflate(R.layout.fragment_user, null);
			initView(rootView);
			refreshData();
		}
		return rootView;
	}

	private void initView(View view) {
		userNameTv = (TextView) view.findViewById(R.id.fr_user_name_tv);
		totalPointTv = (TextView) view.findViewById(R.id.fr_user_totle_point);
		emailTv = (TextView) view.findViewById(R.id.fr_user_email);
		loginStatusTv = (TextView) view.findViewById(R.id.fr_user_login_status_tv);

		view.findViewById(R.id.title_fr_user_refresh).setOnClickListener(this);
		view.findViewById(R.id.fr_user_point_rechange_rl).setOnClickListener(this);
		view.findViewById(R.id.fr_user_point_recoder_rl).setOnClickListener(this);
		view.findViewById(R.id.fr_user_modify_pwd_rl).setOnClickListener(this);
		view.findViewById(R.id.fr_user_login_now_rl).setOnClickListener(this);
		view.findViewById(R.id.fr_user_help_center_rl).setOnClickListener(this);
		view.findViewById(R.id.fr_user_point_helper_qq).setOnClickListener(this);

		ImageView avatarImg = (ImageView) view.findViewById(R.id.fr_user_header_img);
		Glide.with(this).load(R.drawable.icon_user_header).asGif().diskCacheStrategy(DiskCacheStrategy.ALL)
				.placeholder(R.drawable.icon_user_header).into(avatarImg);

	}

	private void refreshData() {
		mInfo = AppUtils.checkIsLogin(getActivity());
		if (null == mInfo) {
			loginStatusTv.setText(getActivity().getString(R.string.fr_user_login_now));
			MyToast.showToast(getActivity(), R.string.toast_user_no_login, Toast.LENGTH_SHORT);
			return;
		}

		loginStatusTv.setText(getActivity().getString(R.string.fr_user_logout_now));
		MyProgressDialog.showProgressDialog(getActivity(), getActivity().getString(R.string.fr_user_refresh_user_info));
		RequestTool.queryUserInfo(mInfo.getUserName(), mInfo.getToken(), callBackJson, TAG);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.fr_user_point_rechange_rl:
			getPointRechargeHistory();
			break;

		case R.id.fr_user_point_recoder_rl:
			getPointEarnHistory();
			break;

		case R.id.fr_user_modify_pwd_rl:
			modifyPwd();
			break;

		case R.id.fr_user_login_now_rl:
			loginOrLogout();
			break;

		case R.id.fr_user_help_center_rl:
			openHelpCenter();
			break;
		case R.id.title_fr_user_refresh:
			refreshData();
			break;

		case R.id.fr_user_point_helper_qq:
			openQQHelper();
			break;
		}
	}

	/**
	 * 打开帮助中心
	 */
	private void openHelpCenter() {

		Intent intent = new Intent(getActivity(), WebViewActivity.class);
		intent.putExtra(WebViewActivity.WEB_URL_KEY, NetURL.Host + NetURL.HELP_CENTER);
		startActivity(intent);
	}

	private void getPointRechargeHistory() {
		mInfo = AppUtils.checkIsLogin(getActivity());
		if (null == mInfo) {// 立即登录
			go2Login();
			return;
		}

		go2PointHistory(POINT_EXCHANGE_FLAG);

	}

	private void getPointEarnHistory() {
		mInfo = AppUtils.checkIsLogin(getActivity());
		if (null == mInfo) {// 立即登录
			go2Login();
			return;
		}

		go2PointHistory(POINT_EARN_FLAG);
	}

	private void go2PointHistory(int flags) {
		Intent intent = new Intent(getActivity(), PiontHistoryAcitivity.class);
		intent.setFlags(flags);
		startActivity(intent);
	}

	public void loginOrLogout() {
		mInfo = AppUtils.checkIsLogin(getActivity());
		if (null == mInfo) {// 立即登录
			go2Login();
			return;
		}

		MyProgressDialog.showProgressDialog(getActivity(), getActivity().getString(R.string.fr_user_cancel_login));
		RequestTool.userLogout(mInfo.getUserName(), mInfo.getToken(), logoutBackJson, TAG_LOGOUT);
	}

	private void modifyPwd() {
		mInfo = AppUtils.checkIsLogin(getActivity());
		if (null == mInfo) {// 立即登录
			go2Login();
			return;
		}

		Intent intent = new Intent(getActivity(), DialogPwdActivity.class);
		startActivityForResult(intent, USER_REQUEST_MODIFY_PWD);
	}

	private void go2Login() {
		MyToast.showToast(getActivity(), R.string.toast_user_login);
		Intent intent = new Intent(getActivity(), UserLoginActivity.class);
		startActivityForResult(intent, USER_REQUEST_LOGIN);
	}

	private void openQQHelper() {
		if (!checkIsQQInstall()) {
			MyToast.showToast(getActivity(), R.string.toast_fr_user_check_qq_install);
			return;
		}

		String url = "mqqwpa://im/chat?chat_type=wpa&uin=3131723431";
		startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
	}

	private boolean checkIsQQInstall() {
		PackageInfo packageInfo = null;
		try {
			String qqPakgName = "com.tencent.mobileqq";
			packageInfo = getActivity().getPackageManager().getPackageInfo(qqPakgName, 0);
		} catch (NameNotFoundException e) {
			packageInfo = null;
			e.printStackTrace();
		}
		if (packageInfo == null) {
			return false;
		} else {
			return true;
		}
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == USER_REQUEST_LOGIN && resultCode == LoginFragment.LOGIN_SCCCESS) {
			refreshData();
			loginStatusTv.setText(getActivity().getString(R.string.fr_user_logout_now));
		}

		if (requestCode == USER_REQUEST_MODIFY_PWD && resultCode == DialogPwdActivity.MODIFY_PWD_SCUCCES) {
			loginStatusTv.setText(getActivity().getString(R.string.fr_user_login_now));
		}
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
		NetClient.getInstance().cancelRequest(TAG_LOGOUT);
		NetClient.getInstance().cancelRequest(TAG);
	}
}
