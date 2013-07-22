package com.wuxi.app.fragment.homepage.informationcenter;

import android.support.v4.app.Fragment;

import com.wuxi.app.fragment.commonfragment.MenuItemNavigatorWithContentFragment;
import com.wuxi.app.fragment.homepage.goverpublicmsg.NavigatorContentExpandListFragment;
import com.wuxi.domain.Channel;
import com.wuxi.domain.MenuItem;

/**
 * 
 * 
 * @author wanglu 信息中心具有左右导航的视图
 * 
 */

public class InfoNavigatorWithContentFragment extends
		MenuItemNavigatorWithContentFragment {

	@Override
	protected Fragment showMenItemContentFragment(MenuItem menuItem) {
		if (menuItem.getType() == MenuItem.WAP_MENU) {
			WapFragment wapFragment = new WapFragment();
			wapFragment.setParentItem(menuItem);
			return wapFragment;
		}
		else if(menuItem.getType() == MenuItem.CUSTOM_MENU||menuItem.getType() == MenuItem.CHANNEL_MENU){
			NavigatorContentExpandListFragment navigatorContentExpandListFragment=new NavigatorContentExpandListFragment();
			navigatorContentExpandListFragment.setParentItem(menuItem);
			return navigatorContentExpandListFragment;
		}
		

		return null;
	}

	@Override
	protected Fragment showChannelContentFragment(Channel channel) {
		return null;
	}

	

}
