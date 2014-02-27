package com.wuxi.app.adapter;

import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.wuxi.app.R;
import com.wuxi.domain.Channel;

/**
 * 
 * @author wanglu 泰得利通 内容channel适配器
 */
public class ContentChannelAdapter extends BaseAdapter {

	private List<Channel> channels;
	private Context context;
	private int selectPosition=0;
	public void setSelectPosition(int selectPosition) {
		this.selectPosition = selectPosition;
	}

	public ContentChannelAdapter(List<Channel> channels, Context context) {
		this.channels = channels;
		this.context = context;
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

	static class ViewHolder {
		TextView tv_channelitem_name;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		Channel channel = channels.get(position);
		ViewHolder viewHolder = null;
		if (convertView == null) {
			convertView = View.inflate(context,
					R.layout.content_channelitem_item_layout, null);

			viewHolder = new ViewHolder();

			viewHolder.tv_channelitem_name = (TextView) convertView
					.findViewById(R.id.channelitem_tv_name);

			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}

		if(position==selectPosition){
			viewHolder.tv_channelitem_name.setTextColor(Color.WHITE);
			viewHolder.tv_channelitem_name.setBackgroundResource(R.drawable.public_service_channel_1bg);
		}else{
			viewHolder.tv_channelitem_name.setTextColor(Color.BLACK);
			viewHolder.tv_channelitem_name.setBackgroundResource(R.color.content_background);
		}
		viewHolder.tv_channelitem_name.setText(channel.getChannelName());
		return convertView;
	}
	
	

}
