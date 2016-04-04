package com.jk.earn.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import cn.jpush.android.api.JPushInterface;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.jk.earn.R;
import com.jk.earn.service.CheckConfService;

public class LauncherActivity extends BaseActivity {

	private static final String TAG = LauncherActivity.class.getSimpleName();

	private ImageView imgView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_launcher);
		initView();
		initService();
	}

	/**
	 * 初始化视图
	 */
	private void initView() {
		imgView = (ImageView) findViewById(R.id.iamge_view);

		Glide.with(this).load(R.drawable.ic_app_luanch).diskCacheStrategy(DiskCacheStrategy.ALL)
				.placeholder(R.drawable.ic_app_luanch).into(imgView);

		Animation anim = AnimationUtils.loadAnimation(this, R.anim.app_in);
		anim.setFillAfter(true);// 是否保持结束时的画面
		anim.setAnimationListener(new MyAnimListener());
		imgView.startAnimation(anim); // 应用动画特效
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	class MyAnimListener implements AnimationListener {

		@Override
		public void onAnimationStart(Animation animation) {

		}

		@Override
		public void onAnimationEnd(Animation animation) {

			startActivity(new Intent(LauncherActivity.this, MainActivity.class));
			finish();
		}

		@Override
		public void onAnimationRepeat(Animation animation) {

		}

	}

	private void initService() {
		Intent service = new Intent(this, CheckConfService.class);
		startService(service);
	}

	@Override
	protected void onResume() {
		super.onResume();
		JPushInterface.onResume(this);
	}

	@Override
	protected void onPause() {
		super.onPause();
		JPushInterface.onPause(this);
	}
}
