package com.wuxi.exception;

/**
 * 
 * @author wanglu 泰得利通
 * 网络连接异常
 *
 */
public class NetException extends Exception {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	public NetException (String msg){
		
		super(msg);
	}
}
