package com.wuxi.app.fragment.homepage.publicservice;

import java.util.List;

import org.json.JSONException;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.wuxi.app.BaseFragment;
import com.wuxi.app.R;
import com.wuxi.app.engine.ChannelService;
import com.wuxi.app.engine.ContentService;
import com.wuxi.app.util.CacheUtil;
import com.wuxi.domain.Channel;
import com.wuxi.domain.Content;
import com.wuxi.exception.NODataException;
import com.wuxi.exception.NetException;

/**
 * 
 * @author wanglu 泰得利通 
 * 
 * 频道显示的内容Fragment
 */
@SuppressLint("HandlerLeak")
public class PublicServiceChannelContentFragment extends BaseFragment {
	protected static final int CHANNEL_LOAD_SUCESS = 0;// 子频道获取成功

	protected static final int CHANNEL_LOAD_FAIL = 1;// 子频道获取失败

	protected static final int COTENT_LOAD_SUCCESS = 2;// 单条内容获取成功

	protected static final int COTENT_LOAD_FAIL = 3;// 单条内容获取失败

	private Channel channel;// 选中的channel

	private View view;
	private LinearLayout wuxicity_decontent_ll;// 内容

	//private ListView wucity_content_lv; // 内容列表
	private HorizontalScrollView wucity_channel_hs;// 头部Channel
	private List<Channel> titleChannels;
	private Context context;
	private RadioGroup wuxicity_rg_title_chanel;
	private ProgressBar pb_content_wb;// webView加载进度条
	private Content content;
	private WebView wuxicity_decontent_wb;// 加载数据的webView
	private TextView wuxicity_decontent_title;// 标题
	private TextView wuxi_decontent_tvtime;// 时间
	private TextView wuxi_decontent_tvbrowcount;// 浏览次数
	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case CHANNEL_LOAD_SUCESS:
				showChannelData();
				break;
			case COTENT_LOAD_SUCCESS:
				showContentData();
				break;
			case COTENT_LOAD_FAIL:
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

	/**
	 * 
	 * wanglu 泰得利通 显示单条数据
	 */
	@SuppressLint("SetJavaScriptEnabled")
	private void showContentData() {
		String wapUrl = content.getWapUrl();
		String title = content.getTitle();
		String time = content.getPublishTime();
		int browCount = content.getBrowseCount();

		wuxicity_decontent_title.setText(title);
		wuxi_decontent_tvtime.setText(wuxi_decontent_tvtime.getText()
				.toString() + time);// 时间
		wuxi_decontent_tvbrowcount.setText(wuxi_decontent_tvbrowcount.getText()
				.toString() + browCount);// 浏览次数

		wuxicity_decontent_wb.getSettings().setJavaScriptEnabled(true);

		wuxicity_decontent_wb.setWebChromeClient(new WebChromeClient() {
			public void onProgressChanged(WebView view, int progress) {

				if (progress == 100) {
					pb_content_wb.setVisibility(ProgressBar.GONE);// 移除进度条

				}

			}
		});

		wuxicity_decontent_wb.loadUrl(wapUrl);

	}

	/**
	 * 
	 * wanglu 泰得利通 初始化view
	 */
	private void initUI() {
		wuxicity_decontent_ll = (LinearLayout) view
				.findViewById(R.id.wuxicity_decontent_ll);
	/*	wucity_content_lv = (ListView) view
				.findViewById(R.id.wucity_content_lv);*/
		wucity_channel_hs = (HorizontalScrollView) view
				.findViewById(R.id.wucity_channel_hs);
		wuxicity_rg_title_chanel = (RadioGroup) view
				.findViewById(R.id.wuxicity_rg_title_chanel);
		pb_content_wb = (ProgressBar) view.findViewById(R.id.pb_content_wb);

		wuxicity_decontent_wb = (WebView) view
				.findViewById(R.id.wuxicity_decontent_wb);// 加载数据的webView
		wuxicity_decontent_title = (TextView) view
				.findViewById(R.id.wuxicity_decontent_title);// 标题
		wuxi_decontent_tvtime = (TextView) view
				.findViewById(R.id.wuxi_decontent_tvtime);// 时间
		wuxi_decontent_tvbrowcount = (TextView) view
				.findViewById(R.id.wuxi_decontent_tvbrowcount);// 浏览次数

		if (channel.getChildrenChannelsCount() > 0
				&& channel.getChildrenContentsCount() >= 0) {// 既有子频道也有内容的情况
																// //至显示频道
			wucity_channel_hs.setVisibility(HorizontalScrollView.VISIBLE);// 隐藏头部布局
		//	wucity_content_lv.setVisibility(ListView.GONE);// 隐藏列表布局
			wuxicity_decontent_ll.setVisibility(LinearLayout.GONE);// 显示具体内容布局

			loadTitleChannel();

		} else if (channel.getChildrenChannelsCount() == 0
				&& channel.getChildrenContentsCount() == 1) {// 只有一个内容的情况下
																// //显示内容
			wucity_channel_hs.setVisibility(HorizontalScrollView.GONE);// 隐藏头部布局
		//	wucity_content_lv.setVisibility(ListView.GONE);// 隐藏列表布局
			wuxicity_decontent_ll.setVisibility(LinearLayout.VISIBLE);// 显示具体内容布局

			loadContentData(this.channel);

		} else if (channel.getChildrenChannelsCount() == 0
				&& channel.getChildrenContentsCount() > 1) {// 有很多内容的情况下 显示内容列表
			wucity_channel_hs.setVisibility(HorizontalScrollView.GONE);// 隐藏头部布局
		//	wucity_content_lv.setVisibility(ListView.VISIBLE);// 隐藏列表布局
			wuxicity_decontent_ll.setVisibility(LinearLayout.GONE);// 显示具体内容布局

			LoadContentsData(this.channel);// 加载内容列表

		} else if (channel.getChildrenChannelsCount() == 0
				&& channel.getChildrenContentsCount() == 0) {
			wucity_channel_hs.setVisibility(HorizontalScrollView.GONE);// 隐藏头部布局
		//	wucity_content_lv.setVisibility(ListView.GONE);// 隐藏列表布局
			wuxicity_decontent_ll.setVisibility(LinearLayout.GONE);// 显示具体内容布局
			return;
		}

	}

	/**
	 * 
	 * wanglu 泰得利通 加载内容列表
	 * 
	 * @param channel
	 */
	private void LoadContentsData(Channel channel) {

	}

	/**
	 * 
	 * wanglu 泰得利通 加载只有一个内容的Channel数据
	 */
	private void loadContentData(final Channel channel) {

		new Thread(new Runnable() {

			@Override
			public void run() {

				Message msg = handler.obtainMessage();
				ContentService contentService = new ContentService(context);
				try {
					List<Content> contents = contentService
							.getContentsById(channel.getChannelId());
					if (contents != null) {
						content = contents.get(0);
						msg.what = COTENT_LOAD_SUCCESS;
					} else {

						msg.what = COTENT_LOAD_FAIL;
						msg.obj = "内容获取错误，稍候再试";
					}
					handler.sendMessage(msg);
				} catch (NetException e) {
					e.printStackTrace();
					msg.what = COTENT_LOAD_FAIL;
					msg.obj = e.getMessage();
					handler.sendMessage(msg);
				} catch (JSONException e) {
					e.printStackTrace();
					msg.what = COTENT_LOAD_FAIL;
					msg.obj = "数据格式出错";
					handler.sendMessage(msg);
				} catch (NODataException e) {
					e.printStackTrace();
					msg.what = COTENT_LOAD_FAIL;
					msg.obj = e.getMessage();
					handler.sendMessage(msg);
				}

			}
		}).start();

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

			RadioGroup.LayoutParams params = new RadioGroup.LayoutParams(
					RadioGroup.LayoutParams.WRAP_CONTENT, 20);
			params.leftMargin = 5;
			RadioButton radioButton = new RadioButton(context);
			radioButton.setBackground(getResources().getDrawable(
					R.drawable.wuxicity_content_channel_item_selector));
			radioButton.setText(channle.getChannelName());
			radioButton.setTextSize(12);
			radioButton.setButtonDrawable(new ColorDrawable(Color.TRANSPARENT));

			wuxicity_rg_title_chanel.addView(radioButton, params);

		}

	}

	public Channel getChannel() {
		return channel;
	}

	public void setChannel(Channel channel) {
		this.channel = channel;
	}

}
