package com.jk.earn.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.jk.earn.R;
import com.jk.earn.net.bean.PhoneProduct;

public class PhoneEmptyAdapter extends BaseAdapter {

	private List<PhoneProduct> products;
	private LayoutInflater inflater;

	public PhoneEmptyAdapter(Context ct, List<PhoneProduct> products) {
		inflater = LayoutInflater.from(ct);
		this.products = products;
	}

	@Override
	public int getCount() {
		return products.size();
	}

	@Override
	public Object getItem(int position) {
		return products.get(position);
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
			convertView = inflater.inflate(R.layout.adapter_phone_empty, null);
			holder.cardWorth = (TextView) convertView
					.findViewById(R.id.phone_product_card_worth);
			holder.point = (TextView) convertView
					.findViewById(R.id.phone_product_point);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		PhoneProduct product = products.get(position);
		holder.cardWorth.setText(product.getCardWorth() + "元");
		holder.point.setText("");
		return convertView;
	}

	public final class ViewHolder {
		public TextView cardWorth;
		public TextView point;
	}
}
