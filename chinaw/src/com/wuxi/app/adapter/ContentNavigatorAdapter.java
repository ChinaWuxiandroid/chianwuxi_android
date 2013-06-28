package com.wuxi.app.adapter;

import java.util.List;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.wuxi.app.R;
import com.wuxi.domain.Channel;

/**
 * 左侧导航频道适配器
 * 
 * @author wanglu
 * 
 */
public class ContentNavigatorAdapter extends BaseAdapter {

	private List<Channel> channels;
	private LayoutInflater mInflater;

	public ContentNavigatorAdapter(LayoutInflater inflater,
			List<Channel> channels) {
		mInflater = inflater;
		this.channels = channels;
	}
	

	@Override
	public int getCount() {

		return channels.size();
	}

	@Override
	public Object getItem(int position) {

		return channels.get(position);
	}

	@Override
	public long getItemId(int position) {

		return position;
	}

	class ViewHolder {

		public TextView title_text;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		String channelName = channels.get(position).getChannelName();
		ViewHolder viewHolder = null;

		if (convertView == null) {
			convertView = mInflater.inflate(
					R.layout.left_navigator_list_item_layout, null);

			viewHolder = new ViewHolder();

			viewHolder.title_text = (TextView) convertView
					.findViewById(R.id.tv_navigator_name);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}

		viewHolder.title_text.setText(channelName);
		return convertView;

	}

}
