package com.wuxi.app.listeners;

import com.wuxi.app.BaseFragment;
import com.wuxi.domain.MenuItem;

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
	
	public void bindContentLayout(BaseFragment fragment);
	
	
	/**
	 * 
	 *wanglu 泰得利通 
	 * @param showMenuItem 显示的菜单
	 * @param showMenuPositon 菜单的位置
	 * @param subMenuPostion 现在菜单的子菜单位置
	 */
	public void redirectFragment(MenuItem showMenuItem,int showMenuPositon,int subMenuPostion);
}
