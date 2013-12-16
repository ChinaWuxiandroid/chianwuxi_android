package com.wuxi.exception;

/**
 * 
 * @author wanglu 泰得利通 自定义没有得到返回数据异常
 * 
 */
public class NODataException extends Exception {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	public NODataException(String msg) {

		super(msg);
	}
}
