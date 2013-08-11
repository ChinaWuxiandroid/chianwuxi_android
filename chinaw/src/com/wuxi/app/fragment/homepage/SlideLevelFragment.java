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
import com.wuxi.app.PopWindowManager;
import com.wuxi.app.R;
import com.wuxi.app.adapter.LeftMenuAdapter;
import com.wuxi.app.fragment.BaseSlideFragment;
import com.wuxi.app.fragment.MainIndexFragment;
import com.wuxi.app.fragment.MainMineFragment;
import com.wuxi.app.fragment.MainSearchFragment;
import com.wuxi.app.fragment.homepage.fantasticwuxi.ChannelFragment;
import com.wuxi.app.fragment.homepage.fantasticwuxi.WuxiChannelContentDetailFragment;
import com.wuxi.app.fragment.homepage.goverpublicmsg.GoverMsgApplyTableFragment;
import com.wuxi.app.fragment.homepage.goverpublicmsg.GoverMsgOpenInfoDetailFragment;
import com.wuxi.app.fragment.homepage.goverpublicmsg.PublicGoverMsgFragment;
import com.wuxi.app.fragment.homepage.goversaloon.GoverSaloonDetailCFFragment;
import com.wuxi.app.fragment.homepage.goversaloon.GoverSaloonDetailQTFragment;
import com.wuxi.app.fragment.homepage.goversaloon.GoverSaloonDetailQZFragment;
import com.wuxi.app.fragment.homepage.goversaloon.GoverSaloonDetailXKFragment;
import com.wuxi.app.fragment.homepage.goversaloon.GoverSaloonDetailZSFragment;
import com.wuxi.app.fragment.homepage.goversaloon.GoverSaloonFragment;
import com.wuxi.app.fragment.homepage.goversaloon.MyOnlineAskFragment;
import com.wuxi.app.fragment.homepage.informationcenter.HotTopicContentFragment;
import com.wuxi.app.fragment.homepage.informationcenter.InfoCenterContentDetailFragment;
import com.wuxi.app.fragment.homepage.informationcenter.InformationCenterFragment;
import com.wuxi.app.fragment.homepage.logorregister.LoginFragment;
import com.wuxi.app.fragment.homepage.logorregister.RegisterFragment;
import com.wuxi.app.fragment.homepage.more.MenuItemSetFragment;
import com.wuxi.app.fragment.homepage.more.SiteMapFragment;
import com.wuxi.app.fragment.homepage.more.SystemSetFragment;
import com.wuxi.app.fragment.homepage.mygoverinteractpeople.ForumContentFragment;
import com.wuxi.app.fragment.homepage.mygoverinteractpeople.ForumPostFragment;
import com.wuxi.app.fragment.homepage.mygoverinteractpeople.HotReviewContentFragment;
import com.wuxi.app.fragment.homepage.publicservice.PublicServiceContentDetailFragment;
import com.wuxi.app.fragment.homepage.publicservice.PublicServiceFragment;
import com.wuxi.app.fragment.search.AdvancedSearchFragment;
import com.wuxi.app.fragment.search.AdvancedSearchResultListFragment;
import com.wuxi.app.fragment.search.SearchResultDetailFragment;
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
	private PopWindowManager popWindowManager;
	private List<FragmentWapper> fragments = new ArrayList<FragmentWapper>(); // 记录每次切换到fragment信息以便回退
	private List<BaseFragment> baseFragments = new ArrayList<BaseFragment>();

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
		popWindowManager = PopWindowManager.getInstance();
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
		manager = getActivity().getSupportFragmentManager();
		mlvMenu = (ListView) view.findViewById(R.id.lv_menu);
		mlvMenu.setOnItemClickListener(this);

		initLeftMenu();// 初始化左侧菜单数据
		initFragment(this.getArguments());

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
					onReplaceFragment(informationCenterFragment, bundle);

				} else if (menuItem.getName().equals("政府信息公开")) {

					PublicGoverMsgFragment publicGoverMsgFragment = new PublicGoverMsgFragment();
					publicGoverMsgFragment.setMenuItem(menuItem);
					onReplaceFragment(publicGoverMsgFragment, bundle);

				} else if (menuItem.getName().equals("公共服务")) {
					PublicServiceFragment publicServiceFragment = new PublicServiceFragment();
					publicServiceFragment.setMenuItem(menuItem);
					onReplaceFragment(publicServiceFragment, bundle);
				} else if (menuItem.getName().equals("政务大厅")) {
					GoverSaloonFragment saloonFragment = new GoverSaloonFragment();
					saloonFragment.setParentMenuItem(menuItem);
					onReplaceFragment(saloonFragment, bundle);
				}

				break;

			case MenuItem.CHANNEL_MENU:// 如果点击的频道菜单
				ChannelFragment ch = new ChannelFragment();
				ch.setMenuItem(menuItem);
				onReplaceFragment(ch, null);


				break;
			case 2:

				break;

			}
		} else {

			switch (fragmentName) {
			case LOGIN_FRAGMENT:// 登录
				LoginFragment loginFragment = new LoginFragment();

				onReplaceFragment(loginFragment, bundle);
				break;
			case REGIST_FRAGMENT:// 注册
				RegisterFragment registerFragment = new RegisterFragment();
				onAddFragment(registerFragment, bundle);
				break;
			case MAINSEARCH_FRAGMENT:// 全站搜索
				MainSearchFragment searchFragment = new MainSearchFragment();
				onReplaceFragment(searchFragment, bundle);
				break;
			case ADVANCED_SEARCH_FRAGMENT:// 全站高级搜索
				AdvancedSearchFragment advancedSearchFragment = new AdvancedSearchFragment();
				onAddFragment(advancedSearchFragment, bundle);
				break;

			case ADVANCED_SEARCH_LIST_FRAGMENT:// 高级搜索列表
				AdvancedSearchResultListFragment advancedSearchResultListFragment = new AdvancedSearchResultListFragment();
				onAddFragment(advancedSearchResultListFragment, bundle);
				break;

			case SEARCH_DETAIL_FRAGMENT:// 搜索内容页
				SearchResultDetailFragment searchResultDetailFragment = new SearchResultDetailFragment();
				onAddFragment(searchResultDetailFragment, bundle);
				break;

			case MAINMINEFRAGMENT:// 政务名互动
				MainMineFragment mainMineFragment = new MainMineFragment();
				mainMineFragment.setParentMenuItem(menuItem);
				onReplaceFragment(mainMineFragment, bundle);
				break;
			case SYSTEMSETF_RAGMENT:// 系统设置
				SystemSetFragment systemSetFragment = new SystemSetFragment();
				onReplaceFragment(systemSetFragment, bundle);

				break;
			case MENUITEMSET_FRAGMENT:// 常用栏设置
				MenuItemSetFragment menusetFragment = new MenuItemSetFragment();
				onAddFragment(menusetFragment, bundle);
				break;
			case SITEMAP_FRAGMENT:// 网站地图
				SiteMapFragment siteMapFragment = new SiteMapFragment();
				onAddFragment(siteMapFragment, bundle);
				break;
			case MYONLINEASKFRAGMENT:// 在线咨询
				MyOnlineAskFragment myOnlineAskFragment = new MyOnlineAskFragment();
				onAddFragment(myOnlineAskFragment, bundle);

				break;
			case HOTREVIEW_CONTENT_FRAGMENT:// 热点话题
				HotReviewContentFragment hotReviewContentFragment = new HotReviewContentFragment();
				onReplaceFragment(hotReviewContentFragment, bundle);
				break;
			case GIP_FOROUM_FRAGMENT:// 公众论坛帖子详细内容
				ForumContentFragment forumContentFragment = new ForumContentFragment();
				onReplaceFragment(forumContentFragment, bundle);
				break;
			case GIP_FORUM_POST_FRAGMENT:// 公众论坛发表帖子界面
				ForumPostFragment forumPostFragment = new ForumPostFragment();
				onReplaceFragment(forumPostFragment, bundle);
				break;

			case GOVERSALOONDETAIL_XK_FRAGMENT:// 政务大厅办件详情
				GoverSaloonDetailXKFragment goverSaloonDetailFragment = new GoverSaloonDetailXKFragment();

				onAddFragment(goverSaloonDetailFragment, bundle);
				break;
			case GOVERSALOONDETAIL_QT_FRAGMENT:// 政务大厅办件详情
				GoverSaloonDetailQTFragment goverSaloonDetailQTFragment = new GoverSaloonDetailQTFragment();

				onAddFragment(goverSaloonDetailQTFragment, bundle);
				break;
			case GOVERSALOONDETAIL_QZ_FRAGMENT:// 政务大厅 强制办件详情
				GoverSaloonDetailQZFragment goverSaloonDetailQZFragment = new GoverSaloonDetailQZFragment();

				onAddFragment(goverSaloonDetailQZFragment, bundle);

				break;
			case GOVERSALOONDETAIL_ZS_FRAGMENT:// 政务大厅 征收办件详情
				GoverSaloonDetailZSFragment goverSaloonDetailZSFragment = new GoverSaloonDetailZSFragment();
				onAddFragment(goverSaloonDetailZSFragment, bundle);


				break;
			case GOVERSALOONDETAIL_CF_FRAGMENT:// 政务大厅 处罚办件详情
				GoverSaloonDetailCFFragment goverSaloonDetailCFFragment = new GoverSaloonDetailCFFragment();
				onAddFragment(goverSaloonDetailCFFragment, bundle);

				break;

			case WUXICHANNELCONTENTDETAILFRAGMENT:// 魅力锡城内容页
				WuxiChannelContentDetailFragment wuxiChannelContentDetailFragment = new WuxiChannelContentDetailFragment();

				onAddFragment(wuxiChannelContentDetailFragment, bundle);
				break;
			case HOTTOPICCONTENTFRAGMENT:// 咨询中心热点专题
				HotTopicContentFragment hotTopicContentFragment = new HotTopicContentFragment();
				onAddFragment(hotTopicContentFragment, bundle);

				break;
			case INFOCENTER_FRAGMENT:
				InfoCenterContentDetailFragment infoCenterContentDetailFragment = new InfoCenterContentDetailFragment();
				onAddFragment(infoCenterContentDetailFragment, bundle);
				break;
			case LEARACTIVITY_FRAGMENT:// 领导活动集锦
				LearActivityFragment learActivityFragment = new LearActivityFragment();
				onReplaceFragment(learActivityFragment, bundle);
				break;
			case FOURTOPIC_ACTIVITYFRAGMENT:
				FourTopicActivityFragment fourTopicActivityFragment = new FourTopicActivityFragment();
				onReplaceFragment(fourTopicActivityFragment, bundle);
				break;
			case PUBLICSERVICECONTENTDETAILFRAGMENT:// 公共那个服务内容页
				PublicServiceContentDetailFragment publicServiceContentDetailFragment = new PublicServiceContentDetailFragment();
				onAddFragment(publicServiceContentDetailFragment, bundle);
				break;
			case GOVERMSG_APPLYTABLE_FRAGMENT://政府信息公开  公共那个服务内容页
				GoverMsgApplyTableFragment goverMsgApplyTableFragment = new GoverMsgApplyTableFragment();
				onAddOtherFragment(goverMsgApplyTableFragment, bundle);
				break;
			case GOVERMSG_INFOOPEN_DETAIL_FRAGMENT://政府信息公开  信息公开指南 和信息公开制度  内容页
				GoverMsgOpenInfoDetailFragment goverMsgOpenInfoDetailFragment = new GoverMsgOpenInfoDetailFragment();
				onAddOtherFragment(goverMsgOpenInfoDetailFragment, bundle);
				break;
			}
		}
	}

	/**
	 * 
	 * wanglu 泰得利通 替换视图
	 * 
	 * @param fragment
	 * @param bundle
	 */
	private void onReplaceFragment(BaseSlideFragment fragment, Bundle bundle) {


		FragmentTransaction ft = manager.beginTransaction();

		ft.replace(FRAME_CONTENT, fragment);
		if (bundle != null) {
			fragment.setArguments(bundle);
		}
		fragment.setFragment(this);
		ft.addToBackStack(null);
		fragment.setManagers(managers);// 传递管理器
		ft.commit();

		baseFragments.clear();// 清楚所有add视图
	}

	/**
	 * 
	 * wanglu 泰得利通 添加Fragment
	 * 
	 * @param fragment
	 * @param bundele
	 */
	private void onAddFragment(BaseSlideFragment fragment, Bundle bundele) {
		fragment.setFragment(this);
		fragment.setManagers(managers);
		fragment.setArguments(bundele);
		FragmentTransaction ft = manager.beginTransaction();
		ft.addToBackStack(null);
		ft.add(FRAME_CONTENT, fragment);
		baseFragments.add(fragment);
		ft.commit();

	}

	//加载非BaseSlideFragment子类的 内容页
	private void onAddOtherFragment(BaseFragment fragment, Bundle bundele) {
		fragment.setManagers(managers);
		fragment.setArguments(bundele);
		FragmentTransaction ft = manager.beginTransaction();
		ft.add(FRAME_CONTENT, fragment);
		baseFragments.add(fragment);
		ft.commit();

	}

	/**
	 * 
	 * wanglu 泰得利通 返回
	 */
	@Override
	public void onBack() {

		if (baseFragments.size() == 0) {// 退出到首页

			managers.ChangeFragment(new MainIndexFragment());

		} else {
			BaseFragment f = baseFragments.get(baseFragments.size() - 1);

			FragmentTransaction ft = manager.beginTransaction();
			ft.addToBackStack(null);
			ft.remove(f);
			ft.commit();
			baseFragments.remove(baseFragments.size() - 1);

		}

	}

	public void openLeftSlideMenu() {
		mSlideMenuLayout.openLeftSlideMenu();
		popWindowManager.removePowWIndows();
	}

	public void openRightSlideMenu() {
		mSlideMenuLayout.openRightSlideMenu();
		popWindowManager.removePowWIndows();
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