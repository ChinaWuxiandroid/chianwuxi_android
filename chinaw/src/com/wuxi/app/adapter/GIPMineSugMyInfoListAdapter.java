/**   
 * @公司: 重庆智佳信息科技有限公司
 * @文件: GIPMineSugMyInfoListAdapter.java 
 * @包名： com.wuxi.app.adapter 
 * @描述: 我的政民互动 征求意见平台 我参与的民意征集和立法征求意见 列表适配器
 * @作者： 罗森   
 * @创建时间： 2013 2013-10-14 下午2:57:35
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
import com.wuxi.domain.PoliticsWrapper.Politics;

/**
 * @类名： GIPMineSugMyInfoListAdapter
 * @描述： 我的政民互动 征求意见平台 我参与的民意征集和立法征求意见 列表适配器
 * @作者： 罗森
 * @创建时间： 2013 2013-10-14 下午2:57:35
 * @修改时间：
 * @修改描述：
 */
public class GIPMineSugMyInfoListAdapter extends BaseAdapter {

	private Context context;
	private List<Politics> politicss;

	/**
	 * @方法： GIPMineSugMyInfoListAdapter
	 * @描述： 构造方法
	 * @param context
	 * @param politicss
	 */
	public GIPMineSugMyInfoListAdapter(Context context, List<Politics> politicss) {
		this.context = context;
		this.politicss = politicss;
	}

	/**
	 * @方法： addItem
	 * @描述： 添加数据到列表
	 * @param politics
	 */
	public void addItem(Politics politics) {
		this.politicss.add(politics);
	}

	/**
	 * @param politics
	 *            要设置的 politics
	 */
	public void setPolitics(List<Politics> politicss) {
		this.politicss = politicss;
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
		return politicss.size();
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
		return politicss.get(position);
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

	class ViewHolder {
		TextView title;
		TextView beginTime;
		TextView endTime;
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
					R.layout.gip_mine_suggestion_my_info_list_layout, null);

			holder = new ViewHolder();

			holder.title = (TextView) convertView
					.findViewById(R.id.gip_mine_sug_my_info_title);
			holder.beginTime = (TextView) convertView
					.findViewById(R.id.gip_mine_sug_my_info_begintime);
			holder.endTime = (TextView) convertView
					.findViewById(R.id.gip_mine_sug_my_info_endtime);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		holder.title.setText((position + 1) + ". 标题："
				+ politicss.get(position).getTitle());
		holder.beginTime.setText("发布时间："
				+ politicss.get(position).getBeginTime());
		holder.endTime.setText("截至时间：" + politicss.get(position).getEndTime());

		return convertView;
	}

}
