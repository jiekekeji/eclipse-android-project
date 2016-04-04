package com.jk.earn.view;

import android.content.Context;


import android.util.AttributeSet;
import android.widget.GridView;

public class NoSrocGridView extends GridView {

	public NoSrocGridView(Context context, AttributeSet attrs,
			int defStyleAttr, int defStyleRes) {
		super(context, attrs, defStyleAttr, defStyleRes);
	}

	public NoSrocGridView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	public NoSrocGridView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public NoSrocGridView(Context context) {
		super(context);
	}

	@Override
	public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
				MeasureSpec.AT_MOST);
		super.onMeasure(widthMeasureSpec, expandSpec);
	}
}
