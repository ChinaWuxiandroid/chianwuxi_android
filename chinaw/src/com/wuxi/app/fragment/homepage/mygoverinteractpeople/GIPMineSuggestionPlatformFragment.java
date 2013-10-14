package com.wuxi.app.fragment.homepage.mygoverinteractpeople;

import android.widget.RadioGroup;

import com.wuxi.app.R;
import com.wuxi.app.fragment.commonfragment.RadioButtonChangeFragment;

/**
 * 我的政民互动  主Fragment --征求意见平台  fragment
 * @author 杨宸 智佳
 * */

public class GIPMineSuggestionPlatformFragment extends RadioButtonChangeFragment{

	private final  int[] radioButtonIds={
			R.id.gip_mine_suggestion_radioButton_internetsurvy,
			R.id.gip_mine_suggestion_radioButton_peoplewill,
			R.id.gip_mine_suggestion_radioButton_lawwill
	};

	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		super.onCheckedChanged(group, checkedId);
		
		switch (checkedId) {

		case R.id.gip_mine_suggestion_radioButton_internetsurvy:
			//			init();
			break;

		case R.id.gip_mine_suggestion_radioButton_peoplewill:	
			break;
		case R.id.gip_mine_suggestion_radioButton_lawwill:	
			break;
		}
	}

	@Override
	protected int getLayoutId() {
		return R.layout.gip_mine_suggestionplatform_layout;
	}

	@Override
	protected int getRadioGroupId() {
		return R.id.gip_mine_suggestion_radioGroup;
	}

	@Override
	protected int[] getRadioButtonIds() {
		return radioButtonIds;
	}

	@Override
	protected int getContentFragmentId() {
		return 0;
	}

	@Override
	protected void init() {
		
	}
}
