package com.jk.base.app;

import com.apkfuns.logutils.LogLevel;
import com.apkfuns.logutils.LogUtils;

import android.app.Application;

public class App extends Application {

	@Override
	public void onCreate() {
		super.onCreate();
		initLogUtils();
		initAppCrashHandler();
	}

	/**
	 * 初始化日志
	 */
	private void initLogUtils() {
		LogUtils.getLogConfig()
		        .configAllowLog(true)//是否允许日志输出
		        .configTagPrefix("LogUtils-")//日志log的前缀
		        .configShowBorders(true)//	是否显示边界
		        .configLevel(LogLevel.TYPE_INFO);//日志显示等级
	}

	/**
	 * 配置app崩溃处理
	 */
	private void initAppCrashHandler() {
		AppCrashHandler mCrashHandler = AppCrashHandler.getInstance();
		mCrashHandler.setCustomCrashHanler(getApplicationContext());
	}

}
