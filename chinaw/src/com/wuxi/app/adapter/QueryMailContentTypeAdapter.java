/**   
 * @公司: 重庆智佳信息科技有限公司
 * @文件: QueryMailContentTypeAdapter.java 
 * @包名： com.wuxi.app.adapter 
 * @描述: 信件查询内容类型下拉框适配器
 * @作者： 罗森   
 * @创建时间： 2013 2013-8-27 下午4:34:42
 * @修改时间：  
 * @修改描述：
 * @版本： V1.0   
 */
package com.wuxi.app.adapter;

import java.util.List;

import com.wuxi.app.R;
import com.wuxi.domain.QueryMailContentTypeWrapper.QueryMailContentType;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.BaseAdapter;

/**
 * @类名： QueryMailContentTypeAdapter
 * @描述： 信件查询内容类型下拉框适配器
 * @作者： 罗森
 * @创建时间： 2013 2013-8-27 下午4:34:42
 * @修改时间：
 * @修改描述：
 */
public class QueryMailContentTypeAdapter extends BaseAdapter implements
		OnItemSelectedListener {

	private List<QueryMailContentType> contentTypes = null;

	private Context context = null;

	/**
	 * @方法： QueryMailContentTypeAdapter
	 * @描述： 构造方法
	 * @param cont
	 */
	public QueryMailContentTypeAdapter(Context cont,
			List<QueryMailContentType> mailContentTypes) {
		this.context = cont;
		this.contentTypes = mailContentTypes;
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
		return contentTypes.size();
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
		return contentTypes.get(position);
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
	 * @创建时间： 2013 2013-8-27 下午4:43:55
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

		viewHolder.tv_dept.setTextColor(Color.BLACK);
		viewHolder.tv_dept.setTextSize(12);
		viewHolder.tv_dept.setText(contentTypes.get(position).getTypename());

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
