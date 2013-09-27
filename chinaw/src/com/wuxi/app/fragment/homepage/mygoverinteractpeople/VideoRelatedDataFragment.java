/**   
 * @公司: 重庆智佳信息科技有限公司
 * @文件: VideoRelatedDataFragment.java 
 * @包名： com.wuxi.app.fragment.homepage.mygoverinteractpeople 
 * @描述: 政民互动 视频直播平台 走进直播间 相关资料 界面 
 * @作者： 罗森   
 * @创建时间： 2013 2013-9-25 下午4:38:36
 * @修改时间：  
 * @修改描述：
 * @版本： V1.0   
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
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView.OnItemClickListener;

import com.wuxi.app.BaseFragment;
import com.wuxi.app.R;
import com.wuxi.app.adapter.VideoRelatedDataAdapter;
import com.wuxi.app.engine.ContentService;
import com.wuxi.app.util.LogUtil;
import com.wuxi.domain.Content;
import com.wuxi.domain.ContentWrapper;
import com.wuxi.exception.NODataException;
import com.wuxi.exception.NetException;

/**
 * @类名： VideoRelatedDataFragment
 * @描述： 政民互动 视频直播平台 走进直播间 相关资料 界面
 * @作者： 罗森
 * @创建时间： 2013 2013-9-25 下午4:38:36
 * @修改时间：
 * @修改描述：
 */
@SuppressLint("HandlerLeak")
public class VideoRelatedDataFragment extends BaseFragment implements
		OnItemClickListener, OnClickListener, OnScrollListener {

	private static final String TAG = "VideoDailyRoutineFragment";

	// 分页加载数据起始地址
	private static final int START = 0;
	// 分页数量
	private static final int PAGE_NUM = 10;

	// 数据加载成功标志
	private static final int DATA_LOAD_SUCESS = 0;
	// 数据加载失败标志
	private static final int DATA_LOAD_ERROR = 1;

	private Context context;
	private View view;

	private ProgressBar progressBar = null;
	private ListView listView = null;

	private ContentWrapper contentWrapper;
	private List<Content> contents;

	private VideoRelatedDataAdapter adapter;

	private int visibleLastIndex;
	private int visibleItemCount;// 当前显示的总条数

	private boolean isFirstLoad = true;// 是不是首次加载数据
	private boolean isSwitch = false;// 切换
	private boolean isLoading = false;

	private View loadMoreView;// 加载更多视图
	private Button loadMoreButton;
	private ProgressBar pb_loadmoore;

	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			String tip = "";

			if (msg.obj != null) {
				tip = msg.obj.toString();
			}
			switch (msg.what) {
			case DATA_LOAD_SUCESS:
				showListData();
				break;
			case DATA_LOAD_ERROR:
				progressBar.setVisibility(View.INVISIBLE);
				Toast.makeText(context, tip, Toast.LENGTH_SHORT).show();
				break;
			}
		};
	};

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.video_daily_routine_layout, null);
		context = getActivity();

		initLayout();

		loadFirstData(START, PAGE_NUM);

		return view;
	}

	/**
	 * @方法： initLayout
	 * @描述： 初始化布局控件
	 */
	private void initLayout() {
		listView = (ListView) view
				.findViewById(R.id.video_daily_routine_listview);

		progressBar = (ProgressBar) view
				.findViewById(R.id.video_daily_routine_progressbar);

		loadMoreView = View.inflate(context, R.layout.list_loadmore_layout,
				null);
		loadMoreButton = (Button) loadMoreView
				.findViewById(R.id.loadMoreButton);
		pb_loadmoore = (ProgressBar) loadMoreView
				.findViewById(R.id.pb_loadmoore);

		listView.addFooterView(loadMoreView);// 为listView添加底部视图
		listView.setOnScrollListener(this);// 增加滑动监听
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
	 * @方法： loadData
	 * @描述： 加载数据
	 * @param start
	 * @param end
	 */
	private void loadData(final int start, final int end) {
		if (isFirstLoad || isSwitch) {
			progressBar.setVisibility(View.VISIBLE);
		} else {
			pb_loadmoore.setVisibility(ProgressBar.VISIBLE);
		}

		new Thread(new Runnable() {

			@Override
			public void run() {
				String id = "2a272a53-fbd5-4964-8362-28b5292c835f";
				isLoading = true;// 正在加载数据
				Message message = handler.obtainMessage();
				ContentService service = new ContentService(context);
				try {
					contentWrapper = service
							.getPageContentsById(id, start, end);
					if (contentWrapper != null) {
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
					LogUtil.i(TAG, "出错");
					e.printStackTrace();
					message.obj = e.getMessage();
					handler.sendEmptyMessage(DATA_LOAD_ERROR);
				} catch (NODataException e) {
					LogUtil.i(TAG, "出错");
					e.printStackTrace();
					message.obj = e.getMessage();
					handler.sendEmptyMessage(DATA_LOAD_ERROR);
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
	 * @方法： showListData
	 * @描述： 显示列表
	 */
	private void showListData() {
		contents = contentWrapper.getContents();
		if (contents == null && contents.size() == 0) {
			Toast.makeText(context, "对不起，暂无相关资料信息", Toast.LENGTH_SHORT).show();
			progressBar.setVisibility(View.GONE);
		} else {
			if (isFirstLoad) {
				adapter = new VideoRelatedDataAdapter(context, contents);
				isFirstLoad = false;
				listView.setAdapter(adapter);
				progressBar.setVisibility(View.GONE);
				isLoading = false;
			} else {
				if (isSwitch) {
					adapter.setContents(contents);
					progressBar.setVisibility(View.GONE);
				} else {
					for (Content content : contents) {
						adapter.addItem(content);
					}
				}

				adapter.notifyDataSetChanged(); // 数据集变化后,通知adapter
				listView.setSelection(visibleLastIndex - visibleItemCount + 1); // 设置选中项
				isLoading = false;
			}
		}

		if (contentWrapper.isNext()) {
			pb_loadmoore.setVisibility(ProgressBar.GONE);
			loadMoreButton.setText("点击加载更多");
		} else {
			listView.removeFooterView(loadMoreView);
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
			if (contentWrapper != null && contentWrapper.isNext()) {// 还有下一条记录

				isSwitch = false;
				loadMoreButton.setText("loading.....");
				loadMoreData(v);
			}
			break;
		}
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

	}
}
