package com.wuxi.app.fragment.homepage.goverpublicmsg;

import android.support.v4.app.Fragment;

import com.wuxi.app.fragment.commonfragment.MenuItemNavigatorWithContentFragment;
import com.wuxi.app.fragment.homepage.informationcenter.WapFragment;
import com.wuxi.domain.Channel;
import com.wuxi.domain.MenuItem;

/**
 * 政府信息公开  导航带内容类
 * @author 杨宸 智佳
 * */
public class GoverMsgNaviWithContentFragment extends MenuItemNavigatorWithContentFragment{
	@Override
	protected Fragment showMenItemContentFragment(MenuItem menuItem) {
		if(menuItem.getType() == MenuItem.CUSTOM_MENU||menuItem.getType() == MenuItem.CHANNEL_MENU){
			NavigatorContentExpandListFragment navigatorContentExpandListFragment=new NavigatorContentExpandListFragment();
			navigatorContentExpandListFragment.setParentItem(menuItem);
			return navigatorContentExpandListFragment;
		}
		else if (menuItem.getType() == MenuItem.WAP_MENU) {
			GoverMsgWebFragment wapFragment = new GoverMsgWebFragment();
			wapFragment.setParentItem(menuItem);
			return wapFragment;
		}
		return null;
	}

	@Override
	protected Fragment showChannelContentFragment(Channel channel) {
		return null;
	}
}
