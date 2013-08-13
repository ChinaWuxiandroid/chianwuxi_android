package com.wuxi.app.util;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.DisplayMetrics;

public class SystemUtil {

	public static SystemUtil systemUtil;

	public synchronized static SystemUtil getInstance() {
		if (systemUtil == null)
			systemUtil = new SystemUtil();
		return systemUtil;
	}

	/**
	 * 
	 * 获取屏幕的宽度
	 * 
	 * @param context
	 * @return
	 */
	public static int getWindowWidth(Context context) {
		DisplayMetrics dm = new DisplayMetrics();
		((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(dm);
		return dm.widthPixels;
	}

	/**
	 * 获取屏幕的高
	 * 
	 * @param context
	 * @return
	 */
	public static int getWindowHeight(Context context) {
		DisplayMetrics dm = new DisplayMetrics();
		((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(dm);
		return dm.heightPixels;
	}
	
	
	/**
	 * 
	 *wanglu 泰得利通 
	 *获取accessToken
	 * @param context
	 * @return
	 */
	public static String getAccessToken(Context context){
		
		SharedPreferences sp=context.getSharedPreferences(Constants.SharepreferenceKey.SHARE_CONFIG, Context.MODE_PRIVATE);
		return sp.getString(Constants.SharepreferenceKey.ACCESSTOKEN, "");
		
	}

}
