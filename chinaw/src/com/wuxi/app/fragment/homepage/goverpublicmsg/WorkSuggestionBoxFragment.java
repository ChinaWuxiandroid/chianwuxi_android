package com.wuxi.app.fragment.homepage.goverpublicmsg;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wuxi.app.BaseFragment;
import com.wuxi.app.R;
import com.wuxi.domain.MenuItem;


/**
 * 工作意见邮箱  Fragment 布局
 * @author 杨宸 智佳
 * */

public class WorkSuggestionBoxFragment extends BaseFragment{
	protected View view;
	protected LayoutInflater mInflater;
	private Context context;
	private MenuItem parentMenuItem; // 父菜单
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.worksuggestionbox_layout, null);
		mInflater = inflater;
		context = getActivity();
		
		initView();
		return view;

	}

	public void initView(){

	}
	
	public void setParentMenuItem(MenuItem parentMenuItem) {
		this.parentMenuItem = parentMenuItem;
	}
}
