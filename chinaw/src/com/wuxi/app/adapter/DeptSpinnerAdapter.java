package com.wuxi.app.adapter;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.wuxi.app.R;
import com.wuxi.domain.Dept;

/**
 * 
 * @author wanglu 泰得利通
 *  部门下拉框适配器
 *
 */
public class DeptSpinnerAdapter extends BaseAdapter {


	public List<Dept> depts;
	public Context context;

	public DeptSpinnerAdapter(List<Dept> depts, Context context) {
		this.depts = depts;
		this.context = context;

	}

	@Override
	public int getCount() {
		return depts.size();
	}

	@Override
	public Object getItem(int position) {
		return depts.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	static class ViewHolder{
		TextView tv_dept;
	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		Dept dept = depts.get(position);
		ViewHolder viewHolder = null;

		if (convertView == null) {
			convertView = View.inflate(context,
					R.layout.comstuom_spinner_item_layout, null);
			viewHolder = new ViewHolder();
			TextView tv = (TextView) convertView.findViewById(R.id.sp_tv);
			viewHolder.tv_dept = tv;
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		viewHolder.tv_dept.setText(dept.getName());
		return convertView;
	}

}
