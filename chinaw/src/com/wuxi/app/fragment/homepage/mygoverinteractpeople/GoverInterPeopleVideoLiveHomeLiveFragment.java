/**
 * 
 */
package com.wuxi.app.fragment.homepage.mygoverinteractpeople;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.wuxi.app.BaseFragment;
import com.wuxi.app.R;

/**
 * 栏目首页中 节目预告碎片
 * 
 * @author 智佳 罗森
 * @changetime 2013年8月9日15:28
 */
public class GoverInterPeopleVideoLiveHomeLiveFragment extends BaseFragment {

	private View view = null;

	private Context context = null;

	// 预告主题
	private TextView advance_themeTextView = null;
	// 预告时间
	private TextView advance_timeTextView = null;
	// 预告嘉宾
	private TextView advance_guestTextView = null;
	// 预告更多
	private TextView advance_moreTextView = null;

	// 直播主题
	private TextView live_themeTextView = null;
	// 直播时间
	private TextView live_timeTextView = null;
	// 直播嘉宾
	private TextView live_guestTextView = null;
	// 直播视频
	private ImageView homeLiveImageView = null;
	// 点击观看视频
	private TextView live_watchTextView = null;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.gip_video_home_live_layout,
				null);
		context = getActivity();

		initLayout();
		return view;
	}

	/**
	 * 初始化布局控件
	 */
	private void initLayout() {
		// 节目预告模块
		advance_themeTextView = (TextView) view
				.findViewById(R.id.bdroom_home_live_theme_text);
		advance_timeTextView = (TextView) view
				.findViewById(R.id.bdroom_home_live_time_text);
		advance_guestTextView = (TextView) view
				.findViewById(R.id.bdroom_home_live_guest_text);
		advance_moreTextView = (TextView) view
				.findViewById(R.id.vedio_bdroom_home_live_more_text);
		advance_moreTextView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Toast.makeText(context, "正在完善该功能...", Toast.LENGTH_SHORT)
						.show();
				advance_moreTextView.setTextColor(Color.BLUE);
			}
		});

		// 视频直播模块
		live_themeTextView = (TextView) view
				.findViewById(R.id.bdroom_home_live_theme_content);
		live_timeTextView = (TextView) view
				.findViewById(R.id.bdroom_home_live_time_content);
		live_guestTextView = (TextView) view
				.findViewById(R.id.bdroom_home_live_guest_content);
		live_watchTextView = (TextView) view
				.findViewById(R.id.vedio_dbroom_home_live_click_text);
		live_watchTextView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Toast.makeText(context, "正在完善该功能...", Toast.LENGTH_SHORT)
						.show();
				live_watchTextView.setTextColor(Color.BLUE);
			}
		});
	}

}
