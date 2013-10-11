package com.wuxi.app.fragment.homepage.mygoverinteractpeople;

import android.widget.RadioGroup;

import com.wuxi.app.R;
import com.wuxi.app.fragment.commonfragment.RadioButtonChangeFragment;


/**
 * 我的政民互动  主Fragment --信息公开平台  fragment
 * @author 杨宸 智佳
 * */

public class GIPMineInfoPublicPlatformFragment extends RadioButtonChangeFragment{

	private final  int[] radioButtonIds={
			R.id.gip_mine_infopublic_radioButton_declaration,
			R.id.gip_mine_infopublic_radioButton_worksuggestion
	};

	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		super.onCheckedChanged(group, checkedId);
		switch (checkedId) {
		case R.id.gip_mine_infopublic_radioButton_declaration:
			//			init();
			break;
		case R.id.gip_mine_infopublic_radioButton_worksuggestion:	
			break;

		}
	}

	@Override
	protected int getLayoutId() {
		return R.layout.gip_mine_infopublicplatform_layout;
	}

	@Override
	protected int getRadioGroupId() {
		return R.id.gip_mine_infopublic_radioGroup;
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
