package com.jk.earn.view;

import android.content.Context;
import android.os.Handler;
import android.view.Gravity;
import android.widget.Toast;

/**
 * 自定义Toast
 * 
 * @author tjk
 * 
 */
public class MyToast {

	private static Toast mToast;
	private static Handler mhandler = new Handler();
	private static Runnable r = new Runnable() {
		public void run() {
			mToast.cancel();
		};
	};

	public static void showToast(Context context, String text, int duration) {
		mhandler.removeCallbacks(r);
		if (null != mToast) {
			mToast.setText(text);
		} else {
			mToast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
		}
		mhandler.postDelayed(r, 5000);
		mToast.setGravity(Gravity.CENTER, 0, 0);
		mToast.show();
	}

	public static void showToast(Context context, int strId, int duration) {
		showToast(context, context.getString(strId), duration);
	}

	public static void showToast(Context context, int strId) {
		showToast(context, context.getString(strId), Toast.LENGTH_SHORT);
	}
}
