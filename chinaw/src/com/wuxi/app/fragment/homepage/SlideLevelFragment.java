package com.wuxi.app.fragment.homepage;

import java.util.ArrayList;
import java.util.List;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;

import com.wuxi.app.BaseFragment;
import com.wuxi.app.R;
import com.wuxi.app.adapter.LeftMenuAdapter;
import com.wuxi.app.fragment.BaseSlideFragment;
import com.wuxi.app.fragment.MainIndexFragment;
import com.wuxi.app.fragment.MainMineFragment;
import com.wuxi.app.fragment.MainSearchFragment;
import com.wuxi.app.fragment.homepage.fantasticwuxi.ChannelFragment;
import com.wuxi.app.fragment.homepage.goverpublicmsg.PublicGoverMsgFragment;
import com.wuxi.app.fragment.homepage.goversaloon.GoverSaloonFragment;
import com.wuxi.app.fragment.homepage.goversaloon.MyOnlineAskFragment;
import com.wuxi.app.fragment.homepage.informationcenter.InformationCenterFragment;
import com.wuxi.app.fragment.homepage.logorregister.LoginFragment;
import com.wuxi.app.fragment.homepage.logorregister.RegisterFragment;
import com.wuxi.app.fragment.homepage.more.MenuItemSetFragment;
import com.wuxi.app.fragment.homepage.more.SiteMapFragment;
import com.wuxi.app.fragment.homepage.more.SystemSetFragment;
import com.wuxi.app.fragment.homepage.publicservice.PublicServiceFragment;
import com.wuxi.app.listeners.SlideLinstener;
import com.wuxi.app.util.CacheUtil;
import com.wuxi.app.util.Constants;
import com.wuxi.app.util.Constants.FragmentName;
import com.wuxi.app.view.SlideMenuLayout;
import com.wuxi.domain.MenuItem;

public class SlideLevelFragment extends BaseFragment implements SlideLinstener,

OnItemClickListener, OnClickListener, OnCheckedChangeListener {

	protected static final int FRAME_CONTENT = R.id.slide_main_content;
	private View view;
	private SlideMenuLayout mSlideMenuLayout;

	private MenuItem menuItem;// 首页选中的菜单
	private ListView mlvMenu;// 左侧菜单列表

	private static final String MENUITEM_CACKE_KEY = "man_menu_item";
	private static final String TAG = "SlideLevelFragment";
	private TextView login_tv_userlogin;
	private TextView login_tv_user_regisster;
	private RadioGroup right_menu_rg;
	private RadioButton right_menu_rb_collect;
	private RadioButton right_menu_rb_mzhd;
	private RadioButton right_menu_rb_mydownload;
	private RadioButton right_menu_rb_myset;
	private LeftMenuAdapter leftMenuAdapter;
	private Constants.FragmentName fragmentName;// 显示的fragmentName名称
	private FragmentManager manager;
	private List<FragmentWapper> fragments = new ArrayList<FragmentWapper>(); // 记录每次切换到fragment信息以便回退

	public void setFragmentName(Constants.FragmentName fragmentName) {
		this.fragmentName = fragmentName;
	}

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

		login_tv_userlogin = (TextView) view
				.findViewById(R.id.login_tv_userlogin);
		login_tv_user_regisster = (TextView) view
				.findViewById(R.id.login_tv_user_regisster);
		right_menu_rg = (RadioGroup) view.findViewById(R.id.right_menu_rg);
		right_menu_rb_collect = (RadioButton) view
				.findViewById(R.id.right_menu_rb_collect);
		right_menu_rb_mzhd = (RadioButton) view
				.findViewById(R.id.right_menu_rb_mzhd);
		right_menu_rb_mydownload = (RadioButton) view
				.findViewById(R.id.right_menu_rb_mydownload);
		right_menu_rb_myset = (RadioButton) view
				.findViewById(R.id.right_menu_rb_myset);

		login_tv_userlogin.setOnClickListener(this);
		login_tv_user_regisster.setOnClickListener(this);
		right_menu_rg.setOnCheckedChangeListener(this);

		mlvMenu = (ListView) view.findViewById(R.id.lv_menu);
		mlvMenu.setOnItemClickListener(this);

		initLeftMenu();// 初始化左侧菜单数据
		initFragment(null);

		return view;
	}

	@SuppressWarnings("unchecked")
	private void initLeftMenu() {

		List<MenuItem> leftMenuItems = (List<MenuItem>) CacheUtil
				.get(MENUITEM_CACKE_KEY);// 直接从缓存中取出菜单

		leftMenuAdapter = new LeftMenuAdapter(getActivity(),
				R.layout.slide_navigator_item, new int[] {
						R.id.tv_left_menu_name, R.id.left_iv_icon },
				leftMenuItems, null, position);
		mlvMenu.setAdapter(leftMenuAdapter);
	}

	/**
	 * 
	 * wanglu 泰得利通
	 * 
	 * @param bundle
	 *            传递的参数
	 */
	private void initFragment(Bundle bundle) {
		if (menuItem != null && !menuItem.getName().equals("政民互动")) {//
			switch (menuItem.getType()) {// 菜单类型

			case MenuItem.CUSTOM_MENU:

				if (menuItem.getName().equals("咨询中心")) {
					InformationCenterFragment informationCenterFragment = new InformationCenterFragment();
					informationCenterFragment.setMenuItem(menuItem);
					onTransaction(informationCenterFragment, null);

				} else if (menuItem.getName().equals("政府信息公开")) {

					PublicGoverMsgFragment publicGoverMsgFragment = new PublicGoverMsgFragment();
					publicGoverMsgFragment.setMenuItem(menuItem);
					onTransaction(publicGoverMsgFragment, null);

				} else if (menuItem.getName().equals("公共服务")) {
					PublicServiceFragment publicServiceFragment = new PublicServiceFragment();
					publicServiceFragment.setMenuItem(menuItem);
					onTransaction(publicServiceFragment, null);
				} else if (menuItem.getName().equals("政务大厅")) {
					GoverSaloonFragment saloonFragment = new GoverSaloonFragment();
					saloonFragment.setParentMenuItem(menuItem);
					onTransaction(saloonFragment, null);
				}

				break;

			case MenuItem.CHANNEL_MENU:// 如果点击的频道菜单
				ChannelFragment ch = new ChannelFragment();
				ch.setMenuItem(menuItem);
				onTransaction(ch, null);
				break;
			case 2:

				break;

			}
		} else {

			switch (fragmentName) {
			case LOGIN_FRAGMENT:// 登录
				LoginFragment loginFragment = new LoginFragment();

				onTransaction(loginFragment, bundle);
				break;
			case REGIST_FRAGMENT:// 注册
				RegisterFragment registerFragment = new RegisterFragment();
				onTransaction(registerFragment, bundle);
				break;
			case MAINSEARCH_FRAGMENT:// 全站搜索
				MainSearchFragment searchFragment = new MainSearchFragment();
				onTransaction(searchFragment, bundle);
				break;
			case MAINMINEFRAGMENT:// 政务名互动
				MainMineFragment mainMineFragment = new MainMineFragment();
				mainMineFragment.setMenuItem(menuItem);
				onTransaction(mainMineFragment, bundle);
				break;
			case SYSTEMSETF_RAGMENT:// 系统设置
				SystemSetFragment systemSetFragment = new SystemSetFragment();
				onTransaction(systemSetFragment, bundle);

				break;
			case MENUITEMSET_FRAGMENT:// 常用栏设置
				MenuItemSetFragment menusetFragment = new MenuItemSetFragment();
				onTransaction(menusetFragment, bundle);
				break;
			case SITEMAP_FRAGMENT:// 网站地图
				SiteMapFragment siteMapFragment = new SiteMapFragment();
				onTransaction(siteMapFragment, bundle);
				break;
			case MYONLINEASKFRAGMENT:// 在线咨询
				MyOnlineAskFragment myOnlineAskFragment = new MyOnlineAskFragment();
				onTransaction(myOnlineAskFragment, bundle);
				break;

			}

		}

	}

	private void onTransaction(BaseSlideFragment fragment, Bundle bundle) {
		manager = getActivity().getSupportFragmentManager();
		FragmentTransaction ft = manager.beginTransaction();
		ft.replace(FRAME_CONTENT, fragment);
		if (bundle != null) {
			fragment.setArguments(bundle);
		}
		fragment.setFragment(this);
		ft.addToBackStack(null);
		fragment.setManagers(managers);// 传递管理器
		ft.commit();
		FragmentWapper f = new FragmentWapper(this.menuItem, this.position,
				this.fragmentName, bundle);

		fragments.add(f);

	}

	/**
	 * 
	 * wanglu 泰得利通 返回
	 */
	@Override
	public void onBack() {

		if (fragments.size() == 1) {// 退出到首页

			if (managers.fragments.size() > 1) {
				managers.BackPress(this);
			} else {
				managers.ChangeFragment(new MainIndexFragment());

			}

		} else {
			FragmentWapper f = fragments.get(fragments.size() - 2);
			this.menuItem = f.menuItem;
			this.position = f.position;
			this.fragmentName = f.fName;
			leftMenuAdapter.setSelectPosition(position);
			leftMenuAdapter.notifyDataSetChanged();
			initFragment(f.bundle);
			fragments.remove(fragments.size() - 1);
			fragments.remove(fragments.size() - 2);

		}

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

	@Override
	public void onItemClick(AdapterView<?> parent, View arg1, int position,
			long id) {

		if (position == this.position)
			return;
		MenuItem checkMenuItem = (MenuItem) parent.getItemAtPosition(position);
		if (checkMenuItem.getName().equals("政民互动")) {

			this.fragmentName = Constants.FragmentName.MAINMINEFRAGMENT;

		} else {
			this.fragmentName = null;
		}
		this.menuItem = checkMenuItem;
		this.position = position;
		leftMenuAdapter.setSelectPosition(position);
		leftMenuAdapter.notifyDataSetChanged();
		initFragment(null);

	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.login_tv_userlogin:// 登录处理

			this.menuItem = null;

			this.fragmentName = Constants.FragmentName.LOGIN_FRAGMENT;
			initFragment(null);
			break;
		case R.id.login_tv_user_regisster:// 注册
			// managers.BackPress(this);
			this.menuItem = null;
			this.fragmentName = Constants.FragmentName.REGIST_FRAGMENT;
			initFragment(null);

			break;

		}
	}

	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {

		switch (checkedId) {

		case R.id.right_menu_rb_collect:// 我的收藏
			break;
		case R.id.right_menu_rb_mzhd:// 民政互动
			break;

		case R.id.right_menu_rb_mydownload:// 我的下载
			break;

		case R.id.right_menu_rb_myset:// 我的设置
			break;
		}
	}

	public class FragmentWapper {
		MenuItem menuItem;
		int position;
		Constants.FragmentName fName;
		Bundle bundle;

		public FragmentWapper(MenuItem menuItem, int position,
				Constants.FragmentName fName, Bundle bundle) {
			this.menuItem = menuItem;
			this.position = position;
			this.fName = fName;
			this.bundle = bundle;

		}
	}

	@Override
	public void replaceFragment(MenuItem menuItem, int position,
			FragmentName fagmentName, Bundle bundle) {

		this.menuItem = menuItem;
		this.position = position;
		this.fragmentName = fagmentName;
		initFragment(bundle);

	}

}