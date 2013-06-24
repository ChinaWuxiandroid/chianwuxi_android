package com.wuxi.app;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

public class FragmentManagers {

	public static final int FRAME_CONTENT = R.id.main_content;
	public static FragmentManagers instance = null;
	public List<BaseFragment> fragments = new ArrayList<BaseFragment>();
	public Context context;
	public FragmentActivity fragmentActivity;

	public synchronized static FragmentManagers getInstance() {
		if (instance == null)
			instance = new FragmentManagers();
		return instance;
	}

	/***
	 * 页面的跳转 
	 * 原理：1、把当前管理类实例传给跳转的fragment
	 * 2、把跳转的fragment 添加到fragment集合中 便于管理
	 * 3、把跳转的fragment 覆盖在当前的fragment （界面的覆盖 相当于activity的跳转）
	 * @param saveFragment
	 */
	public void IntentFragment(BaseFragment saveFragment){
		saveFragment.setManagers(this);
		addFragment(saveFragment);
		onTransaction(saveFragment,"");
	}

	/***
	 * 页面的切换
	 * 原理：1、把当前管理类实例传给跳转的fragment
	 * 2、把跳转的fragment 添加到fragment集合中 便于管理
	 * 3、把跳转的fragment 替换成当前的fragment （界面的替换 相当于切换布局）
	 * @param saveFragment
	 */
	public void ChangeFragment(BaseFragment saveFragment){
		saveFragment.setManagers(this);
		RemoveAllFragment();
		onTransaction(saveFragment);
	}

	/***
	 * 从当前页面返回
	 * 原理1、从fragment集合中清除当前fragment 
	 * 2、从界面上删除当前fragment（相当于activyty的ondestory）
	 * @param saveFragment
	 */
	public void BackPress(BaseFragment saveFragment){
		clear(saveFragment);
		RemoveFragment(saveFragment);
	}
	
	public void setFragmentActivity(FragmentActivity fragmentActivity) {
		this.fragmentActivity = fragmentActivity;
	}
	public BaseFragment getCurrentFragment() {
		return fragments.get(fragments.size()-1);
	}

	public void clear(BaseFragment fragment){
		fragments.remove(fragments.indexOf(fragment));
	}
	
	public void clearAllFragment(){
		if(fragments==null) return ;
		fragments.clear();
	}
	
	public void addFragment(BaseFragment fragment){
		if(fragments.indexOf(fragment)>=0)
			return;
		fragments.add(fragment);
	}
	
	public void onTransaction(BaseFragment saveFragment) {
		FragmentManager manager = fragmentActivity.getSupportFragmentManager();
		FragmentTransaction ft = manager.beginTransaction();
		ft.replace(FRAME_CONTENT, saveFragment);
		ft.addToBackStack(null);
		ft.commit();
	}

	public void onTransaction(BaseFragment saveFragment,String arg) {
		FragmentManager manager = fragmentActivity.getSupportFragmentManager();
		FragmentTransaction ft = manager.beginTransaction();
		ft.add(FRAME_CONTENT, saveFragment,arg);
		ft.addToBackStack(null);
		ft.commit();
	}

	public void RemoveFragment(BaseFragment saveFragment) {
		FragmentManager manager = fragmentActivity.getSupportFragmentManager();
		FragmentTransaction ft = manager.beginTransaction();
		ft.remove(saveFragment);
		ft.addToBackStack(null);
		ft.commit();
	}

	public void RemoveAllFragment(){
		if(fragments==null || fragments.size()<=0)
			return ;
		for(int i=0;i<fragments.size();i++){
			RemoveFragment(fragments.get(i));
		}
	}

}
