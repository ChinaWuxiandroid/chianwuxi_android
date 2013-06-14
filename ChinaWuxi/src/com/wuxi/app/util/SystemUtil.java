package com.wuxi.app.util;

import android.app.Activity;
import android.content.Context;
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

}
