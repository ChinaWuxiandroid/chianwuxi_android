package com.wuxi.app.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.wuxi.app.R;

/**
 * simple listview 的适配器
 * @author 杨宸 智佳
 * */
public class SimpleListViewFragmentAdapter extends BaseAdapter{
	
	private LayoutInflater mInflater;
	private String[] testData;
	
	public SimpleListViewFragmentAdapter(LayoutInflater mInflater, String[] testData){
		this.mInflater=mInflater;
		this.testData=testData;
	}
	@Override
	public int getCount() {
		return testData.length;
	}

	@Override
	public Object getItem(int position) {
		return testData[position];
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
		ViewHolder viewHolder = null;

		if (convertView == null) {
			convertView = mInflater.inflate(
					R.layout.simple_listview_fragment_cell_point, null);

			viewHolder = new ViewHolder();

			viewHolder.title_text = (TextView) convertView
					.findViewById(R.id.simple_listView_textView_name);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}

		viewHolder.title_text.setText(testData[position]);
		return convertView;

	}

}
