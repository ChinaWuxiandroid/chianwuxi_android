/**
 * 
 */
package com.wuxi.app.fragment.homepage.mygoverinteractpeople;

import java.util.List;

import org.json.JSONException;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.wuxi.app.BaseFragment;
import com.wuxi.app.R;
import com.wuxi.app.adapter.VedioReviewListAdapter;
import com.wuxi.app.engine.InterViewService;
import com.wuxi.app.engine.VedioReviewService;
import com.wuxi.app.util.LogUtil;
import com.wuxi.domain.VedioReviewWrapper;
import com.wuxi.domain.VedioReviewWrapper.VedioReview;
import com.wuxi.exception.NODataException;
import com.wuxi.exception.NetException;

/**
 * 走进直播间 往期回顾 碎片布局
 * 
 * @author 智佳 罗森
 * 
 */
public class VedioReviewFragment extends BaseFragment implements
		OnItemClickListener, OnClickListener, OnScrollListener {

	private static final String TAG = "VedioReviewFragment";

	private Context context;

	private View view;

	private ListView mListView = null;

	private ProgressBar list_pb = null;

	private VedioReviewWrapper reviewWrapper = null;

	private List<VedioReview> reviews = null;
	private String viewoURL;

	private VedioReviewListAdapter adapter;

	// 数据加载成功标志
	private static final int DATA_LOAD_SUCESS = 0;
	// 数据加载失败标志
	private static final int DATA_LOAD_ERROR = 1;

	// 获取提问留言的起始坐标
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

	private static final int LOAD_VIEDO_URL_SUCCESS = 2;

	private static final int LOAD_VIEDO_URL_FAIL = 3;

	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			String tip = "";

			if (msg.obj != null) {
				tip = msg.obj.toString();
			}

			switch (msg.what) {
			case DATA_LOAD_SUCESS:

				showReviewList();
				break;
			case LOAD_VIEDO_URL_SUCCESS:
				playVideo();
				break;
			case LOAD_VIEDO_URL_FAIL:

			case DATA_LOAD_ERROR:
				list_pb.setVisibility(View.GONE);
				Toast.makeText(context, tip, Toast.LENGTH_SHORT).show();
				break;
			}
		}
	};

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		view = inflater.inflate(R.layout.gip_vedio_review_layout, null);
		context = getActivity();

		initLayout();

		// 第一次加载数据
		loadFirstData(startIndex, PAGE_NUM);

		return view;
	}

	/**
	 * @方法： initLayout
	 * @描述： 初始化布局控件
	 */
	private void initLayout() {
		mListView = (ListView) view
				.findViewById(R.id.gip_vedio_review_listview);
		mListView.setOnItemClickListener(this);

		list_pb = (ProgressBar) view
				.findViewById(R.id.gip_vedio_review_progressbar);

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
				isLoading = true;// 正在加载数据
				Message message = handler.obtainMessage();
				VedioReviewService reviewService = new VedioReviewService(
						context);
				try {
					reviewWrapper = reviewService.getVedioReviewWrapper(
							startIndex, endIndex);
					if (reviewWrapper != null) {
						handler.sendEmptyMessage(DATA_LOAD_SUCESS);
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
	 * 显示列表数据
	 */
	private void showReviewList() {
		reviews = reviewWrapper.getReviews();

		if (reviews == null || reviews.size() == 0) {
			Toast.makeText(context, "对不起，暂无往期回顾信息", Toast.LENGTH_SHORT).show();
		} else {
			if (isFirstLoad) {
				adapter = new VedioReviewListAdapter(context, reviews);
				isFirstLoad = false;
				mListView.setAdapter(adapter);
				list_pb.setVisibility(View.GONE);
				isLoading = false;
			} else {
				if (isSwitch) {
					adapter.setReviews(reviews);
					list_pb.setVisibility(View.GONE);
				} else {
					for (VedioReview review : reviews) {
						adapter.addItem(review);
					}
				}

				adapter.notifyDataSetChanged(); // 数据集变化后,通知adapter
				mListView.setSelection(visibleLastIndex - visibleItemCount + 1); // 设置选中项
				isLoading = false;
			}
		}

		if (reviewWrapper.isNext()) {
			pb_loadmoore.setVisibility(ProgressBar.GONE);
			loadMoreButton.setText("点击加载更多");
		} else {
			if (adapter != null) {
				
			}
			mListView.removeFooterView(loadMoreView);
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

	/**
	 * 
	 * wanglu 泰得利通
	 */
	private void playVideo() {
		Intent it = new Intent();
		it.setAction(Intent.ACTION_VIEW);
		it.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		Uri uri = Uri.parse(viewoURL);
		it.setType("video/mp4");
		it.setDataAndType(uri, "video/mp4");
		startActivity(it);

	}

	@Override
	public void onItemClick(AdapterView<?> daAdapterView, View arg1,
			int position, long arg3) {
		final VedioReview view = (VedioReview) daAdapterView
				.getItemAtPosition(position);

		new Thread(new Runnable() {

			@Override
			public void run() {
				InterViewService interViewService = new InterViewService(
						context);
				Message msg = handler.obtainMessage();
				try {
					viewoURL = interViewService.getViewURL(view.getId());
					if (viewoURL != null) {
						msg.what = LOAD_VIEDO_URL_SUCCESS;
					} else {
						msg.what = LOAD_VIEDO_URL_FAIL;
						msg.obj = "获取视频地址失败";
					}
					handler.sendMessage(msg);
				} catch (NetException e) {
					msg.what = LOAD_VIEDO_URL_FAIL;
					msg.obj = e.getMessage();
					handler.sendMessage(msg);
					e.printStackTrace();
				} catch (JSONException e) {
					msg.what = LOAD_VIEDO_URL_FAIL;
					msg.obj = "数据格式不正确";
					handler.sendMessage(msg);
					e.printStackTrace();
				}

			}
		}).start();

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
			if (reviewWrapper != null && reviewWrapper.isNext()) {// 还有下一条记录
				isSwitch = false;
				loadMoreButton.setText("loading.....");
				loadMoreData(v);
			}
			break;
		}
	}
}
