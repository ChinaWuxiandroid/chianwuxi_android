package com.wuxi.app.fragment.homepage.mygoverinteractpeople;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wuxi.app.BaseFragment;
import com.wuxi.app.R;
import com.wuxi.domain.MenuItem;

/**
 * 政民互动的内容页面
 * 
 * @author 杨宸 智佳
 * */
public class MyGoverInterPeopleContentFragment extends BaseFragment {
	
	private MenuItem menuItem;

	private View view;

	private static final int CONTENT_MAIN_ID = R.id.gover_interact_people_maincontent_fragment;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		view = inflater.inflate(R.layout.main_me_fragment_layout, null);
		initUI();
		return view;
	}

	private void initUI() {

		switch (menuItem.getType()) {

		// 普通菜单类型
		case MenuItem.CUSTOM_MENU:
			GIPContentFragment gIPMenuItemContentFragment = new GIPContentFragment();
			gIPMenuItemContentFragment.setParentItem(menuItem);
			onTransaction(gIPMenuItemContentFragment);
			break;

		// 频道菜单类型
		case MenuItem.CHANNEL_MENU:
			GIPContentFragment gIPChannelContentFragment = new GIPContentFragment();

			gIPChannelContentFragment.setParentItem(menuItem);
			onTransaction(gIPChannelContentFragment);
			break;
		case MenuItem.APP_MENU:
			// 我的政民互动*
			if (menuItem.getAppUI().equals("MyPoliticalInteraction")) {
				onTransaction(new GoverInterPeopleMineFragment());
			}
//			// 12345来信办理平台*
//			else if (menuItem.getAppUI().equals("Letter12345")) {
//				onTransaction(new GoverInterPeople12345Fragment());
//			}
			// 征求意见平台*
			else if (menuItem.getAppUI().equals("QuestionnairePlatform")) {
				GoverInterPeopleSuggestFragment goverInterPeopleSuggestFragment = new GoverInterPeopleSuggestFragment();
				goverInterPeopleSuggestFragment.setParentItem(menuItem);
				onTransaction(goverInterPeopleSuggestFragment);
			}
//			// 视频直播平台
//			else if (menuItem.getAppUI().equals("VideoBroadcastPlatform")) {
//				GoverInterPeopleVideoLiveFragment goverInterPeopleVideoLiveFragment = new GoverInterPeopleVideoLiveFragment();
//				onTransaction(goverInterPeopleVideoLiveFragment);
//			}
			// 公众论坛
			else if (menuItem.getAppUI().equals("PublicBBS")) {
				GoverInterPeoplePublicForumFragment goverInterPeoplePublicForumFragment = new GoverInterPeoplePublicForumFragment();
				onTransaction(goverInterPeoplePublicForumFragment);
			}
			// 热点话题
			else if (menuItem.getAppUI().equals("HotTopic")) {
				GoverInterPeopleHotReviewFragment goverInterPeopleHotReviewFragment = new GoverInterPeopleHotReviewFragment();
				onTransaction(goverInterPeopleHotReviewFragment);

			}
			// 公开电话
			else if (menuItem.getAppUI().equals("PublicTel")) {
				onTransaction(new GoverInterPeopleOpenTelFragment());
			}
			// 公众监督*
			else if (menuItem.getAppUI().equals("PublicOversight")) {
				onTransaction(new GoverInterPeoplePublicSuperviseFragment());
			}
			break;
		case MenuItem.WAP_MENU:
			break;
		case MenuItem.LINK_MENU:
			break;
		}

	}

	public void setMenuItem(MenuItem menuItem) {
		this.menuItem = menuItem;
	}

	private void onTransaction(BaseFragment fragment) {
		fragment.setArguments(this.getArguments());
		FragmentManager manager = getActivity().getSupportFragmentManager();
		FragmentTransaction ft = manager.beginTransaction();

		ft.replace(CONTENT_MAIN_ID, fragment);

		ft.commitAllowingStateLoss();

	}
}
