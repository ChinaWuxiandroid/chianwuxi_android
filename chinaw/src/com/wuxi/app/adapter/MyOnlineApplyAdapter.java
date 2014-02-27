package com.wuxi.app.adapter;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.wuxi.app.R;
import com.wuxi.domain.MyApply;

/**
 * 
 * @author wanglu 泰得利通 我的申报列表 适配器
 * 
 */
public class MyOnlineApplyAdapter extends BaseAdapter {

	private List<MyApply> myApplies;
	private Context context;

	public MyOnlineApplyAdapter(List<MyApply> myApplies, Context context) {
		this.myApplies = myApplies;
		this.context = context;
	}

	@Override
	public int getCount() {
		return myApplies.size();
	}

	@Override
	public Object getItem(int position) {
		return myApplies.get(position);
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

		MyApply myApply = myApplies.get(position);

		ViewHolder viewHolder = null;
		if (convertView == null) {
			convertView = View.inflate(context,
					R.layout.gover_myonlineapply_item, null);

			viewHolder = new ViewHolder();

			viewHolder.tv_title = (TextView) convertView
					.findViewById(R.id.gover_myonline_apply_title);
			viewHolder.tv_state = (TextView) convertView
					.findViewById(R.id.gover_myonline_apply_state);

			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		String title = myApply.getTitle();

		viewHolder.tv_title.setText(title);
		viewHolder.tv_state.setText("状态:"+myApply.getStatue());
		return convertView;

	}
	
	
	public void addItem(MyApply myApply){
		myApplies.add(myApply);
	}

}
