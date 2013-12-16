package com.wuxi.app.fragment.homepage.mygoverinteractpeople;

import android.widget.RadioGroup;

import com.wuxi.app.R;
import com.wuxi.app.fragment.commonfragment.RadioButtonChangeFragment;

/**
 * 政民互动之视频直播平台之走进直播间
 * 
 * @author 智佳 罗森
 * 
 */

public class GoverInterPeopleVideoLiveFragment extends
		RadioButtonChangeFragment {

	// 存放该界面的RadioBtnID的数组
	private final int[] radioBtnIds = { R.id.gip_video_bdroom_radioBtn_home,
			R.id.gip_video_bdroom_radioBtn_review,
			R.id.gip_video_bdroom_radioBtn_guest,
			R.id.gip_video_bdroom_radioBtn_relative_info,
			R.id.gip_video_bdroom_radioBtn_program,
			R.id.gip_video_bdroom_radioBtn_schedule };

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
		GoverInterPeopleVideoLiveHomeFragment gipVedioLiveHomeFragment = new GoverInterPeopleVideoLiveHomeFragment();
		onTransaction(gipVedioLiveHomeFragment);
	}

	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {

		super.onCheckedChanged(group, checkedId);

		switch (checkedId) {
		// 栏目首页
		case R.id.gip_video_bdroom_radioBtn_home:
			init();
			break;
		// 往期回顾
		case R.id.gip_video_bdroom_radioBtn_review:
			VedioReviewFragment reviewFragment = new VedioReviewFragment();
			onTransaction(reviewFragment);
			break;
		// 嘉宾风采
		case R.id.gip_video_bdroom_radioBtn_guest:
			VideoGuestPresenceFragment videoGuestPresenceFragment = new VideoGuestPresenceFragment();
			onTransaction(videoGuestPresenceFragment);
			break;
		// 相关资料
		case R.id.gip_video_bdroom_radioBtn_relative_info:
			VideoRelatedDataFragment videoRelatedDataFragment = new VideoRelatedDataFragment();
			onTransaction(videoRelatedDataFragment);
			break;
		// 节目介绍
		case R.id.gip_video_bdroom_radioBtn_program:
			VideoProgramIntroducedFragment programIntroducedFragment = new VideoProgramIntroducedFragment();
			onTransaction(programIntroducedFragment);
			break;
		// 日常安排
		case R.id.gip_video_bdroom_radioBtn_schedule:
			VideoDailyRoutineFragment videoDailyRoutineFragment = new VideoDailyRoutineFragment();
			onTransaction(videoDailyRoutineFragment);
			break;

		default:
			break;
		}
	}

}
