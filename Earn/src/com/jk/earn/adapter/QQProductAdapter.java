package com.jk.earn.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.jk.earn.R;
import com.jk.earn.net.NetURL;
import com.jk.earn.net.bean.QQGoods;

public class QQProductAdapter extends BaseAdapter {

	private List<QQGoods> qqGoods;
	private LayoutInflater inflater;
	private Context context;

	public QQProductAdapter(Context ct, List<QQGoods> qqGoods) {
		inflater = LayoutInflater.from(ct);
		this.qqGoods = qqGoods;
		this.context = ct;
	}

	@Override
	public int getCount() {
		return qqGoods.size();
	}

	@Override
	public Object getItem(int position) {
		return qqGoods.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = inflater.inflate(R.layout.adapter_qq_product, null);

			holder.imgView = (ImageView) convertView.findViewById(R.id.adapter_qq_product_img);
			holder.point = (TextView) convertView.findViewById(R.id.adapter_qq_unit_point);
			holder.unit = (TextView) convertView.findViewById(R.id.adapter_qq_unit_price);
			holder.name = (TextView) convertView.findViewById(R.id.adapter_qq_good_name);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		QQGoods good = qqGoods.get(position);
		holder.unit.setText(good.getF() + good.getU());
		holder.point.setText(context.getString(R.string.adpater_qq_product_point) + String.valueOf(good.getPoint()));
		holder.name.setText(good.getB());
		Glide.with(context).load(NetURL.Host + good.getImgurl()).diskCacheStrategy(DiskCacheStrategy.ALL)
				.placeholder(R.drawable.ic_app_img_default).into(holder.imgView);
		return convertView;

	}

	public final class ViewHolder {
		public TextView name;
		public ImageView imgView;
		public TextView unit;
		public TextView point;
	}
}
