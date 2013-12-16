/**   
 * @公司: 重庆智佳信息科技有限公司
 * @文件: InternetPoliticsListAdapter.java 
 * @包名： com.wuxi.app.adapter 
 * @描述: 网上调查列表适配器
 * @作者： 罗森   
 * @创建时间： 2013 2013-9-9 上午10:32:29
 * @修改时间：  
 * @修改描述：
 * @版本： V1.0   
 */
package com.wuxi.app.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.wuxi.app.R;
import com.wuxi.domain.InternetSurveyWrapper.InternetSurvey;

/**
 * @类名： InternetPoliticsListAdapter
 * @描述： 网上调查列表适配器类
 * @作者： 罗森
 * @创建时间： 2013 2013-9-9 上午10:32:29
 * @修改时间： 
 * @修改描述： 
 *
 */
public class InternetPoliticsListAdapter extends BaseAdapter{
	
	private Context context;
	
	private List<InternetSurvey> internetSurveys;
	
	/**
	 * @方法： InternetPoliticsListAdapter
	 * @描述： 构造方法
	 * @param context
	 * @param internetSurveys
	 */
	public InternetPoliticsListAdapter(Context context,List<InternetSurvey> internetSurveys){
		this.context = context;
		this.internetSurveys = internetSurveys;
	}
	
	/**
	 * @param internetSurveys 要设置的  internetSurveys 
	 */
	public void setInternetSurveys(List<InternetSurvey> internetSurveys) {
		this.internetSurveys = internetSurveys;
	}

	/**
	 * @方法： addItem
	 * @描述： 添加网上调查到列表
	 * @param internetSurvey
	 */
	public void addItem(InternetSurvey internetSurvey){
		internetSurveys.add(internetSurvey);
	}
	
	@Override
	public int getCount() {
		return internetSurveys.size();
	}

	@Override
	public Object getItem(int position) {
		return internetSurveys.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	/**
	 * @类名： ViewHolder
	 * @描述： 列表布局控件
	 * @作者： 罗森
	 * @创建时间： 2013 2013-9-9 上午10:40:17
	 * @修改时间： 
	 * @修改描述： 
	 *
	 */
	class ViewHolder {
		public TextView title_text;
		public TextView beginTime_text;
		public TextView endTime_text;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder = null;
		if(convertView==null){
			convertView = LayoutInflater.from(context).inflate(
					R.layout.internetsurvey_list_layout, null);

			viewHolder = new ViewHolder();

			viewHolder.title_text = (TextView) convertView
					.findViewById(R.id.internet_content_text);
			viewHolder.beginTime_text = (TextView) convertView
					.findViewById(R.id.internet_begintime_text);
			viewHolder.endTime_text = (TextView) convertView
					.findViewById(R.id.internet_endtime_text);

			convertView.setTag(viewHolder);
		}
		else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		
		
		viewHolder.title_text.setText(internetSurveys.get(position).getTitle());	
		viewHolder.beginTime_text.setText(internetSurveys.get(position).getCreateDate());
		viewHolder.endTime_text.setText(internetSurveys.get(position).getEndDate());
		
		return convertView;
	}


}
