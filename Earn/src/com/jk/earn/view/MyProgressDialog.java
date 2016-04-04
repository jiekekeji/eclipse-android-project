package com.jk.earn.view;

import android.R.integer;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.view.Window;

public class MyProgressDialog {

	private static ProgressDialog mProgressDialog;

	public static void showProgressDialog(Context context, String text) {
		if (null == mProgressDialog) {
			mProgressDialog = new ProgressDialog(context);
			mProgressDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
			mProgressDialog.setCanceledOnTouchOutside(false);

			mProgressDialog.setOnDismissListener(new OnDismissListener() {

				@Override
				public void onDismiss(DialogInterface dialog) {
					mProgressDialog = null;
				}
			});
		}

		mProgressDialog.setMessage(text);
		mProgressDialog.show();
	}

	public static void showProgressDialog(Context context, int resId) {
		showProgressDialog(context, context.getString(resId));
	}

	public static void closeProgressDialog() {
		if (null != mProgressDialog && mProgressDialog.isShowing()) {
			mProgressDialog.dismiss();
		}
	}
}
