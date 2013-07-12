package com.wuxi.app.fragment.homepage.mygoverinteractpeople;

import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.wuxi.app.R;
import com.wuxi.app.fragment.commonfragment.RadioButtonChangeFragment;

/**
 * 我的政民互动  主Fragment  之 征求意见平台  子fragment  --立法征求意见
 * @author 杨宸 智佳
 * */


public class GIPSuggestLawSuggestionFragment extends RadioButtonChangeFragment{
	private Spinner chooseYear_spinner;
	
	
	@Override
	protected int getLayoutId() {
		// TODO Auto-generated method stub
		return R.layout.gip_suggest_lawsuggestion_layout;
	}

	@Override
	protected int getRadioGroupId() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	protected int[] getRadioButtonIds() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected int getContentFragmentId() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	protected void init() {
		chooseYear_spinner=(Spinner)view.findViewById(R.id.gip_suggest_lawsuggest_spinner_chooseYear);
		ArrayAdapter spinner_adapter = ArrayAdapter.createFromResource(context, R.array.spinnerYear, android.R.layout.simple_spinner_item);  
		spinner_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);  
		chooseYear_spinner.setAdapter(spinner_adapter);  
		chooseYear_spinner.setVisibility(View.VISIBLE); 
		
	}

}
