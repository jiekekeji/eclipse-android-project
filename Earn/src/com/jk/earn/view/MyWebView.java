package com.jk.earn.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.jk.earn.R;

public class MyWebView extends WebView {

	private ProgressBar progressbar;
	private String currentUrl;

	private OnTitleChangeListener mOnTitleChangeListener;
	private OnScrollChangedListener mOnScrollChangedListener;

	public MyWebView(Context context, AttributeSet attrs) {
		super(context, attrs);
		progressbar = new ProgressBar(context, null, android.R.attr.progressBarStyleHorizontal);
		progressbar.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, 10, 0, 0));

		Drawable drawable = context.getResources().getDrawable(R.drawable.webview_progress_bar_states);
		progressbar.setProgressDrawable(drawable);
		addView(progressbar);

		setWebChromeClient(new MyWebChromeClient());
		setWebViewClient(new MyWebViewClient());

		// 是否可以缩放
		getSettings().setSupportZoom(true);
		getSettings().setBuiltInZoomControls(true);

		// 屏蔽掉长按时间，webview长按时将会调用系统的复制控件
		setOnLongClickListener(new MyOnLongClickListener());
		getSettings().setJavaScriptEnabled(true);

		// 输入法无法弹出
		requestFocus(View.FOCUS_DOWN);
		getSettings().setUseWideViewPort(true);

	}

	private class MyWebChromeClient extends WebChromeClient {
		@Override
		public void onProgressChanged(WebView view, int newProgress) {
			if (newProgress == 100) {
				progressbar.setVisibility(GONE);
			} else {
				if (progressbar.getVisibility() == GONE)
					progressbar.setVisibility(VISIBLE);
				progressbar.setProgress(newProgress);
			}
			super.onProgressChanged(view, newProgress);
		}

		@Override
		public void onReceivedTitle(WebView view, String title) {
			super.onReceivedTitle(view, title);
			if (null != mOnTitleChangeListener) {
				mOnTitleChangeListener.onTitleChangeListener(title);
			}
		}
	}

	private class MyWebViewClient extends WebViewClient {
		@Override
		public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
			String data = "<a href='" + currentUrl + "'>点我刷新</a>";
			view.loadUrl("javascript:document.body.innerHTML=\"" + data + "\"");
			Log.i("MyWebView", "onReceivedError=" + data);
		}

		@Override
		public void onPageFinished(WebView view, String url) {
			super.onPageFinished(view, url);
		}

		@Override
		public void onPageStarted(WebView view, String url, Bitmap favicon) {
			super.onPageStarted(view, url, favicon);
			currentUrl = url;
		}

		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			view.loadUrl(url);
			currentUrl = url;
			return false;
		}
	}

	private class MyOnLongClickListener implements OnLongClickListener {
		@Override
		public boolean onLongClick(View v) {
			return true;
		}
	}

	public interface OnTitleChangeListener {
		/**
		 * html title改变时回调
		 * 
		 * @param title
		 */
		void onTitleChangeListener(String title);
	}

	public static interface OnScrollChangedListener {
		/**
		 * x轴偏移和y轴偏移
		 * 
		 * @param dx
		 * @param dy
		 */
		public void onScroll(int dx, int dy);
	}

	public void setOnScrollChangedListener(final OnScrollChangedListener onScrollChangedListener) {
		mOnScrollChangedListener = onScrollChangedListener;
	}

	public void setOnTitleChangeListener(OnTitleChangeListener listener) {
		this.mOnTitleChangeListener = listener;
	}

	/**
	 * 返回当前页面加载的url
	 * 
	 * @return
	 */
	public String getCurrentUrl() {
		return currentUrl;
	}

	@Override
	protected void onScrollChanged(int l, int t, int oldl, int oldt) {
		super.onScrollChanged(l, t, oldl, oldt);

		reLayoutProgressbar(l, t);

		if (mOnScrollChangedListener != null) {
			mOnScrollChangedListener.onScroll(l - oldl, t - oldt);
		}
	}

	private void reLayoutProgressbar(int l, int t) {
		LayoutParams lp = (LayoutParams) progressbar.getLayoutParams();
		lp.x = l;
		lp.y = t;
		progressbar.setLayoutParams(lp);
	}

}
