package com.wuxi.app.adapter;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wuxi.domain.MenuItem;

/**
 * 
 * @author wanglu 泰得利通 左侧菜单适配器
 * 
 */
public class LeftMenuAdapter extends BasicAdapter {

	private List<MenuItem> menuItems;

	public LeftMenuAdapter(Context context, int view, int[] viewId,
			List<MenuItem> menuItems, String[] dataName) {
		super(context, view, viewId, dataName);
		this.menuItems = menuItems;
	}

	@Override
	public int getCount() {
		return menuItems.size();
	}

	@Override
	public Object getItem(int position) {
		return menuItems.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	static class ViewHolder {

		public TextView title_text;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		MenuItem menuItem = menuItems.get(position);
		ViewHolder viewHolder = null;
		if (convertView == null) {
			convertView = getInflater().inflate(getView(), null);
			int[] viewId = getViewId();
			viewHolder = new ViewHolder();

			viewHolder.title_text = (TextView) convertView
					.findViewById(viewId[0]);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}

		viewHolder.title_text.setText(menuItem.getName());
		return convertView;
	}

}
