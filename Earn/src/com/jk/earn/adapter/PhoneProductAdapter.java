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

public class PhoneProductAdapter extends BaseAdapter {

	private List<PhoneProduct> products;
	private LayoutInflater inflater;
	private Context context;

	public PhoneProductAdapter(Context ct, List<PhoneProduct> products) {
		inflater = LayoutInflater.from(ct);
		this.products = products;
		this.context = ct;
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
			convertView = inflater.inflate(R.layout.adapter_phone_product, null);

			holder.cardworth = (TextView) convertView.findViewById(R.id.adapter_phone_product_cardworth);
			holder.point = (TextView) convertView.findViewById(R.id.adapter_phone_product_cardworth_point);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		PhoneProduct product = products.get(position);
		holder.cardworth.setText(product.getCardWorth() + context.getString(R.string.phone_product_adapter_carworth));
		holder.point.setText(context.getString(R.string.phone_product_adapter_point) + product.getPoint());
		return convertView;

	}

	public final class ViewHolder {
		public TextView cardworth;
		public TextView point;
	}
}
