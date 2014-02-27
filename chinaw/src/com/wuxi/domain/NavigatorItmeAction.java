package com.wuxi.domain;

import android.support.v4.app.Fragment;

/**
 * 左侧导航数据封装
 * @author wanglu
 *
 */
public class NavigatorItmeAction {

	private String actionName;
	public NavigatorItmeAction(String actionName, Fragment frament) {
		super();
		this.actionName = actionName;
		this.frament = frament;
	}
	private Fragment frament;//右侧要显示的数据视图
	
	
	public String getActionName() {
		return actionName;
	}
	public void setActionName(String actionName) {
		this.actionName = actionName;
	}
	public Fragment getFrament() {
		return frament;
	}
	public void setFrament(Fragment frament) {
		this.frament = frament;
	}
	
}
