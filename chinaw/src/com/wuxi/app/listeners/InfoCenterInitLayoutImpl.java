package com.wuxi.app.listeners;

import android.support.v4.app.Fragment;

import com.wuxi.app.fragment.homepage.informationcenter.LeaderWindowFragment;
import com.wuxi.domain.MenuItem;

/**
 * 咨询中心监听点击监听
 * 
 * @author wanglu 泰得利通
 * 
 */
public class InfoCenterInitLayoutImpl implements MenuItemInitLayoutListener {

	@Override
	public void bindMenuItemLayout(
			InitializContentLayoutListner initLayoutListner, MenuItem menuItem) {

		Class<? extends Fragment> fragmentClass = menuItem.getContentFragment();
		Fragment fragment;

		try {
			fragment = (Fragment) fragmentClass.newInstance();

			if (fragment == null) {
				return;
			}

			LeaderWindowFragment leaderWindowFragment = null;

			if (fragment instanceof LeaderWindowFragment) {
				leaderWindowFragment = (LeaderWindowFragment) fragment;
				initLayoutListner.bindContentLayout(leaderWindowFragment);
			}

		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}

	}

}
