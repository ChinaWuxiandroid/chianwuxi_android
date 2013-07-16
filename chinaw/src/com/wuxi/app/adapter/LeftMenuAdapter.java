package com.wuxi.app.adapter;

import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.wuxi.app.R;
import com.wuxi.domain.MenuItem;

/**
 * 
 * @author wanglu 泰得利通 左侧菜单适配器
 * 
 */
public class LeftMenuAdapter extends BasicAdapter {

	private List<MenuItem> menuItems;
	private int selectPosition = -1;// 选中的的item

	public LeftMenuAdapter(Context context, int view, int[] viewId,
			List<MenuItem> menuItems, String[] dataName, int selectPostion) {
		super(context, view, viewId, dataName);
		this.menuItems = menuItems;
		this.selectPosition = selectPostion;
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
		public ImageView iv_icon;
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
			viewHolder.iv_icon = (ImageView) convertView
					.findViewById(viewId[1]);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}

		viewHolder.title_text.setText(menuItem.getName());
		if (selectPosition == position) {
			viewHolder.title_text.setTextColor(Color.parseColor("#AD1010"));
			viewHolder.iv_icon.setBackground(context.getResources()
					.getDrawable(R.drawable.left_select_logo));

			convertView.setBackground(context.getResources().getDrawable(
					R.drawable.left_item_select_bg));
		}
		return convertView;
	}

}
