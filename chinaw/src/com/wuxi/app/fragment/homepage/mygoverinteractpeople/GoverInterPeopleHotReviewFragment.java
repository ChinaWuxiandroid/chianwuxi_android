package com.wuxi.app.fragment.homepage.mygoverinteractpeople;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.RadioGroup;

import com.wuxi.app.R;
import com.wuxi.app.fragment.commonfragment.RadioButtonChangeFragment;
import com.wuxi.app.util.GIPRadioButtonStyleChange;

/**
 * 热点话题
 * 
 * @author 杨宸 智佳
 * */
public class GoverInterPeopleHotReviewFragment extends
		RadioButtonChangeFragment {

	private static final String TAG = "GoverInterPeopleHotReviewFragment";

	private final static int FRAGMENT_ID = R.id.gip_suggest_people_fragment;

	public final int HOTREVIEW_TYPE_NOW = 0; // 话题类型 当前话题
	public final int HOTREVIEW_TYPE_BEFORE = 1;// 话题类型 以往话题
	private int reviewType = HOTREVIEW_TYPE_NOW; // 默认为当前话题

	private final int[] radioButtonIds = {
			R.id.goverinterpeople_hottopic_radioButton_now,
			R.id.goverinterpeople_hottopic_radioButton_before };

	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {

		GIPRadioButtonStyleChange radioButtonStyleChange = new GIPRadioButtonStyleChange();
		radioButtonStyleChange.refreshRadioButtonStyle(view, radioButtonIds,
				checkedId);
		switch (checkedId) {

		case R.id.goverinterpeople_hottopic_radioButton_now:
			reviewType = HOTREVIEW_TYPE_NOW;
			gotoListView(reviewType);
			break;

		case R.id.goverinterpeople_hottopic_radioButton_before:
			reviewType = HOTREVIEW_TYPE_BEFORE;
			gotoListView(reviewType);
			break;
		}
	}

	@Override
	protected int getLayoutId() {
		return R.layout.goverinterpeople_hotreview_layout;
	}

	@Override
	protected int getRadioGroupId() {
		return R.id.goverinterpeople_hottopic_radioGroup;
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
		gotoListView(reviewType);
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
		GoverInterPeopleHotReviewListFragment fragment = new GoverInterPeopleHotReviewListFragment();
		fragment.setType(type);
		bindFragment(fragment);
	}

}
