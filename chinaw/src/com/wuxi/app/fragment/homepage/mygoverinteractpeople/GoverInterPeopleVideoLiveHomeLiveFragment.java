/**
 * 
 */
package com.wuxi.app.fragment.homepage.mygoverinteractpeople;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.wuxi.app.BaseFragment;
import com.wuxi.app.R;

/**
 * 栏目首页中视频直播碎片
 * 
 * @author 智佳 罗森
 *
 */
public class GoverInterPeopleVideoLiveHomeLiveFragment extends BaseFragment{
	
	private View view = null;
	
	private Context context = null;
	
	private ImageView homeLiveImageView = null;
	
	private TextView themeTextView = null;
	private TextView timeTextView = null;
	private TextView guestTextView = null;
	private TextView infoTextView = null;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.gip_vedio_bdroom_home_live_layout, null);
		
		context = getActivity();
		initLayout();
		return view;
	}

	/**
	 * 初始化控件
	 */
	private void initLayout(){
		homeLiveImageView = (ImageView) view.findViewById(R.id.bdroom_home_live_imageview);
		
		themeTextView = (TextView) view.findViewById(R.id.bdroom_home_live_theme_content);
		timeTextView = (TextView) view.findViewById(R.id.bdroom_home_live_time_content);
		guestTextView = (TextView) view.findViewById(R.id.bdroom_home_live_guest_content);
		infoTextView = (TextView) view.findViewById(R.id.bdroom_home_live_info_content);
	}
	
}
