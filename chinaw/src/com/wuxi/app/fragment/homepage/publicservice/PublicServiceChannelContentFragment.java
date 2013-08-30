package com.wuxi.app.fragment.homepage.publicservice;

import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.wuxi.app.BaseFragment;
import com.wuxi.app.R;
import com.wuxi.app.adapter.PublicSericeChannelAdapter;
import com.wuxi.app.engine.ChannelService;
import com.wuxi.app.util.CacheUtil;
import com.wuxi.domain.Channel;
import com.wuxi.exception.NetException;

/**
 * 
 * @author wanglu 泰得利通
 * 
 *         频道显示的内容Fragment
 */
@SuppressLint("HandlerLeak")
public class PublicServiceChannelContentFragment extends BaseFragment implements OnItemClickListener {
	protected static final int CHANNEL_LOAD_SUCESS = 0;// 子频道获取成功

	protected static final int CHANNEL_LOAD_FAIL = 1;// 子频道获取失败
	protected static final int CHANNELCONTENT_ID=R.id.channel_content;
	private ProgressBar pb_publicserivce;
	private ListView publicservice_lv_channel;
	private Channel channel;// 选中的channel
	public List<Channel> subChannels;
	private View view;

	private Context context;

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
		publicservice_lv_channel.setOnItemClickListener(this);
		loadTitleChannel();
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
						CacheUtil.put(channel.getChannelId(), subChannels);// 放入缓存
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
	public void onItemClick(AdapterView<?> adapterView, View arg1, int position, long arg3) {
	
		Channel channel=(Channel) adapterView.getItemAtPosition(position);
		
		PublicServiceChannelContentDetailFragment publicServiceChannelContentDetailFragment=new PublicServiceChannelContentDetailFragment();
		publicServiceChannelContentDetailFragment.setArguments(this.getArguments());
		publicServiceChannelContentDetailFragment.setBaseSlideFragment(this.baseSlideFragment);
		publicServiceChannelContentDetailFragment.setChannel(channel);
		FragmentManager manager = getActivity().getSupportFragmentManager();
		FragmentTransaction ft = manager.beginTransaction();

		ft.replace(CHANNELCONTENT_ID, publicServiceChannelContentDetailFragment);

		ft.commitAllowingStateLoss();
	}

}
