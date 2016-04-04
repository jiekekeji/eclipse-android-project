package com.jk.earn.service;

import java.io.IOException;

import android.app.DownloadManager;
import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.jk.earn.AppConstant;
import com.jk.earn.net.NetURL;
import com.jk.earn.net.RequestTool;
import com.jk.earn.net.bean.AppVersion;
import com.jk.earn.net.bean.BaseBean;
import com.jk.earn.net.load.DownLoadUtils;
import com.jk.earn.net.utils.CallBackJson;
import com.jk.earn.utils.AppUtils;
import com.jk.earn.utils.SPUtils;
import com.jk.earn.view.SingleTextDialog;
import com.jk.earn.view.SingleTextDialog.CancelClickListener;
import com.jk.earn.view.SingleTextDialog.ConfireClickListener;
import com.squareup.okhttp.Request;

public class CheckLoginService extends IntentService {

	private static final String TAG = CheckLoginService.class.getSimpleName();

	/**
	 * 监听设备提交的状态
	 */
	private CallBackJson<BaseBean> callBackJson = new CallBackJson<BaseBean>(BaseBean.class) {

		@Override
		public void onError(Request req, IOException exception) {
		}

		@Override
		public void onScucces(BaseBean response) {
			// handleResponse(response);
		}
	};

	// 必须实现
	public CheckLoginService() {
		super("CheckLoginService");
	}

	@Override
	public void onCreate() {
		super.onCreate();
		Log.i(TAG, "service create");
	}

	/**
	 * 异步执行
	 */
	@Override
	protected void onHandleIntent(Intent intent) {
		Log.i(TAG, "service run");

	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}

}
