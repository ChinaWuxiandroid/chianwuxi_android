package com.wuxi.app.util;

import java.util.HashMap;
import java.util.Map;

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
}
