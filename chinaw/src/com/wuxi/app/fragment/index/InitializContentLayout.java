package com.wuxi.app.fragment.index;

import com.wuxi.app.fragment.CityMapFragment;
import com.wuxi.app.fragment.NavigatorWithContentFragment;
import com.wuxi.domain.Channel;
import com.wuxi.domain.MenuItem;

/**
 * 根据MenuItem、Channel等消息类型的name(之后为APPUI)来set Fragment
 * @author 杨宸 智佳
 * */
public class InitializContentLayout {
	public static void initMenuItemContentLayout(MenuItem menuItem){
//		if(menuItem.getName().equals("最新公开信息"))
		  	menuItem.setContentFragment(NavigatorWithContentFragment.class);
	}
	
	public static void initChannelContentLayout(Channel channel){
		if (channel.getChannelName().equals("城市地图")) {//处理城市题图显示的视图
			channel.setContentFragment(CityMapFragment.class);
		} else {
			channel.setContentFragment(NavigatorWithContentFragment.class);
		}
	}
}
