package com.wuxi.app.fragment.homepage.publicservice;

import java.util.List;

import com.wuxi.app.engine.ChannelService;
import com.wuxi.app.fragment.commonfragment.MenuItemMainFragment;
import com.wuxi.app.listeners.MenuItemInitLayoutListener;
import com.wuxi.app.listeners.PublicServiceInitLayoutImpl;
import com.wuxi.domain.Channel;
import com.wuxi.domain.MenuItem;
import com.wuxi.exception.NetException;

/**
 * 
 * @author wanglu 泰得利通 公共服务
 * 
 */
public class PublicServiceFragment extends MenuItemMainFragment {

	@Override
	public void initializSubFragmentsLayout(List<MenuItem> items) {

		for (final MenuItem menu : items) {

			if (menu.getType() == MenuItem.WAP_MENU) {// wap类型菜单

				//menu.setContentFragment(WapFragment.class);
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
								//menu.setContentFragment(ContentListFragment.class);// 内容列表界面
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
	}

	@Override
	protected MenuItemInitLayoutListener getMenuItemInitLayoutListener() {

		return new PublicServiceInitLayoutImpl();
	}

	@Override
	protected int getTitlePerScreenItemCount() {

		return 4;
	}

}
