package com.wuxi.app.adapter;

import java.util.ArrayList;
import java.util.List;

import com.wuxi.domain.TitleItemAction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * 头部滑动模块数据适配器
 * 
 * @author wanglu
 * 
 */
public class TitleActionAdapter extends BasicAdapter {

	private static final String TAG = "TitleActionAdapter";
	private List<TitleItemAction> items = new ArrayList<TitleItemAction>();

	public TitleActionAdapter(LayoutInflater inflater, int view, int[] viewId,
			String[] dataName, List<TitleItemAction> items) {
		super(inflater, view, viewId, dataName);
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

	class ViewHolder {

		public TextView title_text;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		String actionName = items.get(position).getActionName();
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

		viewHolder.title_text.setText(actionName);
		return convertView;
	}

}
