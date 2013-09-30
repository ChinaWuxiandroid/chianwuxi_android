/**   
 * @公司: 重庆智佳信息科技有限公司
 * @文件: AdministrativeLicenseAdapter.java 
 * @包名： com.wuxi.app.adapter 
 * @描述: 行政事项 子菜单内容列表适配器类 
 * @作者： 罗森   
 * @创建时间： 2013 2013-9-29 上午10:56:39
 * @修改时间：  
 * @修改描述：
 * @版本： V1.0   
 */
package com.wuxi.app.adapter;

import java.util.List;

import com.wuxi.app.R;
import com.wuxi.domain.GoverSaoonItem;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * @类名： AdministrativeLicenseAdapter
 * @描述： 行政事项 子菜单内容列表适配器类
 * @作者： 罗森
 * @创建时间： 2013 2013-9-29 上午10:56:39
 * @修改时间：
 * @修改描述：
 */
public class AdministrativeAdapter extends BaseAdapter {

	private Context context;

	private List<GoverSaoonItem> administratives;

	/**
	 * @方法： AdministrativeAdapter
	 * @描述： 构造方法
	 * @param context
	 * @param administratives
	 */
	public AdministrativeAdapter(Context context,
			List<GoverSaoonItem> administratives) {
		this.context = context;
		this.administratives = administratives;
	}

	/**
	 * @param administratives
	 *            要设置的 administratives
	 */
	public void setAdministratives(List<GoverSaoonItem> administratives) {
		this.administratives = administratives;
	}

	/**
	 * @方法： addItem
	 * @描述： 添加数据到列表
	 * @param administrative
	 */
	public void addItem(GoverSaoonItem administrative) {
		this.administratives.add(administrative);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @方法： getCount
	 * 
	 * @描述： 
	 * 
	 * @return
	 * 
	 * @see android.widget.Adapter#getCount()
	 */
	@Override
	public int getCount() {
		return administratives.size();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @方法： getItem
	 * 
	 * @描述：
	 * 
	 * @param position
	 * 
	 * @return
	 * 
	 * @see android.widget.Adapter#getItem(int)
	 */
	@Override
	public Object getItem(int position) {
		return administratives.get(position);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @方法： getItemId
	 * 
	 * @描述：
	 * 
	 * @param position
	 * 
	 * @return
	 * 
	 * @see android.widget.Adapter#getItemId(int)
	 */
	@Override
	public long getItemId(int position) {
		return position;
	}

	/**
	 * @类名： ViewHolder
	 * @描述： 列表布局控件
	 * @作者： 罗森
	 * @创建时间： 2013 2013-9-29 上午11:42:23
	 * @修改时间： 
	 * @修改描述：
	 */
	class ViewHolder {
		public TextView title;
		public TextView num;
		public TextView type;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @方法： getView
	 * 
	 * @描述：
	 * 
	 * @param position
	 * 
	 * @param convertView
	 * 
	 * @param parent
	 * 
	 * @return
	 * 
	 * @see android.widget.Adapter#getView(int, android.view.View,
	 * android.view.ViewGroup)
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(
					R.layout.administrative_list_layout, null);

			holder = new ViewHolder();

			holder.title = (TextView) convertView
					.findViewById(R.id.administrative_list_title);
			holder.num = (TextView) convertView
					.findViewById(R.id.administrative_list_num);
			holder.type = (TextView) convertView
					.findViewById(R.id.administrative_list_type);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		holder.title.setText("事项：" + administratives.get(position).getName());
		holder.num.setText("办件数：" + String.valueOf(administratives.get(position).getNum()));
		holder.type.setText("服务部门："
				+ administratives.get(position).getDeptname());

		return convertView;
	}

}
