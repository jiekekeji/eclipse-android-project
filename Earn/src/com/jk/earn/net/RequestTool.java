package com.jk.earn.net;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import com.jk.earn.net.bean.ADSwitch;
import com.jk.earn.net.bean.AppVersion;
import com.jk.earn.net.bean.BaseBean;
import com.jk.earn.net.bean.LoginInfo;
import com.jk.earn.net.bean.OrderUserList;
import com.jk.earn.net.bean.PhoneNumInfo;
import com.jk.earn.net.bean.PhonePoints;
import com.jk.earn.net.bean.PhoneProducts;
import com.jk.earn.net.bean.QQProducts;
import com.jk.earn.net.bean.UserInfo;
import com.jk.earn.net.bean.UserPointsList;
import com.jk.earn.net.utils.CallBackJson;
import com.jk.earn.net.utils.NetClient;
import com.jk.earn.net.utils.ProgressListener;

public class RequestTool {

	/**
	 * 获取最新版本的apk信息
	 * 
	 * @param channelId
	 *            渠道号
	 * @param devicesid
	 *            MD5(设备号devicesId+mac地址)
	 * @param versionCode
	 *            当前的apk版本
	 * @param time
	 *            请求时间
	 * @param callback
	 *            请求结果的回调
	 * @param tag
	 *            本次请求的标签
	 */
	public static void getNewVersion(String channelId, String devicesid, String versionCode, long time,
			CallBackJson<AppVersion> callback, String tag) {
		String sign = MD5Utils.toMD5(versionCode + NetURL.API_KEY);

		Map<String, String> map = new HashMap<String, String>();
		map.put("channelId", channelId);
		map.put("devicesid", devicesid);
		map.put("versionCode", versionCode);
		map.put("sign", sign);
		map.put("time", String.valueOf(time));

		NetClient.getInstance().getFromNetwork(NetURL.Host + NetURL.APP_VERSION, map, callback, tag);
	}

	public static void loadApk(String channelId, String fileName, Long time, CallBackJson<AppVersion> callback,
			String tag) {
		String sign = MD5Utils.toMD5(fileName + NetURL.API_KEY);

		Map<String, String> map = new HashMap<String, String>();
		map.put("channelId", channelId);
		map.put("fileName", fileName);
		map.put("sign", sign);
		map.put("time", String.valueOf(time));

		NetClient.getInstance().getFromNetwork(NetURL.Host + NetURL.LOAD_APK, map, callback, tag);
	}

	/**
	 * 提交安装apk的设备的设备信息
	 * 
	 * @param channelId
	 *            渠道号
	 * @param devicesid
	 *            MD5(设备号+Mac地址)
	 * @param sysVersionCode
	 *            Android系统版本号
	 * @param phoneType
	 *            手机厂商和类型
	 * @param versionCode
	 *            App的版本号
	 * @param time
	 *            当前请求的时间：毫秒值
	 * @param callback
	 *            请求结果的回调
	 * @param tag
	 *            本次请求的标签
	 */
	public static void submitDevicesInfo(String channelId, String devicesid, String sysVersionCode, String phoneType,
			String versionCode, long time, CallBackJson<BaseBean> callback, String tag) {
		String sign = MD5Utils.toMD5(channelId + NetURL.API_KEY);

		Map<String, String> map = new HashMap<String, String>();
		map.put("channelId", channelId);
		map.put("devicesid", devicesid);
		map.put("sysVersionCode", String.valueOf(sysVersionCode));
		map.put("sign", sign);
		map.put("time", String.valueOf(time));
		map.put("phoneType", phoneType);
		map.put("versionCode", versionCode);

		NetClient.getInstance().getFromNetwork(NetURL.Host + NetURL.SUBMIT_DEVICES_INFO, map, callback, tag);
	}

	/**
	 * 用户注册
	 * 
	 * @param channelId
	 * @param devicesid
	 * @param username
	 * @param email
	 * @param password
	 * @param time
	 * @param callback
	 * @param tag
	 */
	public static void userRegister(String channelId, String devicesid, String username, String email, String password,
			long time, CallBackJson<BaseBean> callback, String tag) {
		String sign = MD5Utils.toMD5(username + NetURL.API_KEY);

		Map<String, String> map = new HashMap<String, String>();
		map.put("channelId", channelId);
		map.put("devicesid", devicesid);
		map.put("username", username);
		map.put("email", email);
		map.put("password", password);
		map.put("sign", sign);
		map.put("time", String.valueOf(time));

		NetClient.getInstance().getFromNetwork(NetURL.Host + NetURL.USER_REGISTER, map, callback, tag);
	}

	/**
	 * 检测用户名是否存在
	 * 
	 * @param username
	 * @param callback
	 * @param tag
	 */
	public static void checkUserName(String username, CallBackJson<BaseBean> callback, String tag) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("username", username);
		NetClient.getInstance().getFromNetwork(NetURL.Host + NetURL.CHECK_USERNAME, map, callback, tag);
	}

	/**
	 * 修改登陆密码
	 * 
	 * @param username
	 * @param password
	 *            新密码
	 * @param token
	 *            登陆成功之后的令牌
	 * @param callback
	 * @param tag
	 */
	public static void modifyPasswd(String username, String password, String token, CallBackJson<BaseBean> callback,
			String tag) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("username", username);
		map.put("password", password);
		map.put("token", token);

		NetClient.getInstance().getFromNetwork(NetURL.Host + NetURL.MODIFY_PASSWORD, map, callback, tag);
	}

	/**
	 * 用户登录
	 * 
	 * @param username
	 * @param password
	 * @param callback
	 * @param tag
	 */
	public static void userLogin(String username, String password, CallBackJson<LoginInfo> callback, String tag) {
		String sign = MD5Utils.toMD5(username + NetURL.API_KEY);

		Map<String, String> map = new HashMap<String, String>();
		map.put("username", username);
		map.put("password", password);
		map.put("sign", sign);

		NetClient.getInstance().getFromNetwork(NetURL.Host + NetURL.USER_LOGIN, map, callback, tag);
	}

	/**
	 * 注销登录
	 * 
	 * @param username
	 * @param token
	 * @param callback
	 * @param tag
	 */
	public static void userLogout(String username, String token, CallBackJson<BaseBean> callback, String tag) {
		String sign = MD5Utils.toMD5(username + NetURL.API_KEY);

		Map<String, String> map = new HashMap<String, String>();
		map.put("username", username);
		map.put("token", token);
		map.put("sign", sign);

		NetClient.getInstance().getFromNetwork(NetURL.Host + NetURL.USER_LOGOUT, map, callback, tag);
	}

	/**
	 * 找回密码
	 * 
	 * @param username
	 * @param email
	 * @param callback
	 * @param tag
	 */
	public static void getBackPasswd(String username, String email, CallBackJson<BaseBean> callback, String tag) {
		String sign = MD5Utils.toMD5(username + NetURL.API_KEY);

		Map<String, String> map = new HashMap<String, String>();
		map.put("username", username);
		map.put("email", email);
		map.put("sign", sign);

		NetClient.getInstance().getFromNetwork(NetURL.Host + NetURL.FIND_BACK_PASSWORD, map, callback, tag);
	}

	/**
	 * 获取QQ产品列表
	 * 
	 * @param callback
	 * @param tag
	 */
	public static void getQQProduct(CallBackJson<QQProducts> callback, String tag) {
		NetClient.getInstance().getFromNetwork(NetURL.Host + NetURL.QQ_PRODUCT, callback, tag);
	}

	/**
	 * 提交QQ产品兑换请求
	 * 
	 * @param username
	 * @param channelId
	 * @param token
	 * @param productId
	 * @param productName
	 * @param buyNum
	 * @param price
	 * @param point
	 * @param account
	 * @param callback
	 * @param tag
	 */
	public static void qqSubmitRecharge(String username, String channelId, String token, String productId,
			String productName, String buyNum, String price, String point, String account,
			CallBackJson<BaseBean> callback, String tag) {

		Map<String, String> map = new HashMap<String, String>();
		map.put("username", username);
		map.put("channelId", channelId);
		map.put("token", token);

		map.put("productId", productId);
		map.put("productName", productName);
		map.put("buyNum", buyNum);

		map.put("price", price);
		map.put("point", point);
		map.put("account", account);

		NetClient.getInstance().getFromNetwork(NetURL.Host + NetURL.QQ_SUBMITRECHARGE, map, callback, tag);
	}

	/**
	 * 获取可兑换的话费面额
	 * 
	 * @param callback
	 * @param tag
	 */
	public static void getPhoneProduct(CallBackJson<PhoneProducts> callback, String tag) {
		NetClient.getInstance().getFromNetwork(NetURL.Host + NetURL.PHONE_PRODUCT, callback, tag);
	}

	/**
	 * 根据手机号和充值面额获取对应的兑换积分
	 * 
	 * @param username
	 * @param token
	 * @param phoneNum
	 * @param cardWorth
	 * @param callback
	 * @param tag
	 */
	public static void getPonitByCardWorth(String username, String token, String phoneNum, int cardWorth,
			CallBackJson<PhonePoints> callback, String tag) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("username", username);
		map.put("phoneNum", phoneNum);
		map.put("token", token);
		map.put("cardWorth", String.valueOf(cardWorth));

		NetClient.getInstance().getFromNetwork(NetURL.Host + NetURL.PONIT_CARDWORTH, map, callback, tag);
	}

	/**
	 * 提交话费面额兑换请求
	 * 
	 * @param username
	 * @param token
	 * @param phoneNum
	 * @param cardWorth
	 * @param point
	 * @param price
	 * @param sign
	 * @param callback
	 * @param tag
	 */
	public static void phSubmitRecharge(String username, String token, String phoneNum, Integer cardWorth, Float point,
			String productId, String channelId, CallBackJson<BaseBean> callback, String tag) {
		String sign = MD5Utils.toMD5(username + phoneNum + cardWorth + point + NetURL.API_KEY);
		Map<String, String> map = new HashMap<String, String>();
		map.put("username", username);
		map.put("phoneNum", phoneNum);
		map.put("token", token);
		map.put("cardWorth", String.valueOf(cardWorth));

		map.put("point", String.valueOf(point));
		map.put("productId", productId);
		map.put("channelId", channelId);
		map.put("sign", sign);

		NetClient.getInstance().getFromNetwork(NetURL.Host + NetURL.PHONE_SUBMITRECHARGE, map, callback, tag);
	}

	/**
	 * 查询用户的积分使用记录和赚取积分的记录
	 * 
	 * @param username
	 * @param token
	 * @param page
	 * @param limit
	 * @param callback
	 * @param tag
	 */
	public static void queryPointsRecoder(String username, String token, int page, int limit,
			CallBackJson<UserPointsList> callback, String tag) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("userName", username);
		map.put("page", String.valueOf(page));
		map.put("limit", String.valueOf(limit));
		map.put("token", token);

		NetClient.getInstance().getFromNetwork(NetURL.Host + NetURL.QUERY_POINTSRECODER, map, callback, tag);
	}

	/**
	 * 查询积分兑换的记录
	 * 
	 * @param username
	 * @param token
	 * @param page
	 * @param limit
	 * @param callback
	 * @param tag
	 */
	public static void queryUserOrder(String username, String token, int page, int limit,
			CallBackJson<OrderUserList> callback, String tag) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("userName", username);
		map.put("page", String.valueOf(page));
		map.put("limit", String.valueOf(limit));
		map.put("token", token);

		NetClient.getInstance().getFromNetwork(NetURL.Host + NetURL.QUERY_USERORDER, map, callback, tag);
	}

	/**
	 * 获取手机号归属地信息
	 * 
	 * @param phoneNum
	 * @param callback
	 * @param tag
	 */
	public static void queryPhoneNumInfo(String phoneNum, CallBackJson<PhoneNumInfo> callback, String tag) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("phoneNum", phoneNum);
		NetClient.getInstance().getFromNetwork(NetURL.Host + NetURL.PHONE_NUM_INFO, map, callback, tag);
	}

	/**
	 * 获取用户信息
	 * 
	 * @param phoneNum
	 * @param callback
	 * @param tag
	 */
	public static void queryUserInfo(String userName, String token, CallBackJson<UserInfo> callback, String tag) {
		String sign = MD5Utils.toMD5(userName + NetURL.API_KEY);
		Map<String, String> map = new HashMap<String, String>();
		map.put("userName", userName);
		map.put("token", token);
		map.put("sign", sign);
		NetClient.getInstance().getFromNetwork(NetURL.Host + NetURL.QUERY_USER_INFO, map, callback, tag);
	}

	/**
	 * 查询广告的状态：开启或关闭
	 * 
	 * @param callback
	 * @param tag
	 */
	public static void queryAdStatus(CallBackJson<ADSwitch> callback, String tag) {
		NetClient.getInstance().getFromNetwork(NetURL.Host + NetURL.AD_SWITCH, callback, tag);
	}

	/**
	 * 上传异常日志
	 * 
	 * @param file
	 * @param listener
	 * @param tag
	 */
	public static void uploadLog(File file, ProgressListener listener, String tag) {
		NetClient.getInstance().upLoad(NetURL.Host + NetURL.LOG_UPLOAD, file, tag, listener);
	}
}
