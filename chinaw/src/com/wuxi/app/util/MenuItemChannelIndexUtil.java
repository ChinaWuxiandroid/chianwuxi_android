/*
 * (#)MenuItemChannelIndexUtil.java 1.0 2013-9-2 2013-9-2 GMT+08:00
 */
package com.wuxi.app.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.wuxi.domain.Channel;
import com.wuxi.domain.MenuItem;

/**
 * @author wanglu 泰得利通
 * @version $1.0, 2013-9-2 2013-9-2 GMT+08:00
 * 
 *          菜单频道索引工具类
 * 
 */
public class MenuItemChannelIndexUtil {

	private static final MenuItemChannelIndexUtil instance = new MenuItemChannelIndexUtil();

	private static Map<String, Map<String, Integer>> indexMap = new HashMap<String, Map<String, Integer>>();

	private MenuItemChannelIndexUtil() {

	}

	public static MenuItemChannelIndexUtil getInstance() {
		return instance;
	}

	/**
	 * 
	 * @author 泰得利通 wanglu
	 * @param parentId
	 * @param subMenuItems
	 *            建立菜单索引
	 */
	public void addMenuItemIndex(String parentId, List<MenuItem> subMenuItems) {

		if (indexMap.containsKey(parentId)) {
			return;
		}
		Map<String, Integer> subIndex = new HashMap<String, Integer>();

		for (int index = 0; index < subMenuItems.size(); index++) {
			subIndex.put(subMenuItems.get(index).getId(), index);
			subIndex.put(subMenuItems.get(index).getName(), index);
		}
		indexMap.put(parentId, subIndex);

	}

	public void addChannelIndex(String parentId, List<Channel> channels) {

		if (indexMap.containsKey(parentId)) {
			return;
		}
		Map<String, Integer> subIndex = new HashMap<String, Integer>();

		for (int index = 0; index < channels.size(); index++) {
			subIndex.put(channels.get(index).getChannelId(), index);
			subIndex.put(channels.get(index).getChannelName(), index);
		}
		indexMap.put(parentId, subIndex);

	}

}
