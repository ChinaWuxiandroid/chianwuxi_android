package com.wuxi.app.adapter;

import java.util.List;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.wuxi.app.R;
import com.wuxi.domain.MenuItem;

public class GoverInteractPeopleNevigationAdapter extends BaseAdapter{
	private LayoutInflater mInflater;
	private List<MenuItem> listMenus;
	
	private int selectedPosition = 0;// 选中的位置  
	
	public GoverInteractPeopleNevigationAdapter(LayoutInflater mInflater,List<MenuItem> listMenus){
		this.mInflater=mInflater;
		this.listMenus=listMenus;
		
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return listMenus.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return listMenus.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}
	class ViewHolder {

		public TextView menu_textView;
	}

	public void setSelectedPosition(int position) {  
		selectedPosition = position;  
	} 


	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder = null;

		if (convertView == null) {
			convertView = mInflater.inflate(
					R.layout.goverinteractpeople_nevigation_listview_item, null);
			viewHolder = new ViewHolder();

			viewHolder.menu_textView = (TextView) convertView
					.findViewById(R.id.goverinteractpeople_nevigation_textview_menu);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		viewHolder.menu_textView.setText(listMenus.get(position).getName());
		if(selectedPosition==position)
			viewHolder.menu_textView.setBackgroundResource(R.drawable.listview_item_selected);
		else
			viewHolder.menu_textView.setBackgroundResource(R.drawable.listview_item_textview_back);
		return convertView;

	}

}
