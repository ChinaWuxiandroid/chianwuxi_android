package com.wuxi.app.adapter;

import java.util.List;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.wuxi.app.R;
import com.wuxi.domain.Channel;
import com.wuxi.domain.MenuItem;

/**
 * 左侧导航频道适配器
 * 
 * @author wanglu
 * 
 */
public class ContentNavigatorAdapter extends BaseAdapter {

	private List<Channel> channels=null;
	private List<MenuItem> menuItems=null;
	private LayoutInflater mInflater;
	private int type=1;          //穿入的消息类型，和api里消息的type一样，默认为Channel

	public ContentNavigatorAdapter(LayoutInflater inflater,
			List<Channel> channels,List<MenuItem> menuItems) {
		mInflater = inflater;
		if(channels!=null){
			this.channels=channels;
			type=1;
		}		
		if(menuItems!=null){
			this.menuItems=menuItems;
			type=0;
		}
	}
	


	@Override
	public int getCount() {
		int size=0;
		switch(type){
		case 1:
			size= channels.size();
			break;
		case 0:
			size= menuItems.size();
			break;
		}
		return size;
	}

	@Override
	public Object getItem(int position) {
		if(type==1)
			return channels.get(position);
		else if(type==0)
			return menuItems.get(position);
		return null;
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
		String name ="";
		if(channels!=null){
			name = channels.get(position).getChannelName();
		}
		else if(menuItems!=null){
			name = menuItems.get(position).getName();
		}
		
		
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

		viewHolder.title_text.setText(name);
		return convertView;

	}

}
