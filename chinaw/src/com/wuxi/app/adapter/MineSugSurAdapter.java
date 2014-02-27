/**   
 * @公司: 重庆智佳信息科技有限公司
 * @文件: MineSugSurAdapter.java 
 * @包名： com.wuxi.app.adapter 
 * @描述: 我参与的网上调查适配器 
 * @作者： 罗森   
 * @创建时间： 2013 2013-10-14 下午5:43:32
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
import com.wuxi.domain.MineSugSurWapper.MineSugSur;

/**
 * @类名： MineSugSurAdapter
 * @描述： 我参与的网上调查适配器
 * @作者： 罗森
 * @创建时间： 2013 2013-10-14 下午5:43:32
 * @修改时间：
 * @修改描述：
 */
public class MineSugSurAdapter extends BaseAdapter {

	private Context context;
	private List<MineSugSur> mineSugSurs;

	/**
	 * @方法： MineSugSurAdapter
	 * @描述： 构造方法
	 * @param context
	 * @param mineSugSurs
	 */
	public MineSugSurAdapter(Context context, List<MineSugSur> mineSugSurs) {
		this.context = context;
		this.mineSugSurs = mineSugSurs;
	}

	/**
	 * @方法： addItem
	 * @描述： 添加数据到列表
	 * @param mineSugSur
	 */
	public void addItem(MineSugSur mineSugSur) {
		this.mineSugSurs.add(mineSugSur);
	}

	/**
	 * @param mineSugSurs
	 *            要设置的 mineSugSurs
	 */
	public void setMineSugSurs(List<MineSugSur> mineSugSurs) {
		this.mineSugSurs = mineSugSurs;
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
		return mineSugSurs.size();
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
		return mineSugSurs.get(position);
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
		TextView replyDpt;
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
					R.layout.gip_mine_sug_sur_list_layout, null);
			
			holder = new ViewHolder();
			
			holder.title = (TextView) convertView.findViewById(R.id.mine_sug_sur_title);
			holder.beginTime = (TextView) convertView.findViewById(R.id.mine_sug_sur_begintime);
			holder.endTime = (TextView) convertView.findViewById(R.id.mine_sug_sur_endtime);
			holder.replyDpt = (TextView) convertView.findViewById(R.id.mine_sug_sur_replydept);
			
			convertView.setTag(holder);
		}else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		holder.title.setText((position+1)+". 标题："+mineSugSurs.get(position).getTitle());
		holder.beginTime.setText("发布时间："+mineSugSurs.get(position).getStarttime());
		holder.endTime.setText("截至时间："+mineSugSurs.get(position).getEndtime());
		holder.replyDpt.setText("答复部门："+mineSugSurs.get(position).getReplydept());
		
		return convertView;
	}

}
