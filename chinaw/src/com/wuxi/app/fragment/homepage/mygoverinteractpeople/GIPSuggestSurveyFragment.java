package com.wuxi.app.fragment.homepage.mygoverinteractpeople;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.RadioGroup;

import com.wuxi.app.R;

import com.wuxi.app.fragment.commonfragment.RadioButtonChangeFragment;

/**
 * 我的政民互动 主Fragment 之 征求意见平台 子fragment --网上调查
 * 
 * @author 杨宸 智佳
 * */
public class GIPSuggestSurveyFragment extends RadioButtonChangeFragment {

	protected static final String TAG = "GIPSuggestSurveyFragment";

	private static final int FRAGMENT_ID = R.id.gip_suggest_survey_fragment;

	private final int[] radioButtonIds = {
			R.id.gip_suggest_survey_radioButton_now,
			R.id.gip_suggest_survey_radioButton_before };

	private int type = 0; // 0:正在调查 1：以往调查

	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {

		super.onCheckedChanged(group, checkedId);
		switch (checkedId) {

		case R.id.gip_suggest_survey_radioButton_now:
			type = 0;
			gotoListView(type);
			break;

		case R.id.gip_suggest_survey_radioButton_before:
			type = 1;
			gotoListView(type);
			break;
		}
	}

	@Override
	protected int getLayoutId() {
		return R.layout.gip_suggest_survey_layout;
	}

	@Override
	protected int getRadioGroupId() {
		return R.id.gip_suggest_survey_radioGroup;
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
		gotoListView(type);	
	}

	/**
	 * 绑定碎片
	 * 
	 * @param fragment
	 */
	private void bindFragment(Fragment fragment) {
		FragmentManager manager = getActivity().getSupportFragmentManager();
		FragmentTransaction ft = manager.beginTransaction();
		ft.replace(FRAGMENT_ID, fragment);
		ft.commitAllowingStateLoss();
	}

	/**
	 * @方法： gotoListView
	 * @描述： 跳转到列表界面
	 * @param type
	 */
	private void gotoListView(int type) {
		GIPSuggestSurveyListFragment fragment = new GIPSuggestSurveyListFragment();
		fragment.setType(type);
		bindFragment(fragment);
	}

}
