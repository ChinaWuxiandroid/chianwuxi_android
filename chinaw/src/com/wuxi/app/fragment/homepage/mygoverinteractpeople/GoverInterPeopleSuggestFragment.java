package com.wuxi.app.fragment.homepage.mygoverinteractpeople;

import android.widget.RadioGroup;

import com.wuxi.app.R;
import com.wuxi.app.fragment.MainMineFragment;
import com.wuxi.app.fragment.commonfragment.RadioButtonChangeFragment;
import com.wuxi.app.util.GIPRadioButtonStyleChange;

/**
 * 我的政民互动  主Fragment  之 征求意见平台  子fragment
 * @author 杨宸 智佳
 * */

public class GoverInterPeopleSuggestFragment extends RadioButtonChangeFragment{

	private final  int[] radioButtonIds={
			R.id.goverinterpeople_suggest_radioButton_lawSuggestion,
			R.id.goverinterpeople_suggest_radioButton_internetSurvey,
			R.id.goverinterpeople_suggest_radioButton_people
	};

	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {

		GIPRadioButtonStyleChange radioButtonStyleChange=new GIPRadioButtonStyleChange();
		radioButtonStyleChange.refreshRadioButtonStyle(view,radioButtonIds,checkedId);
		switch (checkedId) {

		case R.id.goverinterpeople_suggest_radioButton_lawSuggestion:
			init();
			break;

		case R.id.goverinterpeople_suggest_radioButton_internetSurvey:	
			MainMineFragment surveyFragment=new GIPSuggestSurveyFragment();
			onTransaction(surveyFragment);
			break;

		case R.id.goverinterpeople_suggest_radioButton_people:
			MainMineFragment peopleWillFragment=new GIPSuggestPeopleWill();
			onTransaction(peopleWillFragment);
			break;

		}
	}

	@Override
	protected int getLayoutId() {
		// TODO Auto-generated method stub
		return R.layout.goverinterpeople_suggest_layout;
	}

	@Override
	protected int getRadioGroupId() {
		// TODO Auto-generated method stub
		return R.id.goverinterpeople_suggest_radioGroup;
	}

	@Override
	protected int[] getRadioButtonIds() {
		// TODO Auto-generated method stub
		return radioButtonIds;
	}

	@Override
	protected int getContentFragmentId() {
		// TODO Auto-generated method stub
		return R.id.goverinterpeople_suggest_content_fragment;
	}

	@Override
	protected void init() {
		MainMineFragment lawSuggestFragment=new GIPSuggestLawSuggestionFragment();
		onTransaction(lawSuggestFragment);
	}

}
