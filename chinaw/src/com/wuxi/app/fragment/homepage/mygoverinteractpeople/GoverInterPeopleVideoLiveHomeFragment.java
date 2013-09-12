/**
 * 
 */
package com.wuxi.app.fragment.homepage.mygoverinteractpeople;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.wuxi.app.R;
import com.wuxi.app.fragment.commonfragment.RadioButtonChangeFragment;

/**
 * 走进直播间之栏目首页
 * 
 * @author 智佳 罗森
 * @changetime 2013年8月9日 16:47
 * 
 */
public class GoverInterPeopleVideoLiveHomeFragment extends
		RadioButtonChangeFragment {

	protected static final int HOME_CONTENT_ID = R.id.gip_vedio_live_home_fragment;

	// 我来说说按钮
	private Button home_saybtn = null;

	// 我来提问按钮
	private Button home_askbtn = null;

	// 内容类型：0->节目预告界面；1->访谈实录界面；2->留言提问界面
	private int type = 0;

	// 存放该界面的RadioBtnID的数组
	private final int[] radioBtnIds = {
			R.id.gip_video_live_home_radioBtn_vediolive,
			R.id.gip_video_live_home_radioBtn_memoir,
			R.id.gip_video_live_home_radioBtn_message };

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
		// 创建视频直播界面碎片的实例
		GoverInterPeopleVideoLiveHomeLiveFragment gipvlhlFragment = new GoverInterPeopleVideoLiveHomeLiveFragment();
		// 绑定视频直播界面碎片
		bindFragment(gipvlhlFragment);
		initLayout();
	}

	/**
	 * 绑定碎片
	 * 
	 * @param fragment
	 */
	private void bindFragment(Fragment fragment) {
		FragmentManager manager = getActivity().getSupportFragmentManager();
		FragmentTransaction ft = manager.beginTransaction();
		ft.replace(HOME_CONTENT_ID, fragment);
		ft.commitAllowingStateLoss();
	}

	/**
	 * 切换内容
	 */
	private void changeContent(int type) {
		switch (type) {
		case 0:
			init();
			break;

		case 1:
			LiveHomeMemoirFragment liveHomeMemoirFragment = new LiveHomeMemoirFragment();
			bindFragment(liveHomeMemoirFragment);
			break;

		case 2:
			LiveHomeLeaveMessageFragment leaveMessageFragment = new LiveHomeLeaveMessageFragment();
			bindFragment(leaveMessageFragment);
			break;
		}
	}

	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		super.onCheckedChanged(group, checkedId);
		switch (checkedId) {
		case R.id.gip_video_live_home_radioBtn_vediolive:
			type = 0;
			home_saybtn.setVisibility(View.GONE);
			home_askbtn.setVisibility(View.GONE);
			changeContent(type);
			break;

		case R.id.gip_video_live_home_radioBtn_memoir:
			type = 1;
			home_saybtn.setVisibility(View.GONE);
			home_askbtn.setVisibility(View.GONE);
			changeContent(type);
			break;

		case R.id.gip_video_live_home_radioBtn_message:
			type = 2;

			home_saybtn.setVisibility(View.VISIBLE);
			home_askbtn.setVisibility(View.VISIBLE);
			changeContent(type);
			break;
		}
	}

	private void initLayout() {
		home_saybtn = (Button) view.findViewById(R.id.vedio_live_home_saybtn);

		home_saybtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Toast.makeText(context, "正在完善该功能...", Toast.LENGTH_SHORT)
						.show();
			}
		});

		home_askbtn = (Button) view.findViewById(R.id.vedio_live_home_askbtn);

		home_askbtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Toast.makeText(context, "正在完善该功能...", Toast.LENGTH_SHORT)
						.show();
			}
		});
	}

}
