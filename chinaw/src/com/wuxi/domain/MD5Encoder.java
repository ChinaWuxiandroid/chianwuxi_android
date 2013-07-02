package com.wuxi.domain;

import java.security.MessageDigest;

/**
 * 
 * @author wanglu 泰得利通
 * MD5加密工具
 *
 */
public class MD5Encoder {

	/**
	 * 
	 *wanglu 泰得利通 
	 * @param pwd
	 * @return
	 */
	public static String encode(String pwd) {

		try {

			MessageDigest digest = MessageDigest.getInstance("MD5");
			byte[] enpwd = digest.digest(pwd.getBytes());
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < enpwd.length; i++) {

				String str = Integer.toHexString(0xff & enpwd[i]);

				if (str.length() == 1) {
					sb.append("0" + str);
				} else {
					sb.append(str);
				}

			}
			
			return sb.toString();
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
