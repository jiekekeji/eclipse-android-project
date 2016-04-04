package com.jk.earn.utils;

import java.util.regex.Pattern;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import com.jk.earn.constant.SpKey;
import com.jk.earn.net.bean.UserInfo;

/**
 * 与app相关的的辅助类
 * 
 * @author 杰科
 * 
 */
public class AppUtils {

	/**
	 * 获取应用程序版本名称信息
	 * 
	 * @param context
	 * @return String 成功返回app当前版本号，否则返回null
	 */
	public static String getVersionName(Context context) {
		try {
			PackageManager packageManager = context.getPackageManager();
			PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
			return packageInfo.versionName;

		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 打开网络设置界面
	 */
	public static void openSetting(Activity activity) {

		activity.startActivityForResult(new Intent(android.provider.Settings.ACTION_SETTINGS), 0);
		// Intent intent = new Intent("/");
		// ComponentName cm = new ComponentName("com.android.settings",
		// "com.android.settings.WirelessSettings");
		// intent.setComponent(cm);
		// intent.setAction("android.intent.action.VIEW");
		// activity.startActivityForResult(intent, 0);
	}

	/*** 打开联系人 */
	public static void openConstact(Activity activity) {
		Intent intent = new Intent();
		intent.setClassName("com.android.contacts", "com.android.contacts.activities.PeopleActivity");
		// intent.setType(ContactsContract.Contacts.CONTENT_TYPE);
		activity.startActivity(intent);

	}

	// /**
	// * 弹出toast
	// *
	// * @param ac
	// * @param text
	// */
	// public static void myToast(Context context, String text) {
	// MyToast.showToast(context, text, Toast.LENGTH_SHORT);
	// }

	public static final String EMAIL = "([0-9A-Za-z\\-_\\.]+)@([0-9a-z]+\\.[a-z]{2,3}(\\.[a-z]{2})?)";

	/**
	 * 正则匹配
	 * 
	 * @param regular
	 *            匹配规则
	 * @param content
	 *            需要匹配的字符
	 * @return boolean true,匹配成功。false，匹配失败
	 */
	public static boolean checkRegular(String regular, String content) {
		return Pattern.compile(regular).matcher(content).find();
	}

	/**
	 * 获取设备的唯一标示 deviceId+macAddress
	 * 
	 * @param context
	 * @return deviceId+macAddress
	 */
	public static String getUniqueId(Context context) {
		String deMacId = (String) SPUtils.get(context, "DeMacId", null);
		if (deMacId == null) {
			TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);

			String imei = tm.getDeviceId();
			deMacId = imei + getMacAddress(context);
			SPUtils.put(context, "DeMacId", deMacId);
		}
		return deMacId;
	}

	/**
	 * 获取手机mac地址<br/>
	 * 错误返回12个0
	 */
	public static String getMacAddress(Context context) {
		// 获取mac地址：
		String macAddress = "000000000000";
		try {
			WifiManager wifiMgr = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
			WifiInfo info = (null == wifiMgr ? null : wifiMgr.getConnectionInfo());
			if (null != info) {
				if (!TextUtils.isEmpty(info.getMacAddress()))
					macAddress = info.getMacAddress().replace(":", "");
				else
					return macAddress;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return macAddress;
		}
		return macAddress;
	}

	/**
	 * 手机厂商
	 * 
	 * @return
	 */
	public static String getPhoneType() {
		return android.os.Build.MODEL;
	}

	/**
	 * android系统的版本
	 * 
	 * @return
	 */
	public static String getSysCode() {
		return android.os.Build.VERSION.RELEASE;
	}

	/**
	 * 隐藏输入法
	 */
	public static void hintInputMethod(Context context) {
		InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
	}

	/**
	 * 检测用户是否已登录
	 * 
	 * @return
	 */
	public static UserInfo checkIsLogin(Context context) {
		String userName = (String) SPUtils.get(context, SpKey.USERNAME, "");
		String token = (String) SPUtils.get(context, SpKey.TOKEN, "");

		if (TextUtils.isEmpty(userName) || TextUtils.isEmpty(token)) {
			return null;
		}
		UserInfo info = new UserInfo();
		info.setUserName(userName);
		info.setToken(token);
		return info;
	}

	/**
	 * 清除用户登录信息
	 * 
	 * @param context
	 */
	public static void clearLoginInfo(Context context) {
		SPUtils.put(context, SpKey.USER_EMAIL, "");
		SPUtils.put(context, SpKey.TOKEN, "");
		SPUtils.put(context, SpKey.USER_EMAIL, "");
		SPUtils.put(context, SpKey.USER_POINT, "");
	}

}
