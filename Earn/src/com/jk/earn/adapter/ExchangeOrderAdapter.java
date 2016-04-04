package com.jk.earn.adapter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.jk.earn.R;
import com.jk.earn.net.bean.OrderUser;

public class ExchangeOrderAdapter extends BaseAdapter {

	private List<OrderUser> orderList;
	private LayoutInflater inflater;
	private DateFormat dateFormat;

	public ExchangeOrderAdapter(List<OrderUser> orderList, Context context) {
		this.orderList = orderList;
		inflater = LayoutInflater.from(context);
		dateFormat = new SimpleDateFormat("MM-dd  HH:mm");
		dateFormat.setTimeZone(TimeZone.getTimeZone("GMT+08:00"));
	}

	@Override
	public int getCount() {
		return orderList.size();
	}

	@Override
	public Object getItem(int position) {
		return orderList.get(position);
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
			convertView = inflater.inflate(R.layout.adapter_exchange_order, null);

			holder.productNameTv = (TextView) convertView.findViewById(R.id.adapter_exchange_product_name);
			holder.statusTv = (TextView) convertView.findViewById(R.id.adapter_exchange_order_status);
			holder.accountTv = (TextView) convertView.findViewById(R.id.adapter_exchange_account_num);
			holder.pointTv = (TextView) convertView.findViewById(R.id.adapter_exchange_order_point);
			holder.timeTv = (TextView) convertView.findViewById(R.id.adapter_exchange_order_time);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		OrderUser order = orderList.get(position);

		holder.productNameTv.setText(order.getProductName() + "*" + order.getBuyNum());
		holder.accountTv.setText("账号" + order.getAccount());
		holder.pointTv.setText("积分:-" + order.getPoint());
		holder.timeTv.setText(dateFormat.format(new Date(order.getTime())));

		switch (order.getStatus()) {
		case 3000:
			holder.statusTv.setText("正在处理");
			break;
		case 2000:
			holder.statusTv.setText("充值成功");
			break;
		case 4000:
			holder.statusTv.setText("充值失败");
			break;
		case 4001:
			holder.statusTv.setText("充值失败,积分已退");
			break;
		case 1000:
			holder.statusTv.setText("初始");
			break;

		}

		return convertView;
	}

	public final class ViewHolder {
		public TextView productNameTv;
		public TextView statusTv;
		public TextView accountTv;
		public TextView pointTv;
		public TextView timeTv;
	}
}
