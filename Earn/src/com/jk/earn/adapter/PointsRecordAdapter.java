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
import com.jk.earn.net.bean.UserPoints;

public class PointsRecordAdapter extends BaseAdapter {

	private List<UserPoints> pointsList;
	private LayoutInflater inflater;
	private DateFormat dateFormat;

	public PointsRecordAdapter(List<UserPoints> pointsList, Context context) {
		this.pointsList = pointsList;
		inflater = LayoutInflater.from(context);
		dateFormat = new SimpleDateFormat("MM-dd HH:mm");
		dateFormat.setTimeZone(TimeZone.getTimeZone("GMT+08:00"));
	}

	@Override
	public int getCount() {
		return pointsList.size();
	}

	@Override
	public Object getItem(int position) {
		return pointsList.get(position);
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
			convertView = inflater.inflate(R.layout.adapter_point_record, null);

			holder.time = (TextView) convertView.findViewById(R.id.adapter_point_record_point);
			holder.point = (TextView) convertView.findViewById(R.id.adapter_point_record_time);
			holder.type = (TextView) convertView.findViewById(R.id.adapter_point_record_type);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		UserPoints point = pointsList.get(position);
		holder.time.setText(dateFormat.format(new Date(point.getTime())));
		holder.point.setText("积分:+" + point.getPoint());
		holder.type.setText("名称:" + point.getApp());

		return convertView;
	}

	public final class ViewHolder {
		public TextView time;
		public TextView type;
		public TextView point;
	}
}
