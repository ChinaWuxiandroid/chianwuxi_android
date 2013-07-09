package com.wuxi.app.fragment.search;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.wuxi.app.R;
import com.wuxi.app.fragment.MainSearchFragment;

/**
 * 搜索内容 fragment
 * @author 杨宸 智佳
 * */

public class SearchResultShowFragment extends MainSearchFragment{
	public View view = null;
	public ImageView OPearn_btn, Member_btnm,back_btn;
	public TextView Title_text;

	protected LayoutInflater mInflater;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.search_title_layout, null);
		mInflater = inflater;
		context = getActivity();
		
		initView();
		return view;
	}
	
	public void initView(){
		
	}
}
