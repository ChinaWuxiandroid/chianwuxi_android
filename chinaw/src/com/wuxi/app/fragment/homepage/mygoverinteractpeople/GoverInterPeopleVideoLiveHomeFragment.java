/**
 * 
 */
package com.wuxi.app.fragment.homepage.mygoverinteractpeople;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.RadioGroup;

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

	protected static final int HOME_CONTENT_ID=R.id.gip_vedio_live_home_fragment;

	//内容类型：0->视频直播界面；1->访谈实录界面；2->留言提问界面
	private int type = 0;

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
		//创建视频直播界面碎片的实例
		GoverInterPeopleVideoLiveHomeLiveFragment gipvlhlFragment = new GoverInterPeopleVideoLiveHomeLiveFragment();
		//绑定视频直播界面碎片
		bindFragment(gipvlhlFragment);
	}

	/**
	 * 绑定碎片
	 * @param fragment
	 */
	private void bindFragment(Fragment fragment) {		
		FragmentManager manager = getActivity().getSupportFragmentManager();
		FragmentTransaction ft = manager.beginTransaction();
		ft.replace(HOME_CONTENT_ID, fragment);
		ft.commit();	
	}

	/**
	 * 切换内容
	 */
	private void changeContent(int type){
		switch (type) {
		case 1:
			init();
			break;

		case 2:
			LiveHomeMemoirFragment liveHomeMemoirFragment = new LiveHomeMemoirFragment();
			bindFragment(liveHomeMemoirFragment);

			break;

		case 3:

			break;

		default:
			break;
		}
	}

	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		super.onCheckedChanged(group, checkedId);
		switch (checkedId) {
		case R.id.gip_video_live_home_radioBtn_vediolive:
			type = 0;
			init();
			break;

		case R.id.gip_video_live_home_radioBtn_memoir:
			type = 1;
			changeContent(type);

		case R.id.gip_video_live_home_radioBtn_message:
			type = 2;
			changeContent(type);

		default:
			break;
		}
	}

}
