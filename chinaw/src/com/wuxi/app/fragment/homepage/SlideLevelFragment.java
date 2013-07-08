package com.wuxi.app.fragment.homepage;

import java.util.List;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.wuxi.app.BaseFragment;
import com.wuxi.app.R;
import com.wuxi.app.adapter.LeftMenuAdapter;
import com.wuxi.app.fragment.BaseSlideFragment;
import com.wuxi.app.fragment.homepage.fantasticwuxi.ChannelFragment;
import com.wuxi.app.fragment.homepage.goverpublicmsg.PublicGoverMsgFragment;
import com.wuxi.app.fragment.homepage.informationcenter.InformationCenterFragment;
import com.wuxi.app.fragment.homepage.logorregister.LoginFragment;
import com.wuxi.app.fragment.homepage.logorregister.RegisterFragment;
import com.wuxi.app.listeners.SlideLinstener;
import com.wuxi.app.util.CacheUtil;
import com.wuxi.app.util.Constants;
import com.wuxi.app.view.SlideMenuLayout;
import com.wuxi.domain.MenuItem;

public class SlideLevelFragment extends BaseFragment implements SlideLinstener {

	protected static final int FRAME_CONTENT = R.id.slide_main_content;
	private View view;
	private SlideMenuLayout mSlideMenuLayout;

	private MenuItem menuItem;// 首页选中的菜单
	private ListView mlvMenu;// 左侧菜单列表
	private Constants.FragmentName fragmentName;

	public void setFragmentName(Constants.FragmentName fragmentName) {
		this.fragmentName = fragmentName;
	}

	private static final String MENUITEM_CACKE_KEY = "man_menu_item";

	public void setMenuItem(MenuItem menuItem) {
		this.menuItem = menuItem;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.slide_level_layout, null);
		mSlideMenuLayout = (SlideMenuLayout) view
				.findViewById(R.id.slide_menu_layout);
		mSlideMenuLayout.setLeftSlideMenuId(R.id.left_menu);
		mSlideMenuLayout.setRightSlideMenuId(R.id.right_menu);
		mSlideMenuLayout.reset();

		mlvMenu = (ListView) view.findViewById(R.id.lv_menu);

		initLeftMenu();// 初始化左侧菜单数据
		
		return view;
	}

	@SuppressWarnings("unchecked")
	private void initLeftMenu() {

		List<MenuItem> leftMenuItems = (List<MenuItem>) CacheUtil
				.get(MENUITEM_CACKE_KEY);// 直接从缓存中取出菜单

		mlvMenu.setAdapter(new LeftMenuAdapter(getActivity(),
				R.layout.slide_navigator_item,
				new int[] { R.id.tv_left_menu_name }, leftMenuItems, null));
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		init();
	}

	private void init() {
		if (menuItem != null) {//
			switch (menuItem.getType()) {// 菜单类型

			case MenuItem.CUSTOM_MENU:

				if (menuItem.getName().equals("咨询中心")) {

					InformationCenterFragment informationCenterFragment = new InformationCenterFragment();
					informationCenterFragment.setMenuItem(menuItem);
					onTransaction(informationCenterFragment);

				} else if (menuItem.getName().equals("政府信息公开")) {
					PublicGoverMsgFragment publicGoverMsgFragment = new PublicGoverMsgFragment();
					publicGoverMsgFragment.setMenuItem(menuItem);
					onTransaction(publicGoverMsgFragment);
				}

				break;

			case MenuItem.CHANNEL_MENU:// 如果点击的频道菜单
				ChannelFragment ch = new ChannelFragment();
				ch.setMenuItem(menuItem);
				onTransaction(ch);
				break;
			case 2:

				break;

			}
		} else {
			switch (fragmentName) {
			case LOGIN_FRAGMENT:
				LoginFragment loginFragment = new LoginFragment();// 登录
				loginFragment.setManagers(managers);
				onTransaction(loginFragment);
				break;
			case REGIST_FRAGMENT:
				RegisterFragment reg=new RegisterFragment();//注册
				
				onTransaction(reg);
				break;
			}

		}

	}

	private void onTransaction(BaseSlideFragment fragment) {
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