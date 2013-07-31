package com.wuxi.app.fragment.homepage.goverpublicmsg;


import java.util.List;

import com.wuxi.app.R;
import com.wuxi.app.engine.ChannelService;
import com.wuxi.app.fragment.commonfragment.MenuItemMainFragment;
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


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	@Override
	public void initializSubFragmentsLayout(List<MenuItem> items) {
		// TODO Auto-generated method stub
		for (final MenuItem menu : items) {

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
//							System.out.println("menu>name:"+menu.getName()+"  id:"+menu.getId()+"   channelId:"+menu.getChannelId());
							List<Channel> channels = channelService
									.getSubChannels(menu.getChannelId());

							if (channels != null) {
								menu.setContentFragment(GoverMsgNaviWithContentFragment.class);
							} else {
								menu.setContentFragment(GoverMsgContentListFragment.class);// 内容列表界面
							}
						} catch (NetException e) {
							e.printStackTrace();
						}
					}
				}).start();

			} 
			//定制菜单
			else if(menu.getType() == MenuItem.APP_MENU){
				//目前就工作意见箱一个定制菜单
				if(menu.getName().endsWith("工作意见箱")){
					menu.setContentFragment(WorkSuggestionBoxFragment.class);
				}
			}
			// wap类型菜单
			else if (menu.getType() == MenuItem.WAP_MENU) {
				menu.setContentFragment(GoverMsgWebFragment.class);
			} 
		}
	}


	@Override
	protected int getTitlePerScreenItemCount() {
		return 5;
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
