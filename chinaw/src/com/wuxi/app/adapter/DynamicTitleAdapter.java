package com.wuxi.app.adapter;

import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wuxi.app.R;
import com.wuxi.domain.Channel;
import com.wuxi.domain.MenuItem;


/**
 *动态头部view的适配器
 *@author 杨宸 智佳 
 * */

public class DynamicTitleAdapter extends BasicAdapter {
	@SuppressWarnings("rawtypes")
	private List items;
	private int[] viewId;

	@SuppressWarnings("rawtypes")
	public DynamicTitleAdapter(Context context, int view, int[] viewId,
			String[] dataName, List items) {
		super(context, view, viewId, dataName);
		this.viewId=viewId;
		this.items = items;
	}

	@Override
	public int getCount() {
		return items.size();

	}

	@Override
	public Object getItem(int position) {

		return items.get(position);
	}

	@Override
	public long getItemId(int position) {

		return position;
	}
	public void clearData() {
		items.clear();
	}

	protected int[] getViewId() {
		return viewId;
	}
	class ViewHolder {

		public TextView title_text;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
	
		if(parent.getChildCount() == position)
		{
			Object item = items.get(position);
			String chanelName = "";// 频道名称
			if (item instanceof Channel) {
				chanelName = ((Channel) item).getChannelName();
			}  else if (item instanceof MenuItem) {
				chanelName = ((MenuItem) item).getName();
			}

			ViewHolder viewHolder = null;

			if (convertView == null) {
				convertView = getInflater().inflate(getView(), null);
				int[] viewId = getViewId();
				viewHolder = new ViewHolder();

				viewHolder.title_text = (TextView) convertView
						.findViewById(viewId[0]);

				if (position == 0) {

					viewHolder.title_text
					.setBackgroundResource(R.drawable.title_item_select_bg);
					viewHolder.title_text.setTextColor(Color.WHITE);

				}

				convertView.setTag(viewHolder);
			} else {
				viewHolder = (ViewHolder) convertView.getTag();
			}
			viewHolder.title_text.setText(chanelName);
//			System.out.println("position:"+position+"   item name:"+chanelName);
			return convertView; 
		}
		else{
			if (convertView == null) {
				convertView = getInflater().inflate(getView(), null);
			}
			return convertView;
		}
		
	}
}




