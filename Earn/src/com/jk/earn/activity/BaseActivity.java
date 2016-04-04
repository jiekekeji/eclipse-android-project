package com.jk.earn.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.jk.earn.AppExitUtils;

public class BaseActivity extends FragmentActivity {

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		AppExitUtils.getInstance().addActivity(this);

	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		AppExitUtils.getInstance().delActivity(this);
	}

}
