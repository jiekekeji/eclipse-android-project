package com.jk.demo1.adapter;

import java.util.List;

import org.byteam.superadapter.SuperAdapter;
import org.byteam.superadapter.internal.SuperViewHolder;

import android.content.Context;

import com.jk.demo1.bean.Tngou;

public class TngouListAdapter extends SuperAdapter<Tngou> {

	public TngouListAdapter(Context context, List<Tngou> items, int layoutResId) {
		super(context, items, layoutResId);
	}

	@Override
	public void onBind(SuperViewHolder holder, int viewType, int position, Tngou bean) {

	}

}
