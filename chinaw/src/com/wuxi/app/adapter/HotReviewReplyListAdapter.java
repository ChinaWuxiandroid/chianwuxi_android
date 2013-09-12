/**   
 * @公司: 重庆智佳信息科技有限公司
 * @文件: HotReviewReplyListAdapter.java 
 * @包名： com.wuxi.app.adapter 
 * @描述: 热点话题回复列表适配器
 * @作者： 罗森   
 * @创建时间： 2013 2013-9-12 上午9:20:44
 * @修改时间：  
 * @修改描述：
 * @版本： V1.0   
 */
package com.wuxi.app.adapter;

import java.util.List;

import com.wuxi.app.R;
import com.wuxi.domain.HotReviewReplyWrapper.HotReviewReply;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * @类名： HotReviewReplyListAdapter
 * @描述： 热点话题回复列表适配器类
 * @作者： 罗森
 * @创建时间： 2013 2013-9-12 上午9:20:44
 * @修改时间：
 * @修改描述：
 */
public class HotReviewReplyListAdapter extends BaseAdapter {

	private Context context;

	private List<HotReviewReply> hotReviewReplies;

	/**
	 * @方法： HotReviewReplyListAdapter
	 * @描述： 构造方法
	 * @param context
	 * @param hotReviewReplies
	 */
	public HotReviewReplyListAdapter(Context context,List<HotReviewReply> hotReviewReplies){
		this.context = context;
		this.hotReviewReplies = hotReviewReplies;
	}
	
	public void setHotReviewReplies(List<HotReviewReply> hotReviewReplies) {
		this.hotReviewReplies = hotReviewReplies;
	}

	/**
	 * @方法： addItem
	 * @描述： 添加数据到列表
	 * @param hotReviewReply
	 */
	public void addItem(HotReviewReply hotReviewReply) {
		this.hotReviewReplies.add(hotReviewReply);
	}

	@Override
	public int getCount() {
		return hotReviewReplies.size();
	}

	@Override
	public Object getItem(int position) {
		return hotReviewReplies.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	/**
	 * @类名： ViewHolder
	 * @描述： 列表布局控件
	 * @作者： 罗森
	 * @创建时间： 2013 2013-9-12 上午9:26:02
	 * @修改时间： 
	 * @修改描述： 
	 */
	class ViewHolder {
		public TextView content;
		public TextView senduser;
		public TextView sendtime;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;

		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(
					R.layout.hot_review_reply_list_layout, null);
			holder = new ViewHolder();

			holder.content = (TextView) convertView
					.findViewById(R.id.hot_review_reply_content_text);
			holder.senduser = (TextView) convertView
					.findViewById(R.id.hot_review_reply_senduser_text);
			holder.sendtime = (TextView) convertView
					.findViewById(R.id.hot_review_sendtime_text);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		holder.content.setText(hotReviewReplies.get(position).getContent());
		holder.senduser.setText(hotReviewReplies.get(position).getSenduser());
		holder.sendtime.setText(hotReviewReplies.get(position).getSendtime());

		return convertView;
	}

}
