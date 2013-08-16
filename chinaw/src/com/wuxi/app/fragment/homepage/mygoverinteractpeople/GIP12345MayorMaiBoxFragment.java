package com.wuxi.app.fragment.homepage.mygoverinteractpeople;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import android.widget.RadioGroup;

import com.wuxi.app.R;
import com.wuxi.app.fragment.commonfragment.RadioButtonChangeFragment;

/**
 * 12345来信办理平台 主Fragment --市长信箱 fragment
 * 
 * @author 杨宸 智佳
 * */

public class GIP12345MayorMaiBoxFragment extends RadioButtonChangeFragment {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	private static final int HIDEN_CONTENT_ID = R.id.mayor_box_fragment;

	private int contentType = 0; // 内容类型，缺省为0-信件列表 1-写信须知 2-办理规则

	private final int[] radioButtonIds = {
			R.id.gip_12345_mayorbox_radioButton_mailList,
			R.id.gip_12345_mayorbox_radioButton_mustKonwMail,
			R.id.gip_12345_mayorbox_radioButton_mayorBoxRule };

	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {

		super.onCheckedChanged(group, checkedId);
		switch (checkedId) {

		case R.id.gip_12345_mayorbox_radioButton_mailList:
			contentType = 0;
			init();
			break;

		case R.id.gip_12345_mayorbox_radioButton_mustKonwMail:
			contentType = 1;
			changeContent(contentType);
			break;

		case R.id.gip_12345_mayorbox_radioButton_mayorBoxRule:
			contentType = 2;
			changeContent(contentType);
			break;
		}
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
					.setUrl("http://www.wuxi.gov.cn/zmhd/6148280.shtml");
			bindFragment(goverInterPeopleWebFragment);
			break;
		case 2:
			goverInterPeopleWebFragment
					.setUrl("http://www.wuxi.gov.cn/zmhd/6148283.shtml");
			bindFragment(goverInterPeopleWebFragment);
			break;
		}
	}

	@Override
	protected int getLayoutId() {
		return R.layout.gip_12345_mayorbox_layout;
	}

	@Override
	protected int getRadioGroupId() {
		return R.id.gip_12345_mayorbox_radioGroup;
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
		GIP12345MayorMailListFragment gip12345MayorMailListFragment = new GIP12345MayorMailListFragment();
		bindFragment(gip12345MayorMailListFragment);
	}

	/**
	 * 绑定碎片
	 * @param fragment
	 */
	private void bindFragment(Fragment fragment) {
		FragmentManager manager = getActivity().getSupportFragmentManager();
		FragmentTransaction ft = manager.beginTransaction();
		ft.replace(HIDEN_CONTENT_ID, fragment);
		ft.commit();
	}

}
