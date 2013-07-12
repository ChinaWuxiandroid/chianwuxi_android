package com.wuxi.app.fragment.homepage.fantasticwuxi;

import java.util.List;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.wuxi.app.BaseFragment;
import com.wuxi.app.R;
import com.wuxi.app.engine.ChannelService;
import com.wuxi.app.util.CacheUtil;
import com.wuxi.domain.Channel;
import com.wuxi.exception.NetException;

/**
 * 
 * @author wanglu 泰得利通 魅力锡城显示的content区域
 */
public class WuxiChannelContentFragment extends BaseFragment {
	protected static final int CHANNEL_LOAD_SUCESS = 0;// 子频道获取成功

	protected static final int CHANNEL_LOAD_FAIL = 1;// 子频道获取失败

	private Channel channel;// 选中的channel

	private View view;
	private LinearLayout wuxicity_decontent_ll;// 内容

	private ListView wucity_content_lv; // 内容列表
	private HorizontalScrollView wucity_channel_hs;// 头部Channel
	private List<Channel> titleChannels;
	private Context context;
	private RadioGroup wuxicity_rg_title_chanel;

	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case CHANNEL_LOAD_SUCESS:
				showChannelData();
				break;
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
		view = inflater.inflate(R.layout.wuxi_city_content_layout, null);
		context = getActivity();
		initUI();

		return view;
	}

	private void showData() {

	}

	/**
	 * 
	 * wanglu 泰得利通 初始化view
	 */
	private void initUI() {
		wuxicity_decontent_ll = (LinearLayout) view
				.findViewById(R.id.wuxicity_decontent_ll);
		wucity_content_lv = (ListView) view
				.findViewById(R.id.wucity_content_lv);
		wucity_channel_hs = (HorizontalScrollView) view
				.findViewById(R.id.wucity_channel_hs);
		wuxicity_rg_title_chanel = (RadioGroup) view
				.findViewById(R.id.wuxicity_rg_title_chanel);

		if (channel.getChildrenChannelsCount() > 0
				&& channel.getChildrenContentsCount() >= 0) {// 既有子频道也有内容的情况
																// //至显示频道
			wucity_channel_hs.setVisibility(HorizontalScrollView.VISIBLE);// 显示头部布局

			loadTitleChannel();

		} else if (channel.getChildrenChannelsCount() == 0
				&& channel.getChildrenContentsCount() == 1) {// 只有一个内容的情况下
																// //显示内容

		} else if (channel.getChildrenChannelsCount() == 0
				&& channel.getChildrenContentsCount() >= 1) {// 有很多内容的情况下 显示内容列表

		} else if (channel.getChildrenChannelsCount() == 0
				&& channel.getChildrenContentsCount() == 0) {
			return;
		}

	}

	/**
	 * 
	 * wanglu 泰得利通 显示头部
	 */

	@SuppressWarnings("unchecked")
	private void loadTitleChannel() {

		if (CacheUtil.get(channel.getChannelId()) != null) {
			titleChannels = (List<Channel>) CacheUtil.get(channel
					.getChannelId());
			showChannelData();
			return;
		}

		new Thread(new Runnable() {

			@Override
			public void run() {

				Message msg = handler.obtainMessage();
				ChannelService channelService = new ChannelService(context);
				try {
					titleChannels = channelService.getSubChannels(channel
							.getChannelId());

					if (titleChannels != null) {
						msg.what = CHANNEL_LOAD_SUCESS;
						CacheUtil.put(channel.getChannelId(), titleChannels);// 放入缓存
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
	 * <!-- <RadioButton android:layout_width="wrap_content"
	 * android:background="@drawable/wuxicity_content_channel_item_selector"
	 * android:text="无锡市" android:textSize="12sp" android:button="@null"
	 * android:layout_marginLeft="3dip" android:checked="true" /> -->
	 * 
	 * wanglu 泰得利通 显示数据
	 */
	private void showChannelData() {

		for (Channel channle : titleChannels) {

			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
					LinearLayout.LayoutParams.WRAP_CONTENT,
					20);
			params.leftMargin = 80;
			RadioButton radioButton = new RadioButton(context);
			radioButton.setBackground(getResources().getDrawable(
					R.drawable.wuxicity_content_channel_item_selector));
			radioButton.setText(channle.getChannelName());
			radioButton.setTextSize(12);
			radioButton.setButtonDrawable(new ColorDrawable(Color.TRANSPARENT));
			//radioButton.setLayoutParams(params);
		//	View view=new View(Context);
			wuxicity_rg_title_chanel.addView(radioButton,params);

		}

	}

	public Channel getChannel() {
		return channel;
	}

	public void setChannel(Channel channel) {
		this.channel = channel;
	}

}
