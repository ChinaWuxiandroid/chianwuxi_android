package com.wuxi.app.fragment.homepage.mygoverinteractpeople;

import android.widget.RadioGroup;

import com.wuxi.app.R;
import com.wuxi.app.fragment.MainMineFragment;
import com.wuxi.app.fragment.commonfragment.RadioButtonChangeFragment;


/**
 * 我的政民互动  主Fragment  之 征求意见平台  子fragment  --民意征集
 * @author 杨宸 智佳
 * */


public class GIPSuggestPeopleWill extends RadioButtonChangeFragment{

	private final  int[] radioButtonIds={
			R.id.gip_suggest_peoplewill_radioButton_now,
			R.id.gip_suggest_peoplewill_radioButton_before
	};

	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {

		super.onCheckedChanged(group, checkedId);
		switch (checkedId) {

		case R.id.gip_suggest_peoplewill_radioButton_now:
			init();
			break;

		case R.id.gip_suggest_peoplewill_radioButton_before:	
			MainMineFragment suggestionPlatformFragment=new GIPMineSuggestionPlatformFragment();
			onTransaction(suggestionPlatformFragment);
			break;
		}
	}
	
	@Override
	protected int getLayoutId() {
		// TODO Auto-generated method stub
		return R.layout.gip_suggest_peoplewill_layout;
	}

	@Override
	protected int getRadioGroupId() {
		// TODO Auto-generated method stub
		return R.id.gip_suggest_peoplewill_radioGroup;
	}

	@Override
	protected int[] getRadioButtonIds() {
		// TODO Auto-generated method stub
		return radioButtonIds;
	}

	@Override
	protected int getContentFragmentId() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	protected void init() {
		// TODO Auto-generated method stub
		
	}

}
