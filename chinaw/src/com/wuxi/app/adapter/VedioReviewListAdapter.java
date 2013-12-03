/**   
 * @公司: 重庆智佳信息科技有限公司
 * @文件: VedioReviewListAdapter.java 
 * @包名： com.wuxi.app.adapter 
 * @描述: 往期回顾列表适配器
 * @作者： 罗森   
 * @创建时间： 2013 2013-9-11 上午10:13:05
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
import com.wuxi.domain.VedioReviewWrapper.VedioReview;

/**
 * @类名： VedioReviewListAdapter
 * @描述： 往期回顾列表适配器类
 * @作者： 罗森
 * @创建时间： 2013 2013-9-11 上午10:13:05
 * @修改时间：
 * @修改描述：
 * 
 */
public class VedioReviewListAdapter extends BaseAdapter {

	private Context context;

	private List<VedioReview> reviews;

	/**
	 * @方法： VedioReviewListAdapter
	 * @描述： 构造方法
	 * @param context
	 * @param reviews
	 */
	public VedioReviewListAdapter(Context context, List<VedioReview> reviews) {
		this.context = context;
		this.reviews = reviews;
	}

	/**
	 * @方法： addItem
	 * @描述： 添加数据到列表
	 * @param review
	 */
	public void addItem(VedioReview review) {
		this.reviews.add(review);
	}

	public void setReviews(List<VedioReview> reviews) {
		this.reviews = reviews;
	}

	@Override
	public int getCount() {
		return reviews.size();
	}

	@Override
	public Object getItem(int position) {
		return reviews.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	/**
	 * 内部类，布局控件声明类
	 * 
	 * @author 智佳 罗森
	 * 
	 */
	class Holder {
		public TextView review_title_text;
		public TextView review_time_text;
		public TextView review_guest_text;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Holder holder = null;

		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(
					R.layout.video_review_list_layout, null);

			holder = new Holder();

			holder.review_title_text = (TextView) convertView
					.findViewById(R.id.review_title_text);
			holder.review_time_text = (TextView) convertView
					.findViewById(R.id.review_time_text);
			holder.review_guest_text = (TextView) convertView
					.findViewById(R.id.review_guest_text);

			convertView.setTag(holder);
		} else {
			holder = (Holder) convertView.getTag();
		}

		holder.review_title_text.setText("访问主题："
				+ reviews.get(position).getSubject());
		holder.review_time_text.setText("访问时间："
				+ reviews.get(position).getWorkDate());
		holder.review_guest_text.setText("访问嘉宾："
				+ reviews.get(position).getGuests());

		return convertView;
	}

}
