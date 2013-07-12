package com.wuxi.app.fragment.homepage.mygoverinteractpeople;

import android.widget.RadioGroup;

import com.wuxi.app.R;
import com.wuxi.app.fragment.commonfragment.RadioButtonChangeFragment;

/**
 *12345来信办理平台  主Fragment --答复率统计  fragment
 * @author 杨宸 智佳
 * */

public class GIP12345AnswerStatisticsFragment extends RadioButtonChangeFragment{

	private final  int[] radioButtonIds={
			R.id.gip_12345_answerstati_radioButton_mayorBox,
			R.id.gip_12345_answerstati_radioButton_complaint,
			R.id.gip_12345_answerstati_radioButton_leaderBox	
	};	

	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {

		super.onCheckedChanged(group, checkedId);
		switch (checkedId) {

		case R.id.gip_12345_answerstati_radioButton_mayorBox:
			//			init();
			break;

		case R.id.gip_12345_answerstati_radioButton_complaint:	
			break;

		case R.id.gip_12345_answerstati_radioButton_leaderBox:	
			break;


		}
	}
	@Override
	protected int getLayoutId() {
		// TODO Auto-generated method stub
		return R.layout.gip_12345_answerstati_layout;
	}

	@Override
	protected int getRadioGroupId() {
		// TODO Auto-generated method stub
		return R.id.gip_12345_answerstati_radioGroup;
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
