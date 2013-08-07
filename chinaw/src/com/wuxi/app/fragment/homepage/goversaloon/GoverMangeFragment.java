package com.wuxi.app.fragment.homepage.goversaloon;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.wuxi.app.R;
import com.wuxi.app.adapter.GoverManageAdapter;
import com.wuxi.app.engine.ChannelService;
import com.wuxi.app.engine.ContentService;
import com.wuxi.domain.Channel;
import com.wuxi.domain.Content;
import com.wuxi.domain.ContentWrapper;
import com.wuxi.domain.MenuItem;
import com.wuxi.exception.NODataException;
import com.wuxi.exception.NetException;

/**
 * 
 * @author wanglu 泰得利通 组织管理
 * 
 */
@SuppressLint("HandlerLeak")
public class GoverMangeFragment extends GoverSaloonContentFragment implements
		OnPageChangeListener, OnClickListener, OnScrollListener {

	private ListView gover_mange_lv;
	private ViewPager gover_viewpagerLayout;
	private ImageView gover_mange_iv_next;
	private List<View> titleGridViews;
	private static final int PAGE_ITEM = 4;

	protected static final int LOAD_CHANNEL_SUCCESS = 1;
	protected static final int LOAD_CHANNEL_FAIL = 0;
	protected static final int CONTENT_LOAD_SUCCESS = 2;
	protected static final int CONTENT_LOAD_FAIL = 3;
	protected static final int LOAD_SUBCHANNEL_SUCCESS = 4;
	private static final int PAGESIZE = 10;

	private View loadMoreView;// 加载更多视图
	private Button loadMoreButton;
	private int visibleLastIndex;
	private int visibleItemCount;// 当前显示的总条数

	private int checkPositons[];// 选中的坐标
	private int pageNo = 0;
	private GoverManageAdapter manageAdapter;
	private MenuItem menuItem;
	private List<Channel> channels;

	private static final int CHANNEL_TYPE = 1;
	private static final int SUB_CHANNEL_TYPE = 2;// 子Channel

	private ProgressBar pb_mange;
	private ContentWrapper contentWrapper;
	private boolean isFistLoad = true;// 是否是首次加载
	private boolean isSwitch = false;
	private Channel checkChannel;
	private LinearLayout ll_subchannel;
	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {

			switch (msg.what) {
			case LOAD_CHANNEL_SUCCESS:
				showChannelData();
				break;
			case LOAD_SUBCHANNEL_SUCCESS:
				showSubChannel();
				break;
			case CONTENT_LOAD_SUCCESS:
				showContentData();
				break;
			case CONTENT_LOAD_FAIL:

			case LOAD_CHANNEL_FAIL:
				String tip = msg.obj.toString();
				Toast.makeText(context, tip, Toast.LENGTH_SHORT).show();
				break;
			}
		}
	};

	public void initUI() {
		super.initUI();

		gover_mange_lv = (ListView) view.findViewById(R.id.gover_mange_lv);
		gover_viewpagerLayout = (ViewPager) view
				.findViewById(R.id.gover_viewpagerLayout);
		gover_mange_iv_next = (ImageView) view
				.findViewById(R.id.gover_mange_iv_next);
		ll_subchannel = (LinearLayout) view.findViewById(R.id.ll_subchannel);
		gover_viewpagerLayout.setOnPageChangeListener(this);
		gover_mange_iv_next.setOnClickListener(this);
		loadMoreView = View.inflate(context, R.layout.list_loadmore_layout,
				null);
		loadMoreButton = (Button) loadMoreView
				.findViewById(R.id.loadMoreButton);
		gover_mange_lv.addFooterView(loadMoreView);
		gover_mange_lv.setOnScrollListener(this);
		pb_mange = (ProgressBar) view.findViewById(R.id.pb_mange);
		menuItem = (MenuItem) getArguments().get("menuItem");
		loadChannle(CHANNEL_TYPE, menuItem.getChannelId());// 加载子Channel

	}

	/**
	 * 
	 *wanglu 泰得利通
	 *显示子Channel
	 */
	protected void showSubChannel() {

		int index=0;
		for (final Channel channel : channels) {

			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
					LinearLayout.LayoutParams.WRAP_CONTENT,
					LinearLayout.LayoutParams.WRAP_CONTENT);
			params.leftMargin = 10;
			TextView textView = new TextView(context);
			textView.setTextColor(Color.BLUE);
			textView.setText(channel.getChannelName());
			ll_subchannel.addView(textView, params);
			
			textView.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					
					if(channel.getChildrenContentsCount()>0){
						isSwitch = true;
						checkChannel = channel;
						loadContentsData(channel.getChannelId(), 0, PAGESIZE);
					}
				}
			});
			
			if(index==0&&channel.getChildrenContentsCount()>0){//默认显示第一个的内容
				this.checkChannel=channel;
				loadContentsData(channel.getChannelId(),0, PAGESIZE);
			}
			index++;

		}

	}

	private void loadChannle(final int type, final String channelId) {

		new Thread(new Runnable() {

			@Override
			public void run() {

				Message msg = handler.obtainMessage();

				ChannelService channelService = new ChannelService(context);
				try {
					channels = channelService.getSubChannels(channelId);
					if (channels != null) {
						if (type == CHANNEL_TYPE) {
							msg.what = LOAD_CHANNEL_SUCCESS;
						} else if (type == SUB_CHANNEL_TYPE) {
							msg.what = LOAD_SUBCHANNEL_SUCCESS;// 加载子Channel成功
						}

					} else {
						msg.what = LOAD_CHANNEL_FAIL;
						msg.obj = "加载失败";
					}
					handler.sendMessage(msg);
				} catch (NetException e) {
					e.printStackTrace();
					msg.what = LOAD_CHANNEL_FAIL;
					msg.obj = e.getMessage();
					handler.sendMessage(msg);
				}

			}
		}).start();

	}

	private void showChannelData() {

		int i = 0;
		List<Channel> onScreenItems = null;
		titleGridViews = new ArrayList<View>();
		int currentScreen = 0;// 当前屏

		int totalScreenNum = channels.size() / PAGE_ITEM;// 屏数
		checkPositons = new int[totalScreenNum + 1];
		for (int j = 0; j < checkPositons.length; j++) {// 初始化头部安选中的下标
			checkPositons[j] = -1;
		}

		checkPositons[0] = 0;// 默认选中第一屏第一个Chanel

		for (Channel item : channels) {

			if (i % PAGE_ITEM == 0) {

				if (onScreenItems != null) {

					titleGridViews.add(bulidGridView(onScreenItems,
							currentScreen));
					currentScreen++;

				}

				onScreenItems = new ArrayList<Channel>();
			}

			// 最后一屏操作
			if (currentScreen > totalScreenNum + 1) {

				onScreenItems = new ArrayList<Channel>();
			}

			onScreenItems.add(item);

			// add last category screen //最后一屏
			if (i == channels.size() - 1) {

				titleGridViews.add(bulidGridView(onScreenItems, currentScreen));

			}

			i++;
		}

		gover_viewpagerLayout.setAdapter(new ChannelItemViewPageAdapter(
				titleGridViews));// 设置ViewPage适配器

		Channel initChannel = channels.get(0);
		if (initChannel.getChildrenContentsCount() > 0) {
			loadContentsData(initChannel.getChannelId(), 0, PAGESIZE);
			this.checkChannel = initChannel;
			ll_subchannel.setVisibility(LinearLayout.GONE);// 隐藏子Channel View
		} else if (initChannel.getChildrenChannelsCount() > 0) {

			ll_subchannel.setVisibility(LinearLayout.VISIBLE);// 隐藏子Channel View
			loadChannle(SUB_CHANNEL_TYPE, initChannel.getChannelId());
		}

	}

	/**
	 * 
	 * wanglu 泰得利通 加载类容信息
	 * 
	 * @param channelId
	 * @param start
	 * @param end
	 */
	public void loadContentsData(final String channelId, final int start,
			final int end) {

		if (isFistLoad || isSwitch) {
			pb_mange.setVisibility(ProgressBar.VISIBLE);
		}

		new Thread(new Runnable() {

			@Override
			public void run() {

				Message msg = handler.obtainMessage();
				ContentService contentService = new ContentService(context);
				try {
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

	protected void showContentData() {

		if (contentWrapper.isNext()) {
			loadMoreButton.setText("more");

		} else {
			
			gover_mange_lv.removeFooterView(loadMoreView);
		}

		List<Content> contents = contentWrapper.getContents();
		if (contents != null && contents.size() > 0) {
			if (isFistLoad) {
				manageAdapter = new GoverManageAdapter(contents, context);
				isFistLoad = false;
				gover_mange_lv.setAdapter(manageAdapter);
				pb_mange.setVisibility(ProgressBar.GONE);

			} else {

				if (isSwitch) {// 如果是切换
					manageAdapter.setContents(contents);
					pb_mange.setVisibility(ProgressBar.GONE);
				} else {
					for (Content content : contents) {
						manageAdapter.addItem(content);
					}
				}

				manageAdapter.notifyDataSetChanged(); // 数据集变化后,通知adapter
				gover_mange_lv.setSelection(visibleLastIndex - visibleItemCount
						+ 1); // 设置选中项

			}
		}

	}

	private GridView bulidGridView(List<Channel> items, int screenIndex) {
		GridView gridView = (GridView) mInflater.inflate(
				R.layout.gover_mange_title_gridview_layout, null);
		gridView.setColumnWidth(PAGE_ITEM);

		gridView.setAdapter(new ChannelGridViewAdaptger(items, screenIndex));
		gridView.setOnItemClickListener(GridviewOnclick);
		return gridView;
	}

	static class ViewHolder {
		TextView tv_title;
	}

	private class ChannelGridViewAdaptger extends BaseAdapter {

		public List<Channel> channels;
		public int screenIndex;

		public ChannelGridViewAdaptger(List<Channel> channels, int screenIndex) {
			this.channels = channels;
			this.screenIndex = screenIndex;
		}

		@Override
		public int getCount() {
			return channels.size();
		}

		@Override
		public Object getItem(int position) {
			return channels.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			Channel channel = channels.get(position);
			ViewHolder viewHolder = null;
			if (convertView == null) {
				convertView = mInflater.inflate(
						R.layout.gover_mange_title_gridview_item_layout, null);

				viewHolder = new ViewHolder();

				viewHolder.tv_title = (TextView) convertView
						.findViewById(R.id.gover_manger_tv);

				if (screenIndex == 0 && position == 0) {

					viewHolder.tv_title
							.setBackgroundResource(R.drawable.goversaloon_menuitem_bg);
					viewHolder.tv_title.setTextColor(Color.WHITE);

				}

				convertView.setTag(viewHolder);
			} else {
				viewHolder = (ViewHolder) convertView.getTag();
			}

			viewHolder.tv_title.setText(channel.getChannelName());
			return convertView;
		}

	}

	private class ChannelItemViewPageAdapter extends PagerAdapter {

		private List<View> mListViews;

		public ChannelItemViewPageAdapter(List<View> mGridViews) {

			this.mListViews = mGridViews;
		}

		@Override
		public int getCount() {
			return mListViews.size();
		}

		@Override
		public Object instantiateItem(View collection, int position) {

			((ViewPager) collection).addView(mListViews.get(position), 0);

			return mListViews.get(position);
		}

		@Override
		public void destroyItem(View collection, int position, Object view) {
			((ViewPager) collection).removeView(mListViews.get(position));
		}

		@Override
		public boolean isViewFromObject(View view, Object object) {
			return view == (object);
		}

	}

	/**
	 * 菜单点击
	 */
	private OnItemClickListener GridviewOnclick = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {

			Channel channel = (Channel) parent.getItemAtPosition(position);
			/**
			 * 切换选中与未选择的样式
			 */
			if (checkPositons[pageNo] != position) {
				View checkView = parent.getChildAt(position);

				TextView tv_Check = (TextView) checkView
						.findViewById(R.id.gover_manger_tv);
				tv_Check.setBackgroundResource(R.drawable.goversaloon_menuitem_bg);

				tv_Check.setTextColor(Color.WHITE);

				View oldCheckView = parent.getChildAt(checkPositons[pageNo]);
				if (null != oldCheckView) {

					TextView tv_oldCheck = (TextView) oldCheckView
							.findViewById(R.id.gover_manger_tv);
					tv_oldCheck.setBackgroundColor(getResources().getColor(
							R.color.content_background));

					tv_oldCheck.setTextColor(Color.BLACK);

				}

				checkPositons[pageNo] = position;
			}

			isSwitch = true;
			checkChannel = channel;
			if (channel.getChildrenContentsCount() > 0) {
				loadContentsData(channel.getChannelId(), 0, PAGESIZE);
				ll_subchannel.setVisibility(LinearLayout.GONE);// 隐藏子显示子ChannelView

			}else if(channel.getChildrenChannelsCount()>0){
				ll_subchannel.removeAllViews();//移除前面有点view重新加载
				ll_subchannel.setVisibility(LinearLayout.VISIBLE);// 隐藏子显示子ChannelView
				loadChannle(SUB_CHANNEL_TYPE, channel.getChannelId());
			}	

		}
	};

	@Override
	public void onPageSelected(int position) {
		pageNo = position;

	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.gover_mange_iv_next:
			// gover_viewpagerLayout.setCurrentItem(pageNo+1);
			gover_viewpagerLayout.setCurrentItem(pageNo + 1, true);
			break;

		}

	}

	@Override
	protected int getLayoutId() {

		return R.layout.gover_saloon_mange;
	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {

		this.visibleItemCount = visibleItemCount;
		visibleLastIndex = firstVisibleItem + visibleItemCount - 1;// 最后一条索引号
	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		int itemsLastIndex = manageAdapter.getCount() - 1; // 数据集最后一项的索引
		int lastIndex = itemsLastIndex + 1; // 加上底部的loadMoreView项
		if (scrollState == OnScrollListener.SCROLL_STATE_IDLE
				&& visibleLastIndex == lastIndex) {

			if (contentWrapper != null && contentWrapper.isNext()) {// 还有下一条记录

				loadMoreButton.setText("loading.....");
				isSwitch = false;
				loadContentsData(checkChannel.getChannelId(),
						visibleLastIndex + 1, visibleLastIndex + 1 + PAGESIZE);

			}

		}
	}

	@Override
	public void onPageScrollStateChanged(int arg0) {

	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		// TODO Auto-generated method stub

	}

}
