package com.wuxi.app.listeners;

import com.wuxi.app.fragment.commonfragment.NavigatorWithContentFragment;
import com.wuxi.app.fragment.commonfragment.SimpleListViewFragment;
import com.wuxi.app.fragment.homepage.goverpublicmsg.WorkSuggestionBoxFragment;
import com.wuxi.domain.MenuItem;
import android.support.v4.app.Fragment;

/**
 * 政府公开信息 头部滑动条 点击事件的布局处理  监听事件
 * @author 杨宸  智佳
 * */
public class GoverMsgInitLayoutListener{

	private static InitializContentLayoutListner initializContentLayoutListner;// 该自定义控件所在的fragment
	
	public static void setInitializContentLayoutListner(
			InitializContentLayoutListner initializContentLayoutListner) {
		GoverMsgInitLayoutListener.initializContentLayoutListner = initializContentLayoutListner;
	}
	
	public static void init(MenuItem menuItem){
		
		Class<? extends Fragment> fragmentClass = menuItem
				.getContentFragment();
		Fragment fragment;
		
		try {
			fragment = (Fragment) fragmentClass.newInstance();

			if (fragment == null) {
				return;
			}

			NavigatorWithContentFragment nafragment = null;
			SimpleListViewFragment listviewfragment = null;
			WorkSuggestionBoxFragment workSuggestionBoxFragment=null;
			
			if (fragment instanceof NavigatorWithContentFragment) {
				nafragment = (NavigatorWithContentFragment) fragment;
				nafragment.setParentMenuItem(menuItem);
				nafragment.setDataType(NavigatorWithContentFragment.DATA_TPYE_MENUITEM);
			}

			if (fragment instanceof SimpleListViewFragment) {
				listviewfragment = (SimpleListViewFragment) fragment;
			}
			
			if (fragment instanceof WorkSuggestionBoxFragment) {
				workSuggestionBoxFragment = (WorkSuggestionBoxFragment) fragment;
			}

			if (initializContentLayoutListner != null) {
				if (nafragment != null) {
					initializContentLayoutListner
							.bindContentLayout(nafragment);
				} 
				else if(listviewfragment!=null) {
					initializContentLayoutListner
							.bindContentLayout(listviewfragment);
				}
				else {
					initializContentLayoutListner
							.bindContentLayout(workSuggestionBoxFragment);
				}

			}
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	
	}


}
