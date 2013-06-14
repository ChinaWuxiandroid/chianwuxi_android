package com.wuxi.app.adapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class IndexNewsListAdapter extends BasicAdapter {

	List<Map<String, Object>> List = new ArrayList<Map<String, Object>>();

	public IndexNewsListAdapter(LayoutInflater inflater, int view, int[] viewId,List<Map<String, Object>> data, String[] dataName) {
		super(inflater, view, viewId, dataName);
		// TODO Auto-generated constructor stub
		this.List = data;
	}

	@Override
	public int getCount() {
		return List.size();
	}

	@Override
	public Object getItem(int position) {
		return List.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	class ViewHolder {
		public ImageView image;
		public TextView numText,title_text;
	}
	
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Map<String, Object> map= List.get(position);
		ViewHolder viewHolder = null;
		if (convertView == null) {
			convertView = getInflater().inflate(getView(), null);
			int[] viewId = getViewId();
			viewHolder = new ViewHolder();
			viewHolder.numText = (TextView) convertView.findViewById(viewId[0]);
			viewHolder.title_text = (TextView) convertView.findViewById(viewId[1]);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		String[] dataName = getDataName();
		viewHolder.numText.setText(map.get(dataName[0]).toString());
		viewHolder.title_text.setText(map.get(dataName[1]).toString());
		return convertView;
	}

}
