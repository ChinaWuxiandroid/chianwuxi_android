package com.wuxi.app.fragment.search;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.wuxi.app.R;
import com.wuxi.app.fragment.MainSearchFragment;
import com.wuxi.app.fragment.commonfragment.HomeBaseSlideLevelFragment;

/**
 * 搜索内容 fragment
 * @author 杨宸 智佳
 * */

public class SearchResultShowFragment extends MainSearchFragment{
	public View view = null;
	public ImageView back_btn, toList_btn,setting_btn,share_btn;
	public TextView Title_text;

	protected LayoutInflater mInflater;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.search_result_show_layout, null);
		mInflater = inflater;
		context = getActivity();
		findView();
		
		return view;
	}

	public void findView(){
		back_btn=(ImageView)view.findViewById(R.id.search_content_back_btn);
		toList_btn=(ImageView)view.findViewById(R.id.search_content_show_list);
		setting_btn=(ImageView)view.findViewById(R.id.search_content_setting);
		share_btn=(ImageView)view.findViewById(R.id.search_content_share);
		
		back_btn.setOnClickListener(this);
		toList_btn.setOnClickListener(this);
		setting_btn.setOnClickListener(this);
		share_btn.setOnClickListener(this);
		
	}
	
	@Override
	public void onClick(View v) {
		super.onClick(v);

		switch (v.getId()) {
		case R.id.search_content_back_btn:
			HomeBaseSlideLevelFragment searchNormalFragment = new MainSearchFragment();
			managers.IntentFragment(searchNormalFragment);

			break;
		case R.id.search_content_show_list:
			
			break;
		case R.id.search_content_setting:
			
			break;
			
		case R.id.search_content_share:
			HomeBaseSlideLevelFragment searchAdvancedFragment = new AdvancedSearchFragment();
			managers.IntentFragment(searchAdvancedFragment);
			break;
		}
	}

}
