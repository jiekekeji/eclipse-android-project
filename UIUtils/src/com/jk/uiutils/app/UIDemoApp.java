package com.jk.uiutils.app;

import com.apkfuns.logutils.LogLevel;
import com.apkfuns.logutils.LogUtils;

import android.app.Application;

public class UIDemoApp extends Application {

	@Override
	public void onCreate() {
		super.onCreate();
		initLogUtils();
	}

	/**
	 * 初始化日志
	 */
	private void initLogUtils() {
		LogUtils.getLogConfig().configAllowLog(true)// 是否允许日志输出
				.configTagPrefix("LogUtils-")// 日志log的前缀
				.configShowBorders(true)// 是否显示边界
				.configLevel(LogLevel.TYPE_INFO);// 日志显示等级
	}
}
