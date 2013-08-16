package com.wuxi.app.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.wuxi.domain.MenuItem;

/**
 * 缓存数据
 * 
 * @author wanglu
 * 
 */
public class CacheUtil {

	private static Map<String, Object> mapCache = new HashMap<String, Object>();

	public static void put(String key, Object object) {
		mapCache.put(key, object);
	}

	public static Object get(String key) {
		return mapCache.get(key);
	}
	
	
	public static MenuItem getMenuItem(int postion){
		@SuppressWarnings("unchecked")
		List<MenuItem> menItems=(List<MenuItem>) mapCache.get(Constants.CacheKey.HOME_MENUITEM_KEY);
		return menItems.get(postion);
	}
}
