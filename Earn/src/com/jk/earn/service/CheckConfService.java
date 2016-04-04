package com.jk.earn.service;

import java.io.IOException;

import android.app.DownloadManager;
import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.jk.earn.AppConstant;
import com.jk.earn.AppExitUtils;
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

public class CheckConfService extends IntentService {

	private static final String TAG = CheckConfService.class.getSimpleName();

	/**
	 * 检测apk是否是第一次安装
	 */
	private static final String ISFIRSTINSTALL_KEY = "isFirstInstall";

	/**
	 * 监听设备提交的状态
	 */
	private CallBackJson<BaseBean> callBackJson = new CallBackJson<BaseBean>(BaseBean.class) {

		@Override
		public void onError(Request req, IOException exception) {
		}

		@Override
		public void onScucces(BaseBean response) {
			handleResponse(response);
		}
	};

	/**
	 * 监听是否需要更新apk的版本
	 */
	private CallBackJson<AppVersion> callBackappVersion = new CallBackJson<AppVersion>(AppVersion.class) {

		@Override
		public void onError(Request req, IOException exception) {
		}

		@Override
		public void onScucces(AppVersion response) {
			handleAppVersion(response);
		}
	};

	/**
	 * 处理返回的app版本信息
	 * 
	 * @param response
	 */
	protected void handleAppVersion(final AppVersion response) {
		if (102 == response.getCode()) {// 有新版本更新

			if (null == AppExitUtils.getInstance() || AppExitUtils.getInstance().size() == 0) {
				// activity已全部退出
				return;
			}

			final SingleTextDialog dialog = new SingleTextDialog(response.getVersionDesc(), CheckConfService.this);
			dialog.setonCancelClickListener(new CancelClickListener() {

				@Override
				public void onCancel() {
					dialog.dissmissDialog();
				}
			});

			dialog.setonConfireClickListener(new ConfireClickListener() {

				@Override
				public void onConfire() {
					dialog.dissmissDialog();
					startDownLoad(response.getFileName());
				}
			});
			dialog.showDialog();
		}
	}

	private void startDownLoad(String fileName) {
		String url = NetURL.Host + fileName;
		DownloadManager downloadManager = (DownloadManager) this.getSystemService(Context.DOWNLOAD_SERVICE);
		DownLoadUtils.startDownLoad(url, downloadManager, CheckConfService.this);
	}

	// 必须实现
	public CheckConfService() {
		super("CheckConfService");
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
		checkInstall();

		try {
			Thread.currentThread().sleep(1000 * 6);
			checkAppVersion(this);
		} catch (InterruptedException e) {
			Log.e(TAG, "服务线程异常", e);
		}
	}

	/**
	 * 检查是否是第一次安装
	 */
	private void checkInstall() {
		boolean isFirstInstall = (Boolean) SPUtils.get(this, ISFIRSTINSTALL_KEY, true);

		if (isFirstInstall) {
			// 发送手机信息
			sendInstallInfo(this);
		}
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}

	public void sendInstallInfo(Context context) {
		RequestTool.submitDevicesInfo(AppConstant.CHANNELID, AppUtils.getUniqueId(context), AppUtils.getSysCode(),
				AppUtils.getPhoneType(), AppUtils.getVersionName(context), System.currentTimeMillis(), callBackJson,
				"submitDevicesInfo");
	}

	/**
	 * 处理返回的结果
	 */
	protected void handleResponse(BaseBean response) {
		switch (response.getCode()) {
		case 101:// 设备已存在，无需再提交
			SPUtils.put(this, ISFIRSTINSTALL_KEY, false);
			break;
		case 102:// 提交失败
			break;
		case 100:// 提交成功
			SPUtils.put(this, ISFIRSTINSTALL_KEY, false);
			break;
		case 103:// 请求参数不合法
			break;
		}
	}

	/**
	 * 检测是否有新版本更新
	 * 
	 * @param context
	 */
	private void checkAppVersion(Context context) {
		RequestTool.getNewVersion(AppConstant.CHANNELID, AppUtils.getUniqueId(context),
				AppUtils.getVersionName(context), System.currentTimeMillis(), callBackappVersion, "submitDevicesInfo");
	}

}
