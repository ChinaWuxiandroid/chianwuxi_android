package com.wuxi.app.engine;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

import com.wuxi.app.util.Constants;
import com.wuxi.exception.NetException;


/**
 * 提交自定义列表 业务类
 * @author 杨宸 智佳
 * */

public class SubmitListService extends Service{

	public SubmitListService(Context context) {
		super(context);
	}

	public boolean submitByUrl(String url) throws JSONException, NetException{
		if (!checkNet()) {
			throw new NetException(Constants.ExceptionMessage.NO_NET); // 检查网络
		}
		String resultStr = httpUtils.executeGetToString(url, TIME_OUT);

		if (resultStr != null) {
			JSONObject jsonObject = new JSONObject(resultStr);

			return  jsonObject.getBoolean("success");
		} else {
			return false;
		}
	}
}
