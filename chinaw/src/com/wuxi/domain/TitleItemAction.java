package com.wuxi.domain;

import com.wuxi.app.BaseFragment;

/**
 * 头部action动作属性封装
 * 
 * @author wanglu
 * 
 */
public class TitleItemAction {

	private String actionName;// action名称

	private BaseFragment baseFragment;// 要跳转到fragment

	public BaseFragment getBaseFragment() {
		return baseFragment;
	}

	public void setBaseFragment(BaseFragment baseFragment) {
		this.baseFragment = baseFragment;
	}

	public TitleItemAction(String actionName, BaseFragment baseFragment) {
		super();
		this.actionName = actionName;
		this.baseFragment = baseFragment;
	}

	public String getActionName() {
		return actionName;
	}

	public void setActionName(String actionName) {
		this.actionName = actionName;
	}

}
