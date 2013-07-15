package com.wuxi.app.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.wuxi.app.R;
import com.wuxi.domain.MenuItem;

/**
 * 
 * @author wanglu 泰得利通 我的在线咨询 适配器
 * 
 */
public class MyOnlineAskAdapter extends BaseAdapter {

	private String[] itemStr;
	private Context context;

	public MyOnlineAskAdapter(String[] itemStr, Context context) {
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
		ImageView iv_view;
		ImageView iv_goask;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		String text = itemStr[position];
		ViewHolder viewHolder = null;
		if (convertView == null) {
			convertView = View.inflate(context,
					R.layout.gover_myonlineask_item, null);

			viewHolder = new ViewHolder();

			viewHolder.tv_title = (TextView) convertView
					.findViewById(R.id.gover_onlineask_title);
			viewHolder.iv_view = (ImageView) convertView
					.findViewById(R.id.gover_onlineask_view);
			viewHolder.iv_goask = (ImageView) convertView
					.findViewById(R.id.gover_onlineask_goask);

			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}

		viewHolder.tv_title.setText(text);
		return convertView;

	}

}
