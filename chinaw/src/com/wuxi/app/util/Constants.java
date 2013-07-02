package com.wuxi.app.util;

import android.os.Environment;

/**
 * 常量类
 * 
 * @author wanglu
 * 
 */
public class Constants {

	/**
	 * API地址
	 * 
	 * @author wanglu
	 * 
	 */
	public static final class Urls {
		/**
		 * 菜单获取URL
		 * 
		 * recursions false Int 返回层次,不输入则为2,为0则返回所有
		 */
		public static final String MENU_URL = "http://3g.wuxi.gov.cn/api/menu/tree.json";

		/**
		 * 获取子频道URL {channelId}频道ID占位符
		 */
		public static final String CHANNEL_URL = "http://3g.wuxi.gov.cn/api/channel/{channelId}/channels.json";

	}
	
	
	/**
	 * 
	 * @author wanglu 泰得利通
	 * 存储地址
	 *
	 */
	public static final class APPFiles{
		
		/**
		 * 项目外部文件存储路径
		 */
		public static final String APP_PATH=Environment.getExternalStorageDirectory().getPath()+"/chinawi/";
		
		/**
		 * 项目菜单图标存储路径
		 */
		public static final String MENU_ICON_PATH=APP_PATH+"menu/icon/";
		
	}

}
