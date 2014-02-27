package com.wuxi.app.fragment.homepage.publicservice;

import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.wuxi.app.BaseFragment;
import com.wuxi.app.R;
import com.wuxi.app.activity.homepage.publicservice.PublicServiceContentDetailActivity;
import com.wuxi.app.adapter.PublicSericeChannelAdapter;
import com.wuxi.app.engine.ChannelService;
import com.wuxi.app.listeners.ContentLoadListner;
import com.wuxi.app.util.CacheUtil;
import com.wuxi.app.view.ContentListView;
import com.wuxi.domain.Channel;
import com.wuxi.exception.NetException;

/**
 * 
 * @author wanglu 泰得利通
 * 
 *         频道显示的内容Fragment
 */
@SuppressLint("HandlerLeak")
public class PublicServiceChannelContentFragment extends BaseFragment
		implements OnItemClickListener, ContentLoadListner {
	protected static final int CHANNEL_LOAD_SUCESS = 0;// 子频道获取成功

	protected static final int CHANNEL_LOAD_FAIL = 1;// 子频道获取失败

	private ProgressBar pb_publicserivce;
	private ListView publicservice_lv_channel;
	private Channel channel;// 选中的channel
	public List<Channel> subChannels;// 第一级子channel
	private View view;
	private LinearLayout ll_title;
	private TextView public_detial_tv_title;
	private Context context;

	private HorizontalScrollView publicservice_level1, publicservice_level2,
			publicservice_level3;

	private View dev_v_1, dev_v_2, dev_v_3;



	private ContentListView contentListView;

	protected static final int LOAD_CHANNEL_SUCCESS = 2;

	protected static final int LOAD_CHANNEL_FAIL = 3;

	private int level;

	private List<Channel> channels;

	

	private RadioGroup publicserivce_rb_1, publicserivce_rb_2,
			publicserivce_rb_3;

	private boolean isFirst1Change = true;

	private boolean isFirst2Change = true;

	private boolean isFirst3Change = true;

	private boolean isFistLoadContent=true;
	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case CHANNEL_LOAD_SUCESS:
				showChannelData();
				break;

			case LOAD_CHANNEL_SUCCESS:
				showChannel(level);
				break;
			case LOAD_CHANNEL_FAIL:

			case CHANNEL_LOAD_FAIL:
				String tip = msg.obj.toString();
				Toast.makeText(context, tip, Toast.LENGTH_SHORT).show();
				break;
			}

		};
	};

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.publicservice_channel_layout, null);
		context = getActivity();
		initUI();

		return view;
	}

	/**
	 * 
	 * wanglu 泰得利通 初始化view
	 */
	private void initUI() {
		pb_publicserivce = (ProgressBar) view
				.findViewById(R.id.pb_publicserivce);
		publicservice_lv_channel = (ListView) view
				.findViewById(R.id.publicservice_lv_channel);
		ll_title = (LinearLayout) view.findViewById(R.id.ll_title);
		public_detial_tv_title = (TextView) view
				.findViewById(R.id.public_detial_tv_title);
		publicservice_lv_channel.setOnItemClickListener(this);
		loadTitleChannel();

		publicservice_level1 = (HorizontalScrollView) view
				.findViewById(R.id.publicservice_level1);
		publicservice_level2 = (HorizontalScrollView) view
				.findViewById(R.id.publicservice_level2);
		publicservice_level3 = (HorizontalScrollView) view
				.findViewById(R.id.publicservice_level3);
		dev_v_1 = (View) view.findViewById(R.id.dev_v_1);// 分割线
		dev_v_2 = (View) view.findViewById(R.id.dev_v_2);
		dev_v_3 = (View) view.findViewById(R.id.dev_v_3);

		publicserivce_rb_1 = (RadioGroup) view
				.findViewById(R.id.publicserivce_rb_1);
		publicserivce_rb_2 = (RadioGroup) view
				.findViewById(R.id.publicserivce_rb_2);
		publicserivce_rb_3 = (RadioGroup) view
				.findViewById(R.id.publicserivce_rb_3);

		contentListView = (ContentListView) view
				.findViewById(R.id.publicservice_content);

	}

	/**
	 * 
	 * wanglu 泰得利通 显示头部
	 */

	@SuppressWarnings("unchecked")
	private void loadTitleChannel() {

		if (CacheUtil.get(channel.getChannelId()) != null) {
			subChannels = (List<Channel>) CacheUtil.get(channel.getChannelId());
			showChannelData();
			return;
		}

		new Thread(new Runnable() {

			@Override
			public void run() {

				Message msg = handler.obtainMessage();
				ChannelService channelService = new ChannelService(context);
				try {
					subChannels = channelService.getSubChannels(channel
							.getChannelId());

					if (subChannels != null) {
						msg.what = CHANNEL_LOAD_SUCESS;

					} else {
						msg.what = CHANNEL_LOAD_FAIL;
					}
					handler.sendMessage(msg);

				} catch (NetException e) {
					e.printStackTrace();
					msg.what = CHANNEL_LOAD_FAIL;
					msg.obj = e.getMessage();
					handler.sendMessage(msg);
				}
			}
		}).start();

	}

	/**
	 * 
	 * wanglu 泰得利通 获取子Channle
	 * 
	 * @param channel
	 */
	@SuppressWarnings("unchecked")
	public void laodSubChannels(final Channel channel, final int level) {

		if (CacheUtil.get(channel.getChannelId()) != null) {
			channels = (List<Channel>) CacheUtil.get(channel.getChannelId());
			showChannel(level);
			return;
		}

		new Thread(new Runnable() {

			@Override
			public void run() {

				Message msg = handler.obtainMessage();
				ChannelService channelService = new ChannelService(context);
				try {
					channels = channelService.getSubChannels(channel
							.getChannelId());
					if (channels != null) {
						PublicServiceChannelContentFragment.this.level = level;
						msg.what = LOAD_CHANNEL_SUCCESS;

					} else {
						msg.what = LOAD_CHANNEL_FAIL;
						msg.obj = "加载数据失败";

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

		pb_publicserivce.setVisibility(ProgressBar.GONE);

		publicservice_lv_channel.setAdapter(new PublicSericeChannelAdapter(
				subChannels, context));

	}

	public Channel getChannel() {
		return channel;
	}

	public void setChannel(Channel channel) {
		this.channel = channel;
	}

	@Override
	public void onItemClick(AdapterView<?> adapterView, View arg1,
			int position, long arg3) {
		publicservice_lv_channel.setVisibility(ListView.GONE);// 隐藏channel
																// listView
		ll_title.setVisibility(LinearLayout.VISIBLE);// 显示标题
		Channel channel = (Channel) adapterView.getItemAtPosition(position);
		public_detial_tv_title.setText(channel.getChannelName());// 显示channel标题

		if (channel.getChildrenChannelsCount() == 0
				&& channel.getChildrenContentsCount() > 0) {// 没有子菜单，有content
			hideSwitchView(0);
			showContentList(channel);

		} else if (channel.getChildrenContentsCount() == 0
				&& channel.getChildrenChannelsCount() > 0) {// 有子频道
			hideSwitchView(1);

			laodSubChannels(channel, 1);
		}

	}

	/**
	 * 
	 * wanglu 泰得利通 显示Channe信息
	 */
	private void showChannel(int level) {

		switch (level) {

		case 1:

			Level1ChannelChangeLister level1ChannelChangeLister = new Level1ChannelChangeLister(
					channels);
			publicserivce_rb_1
					.setOnCheckedChangeListener(level1ChannelChangeLister);
			buildSubChannelsView(1);
			break;
		case 2:
			Level2ChannelChangeLister level2ChannelChangeLister = new Level2ChannelChangeLister(
					channels);
			publicserivce_rb_2
					.setOnCheckedChangeListener(level2ChannelChangeLister);
			buildSubChannelsView(2);
			break;
		case 3:
			Level3ChannelChangeLister level3ChannelChangeLister = new Level3ChannelChangeLister(
					channels);
			publicserivce_rb_3
					.setOnCheckedChangeListener(level3ChannelChangeLister);
			buildSubChannelsView(3);
			break;
		}

	}

	private void hideSwitchView(int level) {
		this.level = level;
		switch (level) {
		case 0:// 没有子channle
			publicservice_level1.setVisibility(HorizontalScrollView.GONE);
			publicservice_level2.setVisibility(HorizontalScrollView.GONE);
			publicservice_level3.setVisibility(HorizontalScrollView.GONE);
			dev_v_1.setVisibility(View.GONE);
			dev_v_2.setVisibility(View.GONE);
			dev_v_3.setVisibility(View.GONE);
			break;
		case 1:// 有一个子chanle
			publicservice_level1.setVisibility(HorizontalScrollView.VISIBLE);
			publicservice_level2.setVisibility(HorizontalScrollView.GONE);
			publicservice_level3.setVisibility(HorizontalScrollView.GONE);
			dev_v_1.setVisibility(View.VISIBLE);
			dev_v_2.setVisibility(View.GONE);
			dev_v_3.setVisibility(View.GONE);
			break;
		case 2:// 有第二次子channel
			publicservice_level1.setVisibility(HorizontalScrollView.VISIBLE);
			publicservice_level2.setVisibility(HorizontalScrollView.VISIBLE);
			publicservice_level3.setVisibility(HorizontalScrollView.GONE);
			dev_v_1.setVisibility(View.VISIBLE);
			dev_v_2.setVisibility(View.VISIBLE);
			dev_v_3.setVisibility(View.GONE);
			break;
		case 3:// 3级channel
			publicservice_level1.setVisibility(HorizontalScrollView.VISIBLE);
			publicservice_level2.setVisibility(HorizontalScrollView.VISIBLE);
			publicservice_level3.setVisibility(HorizontalScrollView.VISIBLE);
			dev_v_1.setVisibility(View.VISIBLE);
			dev_v_2.setVisibility(View.VISIBLE);
			dev_v_3.setVisibility(View.VISIBLE);
			break;

		}

	}

	/**
	 * 
	 * wanglu 泰得利通 构建二级及3级channle
	 */
	public void buildSubChannelsView(int level) {

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
						.setBackgroundResource(R.drawable.public_service_channel_2bg);
				showChannelView(channle, level);

			} else {

				radioButton
						.setBackgroundResource(R.drawable.public_service_channel_3bg);

			}
			radioButton.setGravity(Gravity.CENTER);
			radioButton.setText(channle.getChannelName());
			radioButton.setTextSize(12);
			radioButton.setPadding(2, 2, 2, 2);
			radioButton.setButtonDrawable(new ColorDrawable(Color.TRANSPARENT));
			if (level == 1) {
				publicserivce_rb_1.addView(radioButton, params);
			} else if (level == 2) {
				publicserivce_rb_2.addView(radioButton, params);

			} else if (level == 3) {
				publicserivce_rb_3.addView(radioButton, params);
			}

			index++;

		}

	}

	/**
	 * 
	 * wanglu 泰得利通 显示内容列表
	 * 
	 * @param channel
	 */
	private void showContentList(Channel channel) {

		contentListView.setVisibility(ListView.VISIBLE);
		pb_publicserivce.setVisibility(ProgressBar.VISIBLE);
		if(isFistLoadContent){
			contentListView.init(this, null, channel,
					PublicServiceContentDetailActivity.class);
			isFistLoadContent=false;

		}else{
			contentListView.changeChannelOrMenItem(channel, null);
		}
		
	}

	/**
	 * 
	 * wanglu 泰得利通 重置
	 */
	public void reset(int level) {

		switch (level) {
		case 1:
			publicserivce_rb_2.removeAllViews();

			publicservice_level1.setVisibility(HorizontalScrollView.VISIBLE);
			publicservice_level2.setVisibility(HorizontalScrollView.GONE);
			publicservice_level3.setVisibility(HorizontalScrollView.GONE);
			dev_v_1.setVisibility(View.VISIBLE);
			dev_v_2.setVisibility(View.GONE);
			dev_v_3.setVisibility(View.GONE);
			isFirst2Change = true;
			break;
		case 2:

			publicservice_level1.setVisibility(HorizontalScrollView.VISIBLE);
			publicservice_level2.setVisibility(HorizontalScrollView.VISIBLE);
			publicservice_level3.setVisibility(HorizontalScrollView.GONE);
			dev_v_1.setVisibility(View.VISIBLE);
			dev_v_2.setVisibility(View.VISIBLE);
			dev_v_3.setVisibility(View.GONE);
			publicserivce_rb_3.removeAllViews();
			isFirst3Change = true;
			break;

		}
	}

	private void showChannelView(Channel channel, int level) {

		switch (level) {
		case 1:
			if (channel.getChildrenChannelsCount() == 0
					&& channel.getChildrenContentsCount() > 0) {
				this.reset(1);
				showContentList(channel);
			} else if (channel.getChildrenChannelsCount() > 0
					&& channel.getChildrenContentsCount() == 0) {// 有子channle
				this.reset(1);
				hideSwitchView(2);// 二级Channel
				laodSubChannels(channel, 2);
			}
			break;
		case 2:
			if (channel.getChildrenChannelsCount() == 0
					&& channel.getChildrenContentsCount() > 0) {
				this.reset(2);
				showContentList(channel);
			} else if (channel.getChildrenChannelsCount() > 0
					&& channel.getChildrenContentsCount() == 0) {// 有子channle
				this.reset(2);
				hideSwitchView(3);// 三级Channel
				laodSubChannels(channel, 3);
			}
			break;

		case 3:
			if (channel.getChildrenChannelsCount() == 0
					&& channel.getChildrenContentsCount() > 0) {

				showContentList(channel);
			}
			break;

		}
	}

	private class Level2ChannelChangeLister implements
			RadioGroup.OnCheckedChangeListener {

		private List<Channel> channels;

		public Level2ChannelChangeLister(List<Channel> channels) {
			this.channels = channels;
		}

		@Override
		public void onCheckedChanged(RadioGroup group, int checkedId) {

			for (int i = 0; i < group.getChildCount(); i++) {

				RadioButton r = (RadioButton) group.getChildAt(i);

				if (isFirst2Change && i == 0) {
					r.setBackgroundColor(Color.TRANSPARENT);
					r.setTextColor(Color.BLACK);
				}

				if (r.isChecked()) {

					r.setBackgroundResource(R.drawable.public_service_channel_2bg);
					r.setTextColor(Color.WHITE);

					showChannelView(channels.get(i), 2);

				} else {

					r.setBackgroundResource(R.drawable.public_service_channel_3bg);
					r.setTextColor(Color.BLACK);
				}

			}

			isFirst2Change = false;

		}

	};

	private class Level1ChannelChangeLister implements
			RadioGroup.OnCheckedChangeListener {

		private List<Channel> channels;

		public Level1ChannelChangeLister(List<Channel> channels) {
			this.channels = channels;
		}

		@Override
		public void onCheckedChanged(RadioGroup group, int checkedId) {

			for (int i = 0; i < group.getChildCount(); i++) {

				RadioButton r = (RadioButton) group.getChildAt(i);

				if (isFirst1Change && i == 0) {
					r.setBackgroundColor(Color.TRANSPARENT);
					r.setTextColor(Color.BLACK);
				}

				if (r.isChecked()) {

					r.setBackgroundResource(R.drawable.public_service_channel_2bg);
					r.setTextColor(Color.WHITE);

					showChannelView(channels.get(i), 1);

				} else {

					r.setBackgroundResource(R.drawable.public_service_channel_3bg);
					r.setTextColor(Color.BLACK);
				}

			}

			isFirst1Change = false;

		}

	};

	private class Level3ChannelChangeLister implements
			RadioGroup.OnCheckedChangeListener {

		private List<Channel> channels;

		public Level3ChannelChangeLister(List<Channel> channels) {
			this.channels = channels;
		}

		@Override
		public void onCheckedChanged(RadioGroup group, int checkedId) {

			for (int i = 0; i < group.getChildCount(); i++) {

				RadioButton r = (RadioButton) group.getChildAt(i);

				if (isFirst3Change && i == 0) {

					r.setBackgroundColor(Color.TRANSPARENT);
					r.setTextColor(Color.BLACK);
				}

				if (r.isChecked()) {

					r.setBackgroundResource(R.drawable.public_service_channel_2bg);
					r.setTextColor(Color.WHITE);

					showChannelView(channels.get(i), 3);

				} else {

					r.setBackgroundResource(R.drawable.public_service_channel_3bg);
					r.setTextColor(Color.BLACK);
				}

			}

			isFirst3Change = false;

		}

	}

	@Override
	public void loadSuccess() {

		pb_publicserivce.setVisibility(ProgressBar.GONE);
	}

}
