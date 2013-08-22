/**
 * 
 */
package com.wuxi.app.fragment.homepage.mygoverinteractpeople;

import java.util.List;

import org.json.JSONException;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.wuxi.app.BaseFragment;
import com.wuxi.app.R;
import com.wuxi.app.engine.HotPostService;
import com.wuxi.app.engine.NoticePostService;
import com.wuxi.app.engine.OpinionPostService;
import com.wuxi.app.engine.OrdinaryPostService;
import com.wuxi.app.util.LogUtil;
import com.wuxi.domain.ForumWrapper.Forum;
import com.wuxi.domain.HotPostWrapper;
import com.wuxi.domain.HotPostWrapper.HotPostReplyWrapper;
import com.wuxi.domain.HotPostWrapper.HotPostReplyWrapper.HotPostReply;
import com.wuxi.domain.NoticePostWrapper;
import com.wuxi.domain.NoticePostWrapper.NoticePostReplyWrapper;
import com.wuxi.domain.NoticePostWrapper.NoticePostReplyWrapper.NoticePostReply;
import com.wuxi.domain.OpinionPostWrapper;
import com.wuxi.domain.OpinionPostWrapper.OpinionPostReplyWrapper;
import com.wuxi.domain.OpinionPostWrapper.OpinionPostReplyWrapper.OpinionPostReply;
import com.wuxi.domain.OrdinaryPostWrapper;
import com.wuxi.domain.OrdinaryPostWrapper.OrdinaryPostRaplyWrapper;
import com.wuxi.domain.OrdinaryPostWrapper.OrdinaryPostRaplyWrapper.OrdinaryPostRaply;
import com.wuxi.exception.NODataException;
import com.wuxi.exception.NetException;

/**
 * 公众论坛 帖子详细内容 普通帖子回复界面碎片
 * 
 * @author 智佳 罗森
 * 
 */
@SuppressLint({ "HandlerLeak", "ShowToast" })
public class ForumOrdinaryReplayFragment extends BaseFragment {

	private static final String TAG = "ForumOrdinaryReplayFragment";

	private View view = null;
	private Context context = null;

	private ProgressBar progressBar = null;

	private ListView listView = null;

	private TextView textView = null;

	private Forum forum;

	private OrdinaryPostWrapper postWrapper = null;
	private OrdinaryPostRaplyWrapper postRaplyWrapper = null;
	private List<OrdinaryPostRaply> postRaplies = null;

	private HotPostWrapper hotPostWrapper = null;
	private HotPostReplyWrapper hotPostReplyWrapper = null;
	private List<HotPostReply> hotPostReplies = null;

	private NoticePostWrapper noticePostWrapper = null;
	private NoticePostReplyWrapper noticePostReplyWrapper = null;
	private List<NoticePostReply> noticePostReplies = null;

	private OpinionPostWrapper opinionPostWrapper = null;
	private OpinionPostReplyWrapper opinionPostReplyWrapper = null;
	private List<OpinionPostReply> opinionPostReplies = null;

	// 数据加载成功标志
	private static final int DATA__LOAD_SUCESS = 0;
	// 数据加载失败标志
	private static final int DATA_LOAD_ERROR = 1;

	// 获取帖子的起始坐标
	private int startIndex = 0;
	// 获取帖子的结束坐标
	private int endIndex = 60;

	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			String tip = "";

			if (msg.obj != null) {
				tip = msg.obj.toString();
			}

			switch (msg.what) {
			case DATA__LOAD_SUCESS:
				progressBar.setVisibility(View.GONE);
				showReplayList();
				break;

			case DATA_LOAD_ERROR:
				Toast.makeText(context, tip, Toast.LENGTH_SHORT).show();
				break;
			}
		};
	};

	/**
	 * @param forum
	 *            the forum to set
	 */
	public void setForum(Forum forum) {
		this.forum = forum;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.forum_ordinary_replay_layout, null);
		context = getActivity();

		initLayout();

		return view;
	}

	/**
	 * 初始化布局控件
	 */
	private void initLayout() {
		listView = (ListView) view.findViewById(R.id.ordinary_replay_listview);

		progressBar = (ProgressBar) view
				.findViewById(R.id.ordinary_replay_progressbar);
		progressBar.setVisibility(View.VISIBLE);

		textView = (TextView) view.findViewById(R.id.forum_reply_content_text);

		loadData();
	}

	/**
	 * 加载数据
	 */
	private void loadData() {
		new Thread(new Runnable() {

			@Override
			public void run() {

				try {
					// 普通帖子回复内容加载
					if (forum.getViewpath().equals("/Get_UserBBSAnswerAll")) {
						OrdinaryPostService postService = new OrdinaryPostService(
								context);
						postWrapper = postService.getOrdinaryPostWrapper(
								forum.getId(), forum.getViewpath(), startIndex,
								endIndex);

						if (postWrapper != null) {
							postRaplyWrapper = postWrapper.getRaplyWrapper();
							postRaplies = postRaplyWrapper.getPostRaplies();
							handler.sendEmptyMessage(DATA__LOAD_SUCESS);
						} else {
							Message message = handler.obtainMessage();
							message.obj = "error";
							handler.sendEmptyMessage(DATA_LOAD_ERROR);
						}
					}
					// 热点话题帖子回复内容加载
					else if (forum.getViewpath().equals("/HotReviewContent")) {
						HotPostService hotPostService = new HotPostService(
								context);
						hotPostWrapper = hotPostService.getHotPostWrapper(
								forum.getId(), forum.getViewpath(), startIndex,
								6);
						if (hotPostWrapper != null) {
							hotPostReplyWrapper = hotPostWrapper
									.getHotPostReplyWrapper();
							hotPostReplies = hotPostReplyWrapper
									.getHotPostReplies();
							handler.sendEmptyMessage(DATA__LOAD_SUCESS);
						} else {
							Message message = handler.obtainMessage();
							message.obj = "error";
							handler.sendEmptyMessage(DATA_LOAD_ERROR);
						}
					}
					// 公告类帖子回复内容加载
					else if (forum.getViewpath().equals(
							"/LegislativeCommentsContent")) {
						NoticePostService noticePostService = new NoticePostService(
								context);
						noticePostWrapper = noticePostService
								.getNoticePostWrapper(forum.getId(),
										forum.getViewpath(), startIndex,
										endIndex);
						if (noticePostWrapper != null) {
							noticePostReplyWrapper = noticePostWrapper
									.getNoticePostReplyWrapper();
							noticePostReplies = noticePostReplyWrapper
									.getNoticePostReplies();
							handler.sendEmptyMessage(DATA__LOAD_SUCESS);
						} else {
							Message message = handler.obtainMessage();
							message.obj = "error";
							handler.sendEmptyMessage(DATA_LOAD_ERROR);
						}
					}
					// 征求意见类帖子回复内容加载
					else if (forum.getViewpath().equals("/JoinPoliticsContent")) {
						OpinionPostService opinionPostService = new OpinionPostService(
								context);
						opinionPostWrapper = opinionPostService
								.getOpinionPostWrapper(forum.getId(),
										forum.getViewpath(), startIndex,
										endIndex);
						if (opinionPostWrapper != null) {
							opinionPostReplyWrapper = opinionPostWrapper
									.getOpinionPostReplyWrapper();
							opinionPostReplies = opinionPostReplyWrapper
									.getOpinionPostReplies();
							handler.sendEmptyMessage(DATA__LOAD_SUCESS);
						} else {
							Message message = handler.obtainMessage();
							message.obj = "error";
							handler.sendEmptyMessage(DATA_LOAD_ERROR);
						}
					}

					else if (forum.getViewpath().equals("/SurveryContent")) {
						if (true) {
							textView.setVisibility(View.VISIBLE);
							textView.setText("该页面还无法显示");
							listView.setVisibility(View.GONE);
							handler.sendEmptyMessage(DATA__LOAD_SUCESS);
						}
					}
				} catch (NetException e) {
					LogUtil.i(TAG, "出错");
					e.printStackTrace();
					Message message = handler.obtainMessage();
					message.obj = e.getMessage();
					handler.sendEmptyMessage(DATA_LOAD_ERROR);

				} catch (JSONException e) {
					e.printStackTrace();
				} catch (NODataException e) {
					e.printStackTrace();
				}
			}
		}).start();
	}

	/**
	 * 显示回复数据列表
	 */
	private void showReplayList() {

		OrdinaryReplayListAdapter ordinaryReplayListAdapter = new OrdinaryReplayListAdapter();

		if (forum.getViewpath().equals("/Get_UserBBSAnswerAll")) {
			if (postRaplies == null || postRaplies.size() == 0) {
				Toast.makeText(context, "对不起，暂无该论坛的回复信息", 2000).show();
			} else {
				listView.setAdapter(ordinaryReplayListAdapter);
			}
		} else if (forum.getViewpath().equals("/HotReviewContent")) {
			if (hotPostReplies == null || hotPostReplies.size() == 0) {
				Toast.makeText(context, "对不起，暂无该论坛的回复信息", 2000).show();
			} else {
				listView.setAdapter(ordinaryReplayListAdapter);
			}
		} else if (forum.getViewpath().equals("/LegislativeCommentsContent")) {
			if (hotPostReplies == null || hotPostReplies.size() == 0) {
				Toast.makeText(context, "对不起，暂无该论坛的回复信息", 2000).show();
			} else {
				listView.setAdapter(ordinaryReplayListAdapter);
			}
		} else if (forum.getViewpath().equals("/JoinPoliticsContent")) {
			if (opinionPostReplies == null || opinionPostReplies.size() == 0) {
				Toast.makeText(context, "对不起，暂无该论坛的回复信息", 2000).show();
			} else {
				listView.setAdapter(ordinaryReplayListAdapter);
			}
		}

	}

	/**
	 * 内部类，普通帖子回复列表适配器
	 * 
	 * @author 智佳 罗森
	 * 
	 */
	public class OrdinaryReplayListAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			if (forum.getViewpath().equals("/Get_UserBBSAnswerAll")) {
				return postRaplies.size();
			} else if (forum.getViewpath().equals("/HotReviewContent")) {
				return hotPostReplies.size();
			} else if (forum.getViewpath()
					.equals("/LegislativeCommentsContent")) {
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
			} else if (forum.getViewpath()
					.equals("/LegislativeCommentsContent")) {
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
				holder.replay_content_text.setText(noticePostReplies.get(
						position).getContent());
			}
			// 征求意见类帖子
			else if (forum.getViewpath().equals("/JoinPoliticsContent")) {
				holder.replay_name_text.setText(opinionPostReplies
						.get(position).getUserName());
				holder.replay_time_text.setText(opinionPostReplies
						.get(position).getSentTime());
				holder.replay_content_text.setText(opinionPostReplies.get(
						position).getContent());
			}
			return convertView;
		}

	}

}
