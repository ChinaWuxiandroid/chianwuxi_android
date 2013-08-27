/**   
 * @公司: 重庆智佳信息科技有限公司
 * @文件: MailTypeAdapter.java 
 * @包名： com.wuxi.app.adapter 
 * @描述: 信件查询 信件类型 下拉框适配器 
 * @作者： 罗森   
 * @创建时间： 2013 2013-8-27 下午5:31:59
 * @修改时间：  
 * @修改描述：
 * @版本： V1.0   
 */
package com.wuxi.app.adapter;

import java.util.List;

import com.wuxi.app.R;
import com.wuxi.domain.MailTypeWrapper.MailType;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.AdapterView.OnItemSelectedListener;

/**
 * @类名： MailTypeAdapter
 * @描述： 信件查询 信件类型 下拉框适配器
 * @作者： 罗森
 * @创建时间： 2013 2013-8-27 下午5:31:59
 * @修改时间：
 * @修改描述：
 * 
 */
public class MailTypeAdapter extends BaseAdapter implements
		OnItemSelectedListener {

	private List<MailType> mailTypes = null;

	private Context context = null;

	/**
	 * @方法： MailTypeAdapter
	 * @描述： 构造方法
	 * @param cont
	 * @param types
	 */
	public MailTypeAdapter(Context cont, List<MailType> types) {
		this.context = cont;
		this.mailTypes = types;
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
		return mailTypes.size();
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
		return mailTypes.get(position);
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
	 * @描述： 下拉列表布局
	 * @作者： 罗森
	 * @创建时间： 2013 2013-8-27 下午5:35:24
	 * @修改时间： 
	 * @修改描述： 
	 *
	 */
	public class ViewHolder {
		TextView tv_dept;
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
		ViewHolder viewHolder = null;

		if (convertView == null) {
			// 加载下拉列表布局文件
			convertView = View.inflate(context,
					R.layout.comstuom_spinner_item_layout, null);
			viewHolder = new ViewHolder();
			TextView tv = (TextView) convertView.findViewById(R.id.sp_tv);
			viewHolder.tv_dept = tv;
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		viewHolder.tv_dept.setText(mailTypes.get(position).getTypename());
		return convertView;
	}

	@Override
	public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
			long arg3) {

	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {

	}

}
