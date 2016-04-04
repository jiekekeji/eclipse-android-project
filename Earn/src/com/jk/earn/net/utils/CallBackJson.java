package com.jk.earn.net.utils;

import java.io.IOException;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

public abstract class CallBackJson<T> implements Callback {

	private static final String TAG = CallBackJson.class.getSimpleName();
	private Class<T> clazz;
	private Request req;
	private IOException exception;

	private Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			if ("error".equals(msg.obj.toString())) {
				onError(req, exception);
			} else {
				try {
					T t = JSON.parseObject((String) msg.obj, clazz);
					onScucces(t);
				} catch (Exception e) {
					Log.e(TAG, "parse json error", e);
				}
			}

		};
	};

	public CallBackJson(Class<T> clazz) {
		this.clazz = clazz;
	}

	/**
	 * 失败请求的回到
	 */
	public abstract void onError(Request req, IOException exception);

	/**
	 * 成功请求的回到
	 */
	public abstract void onScucces(T response);

	@Override
	public void onFailure(Request req, IOException exception) {
		this.req = req;
		this.exception = exception;

		Message msg = Message.obtain();
		msg.obj = "error";
		mHandler.sendMessage(msg);
	}

	@Override
	public void onResponse(Response resp) throws IOException {
		if (resp.isSuccessful()) {
			String json = resp.body().string();
			Log.i(TAG, json);
			Message msg = Message.obtain();
			msg.obj = json;
			mHandler.sendMessage(msg);
		}
	}

}
