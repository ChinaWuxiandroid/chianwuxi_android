package com.wuxi.app.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import android.os.Environment;

import com.wuxi.domain.MenuItem;

/**
 * 缓存数据
 * 
 * @author wanglu
 * 
 */
public class CacheUtil {

	private static Map<String, Object> mapCache = new HashMap<String, Object>();
	
	
	private static CacheUtil instance;// 缓存

	private CacheUtil() {
	}

	public synchronized static CacheUtil getInstance() {

		if (instance == null) {
			instance = new CacheUtil();
			
		}
		return instance;

	}

	public static void put(String key, Object object) {
		mapCache.put(key, object);
	}

	public static Object get(String key) {
		return mapCache.get(key);
	}
	
	

	/**
	 * 
	 * wanglu 泰得利通
	 * 
	 * @param postion
	 * @return
	 */
	public static MenuItem getHomeMenuItem(int postion) {
		@SuppressWarnings("unchecked")
		List<MenuItem> menItems = (List<MenuItem>) mapCache
				.get(Constants.CacheKey.MAIN_MENUITEM_KEY);
		return menItems.get(postion);
	}

	/**
	 * 
	 * wanglu 泰得利通
	 * 
	 * @param url
	 *            访问地址
	 * @param text
	 *            缓存内容
	 */
	public void cacheFile(final String url, final String content) {

		new Thread(new Runnable() {

			@Override
			public void run() {
				if (Environment.getExternalStorageState().equals(
						Environment.MEDIA_MOUNTED)) {

					File file = new File(Constants.APPFiles.CACHE_FILE_PATH);
					if (!file.exists()) {
						file.mkdirs();// 建立目录
					}

					String fileName = MD5Encoder.encode(url);// 文件名

					File cacheFile = new File(file, fileName);

					try {

						FileWriter fw = new FileWriter(cacheFile);
						fw.write(content);
						fw.flush();
						fw.close();

					} catch (FileNotFoundException e) {

						e.printStackTrace();
					} catch (IOException e) {

						e.printStackTrace();
					}

				}

			}
		}).start();

	}

	/**
	 * 
	 * wanglu 泰得利通 判断是否有缓存文件存在
	 * 
	 * @param url
	 * @return
	 */
	public boolean isHasCacheFile(String url) {

		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			String fileName = MD5Encoder.encode(url);// 文件名
			File file = new File(Constants.APPFiles.CACHE_FILE_PATH + fileName);
			return file.exists();

		}

		return false;

	}
	
	
	/**
	 * 
	 *wanglu 泰得利通 
	 *读取缓存文件 
	 * @param url
	 * @return
	 */
	public String getCacheStr(String url){
		String fileName = MD5Encoder.encode(url);// 文件名
		File file = new File(Constants.APPFiles.CACHE_FILE_PATH + fileName);
		
		try {
			BufferedReader input = new BufferedReader (new FileReader(file));
			StringBuffer sb = new StringBuffer();  
			String text;
			while((text = input.readLine()) != null)  {
				  sb.append(text);  
			}
			
			input.close();
			return sb.toString();
			
		} catch (FileNotFoundException e) {
			
			e.printStackTrace();
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		
		return null;
	}

}
