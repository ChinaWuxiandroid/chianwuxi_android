package com.wuxi.app.util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import android.annotation.SuppressLint;

/**
 * 
 * @author wanglu 泰得利通 时间处理类
 */
public class TimeFormateUtil {

	public static final String DATE_PATTERN = "yyyy-MM-dd";
	public static final int START_YEAR = 2000;

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

	public static List<String> getYears(int startYear) {

		Calendar c = Calendar.getInstance();
		int nowYear = c.get(Calendar.YEAR);

		List<String> years = new ArrayList<String>();
		for (int i = startYear; i <= nowYear; i++) {
			years.add(i + "");
		}
		
		return years;

	}
}
