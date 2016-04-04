package com.jk.earn.activity;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import com.jk.earn.R;
import com.jk.earn.view.MyWebView;
import com.jk.earn.view.MyWebView.OnTitleChangeListener;

public class WebViewActivity extends BaseActivity {

	public static final String WEB_URL_KEY = "WebViewActivity_ WEB_URL_KEY";
	private static final String TAG = WebViewActivity.class.getSimpleName();

	private TextView titleTv;
	private MyWebView myWebView;
	private ViewGroup rootView;
	private View titleRootView;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_web_view);
		initView();
	}

	private void initView() {
		titleRootView = findViewById(R.id.title_view_ac_web_view);
		titleTv = (TextView) findViewById(R.id.ac_web_view_title_tv);
		rootView = (ViewGroup) findViewById(R.id.web_view_root);

		findViewById(R.id.ac_web_view_back_img).setOnClickListener(mListener);
		findViewById(R.id.ac_web_view_refresh_img).setOnClickListener(mListener);

		myWebView = (MyWebView) findViewById(R.id.my_web_view);
		myWebView.setOnTitleChangeListener(mTitleChangeListener);

		String url = getIntent().getStringExtra(WEB_URL_KEY);
		if (null != url) {
			myWebView.loadUrl(url);
		}

	}

	OnClickListener mListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.ac_web_view_back_img:
				getBack();
				break;

			case R.id.ac_web_view_refresh_img:
				refreshWebView();
				break;
			}
		}
	};

	OnTitleChangeListener mTitleChangeListener = new OnTitleChangeListener() {

		@Override
		public void onTitleChangeListener(String title) {
			titleTv.setText(title);
		}
	};

	protected void getBack() {
		if (myWebView.canGoBack()) {
			myWebView.goBack();
			return;
		}

		this.finish();
	}

	protected void refreshWebView() {
		myWebView.reload();
	}

	protected void onDestroy() {
		rootView.removeAllViews();
		super.onDestroy();
	};
}
