/**   
 * @公司: 重庆智佳信息科技有限公司
 * @文件: OrdinaryReplayListAdapter.java 
 * @包名： com.wuxi.app.adapter 
 * @描述: 论坛回复列表适配器类
 * @作者： 罗森   
 * @创建时间： 2013 2013-9-13 上午8:58:11
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
import com.wuxi.domain.ForumWrapper.Forum;
import com.wuxi.domain.HotPostWrapper.HotPostReplyWrapper.HotPostReply;
import com.wuxi.domain.NoticePostWrapper.NoticePostReplyWrapper.NoticePostReply;
import com.wuxi.domain.OpinionPostWrapper.OpinionPostReplyWrapper.OpinionPostReply;
import com.wuxi.domain.OrdinaryPostWrapper.OrdinaryPostRaplyWrapper.OrdinaryPostRaply;

/**
 * @类名： OrdinaryReplayListAdapter
 * @描述： 论坛回复列表适配器类
 * @作者： 罗森
 * @创建时间： 2013 2013-9-13 上午8:58:11
 * @修改时间：
 * @修改描述：
 */
public class OrdinaryReplayListAdapter extends BaseAdapter {

	private Context context;

	private List<OrdinaryPostRaply> postRaplies;
	private List<HotPostReply> hotPostReplies;
	private List<NoticePostReply> noticePostReplies;
	private List<OpinionPostReply> opinionPostReplies;
	private Forum forum;

	/**
	 * @方法： OrdinaryReplayListAdapter
	 * @描述： 构造方法，传递参数
	 * @param context
	 * @param postRaplies
	 * @param hotPostReplies
	 * @param noticePostReplies
	 * @param opinionPostReplies
	 * @param forum
	 */
	public OrdinaryReplayListAdapter(Context context,
			List<OrdinaryPostRaply> postRaplies,
			List<HotPostReply> hotPostReplies,
			List<NoticePostReply> noticePostReplies,
			List<OpinionPostReply> opinionPostReplies, Forum forum) {
		this.context = context;
		this.postRaplies = postRaplies;
		this.hotPostReplies = hotPostReplies;
		this.noticePostReplies = noticePostReplies;
		this.opinionPostReplies = opinionPostReplies;
		this.forum = forum;
	}

	/**
	 * @方法： addOrdinaryPostRaply
	 * @描述： 添加普通帖子回复数据到列表
	 * @param ordinaryPostRaply
	 */
	public void addOrdinaryPostRaply(OrdinaryPostRaply ordinaryPostRaply) {
		this.postRaplies.add(ordinaryPostRaply);
	}

	/**
	 * @方法： addHotPostReply
	 * @描述： 添加热点话题类帖子回复数据到列表
	 * @param hotPostReply
	 */
	public void addHotPostReply(HotPostReply hotPostReply) {
		this.hotPostReplies.add(hotPostReply);
	}

	/**
	 * @方法： addNoticePostReply
	 * @描述：添加 公告类帖子回复数据到列表
	 * @param noticePostReply
	 */
	public void addNoticePostReply(NoticePostReply noticePostReply) {
		this.noticePostReplies.add(noticePostReply);
	}

	/**
	 * @方法： addOpinionPostReply
	 * @描述： 添加征求意见类帖子回复数据到列表
	 * @param opinionPostReply
	 */
	public void addOpinionPostReply(OpinionPostReply opinionPostReply) {
		this.opinionPostReplies.add(opinionPostReply);
	}

	public void setPostRaplies(List<OrdinaryPostRaply> postRaplies) {
		this.postRaplies = postRaplies;
	}

	public void setHotPostReplies(List<HotPostReply> hotPostReplies) {
		this.hotPostReplies = hotPostReplies;
	}

	public void setNoticePostReplies(List<NoticePostReply> noticePostReplies) {
		this.noticePostReplies = noticePostReplies;
	}

	public void setOpinionPostReplies(List<OpinionPostReply> opinionPostReplies) {
		this.opinionPostReplies = opinionPostReplies;
	}

	@Override
	public int getCount() {
		if (forum.getViewpath().equals("/Get_UserBBSAnswerAll")) {
			return postRaplies.size();
		} else if (forum.getViewpath().equals("/HotReviewContent")) {
			return hotPostReplies.size();
		} else if (forum.getViewpath().equals("/LegislativeCommentsContent")) {
			return noticePostReplies.size();
		} else if (forum.getViewpath().equals("/JoinPoliticsContent")) {
			return opinionPostReplies.size();
		}

		return 0;
	}

	@Override
	public Object getItem(int position) {
		if (forum.getViewpath().equals("/Get_UserBBSAnswerAll")) {
			return postRaplies.get(position);
		} else if (forum.getViewpath().equals("/HotReviewContent")) {
			return hotPostReplies.get(position);
		} else if (forum.getViewpath().equals("/LegislativeCommentsContent")) {
			return noticePostReplies.get(position);
		} else if (forum.getViewpath().equals("/JoinPoliticsContent")) {
			return opinionPostReplies.get(position);
		}
		return null;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	/**
	 * @类名： ViewHolder
	 * @描述： 列表布局控件
	 * @作者： 罗森
	 * @创建时间： 2013 2013-9-13 上午9:15:51
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
					R.layout.ordinary_replay_list_layout, null);

			holder.replay_name_text = (TextView) convertView
					.findViewById(R.id.replay_name_text);
			holder.replay_time_text = (TextView) convertView
					.findViewById(R.id.replay_time_text);
			holder.replay_content_text = (TextView) convertView
					.findViewById(R.id.replay_content_text);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		// 普通帖子
		if (forum.getViewpath().equals("/Get_UserBBSAnswerAll")) {
			holder.replay_name_text.setText(postRaplies.get(position)
					.getUserName());
			holder.replay_time_text.setText(postRaplies.get(position)
					.getSentTime());
			holder.replay_content_text.setText(postRaplies.get(position)
					.getContent());
		}
		// 热点话题类帖子
		else if (forum.getViewpath().equals("/HotReviewContent")) {
			holder.replay_name_text.setText(hotPostReplies.get(position)
					.getSenduser());
			holder.replay_time_text.setText(hotPostReplies.get(position)
					.getSendtime());
			holder.replay_content_text.setText(hotPostReplies.get(position)
					.getContent());
		}
		// 公告类帖子
		else if (forum.getViewpath().equals("/LegislativeCommentsContent")) {
			holder.replay_name_text.setText(noticePostReplies.get(position)
					.getUsername());
			holder.replay_time_text.setText(noticePostReplies.get(position)
					.getSendtime());
			holder.replay_content_text.setText(noticePostReplies.get(position)
					.getContent());
		}
		// 征求意见类帖子
		else if (forum.getViewpath().equals("/JoinPoliticsContent")) {
			holder.replay_name_text.setText(opinionPostReplies.get(position)
					.getUserName());
			holder.replay_time_text.setText(opinionPostReplies.get(position)
					.getSentTime());
			holder.replay_content_text.setText(opinionPostReplies.get(position)
					.getContent());
		}
		return convertView;
	}

}
