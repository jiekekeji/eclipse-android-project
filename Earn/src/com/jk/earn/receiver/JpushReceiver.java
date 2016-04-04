package com.jk.earn.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import cn.jpush.android.api.JPushInterface;

import com.jk.earn.activity.MainActivity;

public class JpushReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		// 点击通知栏打开
		if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
			openMainActivity(context);
		}
	}

	private void openMainActivity(Context context) {
		Intent intent = new Intent(context, MainActivity.class);
		intent.putExtra("tag_from_jpush", 1000);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
		context.startActivity(intent);
	}
}
