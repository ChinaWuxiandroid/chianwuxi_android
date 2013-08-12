package com.wuxi.app.fragment.search;

import java.util.Calendar;

import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.wuxi.app.R;
import com.wuxi.app.fragment.BaseSlideFragment;
import com.wuxi.app.util.Constants;
import com.wuxi.domain.AdvancedSearchUtil;

/**
 * 高级搜索的fragment  
 * @author 杨宸 智佳
 * */

public class AdvancedSearchFragment extends BaseSlideFragment implements OnClickListener{

	private ImageButton toNormalSearch_Btn;   //跳转到普通检索  的按钮
	private Spinner infoType_spinner,resultsPerPage_spinner,contentType_spinner;    //三种spinner
	private ImageButton searchNow_Btn;   //立即搜索
	private EditText keyWord_editText;   //关键字
	private TextView beginDate_TextView,endDate_TextView;

	private AdvancedSearchUtil searchUtil=new AdvancedSearchUtil();
	private String infoType;
	private String pageSize;

	private Calendar calendar;
	private int year,month,day;
	
	private String DateSearchType="Modified-Day";
	private String beginTime;
	private String endTime;
	private String contentType;
	private String keyWord;

	@Override
	public  void initUI() {

		super.initUI();
		findView();
	}

	public void findView(){
		toNormalSearch_Btn=(ImageButton)view.findViewById(R.id.search_imageButton_to_normal_search);
		keyWord_editText=(EditText)view.findViewById(R.id.search_advanced_edittext_keyword);
		beginDate_TextView=(TextView)view.findViewById(R.id.search_advanced_edittext_startDate);
		endDate_TextView=(TextView)view.findViewById(R.id.search_advanced_edittext_endDate);
		
		
		searchNow_Btn=(ImageButton)view.findViewById(R.id.search_imageButton_search_now);

		infoType_spinner=(Spinner)view.findViewById(R.id.search_spinner_infoType);
		resultsPerPage_spinner=(Spinner)view.findViewById(R.id.search_spinner_resultsperpage);
		contentType_spinner=(Spinner)view.findViewById(R.id.search_spinner_contentType);

		toNormalSearch_Btn.setOnClickListener(this);
		searchNow_Btn.setOnClickListener(this);
		
		
		beginDate_TextView.setClickable(true);
		beginDate_TextView.setFocusable(true);
		endDate_TextView.setClickable(true);
		endDate_TextView.setFocusable(true);
		beginDate_TextView.setOnClickListener(this);
		endDate_TextView.setOnClickListener(this);
		setSpinnerAdapter();
		
		calendar=Calendar.getInstance();
		year=calendar.get(Calendar.YEAR);
		month=calendar.get(Calendar.MONTH);
		day=calendar.get(Calendar.DAY_OF_MONTH);
	}


	public void setSpinnerAdapter(){

		ArrayAdapter infoType_Spinner_adapter = ArrayAdapter.createFromResource(context, R.array.infoType, android.R.layout.simple_spinner_item);  
		infoType_Spinner_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);  
		infoType_spinner.setAdapter(infoType_Spinner_adapter);
		infoType_spinner.setVisibility(View.VISIBLE); 
		infoType_spinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View view,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				infoType=((TextView)view).getText().toString();
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}
		});

		ArrayAdapter resultsPerPage_adapter = ArrayAdapter.createFromResource(context, R.array.resultsPerPage, android.R.layout.simple_spinner_item);  
		resultsPerPage_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);  
		resultsPerPage_spinner.setAdapter(resultsPerPage_adapter);  
		resultsPerPage_spinner.setVisibility(View.VISIBLE); 
		resultsPerPage_spinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View view,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				pageSize=((TextView)view).getText().toString();
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}
		});

		ArrayAdapter contentType_Spinner_adapter = ArrayAdapter.createFromResource(context, R.array.contentType, android.R.layout.simple_spinner_item);  
		contentType_Spinner_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);  
		contentType_spinner.setAdapter(contentType_Spinner_adapter);  
		contentType_spinner.setVisibility(View.VISIBLE); 
		contentType_spinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View view,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				String buffTypeStr=((TextView)view).getText().toString();
				if(buffTypeStr.equals("正文")){
					contentType="content";
				}
				else if(buffTypeStr.equals("标题")){
					contentType="title";
				}
				else{
					contentType=buffTypeStr;
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}
		});
	}

	@Override
	public void onClick(View v) {
		BaseSlideFragment baseSlideFragment;

		switch (v.getId()) {
		case R.id.search_imageButton_to_normal_search:
			onRomove();

			break;
		case R.id.search_imageButton_search_now:
			getSearchUtil();
			//关闭软键盘
			InputMethodManager imm = (InputMethodManager)context.getSystemService(Context.INPUT_METHOD_SERVICE); 
			imm.hideSoftInputFromWindow(searchNow_Btn.getWindowToken(), 0);

			baseSlideFragment=(BaseSlideFragment)this;
			Bundle bundle=new Bundle();
			bundle.putSerializable("searchUtil", searchUtil);
			baseSlideFragment.slideLinstener.replaceFragment(null, -1,
					Constants.FragmentName.ADVANCED_SEARCH_LIST_FRAGMENT, bundle);
			
			break;
			//获取开始日期
		case R.id.search_advanced_edittext_startDate:
			showDateSelectDialog(beginDate_TextView);
			break;
			//获取结束日期
		case R.id.search_advanced_edittext_endDate:
			showDateSelectDialog(endDate_TextView);
			break;
		}
	}

	public void getSearchUtil(){
		searchUtil.setInfoType(infoType);
		searchUtil.setPageSize(pageSize);
		searchUtil.setBeginDate(beginDate_TextView.getText().toString());
		searchUtil.setBeginDate(endDate_TextView.getText().toString());
		searchUtil.setContentType(contentType);
		searchUtil.setKeyWord(keyWord_editText.getText().toString());
	}

	@Override
	protected int getLayoutId() {
		return R.layout.advanced_search_layout;
	}

	@Override
	protected String getTitleText() {

		return "全站搜索";
	}

	protected void onRomove() {
		FragmentManager manager = getActivity().getSupportFragmentManager();
		FragmentTransaction ft = manager.beginTransaction();
		ft.remove(AdvancedSearchFragment.this);
		ft.commit();
	}
	

	public void showDateSelectDialog(final TextView textview){
		   new DatePickerDialog(getActivity(), new OnDateSetListener() {
              @Override
              public void onDateSet(DatePicker view, int year,
                      int monthOfYear, int dayOfMonth) {
            	  monthOfYear=monthOfYear+1;
            	  String monthStr="",dayStr="";
            	  if(monthOfYear<10)
            		  monthStr="0"+monthOfYear;
            	  else
            		  monthStr=""+monthOfYear;
            	  if(dayOfMonth<10)
            		  dayStr="0"+dayOfMonth;
            	  else
            		  dayStr=""+dayOfMonth;
            	  
            	  textview.setText(""+year +monthStr+dayStr);
              }
          }, year, month, day).show();
	}

}
