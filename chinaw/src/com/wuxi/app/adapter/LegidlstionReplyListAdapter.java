/**   
 * @公司: 重庆智佳信息科技有限公司
 * @文件: LegidlstionReplyListAdapter.java 
 * @包名： com.wuxi.app.adapter 
 * @描述: 民意征集回复列表适配器
 * @作者： 罗森   
 * @创建时间： 2013 2013-9-13 上午10:06:31
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
import com.wuxi.domain.NoticePostWrapper.NoticePostReplyWrapper.NoticePostReply;

/**
 * @类名： LegidlstionReplyListAdapter
 * @描述： 民意征集回复列表适配器类
 * @作者： 罗森
 * @创建时间： 2013 2013-9-13 上午10:06:31
 * @修改时间：
 * @修改描述：
 */
public class LegidlstionReplyListAdapter extends BaseAdapter {

	private Context context;

	private List<NoticePostReply> noticePostReplies;

	/**
	 * @方法： LegidlstionReplyListAdapter
	 * @描述： 构造方法
	 * @param context
	 * @param noticePostReplies
	 */
	public LegidlstionReplyListAdapter(Context context,
			List<NoticePostReply> noticePostReplies) {
		this.context = context;
		this.noticePostReplies = noticePostReplies;
	}

	/**
	 * @方法： setNoticePostReplies
	 * @描述： 设置列表数据
	 * @param noticePostReplies
	 */
	public void setNoticePostReplies(List<NoticePostReply> noticePostReplies) {
		this.noticePostReplies = noticePostReplies;
	}

	/**
	 * @方法： addItem
	 * @描述： 添加数据啊到列表
	 * @param noticePostReply
	 */
	public void addItem(NoticePostReply noticePostReply) {
		this.noticePostReplies.add(noticePostReply);
	}

	@Override
	public int getCount() {
		return noticePostReplies.size();
	}

	@Override
	public Object getItem(int position) {
		return noticePostReplies.get(position);

	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	/**
	 * @类名： ViewHolder
	 * @描述： 列表布局控件
	 * @作者： 罗森
	 * @创建时间： 2013 2013-9-13 上午10:12:13
	 * @修改时间：
	 * @修改描述：
	 */
	class ViewHolder {
		public TextView replay_name_text;
		public TextView replay_time_text;
		public TextView replay_content_text;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			holder = new ViewHolder();

			convertView = LayoutInflater.from(context).inflate(
					R.layout.legidlstion_reply_list_layout, null);

			holder.replay_name_text = (TextView) convertView
					.findViewById(R.id.reply_name_text);
			holder.replay_time_text = (TextView) convertView
					.findViewById(R.id.reply_time_text);
			holder.replay_content_text = (TextView) convertView
					.findViewById(R.id.reply_content_text);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		holder.replay_name_text.setText(noticePostReplies.get(position)
				.getUsername());
		holder.replay_time_text.setText(noticePostReplies.get(position)
				.getSendtime());
		holder.replay_content_text.setText(noticePostReplies.get(position)
				.getContent());
		return convertView;
	}

}
