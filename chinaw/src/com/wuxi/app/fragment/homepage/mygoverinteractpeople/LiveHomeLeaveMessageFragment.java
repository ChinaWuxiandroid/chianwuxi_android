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
import com.wuxi.app.adapter.LiveHomeLeaveMessageListAdapter;
import com.wuxi.app.engine.LeaveMessageService;
import com.wuxi.app.util.LogUtil;
import com.wuxi.domain.LeaveMessageWrapper;
import com.wuxi.domain.LeaveMessageWrapper.LeaveMessage;
import com.wuxi.exception.NODataException;
import com.wuxi.exception.NetException;

/**
 * 栏目首页 留言提问 碎片布局
 * 
 * @author 智佳 罗森
 * 
 */
@SuppressLint({ "ShowToast", "HandlerLeak" })
public class LiveHomeLeaveMessageFragment extends BaseFragment implements
		OnClickListener, OnScrollListener {

	protected static final String TAG = "LiveHomeLeaveMessageFragment";

	private View view = null;
	private Context context = null;

	private ProgressBar list_pb = null;

	private ListView mListView = null;

	private LeaveMessageWrapper messageWrapper = null;

	private List<LeaveMessage> leaveMessages = null;

	private LiveHomeLeaveMessageListAdapter adapter;

	// 数据加载成功标志
	private static final int DATA__LOAD_SUCESS = 0;
	// 数据加载失败标志
	private static final int DATA_LOAD_ERROR = 1;

	// 获取提问留言的起始坐标
	private int startIndex = 0;
	private int visibleLastIndex;
	private int visibleItemCount;// 当前显示的总条数
	private final static int PAGE_NUM = 5;

	private boolean isFirstLoad = true;// 是不是首次加载数据
	private boolean isSwitch = false;// 切换
	private boolean isLoading = false;

	private View loadMoreView;// 加载更多视图
	private Button loadMoreButton;
	private ProgressBar pb_loadmoore;

	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			String tip = "";

			if (msg.obj != null) {
				tip = msg.obj.toString();
			}

			switch (msg.what) {
			case DATA__LOAD_SUCESS:
				showLeaveMessage();
				break;

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
		view = inflater.inflate(R.layout.gip_live_home_message_layout, null);
		context = getActivity();

		initLayout();

		// 第一次加载数据
		loadFirstData(startIndex, PAGE_NUM);

		return view;
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
				LeaveMessageService messageService = new LeaveMessageService(
						context);
				try {
					messageWrapper = messageService.getLeaveMessageWrapper(
							"32480e19-76b8-45d9-b7d1-a6c54933f9f7", startIndex,
							endIndex);
					if (messageWrapper != null) {

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
	 * 加载布局控件
	 */
	private void initLayout() {
		mListView = (ListView) view
				.findViewById(R.id.gip_live_home_message_listview);

		list_pb = (ProgressBar) view
				.findViewById(R.id.gip_live_home_message_progressbar);

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
	 * 显示列表数据
	 */
	private void showLeaveMessage() {
		leaveMessages = messageWrapper.getLeaveMessages();

		if (leaveMessages == null || leaveMessages.size() == 0) {
			Toast.makeText(context, "对不起，暂无留言提问信息", 2000).show();
			list_pb.setVisibility(View.GONE);
		} else {
			if (isFirstLoad) {
				adapter = new LiveHomeLeaveMessageListAdapter(context,
						leaveMessages);
				isFirstLoad = false;
				mListView.setAdapter(adapter);
				list_pb.setVisibility(View.GONE);
				isLoading = false;
			} else {
				if (isSwitch) {
					adapter.setLeaveMessages(leaveMessages);
					list_pb.setVisibility(View.GONE);
				} else {
					for (LeaveMessage leaveMessage : leaveMessages) {
						adapter.addItem(leaveMessage);
					}
				}

				adapter.notifyDataSetChanged(); // 数据集变化后,通知adapter
				mListView.setSelection(visibleLastIndex - visibleItemCount + 1); // 设置选中项
				isLoading = false;
			}
		}

		if (messageWrapper.isNext()) {
			pb_loadmoore.setVisibility(ProgressBar.GONE);
			loadMoreButton.setText("点击加载更多");
		} else {
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
			if (messageWrapper != null && messageWrapper.isNext()) {// 还有下一条记录

				isSwitch = false;
				loadMoreButton.setText("loading.....");
				loadMoreData(v);
			}
			break;
		}
	}
}
