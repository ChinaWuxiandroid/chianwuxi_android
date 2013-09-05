package com.wuxi.app.activity.homepage.goverpublicmsg;


import java.util.List;

import com.wuxi.app.R;
import com.wuxi.app.activity.commactivity.MenuItemMainActivity;
import com.wuxi.app.fragment.index.InitializContentLayout;
import com.wuxi.app.listeners.GoverPublicMsgInitLayoutImpl;
import com.wuxi.app.listeners.MenuItemInitLayoutListener;
import com.wuxi.domain.MenuItem;

/**
 * 政府公开信息Fragment
 * @author 杨宸 智佳
 * */

public class PublicGoverMsgActivity extends MenuItemMainActivity{

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	public void initializSubFragmentsLayout(List<MenuItem> items) {
		InitializContentLayout.initMenuItemContentLayout(menuItem, items, this);
	}

	@Override
	protected int getTitlePerScreenItemCount() {
		return 3;
	}

	@Override
	protected MenuItemInitLayoutListener getMenuItemInitLayoutListener() {

		return new GoverPublicMsgInitLayoutImpl();
	}


	@Override
	protected int getLayoutId() {
		return R.layout.fragment_chanel_layout;
	}


	@Override
	protected String getTitleText() {
		return menuItem.getName();
	}

}
