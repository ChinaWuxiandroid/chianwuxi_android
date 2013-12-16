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
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.wuxi.app.BaseFragment;
import com.wuxi.app.R;
import com.wuxi.app.adapter.OrdinaryReplayListAdapter;
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
public class ForumOrdinaryReplayFragment extends BaseFragment implements
		OnClickListener, OnScrollListener {

	private static final String TAG = "ForumOrdinaryReplayFragment";

	private View view = null;
	private Context context = null;

	private ProgressBar list_pb = null;

	private ListView mListView = null;

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

	private OrdinaryReplayListAdapter adapter;

	// 数据加载成功标志
	private static final int DATA__LOAD_SUCESS = 0;
	// 数据加载失败标志
	private static final int DATA_LOAD_ERROR = 1;

	// 获取帖子的起始坐标
	private int startIndex = 0;
	private int visibleLastIndex;
	private int visibleItemCount;// 当前显示的总条数
	private final static int PAGE_NUM = 10;

	private boolean isFirstLoad = true;// 是不是首次加载数据
	private boolean isSwitch = false;// 切换
	private boolean isLoading = false;

	private View loadMoreView;// 加载更多视图
	private Button loadMoreButton;
	private ProgressBar pb_loadmoore;

	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			String tip = "";

			if (msg.obj != null) {
				tip = msg.obj.toString();
			}

			switch (msg.what) {
			case DATA__LOAD_SUCESS:
				showReplayList();
				break;

			case DATA_LOAD_ERROR:
				list_pb.setVisibility(View.VISIBLE);
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

		// 第一次加载数据
		loadFirstData(startIndex, PAGE_NUM);

		return view;
	}

	/**
	 * 初始化布局控件
	 */
	private void initLayout() {
		mListView = (ListView) view.findViewById(R.id.ordinary_replay_listview);

		list_pb = (ProgressBar) view
				.findViewById(R.id.ordinary_replay_progressbar);

		textView = (TextView) view.findViewById(R.id.forum_reply_content_text);

		loadMoreView = View.inflate(context, R.layout.list_loadmore_layout,
				null);
		loadMoreButton = (Button) loadMoreView
				.findViewById(R.id.loadMoreButton);
		pb_loadmoore = (ProgressBar) loadMoreView
				.findViewById(R.id.pb_loadmoore);

		mListView.addFooterView(loadMoreView);// 为listView添加底部视图
		mListView.setOnScrollListener(this);// 增加滑动监听
		loadMoreButton.setOnClickListener(this);
	}

	/**
	 * @方法： loadFirstData
	 * @描述： 第一次加载数据
	 * @param start
	 * @param end
	 */
	private void loadFirstData(int start, int end) {
		loadData(start, end);
	}

	/**
	 * 加载数据
	 */
	private void loadData(final int startIndex, final int endIndex) {
		if (isFirstLoad || isSwitch) {
			list_pb.setVisibility(View.VISIBLE);
		} else {
			pb_loadmoore.setVisibility(ProgressBar.VISIBLE);
		}

		new Thread(new Runnable() {

			@Override
			public void run() {

				try {
					// 普通帖子回复内容加载
					if (forum.getViewpath().equals("/Get_UserBBSAnswerAll")) {
						isLoading = true;// 正在加载数据
						Message message = handler.obtainMessage();
						OrdinaryPostService postService = new OrdinaryPostService(
								context);
						postWrapper = postService.getOrdinaryPostWrapper(
								forum.getId(), forum.getViewpath(), startIndex,
								endIndex);

						if (postWrapper != null) {
							postRaplyWrapper = postWrapper.getRaplyWrapper();

							handler.sendEmptyMessage(DATA__LOAD_SUCESS);
						} else {
							message.obj = "error";
							handler.sendEmptyMessage(DATA_LOAD_ERROR);
						}
					}
					// 热点话题帖子回复内容加载
					else if (forum.getViewpath().equals("/HotReviewContent")) {
						isLoading = true;// 正在加载数据
						Message message = handler.obtainMessage();
						HotPostService hotPostService = new HotPostService(
								context);
						hotPostWrapper = hotPostService.getHotPostWrapper(
								forum.getId(), forum.getViewpath(), startIndex,
								endIndex);
						if (hotPostWrapper != null) {
							hotPostReplyWrapper = hotPostWrapper
									.getHotPostReplyWrapper();

							handler.sendEmptyMessage(DATA__LOAD_SUCESS);
						} else {
							message.obj = "error";
							handler.sendEmptyMessage(DATA_LOAD_ERROR);
						}
					}
					// 公告类帖子回复内容加载
					else if (forum.getViewpath().equals(
							"/LegislativeCommentsContent")) {
						isLoading = true;// 正在加载数据
						Message message = handler.obtainMessage();
						NoticePostService noticePostService = new NoticePostService(
								context);
						noticePostWrapper = noticePostService
								.getNoticePostWrapper(forum.getId(),
										forum.getViewpath(), startIndex,
										endIndex);
						if (noticePostWrapper != null) {
							noticePostReplyWrapper = noticePostWrapper
									.getNoticePostReplyWrapper();

							handler.sendEmptyMessage(DATA__LOAD_SUCESS);
						} else {
							message.obj = "error";
							handler.sendEmptyMessage(DATA_LOAD_ERROR);
						}
					}
					// 征求意见类帖子回复内容加载
					else if (forum.getViewpath().equals("/JoinPoliticsContent")) {
						isLoading = true;// 正在加载数据
						OpinionPostService opinionPostService = new OpinionPostService(
								context);
						opinionPostWrapper = opinionPostService
								.getOpinionPostWrapper(forum.getId(),
										forum.getViewpath(), startIndex,
										endIndex);
						if (opinionPostWrapper != null) {
							opinionPostReplyWrapper = opinionPostWrapper
									.getOpinionPostReplyWrapper();

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
							mListView.setVisibility(View.GONE);
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
		// 普通帖子
		if (forum.getViewpath().equals("/Get_UserBBSAnswerAll")) {
			postRaplies = postRaplyWrapper.getPostRaplies();

			if (postRaplies == null || postRaplies.size() == 0) {
				Toast.makeText(context, "对不起，暂无该贴子的回复信息", Toast.LENGTH_SHORT)
						.show();
				list_pb.setVisibility(View.GONE);
			} else {
				if (isFirstLoad) {
					adapter = new OrdinaryReplayListAdapter(context,
							postRaplies, hotPostReplies, noticePostReplies,
							opinionPostReplies, forum);
					isFirstLoad = false;
					mListView.setAdapter(adapter);
					list_pb.setVisibility(View.GONE);
					isLoading = false;
				} else {
					if (isSwitch) {
						adapter.setPostRaplies(postRaplies);
						list_pb.setVisibility(View.GONE);
					} else {
						for (OrdinaryPostRaply ordinaryPostRaply : postRaplies) {
							adapter.addOrdinaryPostRaply(ordinaryPostRaply);
						}
					}

					adapter.notifyDataSetChanged(); // 数据集变化后,通知adapter
					mListView.setSelection(visibleLastIndex - visibleItemCount
							+ 1); // 设置选中项
					isLoading = false;
				}

			}

			if (postRaplyWrapper.isNext()) {
				pb_loadmoore.setVisibility(ProgressBar.GONE);
				loadMoreButton.setText("点击加载更多");
			} else {
				if (adapter != null) {
					mListView.removeFooterView(loadMoreView);
				}

			}

		}
		// 热点话题类帖子
		else if (forum.getViewpath().equals("/HotReviewContent")) {
			hotPostReplies = hotPostReplyWrapper.getHotPostReplies();

			if (hotPostReplies == null || hotPostReplies.size() == 0) {
				Toast.makeText(context, "对不起，暂无该论坛的回复信息", Toast.LENGTH_SHORT)
						.show();
				list_pb.setVisibility(View.GONE);
			} else {
				if (isFirstLoad) {
					adapter = new OrdinaryReplayListAdapter(context,
							postRaplies, hotPostReplies, noticePostReplies,
							opinionPostReplies, forum);
					isFirstLoad = false;
					mListView.setAdapter(adapter);
					list_pb.setVisibility(View.GONE);
					isLoading = false;
				} else {
					if (isSwitch) {
						adapter.setHotPostReplies(hotPostReplies);
						list_pb.setVisibility(View.GONE);
					} else {
						for (HotPostReply hotPostReply : hotPostReplies) {
							adapter.addHotPostReply(hotPostReply);
						}
					}

					adapter.notifyDataSetChanged(); // 数据集变化后,通知adapter
					mListView.setSelection(visibleLastIndex - visibleItemCount
							+ 1); // 设置选中项
					isLoading = false;
				}
			}

			if (hotPostReplyWrapper.isNext()) {
				pb_loadmoore.setVisibility(ProgressBar.GONE);
				loadMoreButton.setText("点击加载更多");
			} else {
				if (adapter != null) {
					mListView.removeFooterView(loadMoreView);
				}

			}
		}
		// 公告类帖子
		else if (forum.getViewpath().equals("/LegislativeCommentsContent")) {
			noticePostReplies = noticePostReplyWrapper.getNoticePostReplies();

			if (noticePostReplies == null || noticePostReplies.size() == 0) {
				Toast.makeText(context, "对不起，暂无该论坛的回复信息", Toast.LENGTH_SHORT)
						.show();
				list_pb.setVisibility(View.GONE);
			} else {
				if (isFirstLoad) {
					adapter = new OrdinaryReplayListAdapter(context,
							postRaplies, hotPostReplies, noticePostReplies,
							opinionPostReplies, forum);
					isFirstLoad = false;
					mListView.setAdapter(adapter);
					list_pb.setVisibility(View.GONE);
					isLoading = false;
				} else {
					if (isSwitch) {
						adapter.setNoticePostReplies(noticePostReplies);
						list_pb.setVisibility(View.GONE);
					} else {
						for (NoticePostReply noticePostReply : noticePostReplies) {
							adapter.addNoticePostReply(noticePostReply);
						}
					}

					adapter.notifyDataSetChanged(); // 数据集变化后,通知adapter
					mListView.setSelection(visibleLastIndex - visibleItemCount
							+ 1); // 设置选中项
					isLoading = false;
				}

			}

			if (noticePostReplyWrapper.isNext()) {
				pb_loadmoore.setVisibility(ProgressBar.GONE);
				loadMoreButton.setText("点击加载更多");
			} else {
				if (adapter != null) {
					mListView.removeFooterView(loadMoreView);
				}

			}
		}
		// 征求意见类帖子
		else if (forum.getViewpath().equals("/JoinPoliticsContent")) {
			opinionPostReplies = opinionPostReplyWrapper
					.getOpinionPostReplies();

			if (opinionPostReplies == null || opinionPostReplies.size() == 0) {
				Toast.makeText(context, "对不起，暂无该论坛的回复信息", Toast.LENGTH_SHORT)
						.show();
				list_pb.setVisibility(View.GONE);
			} else {
				if (isFirstLoad) {
					adapter = new OrdinaryReplayListAdapter(context,
							postRaplies, hotPostReplies, noticePostReplies,
							opinionPostReplies, forum);
					isFirstLoad = false;
					mListView.setAdapter(adapter);
					list_pb.setVisibility(View.GONE);
					isLoading = false;
				} else {
					if (isSwitch) {
						adapter.setOpinionPostReplies(opinionPostReplies);
						list_pb.setVisibility(View.GONE);
					} else {
						for (OpinionPostReply opinionPostReply : opinionPostReplies) {
							adapter.addOpinionPostReply(opinionPostReply);
						}
					}

					adapter.notifyDataSetChanged(); // 数据集变化后,通知adapter
					mListView.setSelection(visibleLastIndex - visibleItemCount
							+ 1); // 设置选中项
					isLoading = false;
				}
			}

			if (opinionPostReplyWrapper.isNext()) {
				pb_loadmoore.setVisibility(ProgressBar.GONE);
				loadMoreButton.setText("点击加载更多");
			} else {
				if (adapter != null) {
					mListView.removeFooterView(loadMoreView);
				}

			}
		}

	}

	/**
	 * @方法： loadMoreData
	 * @描述： 加载更多数据
	 * @param view
	 */
	private void loadMoreData(View view) {
		if (isLoading) {
			return;
		} else {
			loadData(visibleLastIndex + 1, visibleLastIndex + 1 + PAGE_NUM);
		}
	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
		this.visibleItemCount = visibleItemCount;
		visibleLastIndex = firstVisibleItem + visibleItemCount - 1;// 最后一条索引号
	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		int itemsLastIndex = adapter.getCount() - 1; // 数据集最后一项的索引
		int lastIndex = itemsLastIndex + 1; // 加上底部的loadMoreView项
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.loadMoreButton:
			if (postRaplyWrapper != null && postRaplyWrapper.isNext()) {// 还有下一条记录
				isSwitch = false;
				loadMoreButton.setText("loading.....");
				loadMoreData(v);
			} else if (hotPostReplyWrapper != null
					&& hotPostReplyWrapper.isNext()) {
				isSwitch = false;
				loadMoreButton.setText("loading.....");
				loadMoreData(v);
			} else if (noticePostReplyWrapper != null
					&& noticePostReplyWrapper.isNext()) {
				isSwitch = false;
				loadMoreButton.setText("loading.....");
				loadMoreData(v);
			} else if (opinionPostReplyWrapper != null
					&& opinionPostReplyWrapper.isNext()) {
				isSwitch = false;
				loadMoreButton.setText("loading.....");
				loadMoreData(v);
			}
			break;
		}
	}

}
