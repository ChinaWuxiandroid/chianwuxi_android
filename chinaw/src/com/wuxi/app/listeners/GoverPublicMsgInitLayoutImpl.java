package com.wuxi.app.listeners;

import android.support.v4.app.Fragment;

import com.wuxi.app.fragment.homepage.goverpublicmsg.WorkSuggestionBoxFragment;
import com.wuxi.app.fragment.homepage.informationcenter.InfoNavigatorWithContentFragment;
import com.wuxi.app.fragment.homepage.informationcenter.InforContentListFragment;
import com.wuxi.app.fragment.homepage.informationcenter.WapFragment;
import com.wuxi.domain.MenuItem;

public class GoverPublicMsgInitLayoutImpl implements MenuItemInitLayoutListener {

	@Override
	public void bindMenuItemLayout(
			InitializContentLayoutListner initLayoutListner, MenuItem menuItem) {
		System.out.println("bind lis in GoverPublicMsgInitLayoutImpl");
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
			InforContentListFragment contentListFragment = null;
			InfoNavigatorWithContentFragment contentNavigatorWithContentFragment=null;
			WorkSuggestionBoxFragment workSuggestionBoxFragment=null;
			if (fragment instanceof WapFragment) {
				leaderWindowFragment = (WapFragment) fragment;
				leaderWindowFragment.setParentItem(menuItem);
				initLayoutListner.bindContentLayout(leaderWindowFragment);
			} else if (fragment instanceof InforContentListFragment) {
				contentListFragment = (InforContentListFragment) fragment;
				contentListFragment.setParentItem(menuItem);
				initLayoutListner.bindContentLayout(contentListFragment);
			}else if(fragment instanceof InfoNavigatorWithContentFragment){
				contentNavigatorWithContentFragment=(InfoNavigatorWithContentFragment)fragment;
				contentNavigatorWithContentFragment.setParentMenuItem(menuItem);
				initLayoutListner.bindContentLayout(contentNavigatorWithContentFragment);
			}
			else if(fragment instanceof WorkSuggestionBoxFragment){
				workSuggestionBoxFragment=(WorkSuggestionBoxFragment)fragment;
				workSuggestionBoxFragment.setParentMenuItem(menuItem);
				initLayoutListner.bindContentLayout(workSuggestionBoxFragment);
			}

		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}

	}

}

