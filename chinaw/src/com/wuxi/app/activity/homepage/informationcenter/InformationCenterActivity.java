package com.wuxi.app.activity.homepage.informationcenter;

import java.util.List;

import com.wuxi.app.R;
import com.wuxi.app.activity.commactivity.MenuItemMainActivity;
import com.wuxi.app.listeners.InfoCenterInitLayoutImpl;
import com.wuxi.app.listeners.MenuItemInitLayoutListener;
import com.wuxi.domain.MenuItem;

/**
 * 
 * @author wanglu 泰得利通 资讯中心
 * 
 */
public class InformationCenterActivity extends MenuItemMainActivity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void initializSubFragmentsLayout(List<MenuItem> items) {

		// for (final MenuItem menu : items) {
		//
		// if (menu.getType() == MenuItem.WAP_MENU) {// wap类型菜单
		// menu.setContentFragment(WapFragment.class);
		// } else if (menu.getType() == MenuItem.CHANNEL_MENU) {// 如果菜单上频道菜单
		//
		// new Thread(new Runnable() {
		//
		// @Override
		// public void run() {
		// ChannelService channelService = new ChannelService(
		// context);
		// try {
		// List<Channel> channels = channelService
		// .getSubChannels(menu.getChannelId());
		//
		// if (channels != null) {
		// menu.setContentFragment(InfoNavigatorWithContentFragment.class);
		// } else {
		// menu.setContentFragment(InforContentListFragment.class);// 内容列表界面
		// }
		// } catch (NetException e) {
		// e.printStackTrace();
		// }
		// }
		// }).start();
		//
		// } else if (menu.getType() == MenuItem.CUSTOM_MENU) {// //普通菜单
		// menu.setContentFragment(InfoNavigatorWithContentFragment.class);
		// }
		// }
	}

	@Override
	protected MenuItemInitLayoutListener getMenuItemInitLayoutListener() {

		return new InfoCenterInitLayoutImpl();
	}

	@Override
	protected int getTitlePerScreenItemCount() {

		return 4;
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
