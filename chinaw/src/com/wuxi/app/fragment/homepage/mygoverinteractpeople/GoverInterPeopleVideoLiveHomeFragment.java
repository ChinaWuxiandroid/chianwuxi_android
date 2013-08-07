/**
 * 
 */
package com.wuxi.app.fragment.homepage.mygoverinteractpeople;

import com.wuxi.app.R;
import com.wuxi.app.fragment.commonfragment.RadioButtonChangeFragment;

/**
 * 走进直播间之栏目首页
 * 
 * @author Administrator
 *
 */
public class GoverInterPeopleVideoLiveHomeFragment extends RadioButtonChangeFragment{

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	//存放该界面的RadioBtnID的数组
		private final  int[] radioBtnIds={
				R.id.gip_video_live_home_radioBtn_vediolive,
				R.id.gip_video_live_home_radioBtn_memoir,
				R.id.gip_video_live_home_radioBtn_message
		};
	
	@Override
	protected int getLayoutId() {
		return R.layout.gip_vedio_live_home_layout;
	}

	@Override
	protected int getRadioGroupId() {
		return R.id.gip_vedio_live_home_radioGroup;
	}

	@Override
	protected int[] getRadioButtonIds() {
		return radioBtnIds;
	}

	@Override
	protected int getContentFragmentId() {
		return 0;
	}

	@Override
	protected void init() {
		
	}
	

}
