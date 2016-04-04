package com.jk.earn.view;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.jk.earn.R;

public class SingleTextDialog implements OnClickListener {

	private ConfireClickListener confireClickListener;
	private CancelClickListener cancelClickListener;
	private Dialog dialog;

	public SingleTextDialog(String text, Context context) {
		View view = LayoutInflater.from(context).inflate(
				R.layout.dialog_single_text, null);

		initView(view, text);
		initDialog(view, context);

	}

	private void initDialog(View view, Context context) {
		dialog = new Dialog(context);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setCanceledOnTouchOutside(false);
		dialog.setContentView(view);
		WindowManager.LayoutParams layoutParams = dialog.getWindow()
				.getAttributes();
		layoutParams.width = LayoutParams.MATCH_PARENT;
		layoutParams.height = LayoutParams.WRAP_CONTENT;
		dialog.getWindow().setAttributes(layoutParams);
		dialog.getWindow()
				.setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
	}

	private void initView(View view, String text) {
		((TextView) view.findViewById(R.id.dialog_single_text_text))
				.setText(text);
		((Button) view.findViewById(R.id.dialog_single_text_btn_confire))
				.setOnClickListener(this);
		((Button) view.findViewById(R.id.dialog_single_text_btn_cancel))
				.setOnClickListener(this);

	}

	public void showDialog() {
		if (null != dialog && !dialog.isShowing()) {
			dialog.show();
		}
	}

	public void dissmissDialog() {
		if (null != dialog && dialog.isShowing()) {
			dialog.dismiss();
		}
	}

	public interface ConfireClickListener {
		void onConfire();
	}

	public interface CancelClickListener {
		void onCancel();
	}

	public void setonConfireClickListener(ConfireClickListener listener) {
		this.confireClickListener = listener;
	}

	public void setonCancelClickListener(CancelClickListener listener) {
		this.cancelClickListener = listener;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.dialog_single_text_btn_confire:
			if (confireClickListener != null) {
				confireClickListener.onConfire();
			}
			break;

		case R.id.dialog_single_text_btn_cancel:
			if (cancelClickListener != null) {
				cancelClickListener.onCancel();
			}
			break;
		}
	}
}
