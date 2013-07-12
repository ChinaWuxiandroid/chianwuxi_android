package com.wuxi.app.fragment.homepage.mygoverinteractpeople;

import android.widget.RadioGroup;

import com.wuxi.app.R;
import com.wuxi.app.fragment.MainMineFragment;
import com.wuxi.app.fragment.commonfragment.RadioButtonChangeFragment;
import com.wuxi.app.util.GIPRadioButtonStyleChange;

/**
 * 我的政民互动  主Fragment  之 我的政民互动   子fragment
 * @author 杨宸 智佳
 * */

public class GoverInterPeopleMineFragment extends RadioButtonChangeFragment{

	private final  int[] radioButtonIds={
			R.id.goverinterpeople_mine_radioButton_12345,
			R.id.goverinterpeople_mine_radioButton_suggestionPlatform,
			R.id.goverinterpeople_mine_radioButton_internetGoverSaloon,
			R.id.goverinterpeople_mine_radioButton_infoPublicPlatform,
			R.id.goverinterpeople_mine_radioButton_publicForum
	};

	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {

		GIPRadioButtonStyleChange radioButtonStyleChange=new GIPRadioButtonStyleChange();
		radioButtonStyleChange.refreshRadioButtonStyle(view,radioButtonIds,checkedId);
		switch (checkedId) {

		case R.id.goverinterpeople_mine_radioButton_12345:
			init();
			break;

		case R.id.goverinterpeople_mine_radioButton_suggestionPlatform:	
			MainMineFragment suggestionPlatformFragment=new GIPMineSuggestionPlatformFragment();
			onTransaction(suggestionPlatformFragment);
			break;

		case R.id.goverinterpeople_mine_radioButton_internetGoverSaloon:
			MainMineFragment internetGoverSaloonFragment=new GIPMineInternetGoverSaloonFragment();
			onTransaction(internetGoverSaloonFragment);
			break;

		case R.id.goverinterpeople_mine_radioButton_infoPublicPlatform:
			MainMineFragment infoPublicPlatformFragment=new GIPMineInfoPublicPlatformFragment();
			onTransaction(infoPublicPlatformFragment);

			break;

		case R.id.goverinterpeople_mine_radioButton_publicForum:

			MainMineFragment publicForumFragment=new GIPMinePublicForumFragment();
			onTransaction(publicForumFragment);
			break;
		}
	}

	@Override
	protected int getLayoutId() {
		return R.layout.goverinterpeople_mine_layout;
	}

	@Override
	protected int getRadioGroupId() {
		return R.id.goverinterpeople_mine_radioGroup;
	}

	@Override
	protected int[] getRadioButtonIds() {
		return radioButtonIds;
	}

	@Override
	protected int getContentFragmentId() {
		return R.id.goverinterpeople_mine_content_fragment;
	}

	@Override
	protected void init() {
		// TODO Auto-generated method stub
		MainMineFragment mine12345Fragment=new GIPMine12345Fragment();
		onTransaction(mine12345Fragment);
	}
}
