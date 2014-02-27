package com.wuxi.app;

import android.support.v4.app.Fragment;

/**
 * 该fragment是主界面之上的所有子fragment的父类 实现了fragment的管理类的传递 让子页面直接可以调用管理类进行跳转和切换
 * 
 * @author 方庆银
 * 
 */
public class BaseFragment extends Fragment {

	// 大部分Fragment需要实现初始化它的子布局
	public void initializSubFragmentsLayout() {

	}

}
