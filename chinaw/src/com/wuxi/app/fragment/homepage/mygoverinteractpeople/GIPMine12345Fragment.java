package com.wuxi.app.fragment.homepage.mygoverinteractpeople;

import android.widget.RadioGroup;

import com.wuxi.app.R;
import com.wuxi.app.fragment.commonfragment.RadioButtonChangeFragment;

/**
 * 我的政民互动  主Fragment --12345来信班里平台  fragment
 * @author 杨宸 智佳
 * */

public class GIPMine12345Fragment extends RadioButtonChangeFragment{
	private final  int[] radioButtonIds={
			R.id.gip_mine_12345_radioButton_backmail,
			R.id.gip_mine_12345_radioButton_mybackmail,
	};
	

	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {

		super.onCheckedChanged(group, checkedId);

		switch (checkedId) {

		case R.id.gip_mine_12345_radioButton_backmail:
//			init();
			break;

		case R.id.gip_mine_12345_radioButton_mybackmail:	
			break;
		}
	}

	@Override
	protected int getLayoutId() {
		// TODO Auto-generated method stub
		return R.layout.goverinterpeople_mine_12345_layout;
	}

	@Override
	protected int getRadioGroupId() {
		// TODO Auto-generated method stub
		return R.id.gip_mine_12345_radioGroup;
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
