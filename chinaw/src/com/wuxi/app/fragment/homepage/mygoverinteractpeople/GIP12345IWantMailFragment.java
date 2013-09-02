package com.wuxi.app.fragment.homepage.mygoverinteractpeople;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.RadioGroup;

import com.wuxi.app.R;
import com.wuxi.app.fragment.commonfragment.RadioButtonChangeFragment;

/**
 * 12345来信办理平台 主Fragment --我要写信 fragment
 * 
 * @author 杨宸 智佳
 * */

public class GIP12345IWantMailFragment extends RadioButtonChangeFragment {

	protected static final int HIDEN_CONTENT_ID = R.id.gip_12345_iwantmail_content_framelayout;

	private int contentType = 0; // 内容类型，缺省为0-我要写信 1-写信须知 2-办理规则

	private final int[] radioButtonIds = {
			R.id.gip_12345_iwantmail_radioButton_iwantmail,
			R.id.gip_12345_iwantmail_radioButton_mustKonwMail,
			R.id.gip_12345_iwantmail_radioButton_mayorBoxRule };

	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		super.onCheckedChanged(group, checkedId);
		switch (checkedId) {
		case R.id.gip_12345_iwantmail_radioButton_iwantmail:
			contentType = 0;
			init();
			break;

		case R.id.gip_12345_iwantmail_radioButton_mustKonwMail:
			contentType = 1;
			changeContent(contentType);
			break;

		case R.id.gip_12345_iwantmail_radioButton_mayorBoxRule:
			contentType = 2;
			changeContent(contentType);
			break;
		}
	}

	@Override
	protected int getLayoutId() {
		return R.layout.gip_12345_iwantmail_layout;
	}

	@Override
	protected int getRadioGroupId() {
		return R.id.gip_12345_iwantmail_radioGroup;
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
		GIP12345IWantMailLayoutFragment gIP12345IWantMailLayoutFragment = new GIP12345IWantMailLayoutFragment();
		bindFragment(gIP12345IWantMailLayoutFragment);
	}

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

	private void bindFragment(Fragment fragment) {
		FragmentManager manager = getActivity().getSupportFragmentManager();
		FragmentTransaction ft = manager.beginTransaction();
		ft.replace(HIDEN_CONTENT_ID, fragment);
		ft.commitAllowingStateLoss();
	}
}
