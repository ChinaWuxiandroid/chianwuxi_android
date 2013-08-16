package com.wuxi.app.fragment.homepage.mygoverinteractpeople;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.RadioGroup;

import com.wuxi.app.R;
import com.wuxi.app.fragment.commonfragment.RadioButtonChangeFragment;

/**
 * 12345来信办理平台 主Fragment --建议咨询投诉 fragment
 * 
 * @author 杨宸 智佳
 * */

public class GIP12345ComplaintFragment extends RadioButtonChangeFragment {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	private final int[] radioButtonIds = {
			R.id.gip_12345_complaint_radioButton_latestMailList,
			R.id.gip_12345_complaint_radioButton_mustKonwMail,
			R.id.gip_12345_complaint_radioButton_mayorBoxRule,
			R.id.gip_12345_complaint_radioButton_organizationDuty };

	private static final int HIDEN_CONTENT_ID = R.id.complaint_fragment;

	private int contentType = 0; // 内容类型，缺省为0-信件列表 1-写信须知 2-办理规则 3-机构职责

	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {

		super.onCheckedChanged(group, checkedId);
		switch (checkedId) {

		case R.id.gip_12345_complaint_radioButton_latestMailList:
			contentType = 0;
			init();
			break;

		case R.id.gip_12345_complaint_radioButton_mustKonwMail:
			contentType = 1;
			changeContent(contentType);
			break;

		case R.id.gip_12345_complaint_radioButton_mayorBoxRule:
			contentType = 2;
			changeContent(contentType);
			break;

		case R.id.gip_12345_complaint_radioButton_organizationDuty:
			contentType = 3;
			changeContent(contentType);
			break;
		}
	}

	@Override
	protected int getLayoutId() {
		return R.layout.gip_12345_complaint_layout;
	}

	@Override
	protected int getRadioGroupId() {
		return R.id.gip_12345_complaint_radioGroup;
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
		GIP12345ComplaintListFragment complaintListFragment = new GIP12345ComplaintListFragment();
		bindFragment(complaintListFragment);
	}

	/**
	 * 切换界面
	 * 
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
					.setUrl("http://www.wuxi.gov.cn/zmhd/6148281.shtml");
			bindFragment(goverInterPeopleWebFragment);
			break;
		case 2:
			goverInterPeopleWebFragment
					.setUrl("http://www.wuxi.gov.cn/zmhd/6148282.shtml");
			bindFragment(goverInterPeopleWebFragment);
			break;
		case 3:
			goverInterPeopleWebFragment
					.setUrl("http://www.wuxi.gov.cn/zxzx/jgzn/index.shtml");
			bindFragment(goverInterPeopleWebFragment);
			break;
		}
	}

	/**
	 * 绑定碎片
	 * 
	 * @param fragment
	 */
	private void bindFragment(Fragment fragment) {
		FragmentManager manager = getActivity().getSupportFragmentManager();
		FragmentTransaction ft = manager.beginTransaction();
		ft.replace(HIDEN_CONTENT_ID, fragment);
		ft.commit();
	}

}
