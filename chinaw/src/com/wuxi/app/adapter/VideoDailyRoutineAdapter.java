/**   
 * @公司: 重庆智佳信息科技有限公司
 * @文件: VideoDailyRoutineAdapter.java 
 * @包名： com.wuxi.app.adapter 
 * @描述: 日常安排列表适配器类
 * @作者： 罗森   
 * @创建时间： 2013 2013-9-25 下午3:32:43
 * @修改时间：  
 * @修改描述：
 * @版本： V1.0   
 */
package com.wuxi.app.adapter;

import java.util.List;

import com.wuxi.app.R;
import com.wuxi.app.util.TimeFormateUtil;
import com.wuxi.domain.Content;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * @类名： VideoDailyRoutineAdapter
 * @描述： 日常安排列表适配器类
 * @作者： 罗森
 * @创建时间： 2013 2013-9-25 下午3:32:43
 * @修改时间：
 * @修改描述：
 */
public class VideoDailyRoutineAdapter extends BaseAdapter {

	private Context context;

	private List<Content> contents;

	/**
	 * @方法： VideoDailyRoutineAdapter
	 * @描述： 构造方法
	 * @param context
	 * @param contents
	 */
	public VideoDailyRoutineAdapter(Context context, List<Content> contents) {
		this.context = context;
		this.contents = contents;
	}

	/**
	 * @方法： addItem
	 * @描述： 添加数据到列表
	 * @param content
	 */
	public void addItem(Content content) {
		this.contents.add(content);
	}

	/**
	 * @param contents
	 *            要设置的 contents
	 */
	public void setContents(List<Content> contents) {
		this.contents = contents;
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
		return contents.size();
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
		return contents.get(position);
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
	 * @描述： 列表布局控件类
	 * @作者： 罗森
	 * @创建时间： 2013 2013-9-25 下午4:33:19
	 * @修改时间： 
	 * @修改描述：
	 */
	class ViewHolder {
		public TextView title;
		public TextView time;
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
					R.layout.video_daily_routine_list_layout, null);
			holder = new ViewHolder();

			holder.title = (TextView) convertView
					.findViewById(R.id.video_daily_routine_list_title);
			holder.time = (TextView) convertView
					.findViewById(R.id.video_daily_routine_list_time);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		holder.title.setText("主题：" + contents.get(position).getTitle());
		holder.time.setText("时间："+TimeFormateUtil.formateTime(contents.get(position)
				.getPublishTime(), TimeFormateUtil.DATE_PATTERN));

		return convertView;
	}

}
