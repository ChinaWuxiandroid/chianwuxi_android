/*
 * (#)ContentListView.java 1.0 2013-9-3 2013-9-3 GMT+08:00
 */
package com.wuxi.app.view;

import java.util.List;

import org.json.JSONException;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.wuxi.app.MainTabActivity;
import com.wuxi.app.R;
import com.wuxi.app.activity.commactivity.ContentDetailActivity;
import com.wuxi.app.activity.homepage.fantasticwuxi.WuxiChannelContentDetailActivity;
import com.wuxi.app.adapter.ContentListAdapter;
import com.wuxi.app.engine.ContentService;
import com.wuxi.app.listeners.ContentLoadListner;
import com.wuxi.domain.Channel;
import com.wuxi.domain.Content;
import com.wuxi.domain.ContentWrapper;
import com.wuxi.domain.MenuItem;
import com.wuxi.exception.NODataException;
import com.wuxi.exception.NetException;

/**
 * @author wanglu 泰得利通
 * @version $1.0, 2013-9-3 2013-9-3 GMT+08:00 内容列表自定义 view
 * 
 */
@SuppressLint("HandlerLeak")
public class ContentListView extends ListView implements OnScrollListener,
		OnClickListener, android.widget.AdapterView.OnItemClickListener {

	/**
	 * @author 泰得利通 wanglu
	 * @param context
	 * @param attrs
	 */
	private MenuItem parentItem;

	private Channel channel;

	protected static final int CONTENT_LOAD_SUCCESS = 0;

	protected static final int CONTENT_LOAD_FAIL = 1;

	private static final int PAGE_SIZE = 10;

	protected View view;

	private ContentWrapper contentWrapper;// 内容

	private View loadMoreView;// 加载更多视图

	private Button loadMoreButton;

	private Context context;

	private int visibleLastIndex;

	private int visibleItemCount;// 当前显示的总条数

	private ContentListAdapter adapter;

	private boolean isSwitch = false;// 切换

	private boolean isFirstLoad = true;// 是不是首次加载数据

	private ProgressBar pb_loadmoore;

	private Class<?> activityClass;
	private ContentLoadListner contentLoadListner;
	private Handler handler = new Handler() {
		@SuppressLint("HandlerLeak")
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case CONTENT_LOAD_SUCCESS:
				showContentData();
				break;
			case CONTENT_LOAD_FAIL:

				String tip = msg.obj.toString();
				Toast.makeText(context, tip, Toast.LENGTH_SHORT).show();
				break;
			}
		};
	};

	public ContentListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;

	}

	public ContentListView(Context context) {
		super(context);
		this.context = context;
	}

	public ContentListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		this.context = context;
	}

	protected void showContentData() {

		List<Content> contents = contentWrapper.getContents();
		if (contents != null && contents.size() > 0) {
			if (isFirstLoad) {
				adapter = new ContentListAdapter(contents, context);
				isFirstLoad = false;
				this.setAdapter(adapter);
				contentLoadListner.loadSuccess();//通知加载数据加载完毕
			} else {

				if (isSwitch) {

					adapter.setContents(contents);
					
				} else {
					for (Content content : contents) {
						adapter.addItem(content);
					}
				}
				contentLoadListner.loadSuccess();//通知加载数据加载完毕
				adapter.notifyDataSetChanged(); // 数据集变化后,通知adapter
				this.setSelection(visibleLastIndex - visibleItemCount + 1); // 设置选中项

			}

		}

		if (contentWrapper.isNext()) {
			pb_loadmoore.setVisibility(ProgressBar.GONE);
			loadMoreButton.setText("点击加载更多");

		} else {
			if(adapter!=null){
				this.removeFooterView(loadMoreView);
			}
			
		}

	}

	private void loadData(final int start, final int end) {
		if (!isFirstLoad) {
			pb_loadmoore.setVisibility(ProgressBar.VISIBLE);
		}
		new Thread(new Runnable() {

			@Override
			public void run() {

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

	public void init(ContentLoadListner contentLoadListner,MenuItem menuItem, Channel channel, Class<?> activityClass) {

		this.parentItem = menuItem;
		this.channel = channel;
		this.activityClass = activityClass;
		this.contentLoadListner=contentLoadListner;
		loadMoreView = View.inflate(
			context, R.layout.list_loadmore_layout, null);
		loadMoreButton = (Button) loadMoreView.findViewById(R.id.loadMoreButton);
		pb_loadmoore = (ProgressBar) loadMoreView.findViewById(R.id.pb_loadmoore);
		this.addFooterView(loadMoreView);// 为listView添加底部视图
		this.setOnScrollListener(this);// 增加滑动监听
		loadMoreButton.setOnClickListener(this);

		this.setOnItemClickListener(this);

		loadData(0, PAGE_SIZE);

	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.loadMoreButton:
			if (contentWrapper != null && contentWrapper.isNext()) {// 还有下一条记录

				isSwitch = false;
				loadMoreButton.setText("loading.....");
				loadData(visibleLastIndex + 1, visibleLastIndex + 1 + PAGE_SIZE);
			}
			break;
		}
	}

	public void setParentItem(MenuItem parentItem) {
		this.parentItem = parentItem;
	}

	public void setChannel(Channel channel) {
		this.channel = channel;
	}

	public void changeChannelOrMenItem(Channel channel, MenuItem menuItem) {

		this.isSwitch = true;
		this.channel = channel;
		this.parentItem = menuItem;
		loadData(0, PAGE_SIZE);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * android.widget.AdapterView.OnItemClickListener#onItemClick(android.widget
	 * .AdapterView, android.view.View, int, long)
	 */
	@Override
	public void onItemClick(AdapterView<?> adapterView, View arg1, int position, long arg3) {

		if(activityClass==null) return  ;
		Content content = (Content) adapterView.getItemAtPosition(position);

		Intent intent = new Intent(context,
			activityClass);
		intent.putExtra(WuxiChannelContentDetailActivity.CONTENT_KEY, content);

		if (this.channel != null) {
			intent.putExtra(ContentDetailActivity.CHANNEL_KEY, channel);
		}else if(this.parentItem!=null){
			intent.putExtra(ContentDetailActivity.MENUITEM_KEY, parentItem);
		}

		Animation animation = AnimationUtils.loadAnimation(
			context, R.anim.rbm_in_from_right);
		MainTabActivity.instance.addView(intent, animation);
		
	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
		this.visibleItemCount = visibleItemCount;
		visibleLastIndex = firstVisibleItem + visibleItemCount - 1;// 最后一条索引号
	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		//int itemsLastIndex = adapter.getCount() - 1; // 数据集最后一项的索引
		//int lastIndex = itemsLastIndex + 1; // 加上底部的loadMoreView项

	}

}
