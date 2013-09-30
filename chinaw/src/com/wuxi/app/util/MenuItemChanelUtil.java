/*
 * (#)MenuItemChanelUtil.java 1.0 2013-9-2 2013-9-2 GMT+08:00
 */
package com.wuxi.app.util;

import com.wuxi.app.activity.homepage.fantasticwuxi.ChannelActivity;
import com.wuxi.app.activity.homepage.goverpublicmsg.PublicGoverMsgActivity;
import com.wuxi.app.activity.homepage.goversaloon.GoverSaloonActivity;
import com.wuxi.app.activity.homepage.informationcenter.InformationCenterActivity;
import com.wuxi.app.activity.homepage.mygoverinteractpeople.MainMineActivity;
import com.wuxi.app.activity.homepage.publicservice.PublicServiceActivity;
import com.wuxi.domain.MenuItem;

/**
 * @author wanglu 泰得利通
 * @version $1.0, 2013-9-2 2013-9-2 GMT+08:00
 * 
 */
public class MenuItemChanelUtil {

	private static final String TAG = "MenuItemChanelUtil";

	/**
	 * 
	 * @author 泰得利通 wanglu
	 * @param menuItem
	 *            根据菜单名称获取Activity
	 * @return
	 */
	public static Class<?> getActivityClassByName(MenuItem menuItem) {

		if (menuItem.getType() != MenuItem.CHANNEL_MENU) {
			if (menuItem.getName().equals("资讯中心")) {

				return InformationCenterActivity.class;

			} else if (menuItem.getName().equals("政府信息公开")) {

				return PublicGoverMsgActivity.class;

			} else if (menuItem.getName().equals("公共服务")) {

				return PublicServiceActivity.class;

			} else if (menuItem.getName().equals("政务大厅")) {

				return GoverSaloonActivity.class;
			} else if (menuItem.getName().equals("政民互动")) {

				return MainMineActivity.class;
			}

		} else if (menuItem.getType() == MenuItem.CHANNEL_MENU) {// 频道类型菜单
			return ChannelActivity.class;
		}

		return null;
	}

	/**
	 * 
	 * wanglu 泰得利通 获取六大主菜单的索引位置
	 * 
	 * @param menuItem
	 * @return
	 */
	public static int getMainMenuItemIndex(MenuItem menuItem) {
		return MenuItemChannelIndexUtil.getInstance().getMenueChannelIndex(
				Constants.CacheKey.MAIN_MENUITEM_KEY, menuItem.getId());
	}

}
