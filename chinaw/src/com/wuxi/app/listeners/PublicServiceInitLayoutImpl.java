package com.wuxi.app.listeners;

import android.support.v4.app.Fragment;

import com.wuxi.app.fragment.homepage.informationcenter.ContentListFragment;
import com.wuxi.app.fragment.homepage.informationcenter.InfoNavigatorWithContentFragment;
import com.wuxi.app.fragment.homepage.informationcenter.WapFragment;
import com.wuxi.domain.MenuItem;

/**
 * 公共服务监听点击监听
 * 
 * @author wanglu 泰得利通
 * 
 */
public class PublicServiceInitLayoutImpl implements MenuItemInitLayoutListener {

	@Override
	public void bindMenuItemLayout(
			InitializContentLayoutListner initLayoutListner, MenuItem menuItem) {
		System.out.println("bind lis in PublicServiceInitLayoutImpl");
		Class<? extends Fragment> fragmentClass = menuItem.getContentFragment();
		Fragment fragment;
		if (fragmentClass == null) {
			return;
		}

		try {
			fragment = (Fragment) fragmentClass.newInstance();

			if (fragment == null) {
				return;
			}

			WapFragment leaderWindowFragment = null;
			ContentListFragment contentListFragment = null;
			InfoNavigatorWithContentFragment contentNavigatorWithContentFragment=null;

			if (fragment instanceof WapFragment) {
				leaderWindowFragment = (WapFragment) fragment;
				leaderWindowFragment.setParentItem(menuItem);
				initLayoutListner.bindContentLayout(leaderWindowFragment);
			} else if (fragment instanceof ContentListFragment) {
				contentListFragment = (ContentListFragment) fragment;
				contentListFragment.setParentItem(menuItem);
				initLayoutListner.bindContentLayout(contentListFragment);
			}else if(fragment instanceof InfoNavigatorWithContentFragment){
				contentNavigatorWithContentFragment=(InfoNavigatorWithContentFragment)fragment;
				contentNavigatorWithContentFragment.setParentMenuItem(menuItem);
				initLayoutListner.bindContentLayout(contentNavigatorWithContentFragment);
			}


		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}

	}

}
