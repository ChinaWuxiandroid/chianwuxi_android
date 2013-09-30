package com.wuxi.app.fragment.homepage.mygoverinteractpeople;

import com.wuxi.app.R;
import com.wuxi.app.fragment.commonfragment.RadioButtonChangeFragment;

public class GoverInterPeopleWeibosFragment  extends RadioButtonChangeFragment{

	@Override
	protected int getLayoutId() {
		return R.layout.goverinterpeople_weibos_layout;
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
