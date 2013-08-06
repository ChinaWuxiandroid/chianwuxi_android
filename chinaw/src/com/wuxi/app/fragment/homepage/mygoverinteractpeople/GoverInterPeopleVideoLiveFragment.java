package com.wuxi.app.fragment.homepage.mygoverinteractpeople;

import android.widget.RadioGroup;

import com.wuxi.app.R;
import com.wuxi.app.fragment.MainMineFragment;
import com.wuxi.app.fragment.commonfragment.RadioButtonChangeFragment;
import com.wuxi.app.util.GIPRadioButtonStyleChange;

/**
 * 我的政民互动之视频直播平台之走进直播间
 * 
 * @author 智佳 罗森
 *
 */
public class GoverInterPeopleVideoLiveFragment  extends RadioButtonChangeFragment{

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	//存放该界面的RadioBtnID的数组
	private final  int[] radioBtnIds={
			R.id.gip_video_bdroom_radioBtn_home,
			R.id.gip_video_bdroom_radioBtn_review,
			R.id.gip_video_bdroom_radioBtn_guest,
			R.id.gip_video_bdroom_radioBtn_relative_info,
			R.id.gip_video_bdroom_radioBtn_program,
			R.id.gip_video_bdroom_radioBtn_schedule
	};
	
	@Override
	protected int getLayoutId() {
		return R.layout.gip_video_broadcastroom_layout;
	}

	@Override
	protected int getRadioGroupId() {
		return R.id.gip_vedio_bdroom_radioGroup;
	}

	@Override
	protected int[] getRadioButtonIds() {
		return radioBtnIds;
	}

	@Override
	protected int getContentFragmentId() {
		return R.id.gip_video_bdroom_frame;
	}

	@Override
	protected void init() {
		MainMineFragment gipVedioLiveHomeFragment = new GoverInterPeopleVideoLiveHomeFragment();
		onTransaction(gipVedioLiveHomeFragment);
	}
	
	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		
		GIPRadioButtonStyleChange radioButtonStyleChange=new GIPRadioButtonStyleChange();
		radioButtonStyleChange.refreshRadioButtonStyle(view,radioBtnIds,checkedId);
		
		switch (checkedId) {
		case R.id.gip_video_bdroom_radioBtn_home:
			init();
			break;
			
		case R.id.gip_video_bdroom_radioBtn_review:
			
			break;
			
		case R.id.gip_video_bdroom_radioBtn_guest:
			
			break;
			
		case R.id.gip_video_bdroom_radioBtn_relative_info:
			
			break;
			
		case R.id.gip_video_bdroom_radioBtn_program:
			
			break;
			
		case R.id.gip_video_bdroom_radioBtn_schedule:
			
			break;
			
		default:
			break;
		}
	}

}
