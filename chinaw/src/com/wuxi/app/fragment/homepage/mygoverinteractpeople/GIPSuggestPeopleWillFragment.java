package com.wuxi.app.fragment.homepage.mygoverinteractpeople;

import android.annotation.SuppressLint;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.RadioGroup;

import com.wuxi.app.R;
import com.wuxi.app.fragment.commonfragment.RadioButtonChangeFragment;

/**
 * 我的政民互动 主Fragment 之 征求意见平台 子fragment --民意征集
 * 
 * @author 杨宸 智佳
 * */

@SuppressLint("ShowToast")
public class GIPSuggestPeopleWillFragment extends RadioButtonChangeFragment {

	private static final String TAG = "GIPSuggestPeopleWillFragment";

	private final static int FRAGMENT_ID = R.id.gip_suggest_peoplewill_fragment;

	private int passed = 0; // 是否过期，可选参数，默认值是0 0: 当前 1:以往

	private final int[] radioButtonIds = {
			R.id.gip_suggest_peoplewill_radioButton_now,
			R.id.gip_suggest_peoplewill_radioButton_before };

	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {

		super.onCheckedChanged(group, checkedId);
		switch (checkedId) {

		case R.id.gip_suggest_peoplewill_radioButton_now:
			passed = 0;
			gotoListView(passed);
			break;

		case R.id.gip_suggest_peoplewill_radioButton_before:
			passed = 1;
			gotoListView(passed);
			break;
		}
	}

	@Override
	protected int getLayoutId() {
		return R.layout.gip_suggest_peoplewill_layout;
	}

	@Override
	protected int getRadioGroupId() {
		return R.id.gip_suggest_peoplewill_radioGroup;
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

		gotoListView(passed);
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
		GIPSuggestPeopleWillListFragment fragment = new GIPSuggestPeopleWillListFragment();
		fragment.setType(type);
		bindFragment(fragment);
	}

}
