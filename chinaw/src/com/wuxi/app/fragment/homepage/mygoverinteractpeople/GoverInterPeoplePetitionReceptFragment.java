package com.wuxi.app.fragment.homepage.mygoverinteractpeople;

import android.widget.RadioGroup;

import com.wuxi.app.R;
import com.wuxi.app.fragment.MainMineFragment;
import com.wuxi.app.fragment.commonfragment.RadioButtonChangeFragment;
import com.wuxi.app.util.GIPRadioButtonStyleChange;

public class GoverInterPeoplePetitionReceptFragment extends RadioButtonChangeFragment{
	private final  int[] radioButtonIds={
			R.id.goverinterpeople_recept_radioButton_receptDayInform,
			R.id.goverinterpeople_recept_radioButton_petitonRule,
			R.id.goverinterpeople_recept_radioButton_receptDepart,
			R.id.goverinterpeople_recept_radioButton_someRule
	};

	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {

		GIPRadioButtonStyleChange radioButtonStyleChange=new GIPRadioButtonStyleChange();
		radioButtonStyleChange.refreshRadioButtonStyle(view,radioButtonIds,checkedId);
		switch (checkedId) {

		case R.id.goverinterpeople_recept_radioButton_receptDayInform:
			init();
			break;

		case R.id.goverinterpeople_recept_radioButton_petitonRule:	
			MainMineFragment suggestionPlatformFragment=new GIPMineSuggestionPlatformFragment();
			onTransaction(suggestionPlatformFragment);
			break;

		case R.id.goverinterpeople_recept_radioButton_receptDepart:
			MainMineFragment internetGoverSaloonFragment=new GIPMineInternetGoverSaloonFragment();
			onTransaction(internetGoverSaloonFragment);
			break;

		case R.id.goverinterpeople_recept_radioButton_someRule:
			MainMineFragment infoPublicPlatformFragment=new GIPMineInfoPublicPlatformFragment();
			onTransaction(infoPublicPlatformFragment);

			break;

		}
	}
	@Override
	protected int getLayoutId() {
		// TODO Auto-generated method stub
		return R.layout.goverinterpeople_petitionrecept_layout;
	}

	@Override
	protected int getRadioGroupId() {
		// TODO Auto-generated method stub
		return R.id.goverinterpeople_recept_radioGroup;
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
