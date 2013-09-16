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
import android.view.View.OnClickListener;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.widget.AbsListView.OnScrollListener;

import com.wuxi.app.BaseFragment;
import com.wuxi.app.R;
import com.wuxi.app.adapter.LegidlstionReplyListAdapter;
import com.wuxi.app.engine.LegislationReplyService;
import com.wuxi.app.util.LogUtil;
import com.wuxi.domain.NoticePostWrapper.NoticePostReplyWrapper;
import com.wuxi.domain.NoticePostWrapper.NoticePostReplyWrapper.NoticePostReply;
import com.wuxi.domain.PoliticsWrapper.Politics;
import com.wuxi.exception.NODataException;
import com.wuxi.exception.NetException;

/**
 * 征求意见平台 立法征求意见 详情回复界面
 * 
 * @author 智佳 罗森
 * 
 */
public class LegidlstionReplyFragment extends BaseFragment implements
		OnClickListener, OnScrollListener {

	private static final String TAG = "LegidlstionReplyFragment";

	// private NoticePostWrapper noticePostWrapper = null;
	private NoticePostReplyWrapper noticePostReplyWrapper = null;
	private List<NoticePostReply> noticePostReplies = null;

	private View view = null;
	private Context context = null;

	private ProgressBar list_pb = null;

	private ListView mListView = null;

	private Politics politics = null;

	private LegidlstionReplyListAdapter adapter;

	// 数据加载成功标志
	private static final int DATA__LOAD_SUCESS = 0;
	// 数据加载失败标志
	private static final int DATA_LOAD_ERROR = 1;

	private int visibleLastIndex;
	private int visibleItemCount;// 当前显示的总条数
	private final static int PAGE_NUM = 10;

	private boolean isFirstLoad = true;// 是不是首次加载数据
	private boolean isSwitch = false;// 切换
	private boolean isLoading = false;

	private View loadMoreView;// 加载更多视图
	private Button loadMoreButton;
	private ProgressBar pb_loadmoore;

	/**
	 * @param politics
	 *            the politics to set
	 */
	public void setPolitics(Politics politics) {
		this.politics = politics;
	}

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

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.forum_ordinary_replay_layout, null);
		context = getActivity();

		initLayout();

		loadFirstData(0, PAGE_NUM);

		return view;
	}

	/**
	 * 初始化布局控件
	 */
	private void initLayout() {
		mListView = (ListView) view.findViewById(R.id.ordinary_replay_listview);

		list_pb = (ProgressBar) view
				.findViewById(R.id.ordinary_replay_progressbar);

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
	private void loadData(final int start, final int end) {
		if (isFirstLoad || isSwitch) {
			list_pb.setVisibility(View.VISIBLE);
		} else {
			pb_loadmoore.setVisibility(ProgressBar.VISIBLE);
		}

		new Thread(new Runnable() {

			@Override
			public void run() {
				isLoading = true;// 正在加载数据
				Message message = handler.obtainMessage();
				LegislationReplyService service = new LegislationReplyService(
						context);
				try {
					noticePostReplyWrapper = service.getNoticePostReplyWrapper(
							politics.getId(), start, end);

					if (noticePostReplyWrapper != null) {
						handler.sendEmptyMessage(DATA__LOAD_SUCESS);
					} else {
						message.obj = "error";
						handler.sendEmptyMessage(DATA_LOAD_ERROR);
					}
				} catch (NetException e) {
					LogUtil.i(TAG, "出错");
					e.printStackTrace();
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

	/**
	 * 显示回复数据列表
	 */
	private void showReplayList() {

		noticePostReplies = noticePostReplyWrapper.getNoticePostReplies();

		if (noticePostReplies == null || noticePostReplies.size() == 0) {
			Toast.makeText(context, "对不起，暂无该主题的回复信息", Toast.LENGTH_SHORT)
					.show();
			list_pb.setVisibility(View.GONE);
		} else {
			if (isFirstLoad) {
				adapter = new LegidlstionReplyListAdapter(context,
						noticePostReplies);
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
						adapter.addItem(noticePostReply);
					}
				}

				adapter.notifyDataSetChanged(); // 数据集变化后,通知adapter
				mListView.setSelection(visibleLastIndex - visibleItemCount + 1); // 设置选中项
				isLoading = false;
			}

		}

		if (noticePostReplyWrapper.isNext()) {
			pb_loadmoore.setVisibility(ProgressBar.GONE);
			loadMoreButton.setText("点击加载更多");
		} else {
			mListView.removeFooterView(loadMoreView);
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
			if (noticePostReplyWrapper != null
					&& noticePostReplyWrapper.isNext()) {// 还有下一条记录

				isSwitch = false;
				loadMoreButton.setText("loading.....");
				loadMoreData(v);
			}
			break;
		}
	}

}
