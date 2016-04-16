package com.jk.demo1.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.jk.demo1.R;

public class MainActivity extends BaseActivity {

	private static String TAG = MainActivity.class.getSimpleName();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	/**
	 * 
	 * @param view
	 */
	public void click1(View view) {
		startActivity(new Intent(this, ActivityOne.class));
	}

}
