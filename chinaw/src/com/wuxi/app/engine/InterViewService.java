package com.wuxi.app.engine;

import org.json.JSONException;
import org.json.JSONObject;

import com.wuxi.app.util.Constants;
import com.wuxi.exception.NetException;

import android.content.Context;

/**
 * 
 * @author wanglu 泰得利通 视频业务类
 * 
 */
public class InterViewService extends Service {

	public InterViewService(Context context) {
		super(context);

	}

	/**
	 * 
	 * wanglu 泰得利通 获取视频地址
	 * 
	 * @param id
	 * @return
	 * @throws NetException
	 * @throws JSONException
	 */

	public String getViewURL(String id) throws NetException, JSONException {

		if (!checkNet()) {
			throw new NetException(Constants.ExceptionMessage.NO_NET);
		}

		String url = Constants.Urls.PLAY_VIDEO_URL.replace("{id}", id);

		String resultStr = httpUtils.executeGetToString(url, TIME_OUT);
		
		if (resultStr != null) {

			JSONObject jsonObject = new JSONObject(resultStr);

			if (jsonObject.has("result")) {
				JSONObject jb=jsonObject.getJSONObject("result");
				if(jb.has("errorCode")){
					return null;
				}else{
					return jb.toString();
				}
			}
			if(jsonObject.getBoolean("success")){
				
				return jsonObject.getString("result");
			}else{
				return null;
			}
		}
		return null;
	}

}
