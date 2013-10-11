/**   
 * @公司: 重庆智佳信息科技有限公司
 * @文件: VideoGuestPresenceFragment.java 
 * @包名： com.wuxi.app.fragment.homepage.mygoverinteractpeople 
 * @描述:  政民互动 视频直播平台 走进直播间 嘉宾风采 界面
 * @作者： 罗森   
 * @创建时间： 2013 2013-9-26 上午10:13:42
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
import com.wuxi.app.adapter.VideoGuestPresenceAdapter;
import com.wuxi.app.engine.GuestPresenceService;
import com.wuxi.app.util.LogUtil;
import com.wuxi.domain.GuestPresenceWrapper;
import com.wuxi.domain.GuestPresenceWrapper.GuestPresence;
import com.wuxi.exception.NODataException;
import com.wuxi.exception.NetException;

/**
 * @类名： VideoGuestPresenceFragment
 * @描述： 政民互动 视频直播平台 走进直播间 嘉宾风采 界面
 * @作者： 罗森
 * @创建时间： 2013 2013-9-26 上午10:13:42
 * @修改时间：
 * @修改描述：
 */
public class VideoGuestPresenceFragment extends BaseFragment implements
		OnItemClickListener, OnClickListener, OnScrollListener {

	private static final String TAG = "VideoGuestPresenceFragment";

	// 分页加载数据起始地址
	private static final int START = 0;
	// 分页数量
	private static final int PAGE_NUM = 10;

	// 数据加载成功标志
	private static final int DATA_LOAD_SUCESS = 0;
	// 数据加载失败标志
	private static final int DATA_LOAD_ERROR = 1;

	private GuestPresenceWrapper wrapper;
	private List<GuestPresence> presences;

	private VideoGuestPresenceAdapter adapter;

	private Context context;
	private View view;

	private ProgressBar progressBar;
	private ListView listView;

	private int visibleLastIndex;
	private int visibleItemCount;// 当前显示的总条数

	private boolean isFirstLoad = true;// 是不是首次加载数据
	private boolean isSwitch = false;// 切换
	private boolean isLoading = false;

	private View loadMoreView;// 加载更多视图
	private Button loadMoreButton;
	private ProgressBar pb_loadmoore;

	@SuppressLint("HandlerLeak")
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
		view = inflater.inflate(R.layout.video_guest_presence_layout, null);
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
				.findViewById(R.id.video_guest_presence_listview);

		progressBar = (ProgressBar) view
				.findViewById(R.id.video_guest_presence_progressbar);

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
				String id = "29bee021-3d01-4810-be1f-678e6c4f877c";
				isLoading = true;// 正在加载数据
				Message message = handler.obtainMessage();
				GuestPresenceService service = new GuestPresenceService(context);
				try {
					wrapper = service.getGuestPresenceWrapper(id, start, end);
					if (wrapper != null) {
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
		presences = wrapper.getGuestPresences();
		if (presences == null && presences.size() == 0) {
			Toast.makeText(context, "对不起，暂无嘉宾风采信息", Toast.LENGTH_SHORT).show();
			progressBar.setVisibility(View.GONE);
		} else {
			if (isFirstLoad) {
				adapter = new VideoGuestPresenceAdapter(context, presences);
				isFirstLoad = false;
				listView.setAdapter(adapter);
				progressBar.setVisibility(View.GONE);
				isLoading = false;
			} else {
				if (isSwitch) {
					adapter.setPresences(presences);
					progressBar.setVisibility(View.GONE);
				} else {
					for (GuestPresence presence : presences) {
						adapter.addItem(presence);
					}
				}

				adapter.notifyDataSetChanged(); // 数据集变化后,通知adapter
				listView.setSelection(visibleLastIndex - visibleItemCount + 1); // 设置选中项
				isLoading = false;
			}
		}

		if (wrapper.isNext()) {
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
			if (wrapper != null && wrapper.isNext()) {// 还有下一条记录

				isSwitch = false;
				loadMoreButton.setText("loading.....");
				loadMoreData(v);
			}
			break;
		}
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		
	}

}
