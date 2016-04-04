package com.jk.earn.activity;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.FragmentTabHost;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

import com.jk.earn.AppExitUtils;
import com.jk.earn.R;
import com.jk.earn.fragment.PhoneFragment;
import com.jk.earn.fragment.QQFragment;
import com.jk.earn.fragment.UserFragment;
import com.jk.earn.fragment.WzFragment;
import com.jk.earn.net.load.DownLoadUtils;
import com.jk.earn.service.CheckConfService;
import com.jk.earn.view.MyToast;

public class MainActivity extends BaseActivity {

	private static final String TAG = MainActivity.class.getSimpleName();

	private long exitTime;

	/**
	 * FragmentTabhost
	 */
	private FragmentTabHost mTabHost;
	/**
	 * Fragment数组界面
	 * 
	 */
	private Class mFragmentArray[] = { QQFragment.class, PhoneFragment.class, WzFragment.class, UserFragment.class };
	/**
	 * 存放图片数组
	 * 
	 */
	private int mImageArray[] = { R.drawable.tab_fr_qq, R.drawable.tab_fr_phone, R.drawable.tab_fr_point,
			R.drawable.tab_fr_user };
	/**
	 * 选修卡文字
	 * 
	 */
	private String mTextArray[] = { "QQ商品", "话费商品", "任务栏", "个人中心" };

	/**
	 * 判断主页是否是推送的通知
	 */
	private int tag_from_jpush = 0;

	/**
	 * 点击推送的页面后进入的页面
	 */
	private int currenttab_from_jpush = 2;

	/**
	 * 文件下载完成的广播接收者
	 */
	BroadcastReceiver receiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			long reference = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
			DownLoadUtils.openDownloadById(MainActivity.this, String.valueOf(reference));
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		registerReceiver(receiver, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
		initTag();
		setContentView(R.layout.activity_main);
		setupTabView();
	}

	private void initTag() {
		tag_from_jpush = getIntent().getIntExtra("tag_from_jpush", 0);
	}

	private void setupTabView() {
		mTabHost = (FragmentTabHost) findViewById(R.id.tabhost);
		mTabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);
		// 去除分割线
		mTabHost.getTabWidget().setDividerDrawable(null);
		int count = mFragmentArray.length;
		for (int i = 0; i < count; i++) {
			// 给每个Tab按钮设置图标、文字和内容
			TabHost.TabSpec tabSpec = mTabHost.newTabSpec(mTextArray[i]).setIndicator(getTabItemView(i));
			// 将Tab按钮添加进Tab选项卡中
			mTabHost.addTab(tabSpec, mFragmentArray[i], null);
			// 设置Tab按钮的背景
			mTabHost.getTabWidget().getChildAt(i).setBackgroundResource(R.drawable.shape_border_top_bottom);
			// 设置Tab的高度
			mTabHost.getTabWidget().getChildAt(i).setPadding(0, 8, 0, 8);
		}

		showTab();

	}

	/**
	 * 显示第三个tab
	 */
	private void showTab() {
		if (1000 == tag_from_jpush) {
			mTabHost.setCurrentTab(currenttab_from_jpush);
		}
	}

	/**
	 * 给每个Tab按钮设置图标和文字
	 */
	private View getTabItemView(int index) {
		LayoutInflater layoutInflater = LayoutInflater.from(this);
		View view = layoutInflater.inflate(R.layout.tab_item_view, null);
		ImageView imageView = (ImageView) view.findViewById(R.id.tab_item_view_iv);
		imageView.setImageResource(mImageArray[index]);
		TextView textView = (TextView) view.findViewById(R.id.tab_item_view_tv);
		textView.setText(mTextArray[index]);
		return view;
	}

	@Override
	protected void onResume() {
		super.onResume();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		unregisterReceiver(receiver);
	}

	@Override
	public boolean dispatchKeyEvent(KeyEvent event) {
		if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
			if (event.getAction() == KeyEvent.ACTION_DOWN && event.getRepeatCount() == 0) {
				this.exitApp();
			}
			return true;
		}
		return super.dispatchKeyEvent(event);
	}

	/**
	 * 退出程序
	 */
	private void exitApp() {
		// 判断2次点击事件时间
		if ((System.currentTimeMillis() - exitTime) > 2000) {
			MyToast.showToast(this, R.string.toast_ac_main_exit_app);
			exitTime = System.currentTimeMillis();
		} else {
			AppExitUtils.getInstance().exit();
		}
	}

}
