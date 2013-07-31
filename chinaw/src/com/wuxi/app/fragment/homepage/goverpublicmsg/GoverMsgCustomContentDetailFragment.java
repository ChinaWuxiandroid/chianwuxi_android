package com.wuxi.app.fragment.homepage.goverpublicmsg;

import java.util.List;

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
import android.widget.TextView;
import android.widget.Toast;

import com.wuxi.app.BaseFragment;
import com.wuxi.app.R;
import com.wuxi.app.adapter.PublicSericeChannelAdapter;
import com.wuxi.app.engine.ChannelService;
import com.wuxi.app.util.CacheUtil;
import com.wuxi.domain.Channel;
import com.wuxi.domain.MenuItem;
import com.wuxi.exception.NetException;

public class GoverMsgCustomContentDetailFragment extends BaseFragment implements OnItemClickListener{
	private Context context;
	private View view;
	
	private Channel channel;
	private MenuItem parentMenuItem;
	public List<Channel> subChannels;
	
	private ProgressBar pb_govermsg;
	private TextView textView_title;
	private ListView govermsg_detail_lv_channel;
	
	protected static final int CHANNEL_LOAD_SUCESS = 0;// 子频道获取成功
	protected static final int CHANNEL_LOAD_FAIL = 1;// 子频道获取失败
	private static final int CONTENT_LIST_ID = R.id.govermsg_detail_contentlist_framelayout;
	
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

		view = inflater.inflate(R.layout.govermsg_detail_layout, null);
		context = getActivity();
		initUI();
		return view;
	}
	
	public void initUI(){
		pb_govermsg = (ProgressBar) view
				.findViewById(R.id.govermsg_detail_progressbar);
		textView_title=(TextView)view
				.findViewById(R.id.govermsg_detail_tv_title);
		govermsg_detail_lv_channel = (ListView) view
				.findViewById(R.id.govermsg_detail_listview);
		
		govermsg_detail_lv_channel.setOnItemClickListener(this);
		textView_title.setText(parentMenuItem.getName());
		
//		loadContentList();
		showContentList(parentMenuItem);
	}
	
	@SuppressWarnings("unchecked")
	private void loadContentList() {

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
		pb_govermsg.setVisibility(ProgressBar.GONE);

		govermsg_detail_lv_channel.setAdapter(new PublicSericeChannelAdapter(
				subChannels, context));
	}
	
	public MenuItem getParentMenuItem() {
		return parentMenuItem;
	}

	public void setParentMenuItem(MenuItem menuItem) {
		this.parentMenuItem = menuItem;
	}

	private void showContentList(MenuItem parentMenuItem) {

		GoverMsgCustomContentListFragment goverMsgCustomContentListFragment= new GoverMsgCustomContentListFragment();

		goverMsgCustomContentListFragment.setParentItem(parentMenuItem);
		FragmentManager manager = getActivity().getSupportFragmentManager();
		FragmentTransaction ft = manager.beginTransaction();

		ft.replace(CONTENT_LIST_ID, goverMsgCustomContentListFragment);

		ft.commit();

	}

	@Override
	public void onItemClick(AdapterView<?> adapterView, View arg1, int position, long arg3) {
		// TODO Auto-generated method stub
//		Channel channel=(Channel)adapterView.getItemAtPosition(position);
//		showContentList(channel);
	}
}
