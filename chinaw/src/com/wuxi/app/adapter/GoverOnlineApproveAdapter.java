package com.wuxi.app.adapter;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.wuxi.app.R;
import com.wuxi.domain.GoverSaoonItem;

/**
 * 
 * @author wanglu 泰得利通 网上审批
 * 
 */
public class GoverOnlineApproveAdapter extends BaseAdapter {

	private List<GoverSaoonItem> goverSaoonItems;
	public void setGoverSaoonItems(List<GoverSaoonItem> goverSaoonItems) {
		this.goverSaoonItems = goverSaoonItems;
	}

	private Context context;

	public GoverOnlineApproveAdapter(List<GoverSaoonItem> goverSaoonItems,
			Context context) {
		this.goverSaoonItems = goverSaoonItems;
		this.context = context;
	}

	@Override
	public int getCount() {
		return goverSaoonItems.size();
	}

	@Override
	public Object getItem(int position) {
		return goverSaoonItems.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	static class ViewHolder {
		TextView tv_title;
		TextView tv_state;

	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		GoverSaoonItem goverSaoonItem = goverSaoonItems.get(position);
		ViewHolder viewHolder = null;
		if (convertView == null) {
			convertView = View.inflate(context,
					R.layout.gover_onlineapprove_item, null);

			viewHolder = new ViewHolder();

			viewHolder.tv_title = (TextView) convertView
					.findViewById(R.id.gover_myonline_approve_title);
			viewHolder.tv_state = (TextView) convertView
					.findViewById(R.id.gover_myonline_approve_state);

			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}

		viewHolder.tv_title.setText("事项:" + goverSaoonItem.getName());
		viewHolder.tv_state.setText("办件数:" + goverSaoonItem.getNum() + " 事件类型:"
				+ goverSaoonItem.getTypename());
		return convertView;

	}
	
	public void addItem(GoverSaoonItem goverSaoonItem){
		
		this.goverSaoonItems.add(goverSaoonItem);
	}
	
	
	

}
