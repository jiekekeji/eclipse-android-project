package com.jk.base.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.apkfuns.logutils.LogUtils;
import com.jk.base.R;
import com.jk.base.app.AppExitUtils;

public class MainActivity extends BaseActivity {

	private Object object;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.ac_main);
		String fileDir = this.getFilesDir().getAbsolutePath();

		LogUtils.i(fileDir);

	}

	@Override
	protected void onResume() {
		super.onResume();
		int size = AppExitUtils.getInstance().size();
		Log.i("MainActivity", "大小:" + size);
	}

	public void openB(View view) {
		startActivity(new Intent(this, ActivityB.class));
	}

}
