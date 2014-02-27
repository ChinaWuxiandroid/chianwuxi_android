package com.wuxi.app.util;

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
	 * @param str
	 * @return
	 */
	public static String encode(String str) {

		try {

			MessageDigest digest = MessageDigest.getInstance("MD5");
			byte[] enpwd = digest.digest(str.getBytes());
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < enpwd.length; i++) {

				String code = Integer.toHexString(0xff & enpwd[i]);

				if (code.length() == 1) {
					sb.append("0" + code);
				} else {
					sb.append(code);
				}

			}
			
			return sb.toString();
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/*
	 * 获取登录密码MD5编码
	 */
	public static String getLoginPWDMD5(String str, String encoding) throws Exception {
	    MessageDigest md = MessageDigest.getInstance("MD5");
	    md.update(str.getBytes(encoding));
	    byte[] result = md.digest();
	    StringBuffer sb = new StringBuffer(32);
	    for (int i = 0; i < result.length; i++) {
	        int val = result[i] & 0xff;
	        if (val < 0xf) {
	           sb.append("0");
	        }
	       sb.append(Integer.toHexString(val)).append("-");
	    }
	    String md5= sb.toString().toUpperCase();
	    return md5.substring(0,md5.length()-1);
	}
	
}
