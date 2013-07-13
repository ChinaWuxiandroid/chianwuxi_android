package com.wuxi.app.fragment.homepage.publicservice;

import android.support.v4.app.Fragment;

import com.wuxi.app.fragment.commonfragment.MenuItemNavigatorWithContentFragment;
import com.wuxi.domain.Channel;
import com.wuxi.domain.MenuItem;

public class PublicServiceWithContentFragment extends
		MenuItemNavigatorWithContentFragment {

	@Override
	protected Fragment showMenItemContentFragment(MenuItem menuItem) {
		return null;
	}

	@Override
	protected Fragment showChannelContentFragment(Channel channel) {
		
		PublicServiceChannelContentFragment ch=new PublicServiceChannelContentFragment();
		ch.setChannel(channel);
		return ch;
	}

	

}
