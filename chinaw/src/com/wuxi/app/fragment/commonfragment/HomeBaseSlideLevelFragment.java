package com.wuxi.app.fragment.commonfragment;

import java.util.List;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.wuxi.app.BaseFragment;
import com.wuxi.app.R;
import com.wuxi.app.adapter.LeftMenuAdapter;
import com.wuxi.app.fragment.MainMineFragment;
import com.wuxi.app.fragment.homepage.SlideLevelFragment;
import com.wuxi.app.util.CacheUtil;
import com.wuxi.app.util.Constants;
import com.wuxi.app.view.SlideMenuLayout;
import com.wuxi.domain.MenuItem;

/**
 * 
 * @author wanglu 泰得利通 具有滑动菜单的父类
 * 
 */
public abstract class HomeBaseSlideLevelFragment extends BaseFragment implements
		OnClickListener, OnItemClickListener {

	@Override
	public void onItemClick(AdapterView<?> parent, View arg1, int position,
			long arg3) {

		if (position == this.position)
			return;
		MenuItem checkMenuItem = (MenuItem) parent.getItemAtPosition(position);
		if (checkMenuItem.getName().equals("政民互动")) {// 为回退特殊处理

			MainMineFragment mainMineFragment = new MainMineFragment();
			mainMineFragment.setMenuItem(checkMenuItem);
			mainMineFragment.setMenuItem(checkMenuItem);//
			managers.IntentFragment(mainMineFragment);
			mainMineFragment.setPosition(position);
		} else {

			SlideLevelFragment saveFragment = new SlideLevelFragment();
			saveFragment.setPosition(position);
			saveFragment.setMenuItem(checkMenuItem);//
			managers.IntentFragment(saveFragment);

		}

	}

	protected View view;
	protected LayoutInflater inflater;
	protected Context context;
	protected SlideMenuLayout mSlideMenuLayout;
	protected ListView mlvMenu;// 左侧菜单列表
	protected static final String MENUITEM_CACKE_KEY = Constants.CacheKey.HOME_MENUITEM_KEY;
	private ImageView oPearn_btn, member_btnm, back_btn;
	private TextView tv_title;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(getLayoutId(), null);
		this.inflater = inflater;
		context = getActivity();
		initUI();
		initLeftMenu();// 初始化左侧菜单数据

		return view;
	}

	/**
	 * 
	 * wanglu 泰得利通 返回布局Id 子类实现
	 * 
	 * @return
	 */
	protected abstract int getLayoutId();

	protected void initUI() {
		mSlideMenuLayout = (SlideMenuLayout) view
				.findViewById(R.id.slide_menu_layout);

		mSlideMenuLayout.setLeftSlideMenuId(R.id.left_menu);
		mSlideMenuLayout.setRightSlideMenuId(R.id.right_menu);
		mSlideMenuLayout.reset();

		mlvMenu = (ListView) view.findViewById(R.id.lv_menu);

		oPearn_btn = (ImageView) view.findViewById(R.id.open_close_left_btn);
		member_btnm = (ImageView) view.findViewById(R.id.member_btn);
		tv_title = (TextView) view.findViewById(R.id.Title_Text);
		back_btn = (ImageView) view.findViewById(R.id.back_btn);

		oPearn_btn.setOnClickListener(this);
		member_btnm.setOnClickListener(this);
		back_btn.setOnClickListener(this);
		tv_title.setText(getTtitle());
		mlvMenu.setOnItemClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.open_close_left_btn:
			opearnLeft();
			break;
		case R.id.member_btn:
			opearnRight();
			break;
		case R.id.back_btn:
			onBack();
			break;

		}
	}

	/**
	 * 
	 * wanglu 泰得利通 返回按钮点击处理 子类实现
	 */
	protected abstract void onBack();

	/**
	 * 
	 * wanglu 泰得利通 回返标题名 子类实现
	 * 
	 * @return
	 */
	protected abstract String getTtitle();

	@SuppressWarnings("unchecked")
	private void initLeftMenu() {

		List<MenuItem> leftMenuItems = (List<MenuItem>) CacheUtil
				.get(MENUITEM_CACKE_KEY);// 直接从缓存中取出菜单

		mlvMenu.setAdapter(new LeftMenuAdapter(getActivity(),
				R.layout.slide_navigator_item, new int[] {
						R.id.tv_left_menu_name, R.id.left_iv_icon },
				leftMenuItems, null, position));

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
}
