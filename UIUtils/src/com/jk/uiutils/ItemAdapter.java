package com.jk.uiutils;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ItemAdapter extends BaseAdapter {

	private List<ItemBean> mDatas;
	private Context mContext;
	private LayoutInflater inflater;

	public ItemAdapter(Context context, List<ItemBean> datas) {
		this.mDatas = datas;
		this.mContext = context;
		inflater = LayoutInflater.from(mContext);
	}

	@Override
	public int getCount() {
		return mDatas.size();
	}

	@Override
	public Object getItem(int position) {
		return mDatas.get(position);
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
			convertView = inflater.inflate(R.layout.adapter_item, null);

			holder.title = (TextView) convertView.findViewById(R.id.textView2);
			holder.content = (TextView) convertView.findViewById(R.id.textView1);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		ItemBean bean = mDatas.get(position);
		holder.title.setText(bean.getTitle());
		holder.content.setText(bean.getContent());
		return convertView;
	}

	public final class ViewHolder {
		public TextView title;
		public TextView content;
	}
}
