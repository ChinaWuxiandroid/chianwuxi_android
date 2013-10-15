package com.wuxi.app.fragment.homepage.mygoverinteractpeople;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.RadioGroup;

import com.wuxi.app.BaseFragment;
import com.wuxi.app.R;
import com.wuxi.app.fragment.commonfragment.RadioButtonChangeFragment;

/**
 * 我的政民互动  主Fragment --征求意见平台  fragment
 * @author 杨宸 智佳
 * */

public class GIPMineSuggestionPlatformFragment extends RadioButtonChangeFragment{

	private static final int FRAGMENT_ID = R.id.gip_mine_suggestion_fragment;
	
	private final  int[] radioButtonIds={
			R.id.gip_mine_suggestion_radioButton_internetsurvy,
			R.id.gip_mine_suggestion_radioButton_peoplewill,
			R.id.gip_mine_suggestion_radioButton_lawwill
	};

	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		super.onCheckedChanged(group, checkedId);
		
		GIPMineSuggestionMyPlatformFragment myPlatformFragment = null;
		
		switch (checkedId) {

		case R.id.gip_mine_suggestion_radioButton_internetsurvy:
			init();
			break;

		case R.id.gip_mine_suggestion_radioButton_peoplewill:	
			myPlatformFragment = new GIPMineSuggestionMyPlatformFragment();
			myPlatformFragment.setType("1");
			bindFragment(myPlatformFragment);
			break;
			
		case R.id.gip_mine_suggestion_radioButton_lawwill:	
			myPlatformFragment = new GIPMineSuggestionMyPlatformFragment();
			myPlatformFragment.setType("0");
			bindFragment(myPlatformFragment);
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
		GIPMineSugMySurveyFragment surveyFragment = new GIPMineSugMySurveyFragment();
		bindFragment(surveyFragment);
	}
	
	/**
	 * @方法： bindFragment
	 * @描述： 绑定视图
	 * @param fragment
	 */
	private void bindFragment(BaseFragment fragment){
		FragmentManager manager = getActivity().getSupportFragmentManager();
		FragmentTransaction ft = manager.beginTransaction();
		ft.replace(FRAGMENT_ID, fragment);
		ft.commitAllowingStateLoss();
	}
}
