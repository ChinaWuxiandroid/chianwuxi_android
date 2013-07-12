package com.wuxi.app.fragment.homepage.mygoverinteractpeople;

import android.widget.RadioGroup;

import com.wuxi.app.R;
import com.wuxi.app.fragment.MainMineFragment;
import com.wuxi.app.fragment.commonfragment.RadioButtonChangeFragment;
import com.wuxi.app.util.GIPRadioButtonStyleChange;

public class GoverInterPeoplePublicSuperviseFragment extends RadioButtonChangeFragment{

	private final  int[] radioButtonIds={
			R.id.goverinterpeople_supervise_radioButton_12380,
			R.id.goverinterpeople_supervise_radioButton_12388
	};

	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {

		GIPRadioButtonStyleChange radioButtonStyleChange=new GIPRadioButtonStyleChange();
		radioButtonStyleChange.refreshRadioButtonStyle(view,radioButtonIds,checkedId);
		switch (checkedId) {

		case R.id.goverinterpeople_supervise_radioButton_12380:
			init();
			break;

		case R.id.goverinterpeople_supervise_radioButton_12388:	
			MainMineFragment suggestionPlatformFragment=new GIPMineSuggestionPlatformFragment();
			onTransaction(suggestionPlatformFragment);
			break;
		}
	}

	@Override
	protected int getLayoutId() {
		// TODO Auto-generated method stub
		return R.layout.goverinterpeople_supervise_layout;
	}

	@Override
	protected int getRadioGroupId() {
		// TODO Auto-generated method stub
		return R.id.goverinterpeople_supervise_radioGroup;
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
