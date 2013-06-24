package com.wuxi.app.adapter;

import java.util.List;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.wuxi.app.R;
import com.wuxi.domain.NavigatorItmeAction;

/**
 * 左侧导航适配器
 * @author wanglu
 *
 */
public class ContentNavigatorAdapter extends BaseAdapter {
	
	private List<NavigatorItmeAction> naItems;
	private LayoutInflater mInflater;
	public ContentNavigatorAdapter(LayoutInflater inflater,List<NavigatorItmeAction> naItems){
		mInflater=inflater;
		this.naItems=naItems;
	}

	@Override
	public int getCount() {

		return naItems.size();
	}

	@Override
	public Object getItem(int position) {

		return naItems.get(position);
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

		String actionName = naItems.get(position).getActionName();
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

		viewHolder.title_text.setText(actionName);
		return convertView;

	}
	
}
