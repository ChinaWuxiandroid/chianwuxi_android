package com.wuxi.app.listeners;

import android.support.v4.app.Fragment;

import com.wuxi.app.fragment.homepage.publicservice.PublicServiceWithContentFragment;
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

<<<<<<< HEAD
			
			PublicServiceWithContentFragment publicServiceWithContentFragment=null;
			if(fragment instanceof PublicServiceWithContentFragment){
				publicServiceWithContentFragment=(PublicServiceWithContentFragment)fragment;
				publicServiceWithContentFragment.setParentMenuItem(menuItem);
				initLayoutListner.bindContentLayout(publicServiceWithContentFragment);
			}
=======
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

>>>>>>> 589271dd6f6ae408d6c34f1e266f566670dedd55

		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}

	}

}
