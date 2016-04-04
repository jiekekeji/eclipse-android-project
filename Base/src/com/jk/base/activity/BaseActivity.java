package com.jk.base.activity;

import com.jk.base.app.AppExitUtils;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

public class BaseActivity extends FragmentActivity{

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
