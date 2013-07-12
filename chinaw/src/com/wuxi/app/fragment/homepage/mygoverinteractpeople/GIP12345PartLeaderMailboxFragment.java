package com.wuxi.app.fragment.homepage.mygoverinteractpeople;

import android.widget.RadioGroup;

import com.wuxi.app.R;
import com.wuxi.app.fragment.commonfragment.RadioButtonChangeFragment;

/**
 *12345来信办理平台  主Fragment --部门领导信箱  fragment
 * @author 杨宸 智佳
 * */

public class GIP12345PartLeaderMailboxFragment extends RadioButtonChangeFragment{
	private final  int[] radioButtonIds={
			R.id.gip_12345_leaderbox_radioButton_lederBoxList,
			R.id.gip_12345_leaderbox_radioButton_mustKonwMail
	};	
	
	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {

		super.onCheckedChanged(group, checkedId);
		switch (checkedId) {

		case R.id.gip_12345_leaderbox_radioButton_lederBoxList:
			//			init();
			break;

		case R.id.gip_12345_leaderbox_radioButton_mustKonwMail:	
			break;

		}
		
	}
	@Override
	protected int getLayoutId() {
		// TODO Auto-generated method stub
		return R.layout.gip_12345_leadermailbox_layout;
	}

	@Override
	protected int getRadioGroupId() {
		// TODO Auto-generated method stub
		return R.id.gip_12345_leaderbox_radioGroup;
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
