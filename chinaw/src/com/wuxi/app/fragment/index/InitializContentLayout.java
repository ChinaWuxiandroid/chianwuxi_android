package com.wuxi.app.fragment.index;

import java.util.List;

import android.content.Context;

import com.wuxi.app.BaseFragment;
import com.wuxi.app.engine.ChannelService;
import com.wuxi.app.fragment.commonfragment.NavigatorWithContentFragment;
import com.wuxi.app.fragment.homepage.fantasticwuxi.CityMapFragment;
import com.wuxi.app.fragment.homepage.goverpublicmsg.GoverMsgContentListFragment;
import com.wuxi.app.fragment.homepage.goverpublicmsg.GoverMsgFragmentWebFragment;
import com.wuxi.app.fragment.homepage.goverpublicmsg.GoverMsgNaviWithContentFragment;
import com.wuxi.app.fragment.homepage.goverpublicmsg.GoverMsgSearchContentListFragment;
import com.wuxi.app.fragment.homepage.goverpublicmsg.GoverMsgWebFragment;
import com.wuxi.app.fragment.homepage.goverpublicmsg.WorkSuggestionBoxFragment;
import com.wuxi.app.fragment.homepage.informationcenter.InfoNavigatorWithContentFragment;
import com.wuxi.app.fragment.homepage.informationcenter.InforContentListFragment;
import com.wuxi.app.fragment.homepage.informationcenter.WapFragment;
import com.wuxi.app.fragment.homepage.publicservice.PublicServiceWithContentFragment;
import com.wuxi.domain.Channel;
import com.wuxi.domain.MenuItem;
import com.wuxi.exception.NetException;

/**
 * 根据MenuItem、Channel等消息类型的name(之后为APPUI)来set Fragment
 * 
 * @author 杨宸 智佳
 * */
public class InitializContentLayout {
	/**
	 * 
	 * wanglu 泰得利通
	 * 
	 * @param menuItem
	 *            首页导航模块菜单
	 * @param subMenuItems
	 *            导航菜单子菜单
	 * @param context
	 */
	public static void initMenuItemContentLayout(MenuItem menuItem,
			List<MenuItem> subMenuItems, final Context context) {

		if (menuItem.getName().equals("资讯中心")) {

			for (final MenuItem menu : subMenuItems) {

				if (menu.getType() == MenuItem.WAP_MENU) {// wap类型菜单
					menu.setContentFragment(WapFragment.class);
				} else if (menu.getType() == MenuItem.CHANNEL_MENU) {// 如果菜单上频道菜单

					new Thread(new Runnable() {

						@Override
						public void run() {
							ChannelService channelService = new ChannelService(
									context);
							try {
								List<Channel> channels = channelService
										.getSubChannels(menu.getChannelId());

								if (channels != null) {
									menu.setContentFragment(InfoNavigatorWithContentFragment.class);
								} else {
									menu.setContentFragment(InforContentListFragment.class);// 内容列表界面
								}
							} catch (NetException e) {
								e.printStackTrace();
							}
						}
					}).start();

				} else if (menu.getType() == MenuItem.CUSTOM_MENU) {// //普通菜单
					menu.setContentFragment(InfoNavigatorWithContentFragment.class);
				} else if (menu.getType() == MenuItem.LINK_MENU) {// 链接菜单
					menu.setContentFragment(BaseFragment.class);
				}
			}

		} else if (menuItem.getName().equals("公共服务")) {

			for (final MenuItem menu : subMenuItems) {

				if (menu.getType() == MenuItem.WAP_MENU) {// wap类型菜单

					// menu.setContentFragment(WapFragment.class);
				} else if (menu.getType() == MenuItem.CHANNEL_MENU) {// 如果菜单上频道菜单

					new Thread(new Runnable() {

						@Override
						public void run() {
							ChannelService channelService = new ChannelService(
									context);
							try {
								List<Channel> channels = channelService
										.getSubChannels(menu.getChannelId());

								if (channels != null) {
									menu.setContentFragment(PublicServiceWithContentFragment.class);
								} else {
									// menu.setContentFragment(ContentListFragment.class);//
									// 内容列表界面
								}
							} catch (NetException e) {
								e.printStackTrace();
							}
						}
					}).start();

				} else if (menu.getType() == MenuItem.CUSTOM_MENU) {// //普通菜单
					menu.setContentFragment(PublicServiceWithContentFragment.class);
				}

			}

		} else if (menuItem.getName().equals("政府信息公开")) {

			for (final MenuItem menu : subMenuItems) {

				// 普通菜单
				if (menu.getType() == MenuItem.CUSTOM_MENU) {
					menu.setContentFragment(GoverMsgNaviWithContentFragment.class);
				}
				// 如果菜单上频道菜单
				else if (menu.getType() == MenuItem.CHANNEL_MENU) {
					new Thread(new Runnable() {
						@Override
						public void run() {
							ChannelService channelService = new ChannelService(
									context);
							try {
								List<Channel> channels = channelService
										.getSubChannels(menu.getChannelId());

								if (channels != null) {
									menu.setContentFragment(GoverMsgNaviWithContentFragment.class);
								} else {
									if (menu.getName().equals("信息公开动态")) {
										menu.setContentFragment(GoverMsgSearchContentListFragment.class);// 内容列表界面
									} else {
										menu.setContentFragment(GoverMsgContentListFragment.class);// 内容列表界面
									}
								}
							} catch (NetException e) {
								e.printStackTrace();
							}
						}
					}).start();

				}
				// 定制菜单
				else if (menu.getType() == MenuItem.APP_MENU) {
					// 目前就工作意见箱一个定制菜单
					if (menu.getName().endsWith("工作意见箱")) {
						menu.setContentFragment(WorkSuggestionBoxFragment.class);
					}
				}
				// wap类型菜单
				else if (menu.getType() == MenuItem.WAP_MENU) {

					menu.setContentFragment(GoverMsgWebFragment.class);

				}
				// 碎片类型菜单
				else if (menu.getType() == MenuItem.FRAGMENT_MENU) {
					menu.setContentFragment(GoverMsgFragmentWebFragment.class);
				}
			}

		}

	}

	public static void initChannelContentLayout(Channel channel) {
		if (channel.getChannelName().equals("城市地图")) {// 处理城市题图显示的视图
			channel.setContentFragment(CityMapFragment.class);
		} else {
			channel.setContentFragment(NavigatorWithContentFragment.class);
		}
	}
}
