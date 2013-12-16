package com.wuxi.app.util;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.DisplayMetrics;

import com.wuxi.app.db.AppUseCountDao;
import com.wuxi.app.db.DataBaseHelper;

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
		((Activity) context).getWindowManager().getDefaultDisplay()
				.getMetrics(dm);
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
		((Activity) context).getWindowManager().getDefaultDisplay()
				.getMetrics(dm);
		return dm.heightPixels;
	}

	/**
	 * 
	 * wanglu 泰得利通 注销登录
	 */
	public static void logout(Context context) {
		SharedPreferences sp = context
				.getSharedPreferences(
						Constants.SharepreferenceKey.SHARE_CONFIG,
						Context.MODE_PRIVATE);
		Editor editor = sp.edit();

		editor.putString(Constants.SharepreferenceKey.ACCESSTOKEN, "");
		editor.putString(Constants.SharepreferenceKey.REFRESHTOKEN, "");
		editor.putString(Constants.SharepreferenceKey.USERNAME, "");
		editor.commit();// tijiao
	}

	/**
	 * 
	 * wanglu 泰得利通 获取登录用户
	 * 
	 * @param context
	 * @return
	 */
	public static String getLoginUser(Context context) {

		SharedPreferences sp = context
				.getSharedPreferences(
						Constants.SharepreferenceKey.SHARE_CONFIG,
						Context.MODE_PRIVATE);
		return sp.getString(Constants.SharepreferenceKey.USERNAME, "");

	}

	/**
	 * 
	 * wanglu 泰得利通 获取accessToken
	 * 
	 * @param context
	 * @return
	 */
	public static String getAccessToken(Context context) {

		SharedPreferences sp = context
				.getSharedPreferences(
						Constants.SharepreferenceKey.SHARE_CONFIG,
						Context.MODE_PRIVATE);
		return sp.getString(Constants.SharepreferenceKey.ACCESSTOKEN, "");

	}

	/**
	 * 
	 * wanglu 泰得利通 获取用户使用APP次数
	 * 
	 * @param context
	 * @return
	 */
	@SuppressWarnings("unused")
	public static int getUserAppCount(Context context) {
		if(DataBaseHelper.VERSION>1){
			AppUseCountDao appUseCountDao = new AppUseCountDao(context);
			return appUseCountDao.getUseAppCount();
		}else{
			return  0;
		}
		

	}

}
