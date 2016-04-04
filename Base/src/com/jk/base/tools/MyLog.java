package com.jk.base.tools;

import android.util.Log;

/**
 * 日志工具类
 * 
 * @author Administrator
 * 
 */
public class MyLog {

	/**
	 * info级别日志
	 * 
	 * @param obj
	 *            当前的实例
	 * @param msg
	 *            日志信息
	 */
	public static void i(Object obj, String msg) {
		Log.i(obj.getClass().getSimpleName(), "" + msg);
	}

	/**
	 * error级别日志
	 * 
	 * @param obj
	 *            当前的实例
	 * @param msg
	 *            日志信息
	 */
	public static void e(Object obj, String msg) {
		Log.e(obj.getClass().getSimpleName(), "" + msg);
	}

	/**
	 * debug级别日志
	 * 
	 * @param obj
	 *            当前的实例
	 * @param msg
	 *            日志信息
	 */
	public static void d(Object obj, String msg) {
		Log.d(obj.getClass().getSimpleName(), "" + msg);
	}

	/**
	 * info级别日志
	 * 
	 * @param obj
	 *            当前的实例
	 * @param msg
	 *            日志信息
	 */
	public static void i(Class clz, String msg) {
		Log.i(clz.getSimpleName(), "" + msg);
	}

	/**
	 * error级别日志
	 * 
	 * @param obj
	 *            当前的实例
	 * @param msg
	 *            日志信息
	 */
	public static void e(Class clz, String msg) {
		Log.e(clz.getSimpleName(), "" + msg);
	}

	/**
	 * debug级别日志
	 * 
	 * @param obj
	 *            当前的实例
	 * @param msg
	 *            日志信息
	 */
	public static void d(Class clz, String msg) {
		Log.d(clz.getSimpleName(), "" + msg);
	}
}
