package com.wuxi.app.fragment.homepage.goverpublicmsg;

import java.util.List;

import android.content.Context;
import android.graphics.Color;
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
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.wuxi.app.BaseFragment;
import com.wuxi.app.R;
import com.wuxi.app.engine.ChannelService;
import com.wuxi.app.listeners.GoverMsgInitInfoOpenListener;
import com.wuxi.domain.Channel;
import com.wuxi.exception.NetException;

/**
 * @author 杨宸
 * 政府信息公开  里  市政府信息公开目录 里含子频道 碎片
 */
public class GoverMsgSubChannelsSearchFragment extends BaseFragment{

	private Context context;
	private View view;
	private LayoutInflater mInflater;

	private Channel parentChannel;
	public List<Channel> subChannels;

	protected static final int SUBCHANNELS_LOAD_SUCESS = 0;// 子频道获取成功
	protected static final int SUBCHANNELS_LOAD_FAIL = 1;// 子频道获取失败
	private static final int CONTENT_LIST_ID = R.id.govermsg_subchannels_iframelayout;

	private int fifterType=0;
	
	private TextView title_textView;
	private GridView channelTitles_gridView;
	
	private int checkPoint=0;  //gridview选中坐标

	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case SUBCHANNELS_LOAD_SUCESS:
				showSubChannelsTitle();
				
				break;

			case SUBCHANNELS_LOAD_FAIL:
				String tip = msg.obj.toString();
				Toast.makeText(context, tip, Toast.LENGTH_SHORT).show();
				break;
			}

		};
	};

	public void setFifterType(int type){
		this.fifterType=type;
	}

	public Channel getParentChannel() {
		return parentChannel;
	}

	public void setParentChannel(Channel parentChannel) {
		this.parentChannel = parentChannel;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		view = inflater.inflate(R.layout.govermsg_subchannels_search_layout, null);
		context = getActivity();
		mInflater=LayoutInflater.from(context);
		initUI();
		return view;
	}

	public void initUI(){
		title_textView=(TextView)view.findViewById(R.id.govermsg_subchannels_imgview_tv_title);
		title_textView.setText(parentChannel.getChannelName().toString());
		
		loadSubChannelsTitle();
		
	}
	
	public void showSubChannelsTitle(){
		checkPoint=0;  //默认选中第一个
		channelTitles_gridView=(GridView) view.findViewById(R.id.govermsg_subchannels_gridview_subchannels);
		channelTitles_gridView.setNumColumns(subChannels.size());
		
		channelTitles_gridView.setAdapter(new ChannelGridViewAdaptger(subChannels,checkPoint));
		channelTitles_gridView.setOnItemClickListener(GridviewOnclick);
		
		showContentList(subChannels.get(checkPoint));
	}
	
	public void loadSubChannelsTitle(){
		new Thread(new Runnable() {

			@Override
			public void run() {
				Message msg = handler.obtainMessage();
				ChannelService channelService = new ChannelService(context);
				try {
					subChannels=channelService.getSubChannels(parentChannel.getChannelId());
					if (subChannels != null) {
						msg.what = SUBCHANNELS_LOAD_SUCESS;

					} else {
						msg.what = SUBCHANNELS_LOAD_FAIL;
						msg.obj = "内容获取错误,稍后重试";
					}
					handler.sendMessage(msg);

				} catch (NetException e) {
					e.printStackTrace();
					msg.what = SUBCHANNELS_LOAD_FAIL;
					msg.obj = e.getMessage();
					handler.sendMessage(msg);
				}
			}
		}).start();
	}

	private void showContentList(Channel channel) {
		//在此加载四种类型的菜单
		fifterType=GoverMsgInitInfoOpenListener.getChannelFragmentType(channel,fifterType);
		if(fifterType==0){
			GoverMsgCustomContentListFragment goverMsgCustomContentListFragment= new GoverMsgCustomContentListFragment();
			goverMsgCustomContentListFragment.setChannel(channel);
			FragmentManager manager = getActivity().getSupportFragmentManager();
			FragmentTransaction ft = manager.beginTransaction();
			ft.replace(CONTENT_LIST_ID, goverMsgCustomContentListFragment);
			ft.commit();
		}
		else{
			GoverMsgSearchContentListFragment goverMsgSearchContentListFragment= new GoverMsgSearchContentListFragment();
			goverMsgSearchContentListFragment.setFifterType(fifterType);		
			goverMsgSearchContentListFragment.setChannel(channel);
			FragmentManager manager = getActivity().getSupportFragmentManager();
			FragmentTransaction ft = manager.beginTransaction();
			ft.replace(CONTENT_LIST_ID, goverMsgSearchContentListFragment);
			ft.commit();
		}
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

		public class ViewHolder {
			TextView tv_title;
		}
		
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			Channel channel = channels.get(position);
			ViewHolder viewHolder = null;
			if (convertView == null) {
				convertView = mInflater.inflate(
						R.layout.govermsg_subchannels_gridview_item, null);

				viewHolder = new ViewHolder();

				viewHolder.tv_title = (TextView) convertView
						.findViewById(R.id.govermsg_subchannels_tv_title);

				if (screenIndex == 0 && position == 0) {

					viewHolder.tv_title
							.setBackgroundResource(R.drawable.goversaloon_menuitem_bg);
					viewHolder.tv_title.setTextColor(Color.WHITE);

				}

				convertView.setTag(viewHolder);
			} else {
				viewHolder = (ViewHolder) convertView.getTag();
			}

			viewHolder.tv_title.setText(channels.get(position).getChannelName());
			return convertView;
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
			if (checkPoint!= position) {
				View checkView = parent.getChildAt(position);

				TextView tv_Check = (TextView) checkView
						.findViewById(R.id.govermsg_subchannels_tv_title);
				tv_Check.setBackgroundResource(R.drawable.goversaloon_menuitem_bg);

				tv_Check.setTextColor(Color.WHITE);

				View oldCheckView = parent.getChildAt(checkPoint);
				if (null != oldCheckView) {

					TextView tv_oldCheck = (TextView) oldCheckView
							.findViewById(R.id.govermsg_subchannels_tv_title);
					tv_oldCheck.setBackgroundColor(getResources().getColor(
							R.color.content_background));

					tv_oldCheck.setTextColor(Color.BLACK);

				}
				checkPoint = position;
			}
			
			showContentList(channel);
		}
	};
}
