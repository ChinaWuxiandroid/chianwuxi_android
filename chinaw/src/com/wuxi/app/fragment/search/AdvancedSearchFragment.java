package com.wuxi.app.fragment.search;

import com.wuxi.app.BaseFragment;
import com.wuxi.app.R;
import com.wuxi.app.adapter.SearchSpinnerAdapter;
import com.wuxi.app.fragment.MainSearchFragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

/**
 * 高级搜索的fragment  
 * @author 杨宸 智佳
 * */

public class AdvancedSearchFragment extends MainSearchFragment{
	protected View view;
	protected LayoutInflater mInflater;
	private ImageButton toNormalSearch_Btn;   //跳转到普通检索  的按钮
	private Spinner infoType_spinner,resultsPerPage_spinner,contentType_spinner;    //三种spinner
	private ImageButton searchNow_Btn;   //立即搜索
	private EditText keyWord_editText;   //关键字
	private Context context; 

	private static  String[] infoType_arr;  
	private static  String[] resultsPerPage_arr;  
	private static  String[] contentType_arr;  
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.advanced_search_layout, null);
		mInflater = inflater;
		context = getActivity();

		initView();
		return view;
	}

	public void initView(){
//		infoType_arr=getResources().getStringArray(R.array.infoType);
//		resultsPerPage_arr=getResources().getStringArray(R.array.resultsPerPage);
		contentType_arr=getResources().getStringArray(R.array.contentType);

		infoType_spinner=(Spinner)view.findViewById(R.id.search_spinner_infoType);
		resultsPerPage_spinner=(Spinner)view.findViewById(R.id.search_spinner_resultsperpage);
		contentType_spinner=(Spinner)view.findViewById(R.id.search_spinner_contentType);


		ArrayAdapter infoType_Spinner_adapter = ArrayAdapter.createFromResource(context, R.array.infoType, android.R.layout.simple_spinner_item);  
		infoType_Spinner_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);  
		infoType_spinner.setAdapter(infoType_Spinner_adapter);  
		infoType_spinner.setVisibility(View.VISIBLE); 

		ArrayAdapter resultsPerPage_adapter = ArrayAdapter.createFromResource(context, R.array.resultsPerPage, android.R.layout.simple_spinner_item);  
		resultsPerPage_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);  
		resultsPerPage_spinner.setAdapter(resultsPerPage_adapter);  
		resultsPerPage_spinner.setVisibility(View.VISIBLE); 

//		ArrayAdapter contentType_Spinner_adapter = ArrayAdapter.createFromResource(context, R.array.contentType, android.R.layout.simple_spinner_item);  
//		contentType_Spinner_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);  
//		contentType_spinner.setAdapter(contentType_Spinner_adapter);  
//		contentType_spinner.setVisibility(View.VISIBLE); 
		//        spinner2.setOnItemSelectedListener(new SpinnerXMLSelectedListener());  

		SearchSpinnerAdapter adapter=new SearchSpinnerAdapter(context, R.array.contentType, contentType_arr);
		contentType_spinner.setAdapter(adapter);


		toNormalSearch_Btn=(ImageButton)view.findViewById(R.id.search_imageButton_to_normal_search);
		keyWord_editText=(EditText)view.findViewById(R.id.search_advanced_edittext_keyword);
		searchNow_Btn=(ImageButton)view.findViewById(R.id.search_imageButton_search_now);

		toNormalSearch_Btn.setOnClickListener(toNormalSearchClick);
		searchNow_Btn.setOnClickListener(searchNowClick);
	}

	private OnClickListener toNormalSearchClick = new OnClickListener() {
		@Override
		public void onClick(View v) {
			//跳转到普通搜索页面
			initNormalSearchView();
		}
	};

	private OnClickListener searchNowClick = new OnClickListener() {
		@Override
		public void onClick(View v) {
			Toast.makeText(context, "立即搜索", 2000).show();
		}
	};
	
	

}
