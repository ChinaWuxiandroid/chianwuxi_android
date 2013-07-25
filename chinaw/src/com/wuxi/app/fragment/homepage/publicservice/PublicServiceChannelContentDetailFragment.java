package com.wuxi.app.fragment.homepage.publicservice;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.TextView;

import com.wuxi.app.BaseFragment;
import com.wuxi.app.R;
import com.wuxi.domain.Channel;

public class PublicServiceChannelContentDetailFragment extends BaseFragment {

	private View view;
	private TextView public_detial_tv_title;
	private HorizontalScrollView publicservice_level1, publicservice_level2,
			publicservice_level3;
	private View dev_v_1, dev_v_2, dev_v_3;
	private static final int CONTENT_LIST_ID = R.id.publicservice_contentlist;
	private Channel channel;
	private PublicServiceContentListFragment publicServiceContentListFragment;

	public void setChannel(Channel channel) {
		this.channel = channel;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		view = inflater.inflate(R.layout.publicservice_detail_layout, null);
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

		public_detial_tv_title.setText(channel.getChannelName());

		if (channel.getChildrenChannelsCount() == 0
				&& channel.getChildrenContentsCount() > 0) {// 没有子菜单，有content
			hideSwitchView(0);
			showContentList(channel);

		}else if(channel.getChildrenContentsCount()==0&&channel.getChildrenChannelsCount()>0){//有子频道
			hideSwitchView(1);
			
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

		publicServiceContentListFragment.setChannel(channel);
		FragmentManager manager = getActivity().getSupportFragmentManager();
		FragmentTransaction ft = manager.beginTransaction();

		ft.replace(CONTENT_LIST_ID, publicServiceContentListFragment);

		ft.commit();

	}

	private void hideSwitchView(int level) {

		switch (level) {
		case 0:
			publicservice_level1.setVisibility(HorizontalScrollView.GONE);
			publicservice_level2.setVisibility(HorizontalScrollView.GONE);
			publicservice_level3.setVisibility(HorizontalScrollView.GONE);
			dev_v_1.setVisibility(View.GONE);
			dev_v_2.setVisibility(View.GONE);
			dev_v_3.setVisibility(View.GONE);
			break;
		case 1:
			publicservice_level1.setVisibility(HorizontalScrollView.VISIBLE);
			publicservice_level2.setVisibility(HorizontalScrollView.GONE);
			publicservice_level3.setVisibility(HorizontalScrollView.GONE);
			dev_v_1.setVisibility(View.VISIBLE);
			dev_v_2.setVisibility(View.GONE);
			dev_v_3.setVisibility(View.GONE);
			break;

		}

	}

}
