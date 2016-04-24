package com.jk.demo1.adapter;

import java.util.List;

import org.byteam.superadapter.SuperAdapter;
import org.byteam.superadapter.internal.SuperViewHolder;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.jk.demo1.R;
import com.jk.demo1.bean.Tngou;
import com.jk.demo1.net.URLApi;

public class TngouListAdapter extends SuperAdapter<Tngou> {

	private Context context;

	public TngouListAdapter(Context context, List<Tngou> items, int layoutResId) {
		super(context, items, layoutResId);
		this.context = context;
	}

	@Override
	public void onBind(SuperViewHolder holder, int viewType, int position, Tngou bean) {
		holder.setText(R.id.title, bean.getTitle());
		holder.setText(R.id.count, "数量:" + bean.getCount());
//		holder.setImageResource(viewId, imgResId)
//		holder.setImageResource((R.id.img, R.drawable.ic_launcher)
//		ImageView imgView=holder.getView);
//		imgView.setImageResource();
		Glide.with(context).load(URLApi.SERVER + URLApi.IMG + bean.getImg())
				.into((ImageView) (holder.getView(R.id.img)));
	}

}
