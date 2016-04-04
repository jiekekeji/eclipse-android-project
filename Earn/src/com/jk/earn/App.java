package com.jk.earn;

import net.youmi.android.AdManager;
import android.app.Application;
import cn.jpush.android.api.JPushInterface;

public class App extends Application {

	@Override
	public void onCreate() {
		super.onCreate();

		initAppCrashHandler();
		initJPUSH();
	}

	/**
	 * 初始化消息推送
	 */
	private void initJPUSH() {
		// 设置开启日志,发布时请关闭日志
		JPushInterface.setDebugMode(true);
		JPushInterface.init(this); // 初始化 JPush
	}

	/**
	 * 配置app崩溃处理
	 */
	private void initAppCrashHandler() {
		AppCrashHandler mCrashHandler = AppCrashHandler.getInstance();
		mCrashHandler.setCustomCrashHanler(getApplicationContext());
	}

}
