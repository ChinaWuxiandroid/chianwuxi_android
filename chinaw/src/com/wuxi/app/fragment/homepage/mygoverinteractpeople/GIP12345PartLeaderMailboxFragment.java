package com.wuxi.app.fragment.homepage.mygoverinteractpeople;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.RadioGroup;

import com.wuxi.app.R;
import com.wuxi.app.fragment.commonfragment.RadioButtonChangeFragment;

/**
 *12345来信办理平台  主Fragment --部门领导信箱  fragment
 * @author 杨宸 智佳
 * */

public class GIP12345PartLeaderMailboxFragment extends RadioButtonChangeFragment{
	
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	
	private int contentType = 0;
	
	private static final int HIDEN_CONTENT_ID = R.id.gip_12345_leaderbox_fragment;
	
	private final  int[] radioButtonIds={
			R.id.gip_12345_leaderbox_radioButton_lederBoxList,
			R.id.gip_12345_leaderbox_radioButton_mustKonwMail
	};	
	
	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {

		super.onCheckedChanged(group, checkedId);
		switch (checkedId) {

		case R.id.gip_12345_leaderbox_radioButton_lederBoxList:
			contentType = 0;
			init();
			break;

		case R.id.gip_12345_leaderbox_radioButton_mustKonwMail:	
			contentType = 1;
			changeContent(contentType);
			break;

		}
		
	}
	@Override
	protected int getLayoutId() {
		return R.layout.gip_12345_leadermailbox_layout;
	}

	@Override
	protected int getRadioGroupId() {
		return R.id.gip_12345_leaderbox_radioGroup;
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
		PartLeaderBoxListFragment leaderMailListFragment = new PartLeaderBoxListFragment();
		leaderMailListFragment.setBaseSlideFragment(this.baseSlideFragment);
		bindFragment(leaderMailListFragment);
	}
	
	/**
	 * 切换界面
	 * @param type
	 */
	public void changeContent(int type) {
		GoverInterPeopleWebFragment goverInterPeopleWebFragment = new GoverInterPeopleWebFragment();
		switch (type) {
		case 0:
			init();
			break;
		case 1:
			goverInterPeopleWebFragment
					.setUrl("http://www.wuxi.gov.cn/zmhd/6148278.shtml");
			bindFragment(goverInterPeopleWebFragment);
			break;
		}
	}
	
	/**
	 * 绑定碎片
	 * @param fragment
	 */
	private void bindFragment(Fragment fragment) {
		FragmentManager manager = getActivity().getSupportFragmentManager();
		FragmentTransaction ft = manager.beginTransaction();
		ft.replace(HIDEN_CONTENT_ID, fragment);
		ft.commitAllowingStateLoss();
	}

}
