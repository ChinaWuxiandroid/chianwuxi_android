package com.wuxi.app.fragment.homepage.mygoverinteractpeople;

import android.widget.RadioGroup;

import com.wuxi.app.R;
import com.wuxi.app.fragment.commonfragment.RadioButtonChangeFragment;


/**
 * 12345来信办理平台 主Fragment --市长信箱  fragment
 * @author 杨宸 智佳
 * */


public class GIP12345MayorMaiBoxFragment  extends RadioButtonChangeFragment{

	private final  int[] radioButtonIds={
			R.id.gip_12345_mayorbox_radioButton_mailList,
			R.id.gip_12345_mayorbox_radioButton_mustKonwMail,
			R.id.gip_12345_mayorbox_radioButton_mayorBoxRule
	};	

	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {

		super.onCheckedChanged(group, checkedId);
		switch (checkedId) {

		case R.id.gip_12345_mayorbox_radioButton_mailList:
			//			init();
			break;

		case R.id.gip_12345_mayorbox_radioButton_mustKonwMail:	
			break;


		case R.id.gip_12345_mayorbox_radioButton_mayorBoxRule:	
			break;
		}
	}

	@Override
	protected int getLayoutId() {
		// TODO Auto-generated method stub
		return R.layout.gip_12345_mayorbox_layout;
	}

	@Override
	protected int getRadioGroupId() {
		// TODO Auto-generated method stub
		return R.id.gip_12345_mayorbox_radioGroup;
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
