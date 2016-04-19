package com.jk.demo1.adapter;

import java.util.List;

import org.byteam.superadapter.SuperAdapter;
import org.byteam.superadapter.internal.SuperViewHolder;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import com.jk.demo1.R;
import com.jk.demo1.bean.Tngou;

public class TngouListAdapter extends SuperAdapter<Tngou> {

	private ImageView imgView;
	private TextView titleTv, countTv;

	public TngouListAdapter(Context context, List<Tngou> items, int layoutResId) {
		super(context, items, layoutResId);
	}

	@Override
	public void onBind(SuperViewHolder holder, int viewType, int position, Tngou bean) {
		initView(holder);
		initData(position, bean);
	}

	private void initData(int position, Tngou bean) {
		titleTv.setText(bean.getTitle());
		countTv.setText(bean.getCount());
	}

	private void initView(SuperViewHolder holder) {
		imgView = holder.getView(R.id.img);
		titleTv = holder.getView(R.id.title);
		countTv = holder.getView(R.id.count);
	}

}
