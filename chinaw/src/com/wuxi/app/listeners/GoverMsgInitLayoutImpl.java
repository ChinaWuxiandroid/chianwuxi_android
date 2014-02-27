package com.wuxi.app.listeners;

import android.support.v4.app.Fragment;

import com.wuxi.app.fragment.commonfragment.NavigatorWithContentFragment;
import com.wuxi.app.fragment.commonfragment.SimpleListViewFragment;
import com.wuxi.app.fragment.homepage.goverpublicmsg.MunicipalGovInfoPublicDirecFragment;
import com.wuxi.app.fragment.homepage.goverpublicmsg.WorkSuggestionBoxFragment;
import com.wuxi.domain.MenuItem;

/**
 * 政府公开信息 头部滑动条 点击事件的布局处理 监听事件
 * 
 * @author 杨宸 智佳
 * */
public class GoverMsgInitLayoutImpl implements MenuItemInitLayoutListener {

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

			NavigatorWithContentFragment nafragment = null;
			SimpleListViewFragment listviewfragment = null;
			MunicipalGovInfoPublicDirecFragment municipalGovInfoPublicDirecFragment=null;
			WorkSuggestionBoxFragment workSuggestionBoxFragment = null;

			if (fragment instanceof NavigatorWithContentFragment) {
				nafragment = (NavigatorWithContentFragment) fragment;
				nafragment.setParentMenuItem(menuItem);
				nafragment.setDataType(NavigatorWithContentFragment.DATA_TPYE_MENUITEM);
			}

			if (fragment instanceof MunicipalGovInfoPublicDirecFragment) {
				municipalGovInfoPublicDirecFragment = (MunicipalGovInfoPublicDirecFragment) fragment;
			}
			
			if (fragment instanceof SimpleListViewFragment) {
				listviewfragment = (SimpleListViewFragment) fragment;
			}

			if (fragment instanceof WorkSuggestionBoxFragment) {
				workSuggestionBoxFragment = (WorkSuggestionBoxFragment) fragment;
			}

			if (initLayoutListner != null) {
				if (nafragment != null) {
					initLayoutListner.bindContentLayout(nafragment);
				} else if (listviewfragment != null) {
					initLayoutListner.bindContentLayout(listviewfragment);
				} else if(municipalGovInfoPublicDirecFragment!=null){
					initLayoutListner.bindContentLayout(municipalGovInfoPublicDirecFragment);
				}
				else{
					initLayoutListner.bindContentLayout(workSuggestionBoxFragment);
				}

			}
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}

	}

}
