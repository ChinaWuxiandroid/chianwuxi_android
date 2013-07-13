package com.wuxi.app.util;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.annotation.SuppressLint;

/**
 * 
 * @author wanglu 泰得利通 时间处理类
 */
public class TimeFormateUtil {

	public static final String DATE_PATTERN = "yyyy-MM-dd";

	/**
	 * 
	 * wanglu 泰得利通
	 * 
	 * @param time
	 * @param pattern
	 * @return
	 */

	@SuppressLint("SimpleDateFormat")
	public static String formateTime(String time, String pattern) {

		Date date = new Date(Long.parseLong(time));
		SimpleDateFormat formater = new SimpleDateFormat(pattern);
		return formater.format(date);

	}

}
