package com.wuxi.app.fragment.search;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wuxi.app.R;
import com.wuxi.app.fragment.MainSearchFragment;

/**
 * 高级搜索的结果列表的fragment
 * @author 杨宸 智佳
 * */

public class AdvancedSearchResultListFragment extends MainSearchFragment{
	
	protected View view;
	protected LayoutInflater mInflater;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.search_advanced_searchresult_layout, null);
		mInflater = inflater;
		context = getActivity();

		initView();
		return view;
	}
	
	public void initView(){
		
	}
}
