package com.wuxi.app.fragment.homepage.goverpublicmsg;


import java.util.List;

import com.wuxi.app.R;
import com.wuxi.app.engine.ChannelService;
import com.wuxi.app.fragment.commonfragment.MenuItemMainFragment;
import com.wuxi.app.fragment.index.InitializContentLayout;
import com.wuxi.app.listeners.GoverPublicMsgInitLayoutImpl;
import com.wuxi.app.listeners.MenuItemInitLayoutListener;
import com.wuxi.domain.Channel;
import com.wuxi.domain.MenuItem;
import com.wuxi.exception.NetException;

/**
 * 政府公开信息Fragment
 * @author 杨宸 智佳
 * */

public class PublicGoverMsgFragment extends MenuItemMainFragment{

	@Override
	public void initializSubFragmentsLayout(List<MenuItem> items) {
		// TODO Auto-generated method stub
		InitializContentLayout.initMenuItemContentLayout(menuItem, items, context);
	}


	@Override
	protected int getTitlePerScreenItemCount() {
		return 4;
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
