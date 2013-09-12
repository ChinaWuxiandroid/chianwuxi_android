/**   
 * @公司: 重庆智佳信息科技有限公司
 * @文件: HotReviewListAdapter.java 
 * @包名： com.wuxi.app.adapter 
 * @描述: 热点话题列表适配器 
 * @作者： 罗森   
 * @创建时间： 2013 2013-9-11 下午4:10:03
 * @修改时间：  
 * @修改描述：
 * @版本： V1.0   
 */
package com.wuxi.app.adapter;

import java.util.List;

import com.wuxi.app.R;
import com.wuxi.domain.HotReviewWrapper.HotReview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * @类名： HotReviewListAdapter
 * @描述： 热点话题列表适配器类
 * @作者： 罗森
 * @创建时间： 2013 2013-9-11 下午4:10:03
 * @修改时间： 
 * @修改描述： 
 *
 */
public class HotReviewListAdapter extends BaseAdapter {
	
	private Context context;
	
	private List<HotReview> hotReviews;
	
	/**
	 * @方法： HotReviewListAdapter
	 * @描述： 构造方法
	 * @param context
	 * @param hotReviews
	 */
	public HotReviewListAdapter(Context context,List<HotReview> hotReviews){
		this.context = context;
		this.hotReviews = hotReviews;
	}
	
	/**
	 * @方法： addItem
	 * @描述： 添加数据到列表
	 * @param hotReview
	 */
	public void addItem(HotReview hotReview){
		this.hotReviews.add(hotReview);
	}

	public void setHotReviews(List<HotReview> hotReviews) {
		this.hotReviews = hotReviews;
	}

	@Override
	public int getCount() {
		return hotReviews.size();
	}

	@Override
	public Object getItem(int position) {
		return hotReviews.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	/**
	 * @类名： ViewHolder
	 * @描述： 列表布局控件
	 * @作者： 罗森
	 * @创建时间： 2013 2013-9-11 下午4:18:03
	 * @修改时间： 
	 * @修改描述： 
	 */
	class ViewHolder {
		public TextView title_text;
		public TextView startTime_text;
		public TextView endTime_text;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder = null;
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(
					R.layout.gip_hotreview_listview_item, null);

			viewHolder = new ViewHolder();

			viewHolder.title_text = (TextView) convertView
					.findViewById(R.id.gip_hottopic_listview_title);
			viewHolder.startTime_text = (TextView) convertView
					.findViewById(R.id.gip_hottopic_listview_beginTime);
			viewHolder.endTime_text = (TextView) convertView
					.findViewById(R.id.gip_hottopic_listview_endTime);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		viewHolder.title_text.setText(hotReviews.get(position).getTitle());
		viewHolder.startTime_text.setText(hotReviews.get(position)
				.getStartTime());
		viewHolder.endTime_text.setText(hotReviews.get(position)
				.getEndTime());

		return convertView;
	}

	
}
