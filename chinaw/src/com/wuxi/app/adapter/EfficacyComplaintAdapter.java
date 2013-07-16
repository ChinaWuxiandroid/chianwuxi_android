package com.wuxi.app.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.wuxi.app.R;

/**
 * 
 * @author wanglu 泰得利通  
 * 能效评价适配器
 * 
 * 
 */
public class EfficacyComplaintAdapter extends BaseAdapter {

	private String[] itemStr;
	private Context context;

	public EfficacyComplaintAdapter(String[] itemStr, Context context) {
		this.itemStr = itemStr;
		this.context = context;
	}

	@Override
	public int getCount() {
		return itemStr.length;
	}

	@Override
	public Object getItem(int position) {
		return itemStr[position];
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	static class ViewHolder {
		TextView tv_title;
		TextView tv_state;

	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		String text = itemStr[position];
		ViewHolder viewHolder = null;
		if (convertView == null) {
			convertView = View.inflate(context,
					R.layout.gover_effcom_item, null);

			viewHolder = new ViewHolder();

			viewHolder.tv_title = (TextView) convertView
					.findViewById(R.id.gover_myonline_effcom_title);
			viewHolder.tv_state = (TextView) convertView
					.findViewById(R.id.gover_myonline_effcom_state);

			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		if (text.length() > 10) {
			text = text.substring(0, 10) + "...";
		}

		viewHolder.tv_title.setText(text);
		viewHolder.tv_state.setText("答复部门：无锡市行政服务中心 答复时间: 2013-4-23");
		return convertView;

	}

}
