/*
 * (#)BaseSlideActivity.java 1.0 2013-8-29 2013-8-29 GMT+08:00
 */
package com.wuxi.app.activity;

import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.wuxi.app.MainTabActivity;
import com.wuxi.app.R;
import com.wuxi.app.activity.homepage.logorregister.LoginActivity;
import com.wuxi.app.activity.homepage.logorregister.RegisterActivity;
import com.wuxi.app.activity.homepage.more.MenuItemSetActivity;
import com.wuxi.app.activity.homepage.more.SystemSetActivity;
import com.wuxi.app.activity.homepage.mygoverinteractpeople.MainMineActivity;
import com.wuxi.app.adapter.LeftMenuAdapter;
import com.wuxi.app.util.CacheUtil;
import com.wuxi.app.util.Constants;
import com.wuxi.app.util.MenuItemChanelUtil;
import com.wuxi.app.util.SystemUtil;
import com.wuxi.app.view.SlideMenuLayout;
import com.wuxi.domain.MenuItem;

/**
 * @author wanglu 泰得利通
 * @version $1.0, 2013-8-29 2013-8-29 GMT+08:00 含有左右菜单的activity基类
 * 
 */
public abstract class BaseSlideActivity extends FragmentActivity implements
		OnClickListener, OnItemClickListener {

	protected static final int FRAME_CONTENT = R.id.slide_main_content;

	public static final String SELECT_MENU_POSITION_KEY = "SELECT_MENU_KEY";// 选中的左侧菜单序号

	protected View mainView;// 之间的视图

	private SlideMenuLayout mSlideMenuLayout;

	private ListView mlvMenu;// 左侧菜单列表

	protected MenuItem menuItem;// 当前的最外层菜单

	private static final String MENUITEM_CACKE_KEY = Constants.CacheKey.MAIN_MENUITEM_KEY;

	private TextView login_tv_userlogin;

	private TextView login_tv_user_regisster;

	private RadioButton right_menu_rb_collect;

	private RadioButton right_menu_rb_mzhd;

	private RadioButton right_menu_rb_mydownload;

	private RadioButton right_menu_rb_myset;

	private LeftMenuAdapter leftMenuAdapter;

	protected ImageView opearn_btn, member_btnm, back_btn;

	public TextView title_text;// 窗口比标题

	private int leftSelectPostion = -1;// 左侧选中的项

	private FrameLayout flmain;

	private List<MenuItem> leftMenuItems;// 左侧菜单列表

	public void setLeftSelectPostion(int leftSelectPostion) {
		this.leftSelectPostion = leftSelectPostion;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.slide_level_layout);

		Bundle bundle = getIntent().getExtras();
		if (bundle != null) {
			leftSelectPostion = bundle.getInt(SELECT_MENU_POSITION_KEY);

		}
		findViews();
		initLeftMenu();// 初始化左侧菜单数据
		if (leftSelectPostion != -1) {
			this.menuItem = leftMenuItems.get(leftSelectPostion);
		}

		mainView = View.inflate(this, getLayoutId(), null);
		flmain.addView(mainView);// 将中间视图添加到布局中
		findMainTitleViews();// 寻找并初始化中间视图的头部数据
		findMainContentViews(mainView);// 寻找并初始化中间视图的头部数据子activity实现

	}

	/**
	 * 
	 * @author 泰得利通 wanglu
	 * @param view
	 *            中间视图的View
	 */
	protected abstract void findMainContentViews(View view);

	/**
	 * @author 泰得利通 wanglu 初始化中间视图的控件
	 */
	protected void findMainTitleViews() {

		opearn_btn = (ImageView) mainView
				.findViewById(R.id.open_close_left_btn);
		member_btnm = (ImageView) mainView.findViewById(R.id.member_btn);
		title_text = (TextView) mainView.findViewById(R.id.Title_Text);
		back_btn = (ImageView) mainView.findViewById(R.id.back_btn);

		opearn_btn.setOnClickListener(LeftClick);
		member_btnm.setOnClickListener(MemberClick);
		back_btn.setOnClickListener(BackClick);

		title_text.setText(getTitleText());

	}

	private OnClickListener LeftClick = new OnClickListener() {
		@Override
		public void onClick(View v) {
			opearnLeft();
		}
	};

	private OnClickListener MemberClick = new OnClickListener() {
		@Override
		public void onClick(View v) {
			opearnRight();
		}
	};

	private OnClickListener BackClick = new OnClickListener() {

		@Override
		public void onClick(View v) {
			onBack();
		}
	};

	/**
	 * 
	 * @author 泰得利通 wanglu 回退处理
	 */
	protected void onBack() {

		MainTabActivity.instance.pop();// 回退

	}

	/**
	 * 
	 * wanglu 泰得利通 返回布局文件ID，该布局文件头部必须有菜单按钮，回退按钮，及登录按钮 子类必须实现
	 * 
	 * @return
	 */
	protected abstract int getLayoutId();

	/**
	 * 
	 * wanglu 泰得利通 标题 子类实现
	 * 
	 * @return
	 */
	protected abstract String getTitleText();

	/**
	 * 
	 * @author 泰得利通 wanglu 寻找视图
	 */
	protected void findViews() {

		mSlideMenuLayout = (SlideMenuLayout) findViewById(R.id.slide_menu_layout);
		mSlideMenuLayout.setLeftSlideMenuId(R.id.left_menu);
		mSlideMenuLayout.setRightSlideMenuId(R.id.right_menu);
		mSlideMenuLayout.reset();// 左右滑动菜单视图初始化

		flmain = (FrameLayout) findViewById(FRAME_CONTENT);
		// 右测菜单视图初始化
		login_tv_userlogin = (TextView) findViewById(R.id.login_tv_userlogin);
		login_tv_user_regisster = (TextView) findViewById(R.id.login_tv_user_regisster);

		right_menu_rb_collect = (RadioButton) findViewById(R.id.right_menu_rb_collect);
		right_menu_rb_mzhd = (RadioButton) findViewById(R.id.right_menu_rb_mzhd);
		right_menu_rb_mydownload = (RadioButton) findViewById(R.id.right_menu_rb_mydownload);
		right_menu_rb_myset = (RadioButton) findViewById(R.id.right_menu_rb_myset);

		right_menu_rb_collect.setOnClickListener(this);
		right_menu_rb_mzhd.setOnClickListener(this);
		right_menu_rb_mydownload.setOnClickListener(this);
		right_menu_rb_myset.setOnClickListener(this);
		login_tv_userlogin.setOnClickListener(this);
		login_tv_user_regisster.setOnClickListener(this);

		mlvMenu = (ListView) findViewById(R.id.lv_menu);
		mlvMenu.setOnItemClickListener(this);

	}

	/**
	 * 
	 * @author 泰得利通 wanglu 初始化左侧菜单
	 */
	@SuppressWarnings("unchecked")
	private void initLeftMenu() {

		leftMenuItems = (List<MenuItem>) CacheUtil.get(MENUITEM_CACKE_KEY);// 直接从缓存中取出菜单

		leftMenuAdapter = new LeftMenuAdapter(this,
				R.layout.slide_navigator_item, new int[] {
						R.id.tv_left_menu_name, R.id.left_iv_icon },
				leftMenuItems, null, leftSelectPostion);
		mlvMenu.setAdapter(leftMenuAdapter);
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

	public void opearnLeft() {
		if (isLeftMenuEnabled())
			closeSlideMenu();
		else
			openLeftSlideMenu();
	}

	public void opearnRight() {
		if (isRightMenuEnabled())
			closeSlideMenu();
		else
			openRightSlideMenu();
	}

	@Override
	public void onItemClick(AdapterView<?> adapterView, View arg1,
			int position, long arg3) {

		if (!mSlideMenuLayout.getLeftSlideMenuEnabled()) {
			return;
		}
		if (position == this.leftSelectPostion)
			return;
		MenuItem checkMenuItem = (MenuItem) adapterView
				.getItemAtPosition(position);
		closeSlideMenu();
		switchActivity(checkMenuItem, position);

	}

	/**
	 * 
	 * @author 泰得利通 wanglu
	 * @param menuItem
	 *            切换activity
	 */
	protected void switchActivity(MenuItem menuItem, int position) {

		Intent intent = null;

		Class<?> acClass = MenuItemChanelUtil.getActivityClassByName(menuItem);
		if (acClass != null) {
			intent = new Intent(BaseSlideActivity.this, acClass);

			intent.putExtra(BaseSlideActivity.SELECT_MENU_POSITION_KEY,
					position);

			MainTabActivity.instance.addView(intent);
		}
	}

	@Override
	public void onClick(View v) {

		if (!isRightMenuEnabled()) {
			return;
		}
		Intent intent = null;
		switch (v.getId()) {
		case R.id.login_tv_userlogin:// 登录处理
			intent = new Intent(BaseSlideActivity.this, LoginActivity.class);

			break;
		case R.id.login_tv_user_regisster:// 注册
			intent = new Intent(BaseSlideActivity.this, RegisterActivity.class);

			break;
		case R.id.right_menu_rb_collect:// 我的收藏
			intent = new Intent(BaseSlideActivity.this,
					MenuItemSetActivity.class);

			break;
		case R.id.right_menu_rb_mzhd:// 我的政民互动

			if ("".equals(SystemUtil.getAccessToken(this))) {
				Toast.makeText(this, "请先登录", Toast.LENGTH_SHORT).show();
				intent = new Intent(BaseSlideActivity.this, LoginActivity.class);

			} else {
				intent = new Intent(BaseSlideActivity.this,
						MainMineActivity.class);
			}

			break;

		case R.id.right_menu_rb_mydownload:// 我的下载
			Toast.makeText(this, "正在施工中....", Toast.LENGTH_SHORT).show();
			break;
		case R.id.right_menu_rb_myset:// 我的设置
			intent = new Intent(BaseSlideActivity.this, SystemSetActivity.class);
			break;

		}

		if (intent != null) {
			closeSlideMenu();
			MainTabActivity.instance.addView(intent);
		}

	}

	@Override
	public boolean dispatchKeyEvent(KeyEvent event) {
		if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
			if (event.getAction() == KeyEvent.ACTION_UP) {
				onBack();
			}
			return true;
		}
		return super.dispatchKeyEvent(event);
	}
}
