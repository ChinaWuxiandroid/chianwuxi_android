/**   
 * @公司: 重庆智佳信息科技有限公司
 * @文件: OpenInfoDeptAdapter.java 
 * @包名： com.wuxi.app.adapter 
 * @描述: 政府信息公开 部门下拉框 适配器
 * @作者： 罗森   
 * @创建时间： 2013 2013-10-25 下午3:03:44
 * @修改时间：  
 * @修改描述：
 * @版本： V1.0   
 */
package com.wuxi.app.adapter;

import java.util.List;

import com.wuxi.app.R;
import com.wuxi.domain.OpenInfoDept;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * @类名： OpenInfoDeptAdapter
 * @描述： 政府信息公开 部门下拉框 适配器
 * @作者： 罗森
 * @创建时间： 2013 2013-10-25 下午3:03:44
 * @修改时间： 
 * @修改描述： 
 */
public class OpenInfoDeptAdapter extends BaseAdapter {

	private List<OpenInfoDept> depts;
	private Context context;
	
	public OpenInfoDeptAdapter(Context context,List<OpenInfoDept> depts){
		this.context = context;
		this.depts = depts;
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

	public class ViewHolder {
		TextView tv_dept;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		OpenInfoDept dept = depts.get(position);
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

		viewHolder.tv_dept.setTextColor(Color.BLACK);
		viewHolder.tv_dept.setTextSize(10);
		viewHolder.tv_dept.setText(dept.getName());

		return convertView;
	}
	
	@Override
	public View getDropDownView(int position, View convertView,
			ViewGroup parent) {
		if (convertView == null) {
			LayoutInflater inflater = LayoutInflater.from(context);
			convertView = inflater.inflate(
					android.R.layout.simple_spinner_item, parent, false);
		}

		TextView tv = (TextView) convertView
				.findViewById(android.R.id.text1);
		tv.setText(depts.get(position).getName());
		tv.setGravity(Gravity.LEFT);
		tv.setTextColor(Color.BLACK);
		tv.setTextSize(14);
		tv.setPadding(3, 8, 5, 8);

		return convertView;
	}

}
