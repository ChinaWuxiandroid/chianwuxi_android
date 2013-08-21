package com.wuxi.app.fragment.homepage.mygoverinteractpeople;

import com.wuxi.app.R;
import com.wuxi.app.fragment.commonfragment.RadioButtonChangeFragment;

/**
 *12345来信办理平台  主Fragment --区市长信箱  fragment
 * @author 杨宸 智佳
 * */

public class GIP12345CMayorMailBoxFragment extends RadioButtonChangeFragment{

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected int getLayoutId() {
		return R.layout.gip_12345_cmayorbox_layout;
	}

	@Override
	protected int getRadioGroupId() {
		return 0;
	}

	@Override
	protected int[] getRadioButtonIds() {
		return null;
	}

	@Override
	protected int getContentFragmentId() {
		return 0;
	}

	@Override
	protected void init() {
		
	}

}
