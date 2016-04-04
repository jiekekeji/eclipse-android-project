package com.jk.base.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.jk.base.R;
import com.jk.base.app.AppExitUtils;

public class ActivityB extends BaseActivity{
	
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.ac_b);
		int size = AppExitUtils.getInstance().size();
		Log.i("MainActivity", "大小:" + size);		
	}

	public void finishMe(View view){
		finish();
	}
	
	public void finishApp(View view){
		AppExitUtils.getInstance().exit();
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
	}
}
