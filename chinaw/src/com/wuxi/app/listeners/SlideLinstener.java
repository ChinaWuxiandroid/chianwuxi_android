package com.wuxi.app.listeners;

import com.wuxi.app.fragment.BaseSlideFragment;
import com.wuxi.app.util.Constants;
import com.wuxi.domain.MenuItem;

/**
 * 
 * @author wanglu 泰得利通 菜单监听器
 */
public interface SlideLinstener {
	public void openLeftSlideMenu();

	public void openRightSlideMenu();

	/**
	 * 
	 * wanglu 泰得利通 关闭按钮回调事件
	 */
	public void closeSlideMenu();

	public boolean isLeftMenuEnabled();

	public boolean isRightMenuEnabled();

	public void setUnTouchableOffset(float start, float end);

	public void OpearnLeft();

	public void OpearnRight();

	public void onBack();

	public void replaceFragment(MenuItem menuItem, int position,
			Constants.FragmentName fagmentName);
}
