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
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.wuxi.app.BaseFragment;
import com.wuxi.app.R;
import com.wuxi.app.adapter.PublicSericeChannelAdapter;
import com.wuxi.app.listeners.GoverMsgInitInfoOpenListener;
import com.wuxi.domain.Channel;
import com.wuxi.domain.MenuItem;

public class GoverMsgCustomContentDetailFragment extends BaseFragment implements
		OnItemClickListener,OnClickListener{
	private Context context;
	private View view;

	// private Channel channel;
	private MenuItem parentMenuItem;
	private Channel parentChannel;
	public List<Channel> subChannels;

	private ProgressBar pb_govermsg;
	private TextView textView_title;
	private ListView govermsg_detail_lv_channel;
	private Button packup_btn;

	protected static final int CHANNEL_LOAD_SUCESS = 0;// 子频道获取成功
	protected static final int CHANNEL_LOAD_FAIL = 1;// 子频道获取失败
	private static final int CONTENT_LIST_ID = R.id.govermsg_detail_contentlist_framelayout;

	private int fifterType = 0;

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

	public void setFifterType(int type) {
		this.fifterType = type;
	}

	public MenuItem getParentMenuItem() {
		return parentMenuItem;
	}

	public void setParentMenuItem(MenuItem menuItem) {
		this.parentMenuItem = menuItem;
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

		view = inflater.inflate(R.layout.govermsg_detail_layout, null);
		context = getActivity();
		initUI();
		return view;
	}

	public void initUI() {
		pb_govermsg = (ProgressBar) view
				.findViewById(R.id.govermsg_detail_progressbar);
		textView_title = (TextView) view
				.findViewById(R.id.govermsg_detail_tv_title);
		govermsg_detail_lv_channel = (ListView) view
				.findViewById(R.id.govermsg_detail_listview);
		packup_btn = (Button) view
				.findViewById(R.id.govermsg_detail_btn_packup);
		
		govermsg_detail_lv_channel.setOnItemClickListener(this);
		packup_btn.setOnClickListener(this);
		
		if (parentMenuItem != null) {
			textView_title.setText(parentMenuItem.getName());
		} else if (parentChannel != null) {
			textView_title.setText(parentChannel.getChannelName());
		}

		showContentList();
	}

	private void showChannelData() {
		pb_govermsg.setVisibility(ProgressBar.GONE);

		govermsg_detail_lv_channel.setAdapter(new PublicSericeChannelAdapter(
				subChannels, context));
	}

	private void showContentList() {
		System.out.println("show content list:");
		// 在此加载四种类型的菜单
		if (parentMenuItem != null) {
			fifterType = GoverMsgInitInfoOpenListener.getMenuItemFragmentType(
					parentMenuItem, fifterType);
		} else if (parentChannel != null) {
			fifterType = GoverMsgInitInfoOpenListener.getChannelFragmentType(
					parentChannel, fifterType);
		}
		if (fifterType == 0) {
			GoverMsgCustomContentListFragment goverMsgCustomContentListFragment = new GoverMsgCustomContentListFragment();
			goverMsgCustomContentListFragment.setBaseSlideFragment(this.baseSlideFragment);
			goverMsgCustomContentListFragment.setParentItem(parentMenuItem);
			FragmentManager manager = getActivity().getSupportFragmentManager();
			FragmentTransaction ft = manager.beginTransaction();
			ft.replace(CONTENT_LIST_ID, goverMsgCustomContentListFragment);
			ft.commit();
		} else {
			GoverMsgSearchContentListFragment goverMsgSearchContentListFragment = new GoverMsgSearchContentListFragment();
			goverMsgSearchContentListFragment.setBaseSlideFragment(this.baseSlideFragment);
			goverMsgSearchContentListFragment.setFifterType(fifterType);
			if (parentMenuItem != null) {
				goverMsgSearchContentListFragment
						.setParentMenuItem(parentMenuItem);
			} else if (parentChannel != null) {
				goverMsgSearchContentListFragment.setChannel(parentChannel);
			}
			FragmentManager manager = getActivity().getSupportFragmentManager();
			FragmentTransaction ft = manager.beginTransaction();
			
			ft.replace(CONTENT_LIST_ID, goverMsgSearchContentListFragment);
			ft.commit();
		}
	}

	@Override
	public void onItemClick(AdapterView<?> adapterView, View arg1,
			int position, long arg3) {
		// TODO Auto-generated method stub
		// Channel channel=(Channel)adapterView.getItemAtPosition(position);
		// showContentList(channel);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()){
		case R.id.govermsg_detail_btn_packup:
			FragmentManager manager = getActivity().getSupportFragmentManager();
			FragmentTransaction ft = manager.beginTransaction();
			ft.remove(GoverMsgCustomContentDetailFragment.this);
			ft.commit();
			break;
		}
	}
}
