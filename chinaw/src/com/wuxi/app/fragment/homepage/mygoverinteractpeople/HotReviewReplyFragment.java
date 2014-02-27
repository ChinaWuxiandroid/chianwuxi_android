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
import android.widget.Toast;

import com.wuxi.app.BaseFragment;
import com.wuxi.app.R;
import com.wuxi.app.adapter.HotReviewReplyListAdapter;
import com.wuxi.app.engine.HotReviewReplayService;
import com.wuxi.app.util.LogUtil;
import com.wuxi.domain.HotReviewReplyWrapper;
import com.wuxi.domain.HotReviewReplyWrapper.HotReviewReply;
import com.wuxi.domain.HotReviewWrapper.HotReview;
import com.wuxi.exception.NODataException;
import com.wuxi.exception.NetException;

/**
 * 政民互动 热点话题 网友留言与回复 碎片界面
 * 
 * @author 智佳 罗森
 * 
 */
public class HotReviewReplyFragment extends BaseFragment implements
		OnClickListener, OnScrollListener {

	private static final String TAG = "HotReviewReplyFragment";

	private View view = null;
	private Context context = null;

	private HotReview hotReview;

	private ListView mListView = null;

	private ProgressBar list_pb = null;

	private HotReviewReplyWrapper replyWrapper = null;
	private List<HotReviewReply> hotReviewReplies = null;

	private HotReviewReplyListAdapter adapter;

	private int start = 0;
	private int visibleLastIndex;
	private int visibleItemCount;// 当前显示的总条数
	private final static int PAGE_NUM = 10;

	private boolean isFirstLoad = true;// 是不是首次加载数据
	private boolean isSwitch = false;// 切换
	private boolean isLoading = false;

	private View loadMoreView;// 加载更多视图
	private Button loadMoreButton;
	private ProgressBar pb_loadmoore;

	// 数据加载成功标志
	private static final int DATA__LOAD_SUCESS = 0;
	// 数据加载失败标志
	private static final int DATA_LOAD_ERROR = 1;

	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			String tip = "";

			if (msg.obj != null) {
				tip = msg.obj.toString();
			}

			switch (msg.what) {
			case DATA__LOAD_SUCESS:

				showHotReviewReply();
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
		view = inflater.inflate(R.layout.hot_review_reply_layout, null);
		context = getActivity();

		initLayout();

		// 第一次加载数据
		loadFirstData(start, PAGE_NUM);

		return view;
	}

	/**
	 * @param hotReview
	 *            the hotReview to set
	 */
	public void setHotReview(HotReview hotReview) {
		this.hotReview = hotReview;
	}

	/**
	 * 初始化布局控件
	 */
	private void initLayout() {
		mListView = (ListView) view
				.findViewById(R.id.hot_review_reply_listview);

		list_pb = (ProgressBar) view
				.findViewById(R.id.hot_review_reply_progressbar);

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
				HotReviewReplayService replayService = new HotReviewReplayService(
						context);
				try {
					replyWrapper = replayService.getHotReviewReplyWrapper(
							hotReview.getId(), start, end);
					if (replyWrapper != null) {
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
	 * 显示列表
	 */
	private void showHotReviewReply() {
		hotReviewReplies = replyWrapper.getHotReviewReplies();

		if (hotReviewReplies == null || hotReviewReplies.size() == 0) {
			Toast.makeText(context, "对不起，暂无该话题回复信息", Toast.LENGTH_SHORT).show();
			list_pb.setVisibility(View.GONE);
		} else {
			if (isFirstLoad) {
				adapter = new HotReviewReplyListAdapter(context,
						hotReviewReplies);
				isFirstLoad = false;
				mListView.setAdapter(adapter);
				list_pb.setVisibility(View.GONE);
				isLoading = false;
			} else {
				if (isSwitch) {
					adapter.setHotReviewReplies(hotReviewReplies);
					list_pb.setVisibility(View.GONE);
				} else {
					for (HotReviewReply hotReviewReply : hotReviewReplies) {
						adapter.addItem(hotReviewReply);
					}
				}

				adapter.notifyDataSetChanged(); // 数据集变化后,通知adapter
				mListView.setSelection(visibleLastIndex - visibleItemCount + 1); // 设置选中项
				isLoading = false;
			}
		}

		if (replyWrapper.isNext()) {
			pb_loadmoore.setVisibility(ProgressBar.GONE);
			loadMoreButton.setText("点击加载更多");
		} else {
			if (adapter != null) {
				mListView.removeFooterView(loadMoreView);
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
			if (replyWrapper != null && replyWrapper.isNext()) {// 还有下一条记录

				isSwitch = false;
				loadMoreButton.setText("loading.....");
				loadMoreData(v);
			}
			break;
		}
	}

}
