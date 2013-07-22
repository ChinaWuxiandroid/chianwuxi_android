package com.wuxi.app.listeners;

import android.support.v4.app.Fragment;

<<<<<<< HEAD
import com.wuxi.app.fragment.homepage.informationcenter.ContentListFragment;
import com.wuxi.app.fragment.homepage.informationcenter.InfoNavigatorWithContentFragment;
import com.wuxi.app.fragment.homepage.informationcenter.WapFragment;
=======
import com.wuxi.app.fragment.homepage.publicservice.PublicServiceWithContentFragment;
>>>>>>> b2bd5b26d505fe7d413d33e88ef7d6914d767b63
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


			
			PublicServiceWithContentFragment publicServiceWithContentFragment=null;
			if(fragment instanceof PublicServiceWithContentFragment){
				publicServiceWithContentFragment=(PublicServiceWithContentFragment)fragment;
				publicServiceWithContentFragment.setParentMenuItem(menuItem);
				initLayoutListner.bindContentLayout(publicServiceWithContentFragment);
			}


		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}

	}

}
