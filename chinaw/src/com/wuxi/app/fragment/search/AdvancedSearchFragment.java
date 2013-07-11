package com.wuxi.app.fragment.search;

import android.content.Context;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;

import com.wuxi.app.R;
import com.wuxi.app.fragment.MainSearchFragment;
import com.wuxi.app.fragment.commonfragment.HomeBaseSlideLevelFragment;

/**
 * 高级搜索的fragment  
 * @author 杨宸 智佳
 * */

public class AdvancedSearchFragment extends HomeBaseSlideLevelFragment{
	private Context context; 
	private ImageButton toNormalSearch_Btn;   //跳转到普通检索  的按钮
	private Spinner infoType_spinner,resultsPerPage_spinner,contentType_spinner;    //三种spinner
	private ImageButton searchNow_Btn;   //立即搜索
	private EditText keyWord_editText;   //关键字
	private EditText startDate_editText,endDate_editText;


	@Override
	protected void initUI() {
		context = getActivity();
		super.initUI();
		findView();
	}

	public void findView(){
		toNormalSearch_Btn=(ImageButton)view.findViewById(R.id.search_imageButton_to_normal_search);
		keyWord_editText=(EditText)view.findViewById(R.id.search_advanced_edittext_keyword);
		startDate_editText=(EditText)view.findViewById(R.id.search_advanced_edittext_startDate);

		endDate_editText=(EditText)view.findViewById(R.id.search_advanced_edittext_endDate);
		searchNow_Btn=(ImageButton)view.findViewById(R.id.search_imageButton_search_now);

		infoType_spinner=(Spinner)view.findViewById(R.id.search_spinner_infoType);
		resultsPerPage_spinner=(Spinner)view.findViewById(R.id.search_spinner_resultsperpage);
		contentType_spinner=(Spinner)view.findViewById(R.id.search_spinner_contentType);

		toNormalSearch_Btn.setOnClickListener(this);
		searchNow_Btn.setOnClickListener(this);
		setSpinnerAdapter();
	}


	public void setSpinnerAdapter(){

		ArrayAdapter infoType_Spinner_adapter = ArrayAdapter.createFromResource(context, R.array.infoType, android.R.layout.simple_spinner_item);  
		infoType_Spinner_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);  
		infoType_spinner.setAdapter(infoType_Spinner_adapter);  
		infoType_spinner.setVisibility(View.VISIBLE); 

		ArrayAdapter resultsPerPage_adapter = ArrayAdapter.createFromResource(context, R.array.resultsPerPage, android.R.layout.simple_spinner_item);  
		resultsPerPage_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);  
		resultsPerPage_spinner.setAdapter(resultsPerPage_adapter);  
		resultsPerPage_spinner.setVisibility(View.VISIBLE); 

		ArrayAdapter contentType_Spinner_adapter = ArrayAdapter.createFromResource(context, R.array.contentType, android.R.layout.simple_spinner_item);  
		contentType_Spinner_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);  
		contentType_spinner.setAdapter(contentType_Spinner_adapter);  
		contentType_spinner.setVisibility(View.VISIBLE); 	
	}

	@Override
	public void onClick(View v) {
		super.onClick(v);

		switch (v.getId()) {
		case R.id.search_imageButton_to_normal_search:
			HomeBaseSlideLevelFragment normalSearchFragment = new MainSearchFragment();
			managers.IntentFragment(normalSearchFragment);

			break;
		case R.id.search_imageButton_search_now:
			HomeBaseSlideLevelFragment searchAdvancedResultListFragment = new AdvancedSearchResultListFragment();
			managers.IntentFragment(searchAdvancedResultListFragment);
			break;
		}
	}


	@Override
	protected int getLayoutId() {
		return R.layout.slide_search_advanced_layout;
	}

	@Override
	protected void onBack() {
		managers.BackPress(this);
	}

	@Override
	protected String getTtitle() {
		return "全站搜索";
	}

}
