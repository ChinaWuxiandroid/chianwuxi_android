package com.wuxi.app.fragment.homepage.mygoverinteractpeople;

import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;

import com.wuxi.app.R;
import com.wuxi.app.activity.BaseItemContentActivity;

/***
 * 
 * 政民互动---征求意见平台---网上调查---item
 * 
 * @类名： GoverInterOnlineSurveyFragment
 * @描述： TODO
 * @作者： 罗森
 * @创建时间： 2013 2013-11-14 下午5:42:18
 * @修改时间：
 * @修改描述：
 */
public class GoverInterOnlineSurveyFragment extends BaseItemContentActivity
		implements OnCheckedChangeListener {

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

	}

	@Override
	protected int getContentLayoutId() {
		return R.layout.online_survey_layout;
	}

	@Override
	protected String getContentTitleText() {
		return null;
	}

}
