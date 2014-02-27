package com.wuxi.app.util;

import android.util.Log;

/**
 * log 统一管理类
 * 
 * @author wanglu
 * 
 */
public class LogUtil {

	private static final int LOG_LEVEL = 1;
	private static final int LEVEL = 0;

	/**
	 * 输出信息
	 * 
	 * @param tag
	 * @param msg
	 */
	public static void i(String tag, String msg) {

		if (log()) {
			Log.i(tag, msg);

		}

	}

	/**
	 * 输出错误信息
	 * 
	 * @param tag
	 * @param msg
	 */
	public static void e(String tag, String msg) {
		if (log()) {
			Log.e(tag, msg);
		}
	}

	/**
	 * 输出调试信息
	 * 
	 * @param tag
	 * @param msg
	 */
	public static void d(String tag, String msg) {
		if (log()) {
			Log.d(tag, msg);
		}
	}

	/**
	 * 是否打印log 根据定义级别判断
	 * 
	 * @return
	 */

	private static boolean log() {
		return LOG_LEVEL > LEVEL;

	}
}
