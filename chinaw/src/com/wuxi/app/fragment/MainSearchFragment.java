package com.wuxi.app.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.wuxi.app.BaseFragment;
import com.wuxi.app.R;
import com.wuxi.app.fragment.search.AdvancedSearchFragment;
import com.wuxi.app.fragment.search.AdvancedSearchResultListFragment;
import com.wuxi.app.fragment.search.NormalSearchFragment;
import com.wuxi.app.fragment.search.SearchTitleFragment;

/**
 * 全站搜索模块  主Fragment
 * @author 杨宸 智佳
 * */

public class MainSearchFragment extends BaseFragment{

	public LayoutInflater inflater;
	public View view;
	public Context context;
	public FragmentTransaction transaction;  
	public SearchTitleFragment searchTitleFragment;
	public NormalSearchFragment normalSearchFragment;
	public AdvancedSearchFragment advancedSearchFragment;
	public AdvancedSearchResultListFragment advancedSearchResultListFragment;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		view = inflater.inflate(R.layout.main_search_fragment_layout, null);

		this.inflater = inflater;
		context = this.getActivity();
		initNormalSearchView();

		return view;
	}

	/*
	 * 初始化普通搜索界面
	 * */
	public void initNormalSearchView(){
		transaction=getFragmentManager().beginTransaction();
		//init title	
		searchTitleFragment=new SearchTitleFragment();  
		transaction.replace(R.id.search_title_fragment,  searchTitleFragment); 
		//init content
		normalSearchFragment=new NormalSearchFragment();
		transaction.replace(R.id.search_content_fragment,  normalSearchFragment); 
		transaction.commit(); 
	}

	/*
	 * 初始化高级搜索页面
	 * */
	public void initAdvancedSearchView(){
		transaction=getFragmentManager().beginTransaction();
		advancedSearchFragment=new AdvancedSearchFragment();
		transaction.replace(R.id.search_content_fragment,  advancedSearchFragment); 
		transaction.commit(); 

	}
	
	/*
	 * 初始化
	 * */
	public void initAdvancedSearchResultView(){
		transaction=getFragmentManager().beginTransaction();
		//init title	
		advancedSearchResultListFragment=new AdvancedSearchResultListFragment();  
		
		transaction.replace(R.id.search_content_fragment,  advancedSearchResultListFragment); 
		transaction.commit(); 
	}
}
