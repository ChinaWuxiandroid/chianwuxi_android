package com.wuxi.app;

import android.support.v4.app.Fragment;

/**
 * 该fragment是主界面之上的所有子fragment的父类
 * 实现了fragment的管理类的传递 让子页面直接可以调用管理类进行跳转和切换
 * @author 方庆银
 *
 */
public class BaseFragment extends Fragment {

	public FragmentManagers managers;

	public int position = 0;

	/**
	 * 传递管理类
	 * @param managers
	 */
	public void setManagers(FragmentManagers managers) {
		this.managers = managers;
	}

	public void setPosition(int position) {
		this.position = position;
	}

	//大部分Fragment需要实现初始化它的子布局
	public void initializSubFragmentsLayout(){
		
	}
}
 