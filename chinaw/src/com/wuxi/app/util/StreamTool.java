package com.wuxi.app.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * 
 * @author wanglu 泰得利通 流帮助类
 * 
 */
public class StreamTool {

	public static String readInputStream(InputStream is) {

		BufferedReader br = new BufferedReader(new InputStreamReader(is));
		StringBuffer sb = new StringBuffer();
		String str;

		try {
			while ((str = br.readLine()) != null) {
				sb.append(str);
			}

			br.close();
			is.close();
			return str;

		} catch (IOException e) {

			e.printStackTrace();
		}

		return null;
	}
	
	
}
