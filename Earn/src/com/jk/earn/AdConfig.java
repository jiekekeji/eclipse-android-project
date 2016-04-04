package com.jk.earn;

import net.youmi.android.offers.OffersManager;
import android.app.Activity;
import android.content.Context;

import com.bb.dd.BeiduoPlatform;
import com.dc.wall.DianCai;

public class AdConfig {

	// 有米

	public static final String YoumiAppId = "482a31ff1f3bc7e3";
	public static final String YoumiAppSecret = "6a18fad7fa7d0692";

	public static void initYouMiAD(Context context, String userId) {
		net.youmi.android.AdManager.getInstance(context).init(AdConfig.YoumiAppId, AdConfig.YoumiAppSecret, false);
		OffersManager.setUsingServerCallBack(true);
		OffersManager.getInstance(context).setCustomUserId(userId);
		// 如果使用积分广告，请务必调用积分广告的初始化接口:
		OffersManager.getInstance(context).onAppLaunch();
	}

	public static void destoryYouMiAD(Context context) {
		OffersManager.getInstance(context).onAppExit();
	}

	public static void initYouMiVidoAD(Context context, String userId) {
		net.youmi.android.AdManager.getInstance(context).init(AdConfig.YoumiAppId, AdConfig.YoumiAppSecret, false);
	}

	public static void destoryYouMiVidoAD(Context context) {
	}

	// 贝多

	public static final String BeiduoAppId = "14114";
	public static final String BeiduoAppSecret = "15100101e7f1112";

	public static void initBeiDuo(Context context, String userId) {
		BeiduoPlatform.setAppId(context, BeiduoAppId, BeiduoAppSecret);
		BeiduoPlatform.setUserId(userId);
	}

	// 麒点
//	public static final String QiDianAppId = "234";
//
//	public static void initQiDian(Activity context) {
//		QDOpenWall.setAppId(context, QiDianAppId);
//		QDOpenWall.initServiceType(context);
//	}

	// 赢告
//	public static final String YingGaoAppSecret = "1439A8CED95DF6357F0C30EE2AC4FBE31C2573E7";
//
//	public static void initYingGao(Activity context, String userId) {
//		AdManager.setAPPID(context, YingGaoAppSecret);
//		AdManager.setUserID(context, userId);
//	}

	// 点财
	public static final String DianChaiAppId = "13060";
	public static final String DianChaiAppSecret = "7ba6ca592f2d4e638c7254e9b13ea6c4";

	public static void initDianChai(Activity context, String userId) {
		DianCai.initApp(context, DianChaiAppId, DianChaiAppSecret);
		DianCai.setUserId(userId);
	}

	// 中艺
	// public static final String ZhongYiAppSecret = "b4281013de1ab9de";
	//
	// public static void initZhongYi(Activity context, String userId) {
	// SDKInit.initAd(context, ZhongYiAppSecret, userId);
	// }

}
