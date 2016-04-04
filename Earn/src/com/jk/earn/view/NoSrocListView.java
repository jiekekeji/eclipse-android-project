package com.jk.earn.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

public class NoSrocListView extends ListView {

	public NoSrocListView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
		super(context, attrs, defStyleAttr, defStyleRes);
	}

	public NoSrocListView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	public NoSrocListView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public NoSrocListView(Context context) {
		super(context);
	}

	@Override
	public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
		super.onMeasure(widthMeasureSpec, expandSpec);
	}
}
