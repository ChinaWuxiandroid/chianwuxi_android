package com.wuxi.app.fragment.homepage.mygoverinteractpeople;

import android.widget.RadioGroup;

import com.wuxi.app.R;
import com.wuxi.app.fragment.commonfragment.RadioButtonChangeFragment;


/**
 * 我的政民互动  主Fragment --网上政务大厅  fragment
 * @author 杨宸 智佳
 * */

public class GIPMineInternetGoverSaloonFragment extends RadioButtonChangeFragment{

	private final  int[] radioButtonIds={
			R.id.gip_mine_saloon_radioButton_onlineinquire,
			R.id.gip_mine_saloon_radioButton_declarationlist
	};
	
	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {

		super.onCheckedChanged(group, checkedId);

		switch (checkedId) {

		case R.id.gip_mine_saloon_radioButton_onlineinquire:
			//			init();
			break;

		case R.id.gip_mine_saloon_radioButton_declarationlist:	
			break;

		}
	}

	@Override
	protected int getLayoutId() {
		return R.layout.goverinterpeople_mine_internetgoversaloon_layout;
	}

	@Override
	protected int getRadioGroupId() {
		return R.id.gip_mine_saloon_radioGroup;
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
		
	}
}
