package com.wuxi.app.fragment.index;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.wuxi.app.BaseFragment;
import com.wuxi.app.R;
import com.wuxi.app.fragment.index.type.BaseSlideFragment;
import com.wuxi.app.fragment.index.type.ChannelFragment;
import com.wuxi.app.fragment.index.type.PublicGoverMsgFragment;
import com.wuxi.app.view.SlideMenuLayout;
import com.wuxi.domain.MenuItem;

public class SlideLevelFragment extends BaseFragment implements
		OnCheckedChangeListener {

	protected static final int FRAME_CONTENT = R.id.slide_main_content;
	private View view;
	private SlideMenuLayout mSlideMenuLayout;
	private RadioGroup radioGroup;
	private int mCurrentFragmentId = 0;
	private MenuItem menuItem;// 首页选中的菜单

	public void setMenuItem(MenuItem menuItem) {
		this.menuItem = menuItem;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		view = inflater.inflate(R.layout.slide_level_layout, null);
		mSlideMenuLayout = (SlideMenuLayout) view
				.findViewById(R.id.slide_menu_layout);
		mSlideMenuLayout.setLeftSlideMenuId(R.id.left_menu);
		mSlideMenuLayout.setRightSlideMenuId(R.id.right_menu);
		mSlideMenuLayout.reset();
		radioGroup = (RadioGroup) view
				.findViewById(R.id.slide_left_tab_radiogroup);
		radioGroup.setOnCheckedChangeListener(this);
		return view;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		init();
	}

	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		// TODO Auto-generated method stub

		switch (checkedId) {

		case R.id.left_tab_public_goverment:// 政府信息公开
			onTransaction(new PublicGoverMsgFragment(), checkedId);
			break;

		case R.id.left_tab_xicheng:// 魅力锡城
			onTransaction(new ChannelFragment(), checkedId);
			break;

		case R.id.left_tab_msg_center:// 资讯中心

			break;

		case R.id.left_tab_hotall:// 政务大厅

			break;

		case R.id.left_tab_public_service:// 公共服务

			break;

		case R.id.left_tab_hudong:// 政民互动

			break;
		}
	}

	private void init() {
		switch (menuItem.getType()) {// 菜单类型
		case 0:
			onTransaction(new PublicGoverMsgFragment(),
					R.id.left_tab_public_goverment);
			break;

		case MenuItem.CHANNEL_MENU:// 如果点击的频道菜单
			ChannelFragment ch = new ChannelFragment();
			ch.setMenuItem(menuItem);
			onTransaction(ch, R.id.left_tab_xicheng);
			break;
		case 2:

			break;
		}

	}

	private void onTransaction(BaseSlideFragment fragment, int id) {
		if (id == mCurrentFragmentId)
			return;
		mCurrentFragmentId = id;
		FragmentManager manager = getActivity().getSupportFragmentManager();
		FragmentTransaction ft = manager.beginTransaction();
		ft.replace(FRAME_CONTENT, fragment);
		fragment.setFragment(this);

		ft.addToBackStack(null);
		ft.commit();
	}

	public void openLeftSlideMenu() {
		mSlideMenuLayout.openLeftSlideMenu();
	}

	public void openRightSlideMenu() {
		mSlideMenuLayout.openRightSlideMenu();
	}

	public void closeSlideMenu() {
		mSlideMenuLayout.reset();
	}

	public boolean isLeftMenuEnabled() {
		return mSlideMenuLayout.getLeftSlideMenuEnabled();
	}

	public boolean isRightMenuEnabled() {
		return mSlideMenuLayout.getRightSlideMenuEnabled();
	}

	public void setUnTouchableOffset(float start, float end) {
		mSlideMenuLayout.setUnTouchableOffset(start, end);
	}

	public void OpearnLeft() {
		if (isLeftMenuEnabled())
			closeSlideMenu();
		else
			openLeftSlideMenu();
	}

	public void OpearnRight() {
		if (isRightMenuEnabled())
			closeSlideMenu();
		else
			openRightSlideMenu();
	}

}