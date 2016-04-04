package com.jk.earn.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.jk.earn.R;
import com.jk.earn.fragment.PointEarnFragment;
import com.jk.earn.fragment.PointExchangeFragment;
import com.jk.earn.fragment.UserFragment;

public class PiontHistoryAcitivity extends BaseActivity implements OnClickListener {

	private Fragment earnFrament, exchangeFragment;
	private FragmentManager fm;

	public static final int EARNFRAMENT_TAG = 1001;
	private static final int EXCHANGEFRAGMENT_TAG = 1000;

	private boolean SHOW_EARNFRAMENT = true;
	private TextView typeTv;
	private TextView titleTv;
	private ImageView backImgView;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_point_history);
		initContent();
	}

	private void initContent() {
		typeTv = (TextView) findViewById(R.id.ac_point_point_op_title_text_view);
		typeTv.setOnClickListener(this);

		backImgView = (ImageView) findViewById(R.id.ac_history_point_back_img);
		backImgView.setOnClickListener(this);

		titleTv = (TextView) findViewById(R.id.ac_point_history_current_title_text_view);

		fm = getSupportFragmentManager();

		showInitFragment();

	}

	private void showInitFragment() {
		int flags = getIntent().getFlags();
		switch (flags) {
		case UserFragment.POINT_EARN_FLAG:
			SHOW_EARNFRAMENT = true;
			changeContentUI();
			break;

		case UserFragment.POINT_EXCHANGE_FLAG:
			SHOW_EARNFRAMENT = false;
			changeContentUI();
			break;
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.ac_history_point_back_img:
			finish();
			break;

		case R.id.ac_point_point_op_title_text_view:
			changeContentUI();
			break;

		}
	}

	/**
	 * 切换fragment
	 * 
	 * @param fragmentId
	 * @param fragment
	 */
	private void showFragment(int checkedId) {
		FragmentTransaction transaction = fm.beginTransaction();
		hideFragments(transaction);

		switch (checkedId) {
		case EARNFRAMENT_TAG:
			if (earnFrament != null) {
				transaction.show(earnFrament);
			} else {
				earnFrament = new PointEarnFragment();
				transaction.add(R.id.ac_point_history_content, earnFrament);
			}
			break;

		case EXCHANGEFRAGMENT_TAG:
			if (exchangeFragment != null) {
				transaction.show(exchangeFragment);
			} else {
				exchangeFragment = new PointExchangeFragment();
				transaction.add(R.id.ac_point_history_content, exchangeFragment);
			}
			break;

		}
		transaction.setTransition(FragmentTransaction.TRANSIT_NONE);
		transaction.commit();
	}

	/**
	 * 当fragment已被实例化，就隐藏起来
	 * 
	 * @param ft
	 */
	public void hideFragments(FragmentTransaction ft) {
		if (earnFrament != null)
			ft.hide(earnFrament);
		if (exchangeFragment != null)
			ft.hide(exchangeFragment);

	}

	public void changeContentUI() {
		if (SHOW_EARNFRAMENT) {
			showFragment(EARNFRAMENT_TAG);
			typeTv.setText(R.string.title_ac_point_history_recharge);
			titleTv.setText(R.string.title_ac_point_history_earn);
			SHOW_EARNFRAMENT = false;
		} else {
			showFragment(EXCHANGEFRAGMENT_TAG);
			typeTv.setText(R.string.title_ac_point_history_earn);
			titleTv.setText(R.string.title_ac_point_history_recharge);
			SHOW_EARNFRAMENT = true;
		}
	}
}
