package com.wuxi.app.listeners;

import com.wuxi.app.fragment.NavigatorChannelFragment;

import android.support.v4.app.Fragment;

/**
 * 内容界面绑定监听器
 * 
 * @author wanglu
 * 
 */
public interface InitializContentLayoutListner {

	/**
	 * 绑定界面
	 * @param fragment
	 */
	
	public void bindContentLayout(Fragment fragment);
}
