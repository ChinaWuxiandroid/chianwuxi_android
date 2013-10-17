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

		if (channel.getChildrenChannelsCount() > 0) {
			
			PublicServiceChannelContentFragment ch = new PublicServiceChannelContentFragment();
			ch.setArguments(this.getArguments());
			
			ch.setChannel(channel);
			return ch;
		} else if (channel.getChildrenContentsCount() > 0) {
			PublicServiceContentListFragment publicServiceContentListFragment = new PublicServiceContentListFragment();

			publicServiceContentListFragment.setChannel(channel);
			publicServiceContentListFragment.setArguments(getArguments());
			
			return publicServiceContentListFragment;

		}

		return null;
	}

}
