package com.wuxi.app.fragment.homepage.publicservice;

import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.HorizontalScrollView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.wuxi.app.BaseFragment;
import com.wuxi.app.R;
import com.wuxi.app.adapter.ContentChannelAdapter;
import com.wuxi.app.engine.ChannelService;
import com.wuxi.app.util.CacheUtil;
import com.wuxi.domain.Channel;
import com.wuxi.exception.NetException;

public class PublicServiceChannelContentDetailFragment extends BaseFragment
		implements OnItemClickListener {

	private Context context;
	private View view;
	private TextView public_detial_tv_title;
	private HorizontalScrollView publicservice_level1, publicservice_level2,
			publicservice_level3;
	private View dev_v_1, dev_v_2, dev_v_3;
	private static final int CONTENT_LIST_ID = R.id.publicservice_contentlist;
	protected static final int LOAD_CHANNEL_SUCCESS = 1;
	protected static final int LOAD_CHANNEL_FAIL = 0;
	private Channel channel;
	private int level;
	private PublicServiceContentListFragment publicServiceContentListFragment;
	private List<Channel> channels;
	// private GridView dishtype;
	private ContentChannelAdapter contentChannelAdapter;
	private RadioGroup publicserivce_rb_1, publicserivce_rb_2,
			publicserivce_rb_3;
	private boolean isFirst1Change = true;
	private boolean isFirst2Change = true;
	private boolean isFirst3Change = true;
	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case LOAD_CHANNEL_SUCCESS:
				showChannel(level);
				break;
			case LOAD_CHANNEL_FAIL:
				String tip = msg.obj.toString();
				Toast.makeText(context, tip, Toast.LENGTH_SHORT).show();
				break;
			}

		};
	};

	public void setChannel(Channel channel) {
		this.channel = channel;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		view = inflater.inflate(R.layout.publicservice_detail_layout, null);
		context = getActivity();
		initUI();
		return view;
	}

	private void initUI() {
		public_detial_tv_title = (TextView) view
				.findViewById(R.id.public_detial_tv_title);

		publicservice_level1 = (HorizontalScrollView) view
				.findViewById(R.id.publicservice_level1);
		publicservice_level2 = (HorizontalScrollView) view
				.findViewById(R.id.publicservice_level2);
		publicservice_level3 = (HorizontalScrollView) view
				.findViewById(R.id.publicservice_level3);
		dev_v_1 = (View) view.findViewById(R.id.dev_v_1);// 分割线
		dev_v_2 = (View) view.findViewById(R.id.dev_v_2);
		dev_v_3 = (View) view.findViewById(R.id.dev_v_3);
		publicserivce_rb_1=(RadioGroup) view.findViewById(R.id.publicserivce_rb_1);
		publicserivce_rb_2 = (RadioGroup) view
				.findViewById(R.id.publicserivce_rb_2);

		publicserivce_rb_2
				.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

					@Override
					public void onCheckedChanged(RadioGroup group, int checkedId) {

					}
				});

		publicserivce_rb_3 = (RadioGroup) view
				.findViewById(R.id.publicserivce_rb_3);

		// dishtype = (GridView) view.findViewById(R.id.dishtype);

		public_detial_tv_title.setText(channel.getChannelName());
		// dishtype.setOnItemClickListener(this);
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
						PublicServiceChannelContentDetailFragment.this.level = level;
						msg.what = LOAD_CHANNEL_SUCCESS;
						CacheUtil.put(channel.getChannelId(), channels);
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

		publicServiceContentListFragment = new PublicServiceContentListFragment();
		publicServiceContentListFragment.setArguments(this.getArguments());
		publicServiceContentListFragment.setChannel(channel);
		FragmentManager manager = getActivity().getSupportFragmentManager();
		FragmentTransaction ft = manager.beginTransaction();

		ft.replace(CONTENT_LIST_ID, publicServiceContentListFragment);

		ft.commit();

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

	@Override
	public void onItemClick(AdapterView<?> adapterView, View arg1,
			int position, long arg3) {
		Channel channel = (Channel) adapterView.getItemAtPosition(position);
		contentChannelAdapter.setSelectPosition(position);
		contentChannelAdapter.notifyDataSetChanged();

		showChannelView(channel, 1);

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

	};

}
