package com.wuxi.app.fragment.homepage.mygoverinteractpeople;

import android.widget.RadioGroup;

import com.wuxi.app.R;
import com.wuxi.app.fragment.commonfragment.RadioButtonChangeFragment;


/**
 * 我的政民互动  主Fragment --公众论坛  fragment
 * @author 杨宸 智佳
 * */


public class GIPMinePublicForumFragment extends RadioButtonChangeFragment{

	/**
	 * @字段： serialVersionUID
	 * @类型： long
	 * @描述： 序列化序号 
	 */
	private static final long serialVersionUID = 1L;
	private final  int[] radioButtonIds={
			R.id.gip_mine_publicforum_radioButton_myTheme,
			R.id.gip_mine_publicforum_radioButton_themeTJoin,
			R.id.gip_mine_publicforum_radioButton_collectTheme,
			R.id.gip_mine_radioButton_suggestionPlatform
	};

	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {

		super.onCheckedChanged(group, checkedId);

		switch (checkedId) {

		case R.id.gip_mine_publicforum_radioButton_myTheme:
			//			init();
			break;

		case R.id.gip_mine_publicforum_radioButton_themeTJoin:	
			break;
		case R.id.gip_mine_publicforum_radioButton_collectTheme:	
			break;
		case R.id.gip_mine_radioButton_suggestionPlatform:	
			break;
		}
	}

	@Override
	protected int getLayoutId() {
		return R.layout.gip_mine_publicforum_layout;
	}

	@Override
	protected int getRadioGroupId() {
		return R.id.gip_mine_publicforum_radioGroup;
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
