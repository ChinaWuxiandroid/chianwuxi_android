package com.wuxi.app.adapter;

import java.util.List;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.wuxi.app.R;
import com.wuxi.domain.MenuItem;

public class IndexGridAdapter extends BasicAdapter {

	private List<MenuItem> menuItems;
	int[] image = { R.drawable.index_msg_public, R.drawable.index_meili_wuxi,
			R.drawable.index_msg_center, R.drawable.index_gover_holl,
			R.drawable.index_server_public, R.drawable.index_hudong,
			R.drawable.index_hudong, R.drawable.index_hudong,
			R.drawable.index_hudong, };

	public IndexGridAdapter(LayoutInflater inflater, int view, int[] viewId,
			List<MenuItem> menuItems, String[] dataName) {
		super(inflater, view, viewId, dataName);
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

	class ViewHolder {
		public ImageView image;
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
			viewHolder.image = (ImageView) convertView.findViewById(viewId[0]);
			viewHolder.title_text = (TextView) convertView
					.findViewById(viewId[1]);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		if (position > 5) {
			position = 0;
		}
		viewHolder.image.setImageResource(image[position]);// 图片暂时做死了，
		viewHolder.title_text.setText(menuItem.getName());
		return convertView;
	}

}
