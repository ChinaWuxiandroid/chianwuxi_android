package com.wuxi.app.fragment.homepage.goversaloon;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wuxi.app.BaseFragment;

/**
 * 
 * @author wanglu 泰得利通 内容页父类
 * 
 */
public abstract class GoverSaloonContentFragment extends BaseFragment {

	protected View view;

	protected Context context;
	protected LayoutInflater mInflater;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(getLayoutId(), null);
		this.context=getActivity();
		this.mInflater=inflater;
		
		initUI();
		
		
		return view;
	}

	
	protected void initUI() {
		
	}

	/**
	 * 子类实现 wanglu 泰得利通 该视图必须含有有头部和底部的view
	 * 
	 * @return
	 */
	protected abstract int getLayoutId();

}
