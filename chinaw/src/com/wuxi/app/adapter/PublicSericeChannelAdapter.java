package com.wuxi.app.adapter;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.wuxi.app.R;
import com.wuxi.domain.Channel;

/**
 * 
 * @author wanglu 泰得利通 公共服务子 channle列表适配器
 */
public class PublicSericeChannelAdapter extends BaseAdapter {

	private List<Channel> channles;
	private Context context;

	public PublicSericeChannelAdapter(List<Channel> channles, Context context) {
		this.channles = channles;
		this.context = context;

	}

	@Override
	public int getCount() {
		
		return channles.size();
	}

	@Override
	public Object getItem(int position) {
		return channles.get(position);
	}

	@Override
	public long getItemId(int position) {
		
		return position;
	}

	static class ViewHolder {
		TextView tv_channeName;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		Channel channel=channles.get(position);
		

		ViewHolder viewHolder = null;

		if (convertView == null) {
			convertView = View.inflate(context, R.layout.publicservice_channel_item_layout, null);
			viewHolder = new ViewHolder();

			viewHolder.tv_channeName = (TextView) convertView
					.findViewById(R.id.publicsercie_tv_channelname);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}


		viewHolder.tv_channeName.setText(channel.getChannelName());
		return convertView;
	}

}
