package com.jk.demo1;

import cn.finalteam.okhttpfinal.BaseHttpRequestCallback;
import cn.finalteam.okhttpfinal.HttpRequest;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends Activity {

	private static String TAG = MainActivity.class.getSimpleName();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		HttpRequest.get("http://baidu.com", new BaseHttpRequestCallback<String>() {
			@Override
			protected void onSuccess(String t) {
				super.onSuccess(t);
				Log.i(TAG, "t=" + t);
			}

			@Override
			public void onStart() {
				super.onStart();
				Log.i(TAG, "onStart");
			}

			@Override
			public void onFailure(int errorCode, String msg) {
				super.onFailure(errorCode, msg);
				Log.i(TAG, "onFailure");
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
