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
import com.jk.earn.fragment.LoginFragment;
import com.jk.earn.fragment.RegisterFragment;

public class UserLoginActivity extends BaseActivity implements OnClickListener {

	private Fragment loginFrament, registerFragment;
	private FragmentManager fm;

	public static final int LOGINFRAMENT_TAG = 1000;
	private static final int REGISTERFRAGMENT_TAG = 1001;

	private boolean SHOW_LOGINFRAMENT = true;
	private TextView typeTv;
	private TextView titleTv;
	private ImageView backImgView;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_user_login);
		initContent();
	}

	private void initContent() {
		typeTv = (TextView) findViewById(R.id.ac_user_login_op_title_text_view);
		typeTv.setOnClickListener(this);

		titleTv = (TextView) findViewById(R.id.ac_user_login_title_text_view);
		backImgView = (ImageView) findViewById(R.id.ac_user_login_back_img);
		backImgView.setOnClickListener(this);

		fm = getSupportFragmentManager();
		showFragment(LOGINFRAMENT_TAG);
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
		case LOGINFRAMENT_TAG:
			if (loginFrament != null) {
				transaction.show(loginFrament);
			} else {
				loginFrament = new LoginFragment();
				transaction.add(R.id.ac_user_login_content, loginFrament);
			}
			break;

		case REGISTERFRAGMENT_TAG:
			if (registerFragment != null) {
				transaction.show(registerFragment);
			} else {
				registerFragment = new RegisterFragment();
				transaction.add(R.id.ac_user_login_content, registerFragment);
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
		if (loginFrament != null)
			ft.hide(loginFrament);
		if (registerFragment != null)
			ft.hide(registerFragment);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.ac_user_login_back_img:
			finish();
			break;
		case R.id.ac_user_login_op_title_text_view:
			changeContentUI();
			break;

		}
	}

	public void changeContentUI() {
		if (SHOW_LOGINFRAMENT) {
			showFragment(REGISTERFRAGMENT_TAG);
			typeTv.setText(R.string.title_ac_user_login);
			titleTv.setText(R.string.title_ac_user_register);
			SHOW_LOGINFRAMENT = false;
		} else {
			showFragment(LOGINFRAMENT_TAG);
			typeTv.setText(R.string.title_ac_user_register);
			titleTv.setText(R.string.title_ac_user_login);
			SHOW_LOGINFRAMENT = true;
		}
	}
}
