package com.wuxi.app.fragment.commonfragment;

import java.util.List;

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
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.wuxi.app.BaseFragment;
import com.wuxi.app.R;
import com.wuxi.app.dialog.LoginDialog;
import com.wuxi.domain.Channel;
import com.wuxi.domain.CommonDataWrapper;
import com.wuxi.domain.MenuItem;

public abstract class PagingLoadListFragment extends BaseFragment implements
		OnScrollListener, OnItemClickListener, OnClickListener {

	protected Context context;
	protected static final int FRAMELAYOUT_ID = R.id.paging_loading_list_fragmeLayout;
	protected static final int LIST_LOAD_SUCCESS = 0;
	protected static final int LIST_LOAD_FAIL = 1;
	private static final int PAGE_SIZE = 10;
	protected View view;
	protected ListView content_list_lv;

	private ProgressBar content_list_pb;
	private View loadMoreView;// 加载更多视图
	private Button loadMoreButton;
	private int visibleLastIndex;
	private int visibleItemCount;// 当前显示的总条数
	private boolean isSwitch = false;// 切换
	private boolean isFirstLoad = true;// 是不是首次加载数据
	private boolean isLoading = false;
	private ProgressBar pb_loadmoore;

	private CommonDataWrapper wrapper;// 内容
	private BaseAdapter adapter;
	protected List<Object> objects;

	private Handler handler = new Handler() {
		@SuppressLint("HandlerLeak")
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case LIST_LOAD_SUCCESS:
				// content_list_pb.setVisibility(ProgressBar.GONE);
				showContentData();
				break;
			case LIST_LOAD_FAIL:
				content_list_pb.setVisibility(ProgressBar.INVISIBLE);
				String tip = msg.obj.toString();
				Toast.makeText(context, tip, Toast.LENGTH_SHORT).show();
				break;
			}
		};
	};

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		view = inflater.inflate(R.layout.content_list_layout, null);
		context = getActivity();
		initUI();
		initData(0, PAGE_SIZE);// 加载数据
		return view;
	}

	/**
	 * 得到包装类 内部类 list对象
	 * */
	protected abstract List<Object> getContents();

	/**
	 * 得到其适配器对象
	 * */
	protected abstract BaseAdapter getAdapter();

	/**
	 * 适配器添加对象
	 * */
	protected abstract void addItem(Object object);

	/**
	 * 切换操作
	 * */
	protected abstract void switchContents();

	/**
	 * 得到包装类
	 * */
	protected abstract CommonDataWrapper getWarpper(int start, int end);

	/**
	 * 首次加载数据
	 */
	private void initData(final int start, final int end) {

		loadData(start, end);

	}

	public void loadData(final int start, final int end) {
		if (isFirstLoad || isSwitch) {
			content_list_pb.setVisibility(ProgressBar.VISIBLE);
		} else {
			pb_loadmoore.setVisibility(ProgressBar.VISIBLE);
		}
		new Thread(new Runnable() {

			@Override
			public void run() {
				isLoading = true;// 正在加载数据
				Message msg = handler.obtainMessage();

				try {

					System.out.println("测试1");
					wrapper = getWarpper(start, end);

					/*
					 * ContentService contentService = new
					 * ContentService(context); try {
					 * 
					 * String channelId = ""; if (channel != null) { channelId =
					 * channel.getChannelId(); } else if (parentItem != null) {
					 * channelId = parentItem.getChannelId(); } contentWrapper =
					 * contentService.getPageContentsById(channelId, start,
					 * end);
					 */
					// Looper.loop();
					if (wrapper != null) {
						msg.what = LIST_LOAD_SUCCESS;
					} else {
						msg.what = LIST_LOAD_FAIL;
						msg.obj = "内容获取错误,稍后重试";
					}
					handler.sendMessage(msg);
				} catch (Exception e) {
					e.printStackTrace();
					msg.what = LIST_LOAD_FAIL;
					System.out.println("测试2");
					msg.obj = "加载失败";
					handler.sendMessage(msg);
				}

			}
		}).start();
	}

	public void loadMore(View view) {
		if (isLoading) {
			return;
		} else {
			loadData(visibleLastIndex + 1, visibleLastIndex + 1 + PAGE_SIZE);
		}
	}

	/**
	 * 
	 * wanglu 泰得利通 初始化界面
	 */
	protected void initUI() {
		content_list_lv = (ListView) view.findViewById(R.id.content_list_lv);
		content_list_lv.setOnItemClickListener(this);
		content_list_pb = (ProgressBar) view.findViewById(R.id.content_list_pb);

		loadMoreView = View.inflate(context, R.layout.list_loadmore_layout,
				null);
		loadMoreButton = (Button) loadMoreView
				.findViewById(R.id.loadMoreButton);
		pb_loadmoore = (ProgressBar) loadMoreView
				.findViewById(R.id.pb_loadmoore);
		content_list_lv.addFooterView(loadMoreView);// 为listView添加底部视图
		content_list_lv.setOnScrollListener(this);// 增加滑动监听
		loadMoreButton.setOnClickListener(this);
	}

	protected MenuItem parentItem;

	public void setParentItem(MenuItem parentItem) {
		this.parentItem = parentItem;
	}

	protected Channel channel;

	public void setChannel(Channel channel) {
		this.channel = channel;
	}

	protected void showContentData() {

		LoginDialog dialog = new LoginDialog(context);

		if (!dialog.checkLogin()) {
			dialog.showDialog();
			content_list_pb.setVisibility(View.GONE);
		} else {
			objects = getContents();

			if (objects != null && objects.size() <= 0) {
				content_list_pb.setVisibility(ProgressBar.GONE);
				Toast.makeText(context, "没有信息或加载信息失败", Toast.LENGTH_SHORT)
						.show();
			} else {
				if (isFirstLoad) {

					isFirstLoad = false;
					adapter = getAdapter();
					content_list_lv.setAdapter(adapter);
					/*
					 * adapter = new ContentListAdapter(contents, context);
					 * content_list_lv.setAdapter(adapter);
					 */

					content_list_pb.setVisibility(ProgressBar.GONE);
					isLoading = false;
				} else {

					if (isSwitch) {
						switchContents();
						/*
						 * adapter.setContents(contents);
						 */

						content_list_pb.setVisibility(ProgressBar.GONE);
					} else {
						for (Object object : objects) {
							addItem(object);
							/*
							 * adapter.addItem(content);
							 */
						}
					}

					adapter.notifyDataSetChanged(); // 数据集变化后,通知adapter
					content_list_lv.setSelection(visibleLastIndex
							- visibleItemCount + 1); // 设置选中项
					isLoading = false;
				}
			}

			if (wrapper.isNext()) {
				pb_loadmoore.setVisibility(ProgressBar.GONE);
				loadMoreButton.setText("点击加载更多");
			} else {
				content_list_lv.removeFooterView(loadMoreView);
			}

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
		/*
		 * if (scrollState == OnScrollListener.SCROLL_STATE_IDLE &&
		 * visibleLastIndex == lastIndex) { if (contentWrapper != null &&
		 * contentWrapper.isNext()) {// 还有下一条记录
		 * 
		 * isSwitch = false; loadMoreButton.setText("loading.....");
		 * loadData(visibleLastIndex + 1, visibleLastIndex + 1 + PAGE_SIZE); } }
		 */

	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.loadMoreButton:
			if (wrapper != null && wrapper.isNext()) {// 还有下一条记录
				isSwitch = false;
				loadMoreButton.setText("loading.....");
				loadData(visibleLastIndex + 1, visibleLastIndex + 1 + PAGE_SIZE);
			}
			break;
		}
	}

	public void changeChannelOrMenItem(Channel channel, MenuItem menuItem) {

		this.isSwitch = true;
		this.channel = channel;
		this.parentItem = menuItem;
		loadData(0, PAGE_SIZE);
	}

}
