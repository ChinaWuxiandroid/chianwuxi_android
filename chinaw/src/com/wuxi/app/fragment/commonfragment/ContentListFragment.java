package com.wuxi.app.fragment.commonfragment;

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
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.wuxi.app.BaseFragment;
import com.wuxi.app.R;
import com.wuxi.app.adapter.ContentListAdapter;
import com.wuxi.app.engine.ContentService;
import com.wuxi.domain.Channel;
import com.wuxi.domain.Content;
import com.wuxi.domain.ContentWrapper;
import com.wuxi.domain.MenuItem;
import com.wuxi.exception.NODataException;
import com.wuxi.exception.NetException;

/**
 * 
 * @author wanglu 泰得利通 内容列表父类
 * 
 */
@SuppressLint("HandlerLeak")
public abstract class ContentListFragment extends BaseFragment implements
		OnScrollListener, OnItemClickListener, OnClickListener {

	protected static final int CONTENT_LOAD_SUCCESS = 0;
	protected static final int CONTENT_LOAD_FAIL = 1;
	private static final int PAGE_SIZE = 10;
	protected View view;
	protected ListView content_list_lv;
	private ProgressBar content_list_pb;
	private ContentWrapper contentWrapper;// 内容
	private View loadMoreView;// 加载更多视图
	private Button loadMoreButton;
	private Context context;
	private int visibleLastIndex;
	private int visibleItemCount;// 当前显示的总条数
	private ContentListAdapter adapter;
	private boolean isSwitch = false;// 切换
	private boolean isFirstLoad = true;// 是不是首次加载数据
	private boolean isLoading = false;
	private ProgressBar pb_loadmoore;
	protected MenuItem parentItem;
	protected Channel channel;
	private Handler handler = new Handler() {
		@SuppressLint("HandlerLeak")
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case CONTENT_LOAD_SUCCESS:
				showContentData();
				break;
			case CONTENT_LOAD_FAIL:
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

	protected void showContentData() {

		List<Content> contents = contentWrapper.getContents();
		if (contents != null && contents.size() > 0) {
			if (isFirstLoad) {
				adapter = new ContentListAdapter(contents, context);
				isFirstLoad = false;
				content_list_lv.setAdapter(adapter);
				content_list_pb.setVisibility(ProgressBar.GONE);
				isLoading = false;
			} else {

				if (isSwitch) {

					adapter.setContents(contents);
					content_list_pb.setVisibility(ProgressBar.GONE);
				} else {
					for (Content content : contents) {
						adapter.addItem(content);
					}
				}

				adapter.notifyDataSetChanged(); // 数据集变化后,通知adapter
				content_list_lv.setSelection(visibleLastIndex
						- visibleItemCount + 1); // 设置选中项
				isLoading = false;
			}

		} else {
			Toast.makeText(context, "对不起，没有数据！", Toast.LENGTH_SHORT).show();
			content_list_pb.setVisibility(ProgressBar.GONE);
		}

		if (contentWrapper.isNext()) {
			pb_loadmoore.setVisibility(ProgressBar.GONE);
			loadMoreButton.setText("点击加载更多");

		} else {
			if (adapter != null) {
				content_list_lv.removeFooterView(loadMoreView);
			}

		}

	}

	/**
	 * 
	 * wanglu 泰得利通 首次加载数据
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
				ContentService contentService = new ContentService(context);
				try {

					String channelId = "";
					if (channel != null) {
						channelId = channel.getChannelId();
					} else if (parentItem != null) {
						channelId = parentItem.getChannelId();
					}

					contentWrapper = contentService.getPageContentsById(
							channelId, start, end);

					if (contentWrapper != null) {
						msg.what = CONTENT_LOAD_SUCCESS;

					} else {
						msg.what = CONTENT_LOAD_FAIL;
						msg.obj = "内容获取错误,稍后重试";
					}
					handler.sendMessage(msg);

				} catch (NetException e) {
					e.printStackTrace();
					msg.what = CONTENT_LOAD_FAIL;
					msg.obj = e.getMessage();
					handler.sendMessage(msg);
				} catch (JSONException e) {
					e.printStackTrace();
					msg.what = CONTENT_LOAD_FAIL;
					msg.obj = e.getMessage();
					handler.sendMessage(msg);
				} catch (NODataException e) {
					e.printStackTrace();
					msg.what = CONTENT_LOAD_FAIL;
					msg.obj = e.getMessage();
					handler.sendMessage(msg);
				}
			}
		}).start();
	}

	private void loadMore(View view) {
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
	private void initUI() {
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

	public void setParentItem(MenuItem parentItem) {
		this.parentItem = parentItem;
	}

	public void setChannel(Channel channel) {
		this.channel = channel;
	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
		this.visibleItemCount = visibleItemCount;
		visibleLastIndex = firstVisibleItem + visibleItemCount - 1;// 最后一条索引号
	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {

	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.loadMoreButton:
			if (contentWrapper != null && contentWrapper.isNext()) {// 还有下一条记录
				isSwitch = false;
				loadMoreButton.setText("loading.....");
				loadMore(v);
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
