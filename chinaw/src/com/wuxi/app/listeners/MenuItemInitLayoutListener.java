package com.wuxi.app.listeners;

import com.wuxi.domain.MenuItem;

/**
 * 
 * @author wanglu 泰得利通 菜单监听
 * 
 */
public interface MenuItemInitLayoutListener {

	/**
	 * 
	 * wanglu 泰得利通
	 * 
	 * @param menuItem
	 *            设置菜单与绑定并切换
	 */
	public void bindMenuItemLayout(InitializContentLayoutListner initLayoutListner,
			MenuItem menuItem);
}
