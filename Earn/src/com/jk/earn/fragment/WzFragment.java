package com.jk.earn.fragment;

import java.io.IOException;

import net.youmi.android.offers.OffersBrowserConfig;
import net.youmi.android.offers.OffersManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bb.dd.BeiduoPlatform;
import com.dc.wall.DianCai;
import com.jk.earn.AdConfig;
import com.jk.earn.R;
import com.jk.earn.activity.UserLoginActivity;
import com.jk.earn.activity.WebViewActivity;
import com.jk.earn.constant.SpKey;
import com.jk.earn.net.NetURL;
import com.jk.earn.net.RequestTool;
import com.jk.earn.net.bean.ADSwitch;
import com.jk.earn.net.utils.CallBackJson;
import com.jk.earn.net.utils.NetClient;
import com.jk.earn.utils.AppUtils;
import com.jk.earn.utils.SPUtils;
import com.jk.earn.view.MyProgressDialog;
import com.jk.earn.view.MyToast;
import com.squareup.okhttp.Request;
import com.volley.tools.video.tbwy;
import com.volley.tools.video.listener.tbvy;

public class WzFragment extends Fragment {

	private static final String TAG = WzFragment.class.getSimpleName();
	private View rootView;
	private View adContentView;

	CallBackJson<ADSwitch> backJson = new CallBackJson<ADSwitch>(ADSwitch.class) {

		@Override
		public void onError(Request req, IOException exception) {
			MyProgressDialog.closeProgressDialog();
			MyToast.showToast(getActivity(), R.string.toast_fr_wz_load_ad_error);
		}

		@Override
		public void onScucces(ADSwitch response) {
			MyProgressDialog.closeProgressDialog();
			switch (response.getCode()) {
			case 100:
				if (response.getOac() == 1) {
					adContentView.setVisibility(View.VISIBLE);
				} else {
					MyToast.showToast(getActivity(), R.string.toast_fr_wz_load_ad_turn_off);
				}

				break;
			default:
				MyToast.showToast(getActivity(), R.string.toast_fr_wz_load_ad_error);
				break;
			}

		}
	};

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		if (null == rootView) {
			rootView = inflater.inflate(R.layout.fragment_wz, null);
			adContentView = rootView.findViewById(R.id.ad_content_view);
			adContentView.setVisibility(View.GONE);
			rootView.findViewById(R.id.youmi_ad).setOnClickListener(mListener);
			rootView.findViewById(R.id.beiduo_ad).setOnClickListener(mListener);
			// rootView.findViewById(R.id.qidian_ad).setOnClickListener(mListener);
			// rootView.findViewById(R.id.yinggao_ad).setOnClickListener(mListener);
			rootView.findViewById(R.id.diancai_ad).setOnClickListener(mListener);
			// rootView.findViewById(R.id.zhongyi_ad).setOnClickListener(mListener);
			rootView.findViewById(R.id.youmi_vidio_ad).setOnClickListener(mListener);
			rootView.findViewById(R.id.youmi_share_ad).setOnClickListener(mListener);

			rootView.findViewById(R.id.task_help_center_imgv).setOnClickListener(mListener);
			initData();
		}

		return rootView;
	}

	OnClickListener mListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			witchOnclick(v);
		}
	};

	/**
	 * 获取广告状态
	 */
	private void initData() {
		MyProgressDialog.showProgressDialog(getActivity(), R.string.dialog_fr_wz_load_dialog);
		RequestTool.queryAdStatus(backJson, TAG);

	}

	protected void witchOnclick(View v) {
		switch (v.getId()) {
		case R.id.youmi_ad:
			openYoumiAD();
			break;
		case R.id.beiduo_ad:
			openBeiduoAD();
			break;
		// case R.id.qidian_ad:
		// openQiDianAD();
		// break;
		// case R.id.yinggao_ad:
		// openYingGaoAD();
		// break;
		case R.id.diancai_ad:
			openDianCaiAD();
			break;
		// case R.id.zhongyi_ad:
		// openZhongYiAD();
		// break;
		case R.id.youmi_vidio_ad:
			openYoumiVidioAD();
			break;
		case R.id.youmi_share_ad:
			oepnShareAD();
			break;
		case R.id.task_help_center_imgv:
			openTaskHelpCenter();
			break;
		}
	}

	// 打开有米
	private void openYoumiAD() {
		if (!checkIsLogin()) {
			return;
		}
		// 登录成功，初始化广告列表
		AdConfig.initYouMiAD(getActivity(), SPUtils.get(getActivity(), SpKey.USERNAME, "jack").toString());
		OffersBrowserConfig.setBrowserTitleText("有米任务");
		OffersBrowserConfig.setPointsLayoutVisibility(false);
		OffersManager.getInstance(getActivity()).showOffersWall();
	}

	// 打开贝多
	private void openBeiduoAD() {
		if (!checkIsLogin()) {
			return;
		}
		AdConfig.initBeiDuo(getActivity(), SPUtils.get(getActivity(), SpKey.USERNAME, "jack").toString());
		BeiduoPlatform.showOfferWall(getActivity());
	}

	// 打开麒点
	// private void openQiDianAD() {
	// if (!checkIsLogin()) {
	// return;
	// }
	// AdConfig.initQiDian(getActivity());
	// QDOpenWall.getInstance().show(SPUtils.get(getActivity(), SpKey.USERNAME,
	// "jack").toString());
	// }

	// 打开赢告
	// private void openYingGaoAD() {
	// if (!checkIsLogin()) {
	// return;
	// }
	// AdConfig.initYingGao(getActivity(), SPUtils.get(getActivity(),
	// SpKey.USERNAME, "jack").toString());
	// AdManager.showAdOffers(getActivity());
	//
	// }

	// 打开点财
	private void openDianCaiAD() {
		if (!checkIsLogin()) {
			return;
		}
		AdConfig.initDianChai(getActivity(), SPUtils.get(getActivity(), SpKey.USERNAME, "jack").toString());
		DianCai.showOfferWall();
	}

	// 打开中艺
	// private void openZhongYiAD() {
	// if (!checkIsLogin()) {
	// return;
	// }
	// AdConfig.initZhongYi(getActivity(), SPUtils.get(getActivity(),
	// SpKey.USERNAME, "jack").toString());
	// SDKInit.initAdList(getActivity());
	// }

	// 打开有米视频广告
	private void openYoumiVidioAD() {
		tbwy.getInstance(getActivity()).bbfg(getActivity(), videoListener);
	}

	tbvy videoListener = new tbvy() {

		// 视频播放失败
		@Override
		public void onVideoPlayFail() {
			MyToast.showToast(getActivity(), R.string.ad_vidio_play_fail);
		}

		// 视频播放完成
		@Override
		public void onVideoPlayComplete() {
			Log.d("videoPlay", "complete");
			MyToast.showToast(getActivity(), R.string.ad_vidio_complete);
		}

		// 视频播放中途退出
		@Override
		public void onVideoPlayInterrupt() {
			MyToast.showToast(getActivity(), R.string.ad_vidio_exit);
		}

		@Override
		public void onDownloadComplete(String id) {
			Log.e("videoPlay", "download complete: " + id);
		}

		@Override
		public void onNewApkDownloadStart() {
			Log.e("videoPlay", "开始下载");
		}

		@Override
		public void onVideoLoadComplete() {
			Log.e("videoPlay", "视频加载完成");
		}
	};

	// 打开有米分享任务
	private void oepnShareAD() {
		if (!checkIsLogin()) {
			return;
		}
		// 登录成功，初始化广告列表
		AdConfig.initYouMiAD(getActivity(), SPUtils.get(getActivity(), SpKey.USERNAME, "jack").toString());
		OffersBrowserConfig.setBrowserTitleText("分享任务");
		OffersBrowserConfig.setPointsLayoutVisibility(false);
		OffersManager.getInstance(getActivity()).showShareWall();
	}

	/**
	 * 打开任务帮助中心
	 */
	private void openTaskHelpCenter() {
		Intent intent = new Intent(getActivity(), WebViewActivity.class);
		intent.putExtra(WebViewActivity.WEB_URL_KEY, NetURL.Host + NetURL.TASK_HELP_CENTER);
		startActivity(intent);
	}

	private boolean checkIsLogin() {
		if (null == AppUtils.checkIsLogin(getActivity())) {
			MyToast.showToast(getActivity(), R.string.toast_user_login, Toast.LENGTH_SHORT);
			startActivity(new Intent(getActivity(), UserLoginActivity.class));
			return false;
		}
		return true;
	}

	@Override
	public void onDestroy() {
		AdConfig.destoryYouMiAD(getActivity());
		NetClient.getInstance().cancelRequest(TAG);
		super.onDestroy();
	}
}
