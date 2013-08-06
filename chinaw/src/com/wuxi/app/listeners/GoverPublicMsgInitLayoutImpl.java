package com.wuxi.app.listeners;

import android.support.v4.app.Fragment;

import com.wuxi.app.fragment.homepage.goverpublicmsg.GoverMsgContentListFragment;
import com.wuxi.app.fragment.homepage.goverpublicmsg.GoverMsgNaviWithContentFragment;
import com.wuxi.app.fragment.homepage.goverpublicmsg.GoverMsgWebFragment;
import com.wuxi.app.fragment.homepage.goverpublicmsg.WorkSuggestionBoxFragment;
import com.wuxi.domain.MenuItem;

public class GoverPublicMsgInitLayoutImpl implements MenuItemInitLayoutListener {

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

			GoverMsgWebFragment leaderWindowFragment = null;
			GoverMsgContentListFragment contentListFragment = null;
			GoverMsgNaviWithContentFragment contentNavigatorWithContentFragment = null;
			WorkSuggestionBoxFragment workSuggestionBoxFragment = null;

			if (fragment instanceof GoverMsgNaviWithContentFragment) {
				contentNavigatorWithContentFragment = (GoverMsgNaviWithContentFragment) fragment;
				contentNavigatorWithContentFragment.setParentMenuItem(menuItem);
				initLayoutListner
						.bindContentLayout(contentNavigatorWithContentFragment);
			} else if (fragment instanceof GoverMsgContentListFragment) {
				contentListFragment = (GoverMsgContentListFragment) fragment;
				contentListFragment.setParentItem(menuItem);
				initLayoutListner.bindContentLayout(contentListFragment);
			} else if (fragment instanceof GoverMsgWebFragment) {
				leaderWindowFragment = (GoverMsgWebFragment) fragment;
				leaderWindowFragment.setParentItem(menuItem);
				initLayoutListner.bindContentLayout(leaderWindowFragment);
			} else if (fragment instanceof WorkSuggestionBoxFragment) {
				workSuggestionBoxFragment = (WorkSuggestionBoxFragment) fragment;
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
