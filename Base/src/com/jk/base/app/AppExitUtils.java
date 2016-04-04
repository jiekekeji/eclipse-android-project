package com.jk.base.app;

import java.util.LinkedList;
import java.util.List;

import android.app.Activity;
import android.util.Log;

/**
 * android退出程序的工具类，使用单例模式
 * 1.在Activity的onCreate()的方法中调用addActivity()方法添加到mActivityList
 * 2.你可以在Activity的onDestroy()的方法中调用delActivity()来删除已经销毁的Activity实例
 * 这样避免了mActivityList容器中有多余的实例而影响程序退出速度
 * 
 * @author
 * 
 */
public class AppExitUtils {
	private static final String TAG=AppExitUtils.class.getSimpleName();
	/**
	 * 装载Activity的容器
	 */
	private List<Activity> mActivityList = new LinkedList<Activity>();
	private static AppExitUtils instance = new AppExitUtils();

	/**
	 * 将构造函数私有化
	 */
	private AppExitUtils() {
	};

	/**
	 * 获取ExitAppUtils的实例，保证只有一个ExitAppUtils实例存在
	 * 
	 * @return
	 */
	public static AppExitUtils getInstance() {
		return instance;
	}

	/**
	 * 添加Activity实例到mActivityList中，在onCreate()中调用
	 * 
	 * @param activity
	 */
	public void addActivity(Activity activity) {
		Log.i(TAG, "addActivity");
		mActivityList.add(activity);
	}

	/**
	 * 从容器中删除多余的Activity实例，在onDestroy()中调用
	 * 
	 * @param activity
	 */
	public void delActivity(Activity activity) {
		Log.i(TAG, "delActivity");
		mActivityList.remove(activity);
	}

	/**
	 * 退出程序的方法
	 */
	public void exit() {
		for (Activity activity : mActivityList) {
			activity.finish();
		}

		// System.exit(0);

		Log.i("TAG", this.mActivityList.size() + "");
	}

	/**
	 * 返回容器的大小
	 * 
	 * @return
	 */
	public int size() {
		return mActivityList.size();
	}

}
