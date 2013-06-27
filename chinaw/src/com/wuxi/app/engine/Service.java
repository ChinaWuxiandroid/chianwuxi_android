package com.wuxi.app.engine;

import com.wuxi.app.net.HttpUtils;
import com.wuxi.app.net.NetworkUtil;

import android.content.Context;

/**
 * 
 * @author wanglu 泰得利通 业务父类
 * 
 */
public class Service {

	protected Context context;
	protected NetworkUtil networkUtil = NetworkUtil.getInstance();
	protected HttpUtils httpUtils = HttpUtils.getInstance();
	protected static final  String NET_ERROR="请检查网络连接";
	protected static final String DATA_FORMATE_ERROR="数据格式有误";

	public Service(Context context) {
		this.context = context;

	}

	/**
	 * 
	 * wanglu 泰得利通 检查网络
	 * 
	 * @return true网络可用 false网络不可用
	 */
	protected boolean checkNet() {
		return networkUtil.checkInternet(context);
	}
}
