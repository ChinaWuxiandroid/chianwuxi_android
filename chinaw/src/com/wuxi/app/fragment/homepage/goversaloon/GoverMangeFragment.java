package com.wuxi.app.fragment.homepage.goversaloon;

import java.util.List;

import org.json.JSONException;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import com.wuxi.app.R;
import com.wuxi.app.adapter.GoverManageAdapter;
import com.wuxi.app.engine.ChannelService;
import com.wuxi.app.engine.ContentService;
import com.wuxi.app.fragment.commonfragment.ContentDetailFragment;
import com.wuxi.app.util.Constants.FragmentName;
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
		OnClickListener, OnScrollListener, OnCheckedChangeListener,
		OnItemClickListener {

	private ListView gover_mange_lv;

	private ImageView gover_mange_iv_next;

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

	private GoverManageAdapter manageAdapter;
	private MenuItem menuItem;
	private List<Channel> subChannels;
	private List<Channel> channels;

	private static final int CHANNEL_TYPE = 1;
	private static final int SUB_CHANNEL_TYPE = 2;// 子Channel

	private ProgressBar pb_mange;
	private ContentWrapper contentWrapper;
	private boolean isFistLoad = true;// 是否是首次加载
	private boolean isSwitch = false;
	private Channel checkChannel;
	private LinearLayout ll_subchannel;
	private ProgressBar pb_loadmoore;
	private RadioGroup mange_rg_channel;
	private boolean isFirstChange = true;
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
		mange_rg_channel = (RadioGroup) view
				.findViewById(R.id.mange_rg_channel);
		mange_rg_channel.setOnCheckedChangeListener(this);
	
		gover_mange_iv_next = (ImageView) view
				.findViewById(R.id.gover_mange_iv_next);
		ll_subchannel = (LinearLayout) view.findViewById(R.id.ll_subchannel);
		
		gover_mange_iv_next.setOnClickListener(this);
		
		
		gover_mange_lv.addFooterView(getFootView());
		gover_mange_lv.setOnScrollListener(this);
		pb_mange = (ProgressBar) view.findViewById(R.id.pb_mange);
		menuItem = (MenuItem) getArguments().get("menuItem");
		gover_mange_lv.setOnItemClickListener(this);
		loadChannle(CHANNEL_TYPE, menuItem.getChannelId());// 加载子Channel
		

	}

	
	/**
	 * 
	 *wanglu 泰得利通 
	 *listView加载视图
	 * @return
	 */
	private View getFootView(){
		loadMoreView = View.inflate(context, R.layout.list_loadmore_layout,
				null);
		loadMoreButton = (Button) loadMoreView
				.findViewById(R.id.loadMoreButton);
		loadMoreButton.setOnClickListener(this);
		pb_loadmoore = (ProgressBar) loadMoreView
				.findViewById(R.id.pb_loadmoore);
		return loadMoreView;
	}
	/**
	 * 
	 * wanglu 泰得利通 显示子Channel
	 */
	protected void showSubChannel() {

		int index = 0;
		for (final Channel channel : subChannels) {

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

					if (channel.getChildrenContentsCount() > 0) {
						isSwitch = true;
						checkChannel = channel;
						loadContentsData(channel.getChannelId(), 0, PAGESIZE);
					}
				}
			});

			if (index == 0 && channel.getChildrenContentsCount() > 0) {// 默认显示第一个的内容
				this.checkChannel = channel;
				loadContentsData(channel.getChannelId(), 0, PAGESIZE);
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

					if (type == CHANNEL_TYPE) {
						channels = channelService.getSubChannels(channelId);
						if (channels != null) {
							msg.what = LOAD_CHANNEL_SUCCESS;
						} else {
							msg.what = LOAD_CHANNEL_FAIL;
							msg.obj = "加载失败";
						}

					} else if (type == SUB_CHANNEL_TYPE) {
						subChannels = channelService.getSubChannels(channelId);
						if (channels != null) {
							msg.what = LOAD_SUBCHANNEL_SUCCESS;// 加载子Channel成功
						} else {
							msg.what = LOAD_CHANNEL_FAIL;
							msg.obj = "加载失败";
						}

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

		int index = 0;
		for (Channel channle : channels) {

			RadioGroup.LayoutParams params = new RadioGroup.LayoutParams(
					RadioGroup.LayoutParams.WRAP_CONTENT,
					RadioGroup.LayoutParams.WRAP_CONTENT);
			params.leftMargin = 5;
			RadioButton radioButton = new RadioButton(context);
			if (index == 0) {

				radioButton.setTextColor(Color.WHITE);

				radioButton
						.setBackgroundResource(R.drawable.wuxi_content_channelselect_);

				if (channle.getChildrenContentsCount() > 0) {
					loadContentsData(channle.getChannelId(), 0, PAGESIZE);
					this.checkChannel = channle;
					ll_subchannel.setVisibility(LinearLayout.GONE);// 隐藏子Channel
																	// View
				} else if (channle.getChildrenChannelsCount() > 0) {

					ll_subchannel.setVisibility(LinearLayout.VISIBLE);// 隐藏子Channel
																		// View
					loadChannle(SUB_CHANNEL_TYPE, channle.getChannelId());
				}

			}

			radioButton.setText(channle.getChannelName());
			radioButton.setTextSize(14);
			radioButton.setPadding(5, 5, 5, 5);
			radioButton.setButtonDrawable(new ColorDrawable(Color.TRANSPARENT));

			mange_rg_channel.addView(radioButton, params);
			index++;

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
		} else {
			pb_loadmoore.setVisibility(ProgressBar.VISIBLE);
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

		if (contentWrapper.isNext()) {
			if(gover_mange_lv.getFooterViewsCount()!=0){
				loadMoreButton.setText("点击加载更多");
				pb_loadmoore.setVisibility(ProgressBar.GONE);
			}else{
				gover_mange_lv.addFooterView(getFootView());
			}
			

		} else {

			gover_mange_lv.removeFooterView(loadMoreView);
		}

	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {

		case R.id.loadMoreButton:
			if (contentWrapper != null && contentWrapper.isNext()) {// 还有下一条记录

				loadMoreButton.setText("loading.....");
				isSwitch = false;
				loadContentsData(checkChannel.getChannelId(),
						visibleLastIndex + 1, visibleLastIndex + 1 + PAGESIZE);

			}

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

	}

	@Override
	public void onItemClick(AdapterView<?> adapterView, View arg1,
			int position, long arg3) {

		Content content = (Content) adapterView.getItemAtPosition(position);
		Bundle bundle = new Bundle();
		bundle.putSerializable(ContentDetailFragment.CHANNEL_KEY,
				this.checkChannel);
		bundle.putSerializable(ContentDetailFragment.CONTENT_KEY, content);
		this.baseSlideFragment.slideLinstener.replaceFragment(null, -1,
				FragmentName.GOVERSALOONCONTENTDETIALFRAGMENT, bundle);
	}

	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {

		for (int i = 0; i < group.getChildCount(); i++) {

			RadioButton r = (RadioButton) group.getChildAt(i);

			if (isFirstChange && i == 0) {
				r.setBackgroundColor(Color.TRANSPARENT);
				r.setTextColor(Color.BLACK);
			}

			if (r.isChecked()) {

				r.setBackgroundResource(R.drawable.wuxicity_content_channel_item_selector);

				r.setTextColor(Color.WHITE);

				isSwitch = true;
				checkChannel = channels.get(i);
				if (checkChannel.getChildrenContentsCount() > 0) {
					loadContentsData(checkChannel.getChannelId(), 0, PAGESIZE);
					ll_subchannel.setVisibility(LinearLayout.GONE);// 隐藏子显示子ChannelView

				} else if (checkChannel.getChildrenChannelsCount() > 0) {
					ll_subchannel.removeAllViews();// 移除前面有点view重新加载
					ll_subchannel.setVisibility(LinearLayout.VISIBLE);// 隐藏子显示子ChannelView
					loadChannle(SUB_CHANNEL_TYPE, checkChannel.getChannelId());
				}

			} else {

				r.setTextColor(Color.BLACK);
			}

		}

		isFirstChange = false;

	}

}
