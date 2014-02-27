/**   
 * @公司: 重庆智佳信息科技有限公司
 * @文件: VideoSubmitIdeaService.java 
 * @包名： com.wuxi.app.engine 
 * @描述: 走进直播间 栏目首页 提交说说或留言 
 * @作者： 罗森   
 * @创建时间： 2013 2013-9-24 下午3:11:44
 * @修改时间：  
 * @修改描述：
 * @版本： V1.0   
 */
package com.wuxi.app.engine;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

import com.wuxi.app.util.Constants;
import com.wuxi.exception.NetException;

/**
 * @类名： VideoSubmitIdeaService
 * @描述： 走进直播间 栏目首页 提交说说或留言
 * @作者： 罗森
 * @创建时间： 2013 2013-9-24 下午3:11:44
 * @修改时间：
 * @修改描述：
 */
public class VideoSubmitIdeaService extends Service {

	/**
	 * @方法： VideoSubmitIdeaService
	 * @描述： 构造方法
	 * @param context
	 */
	public VideoSubmitIdeaService(Context context) {
		super(context);
	}

	/**
	 * @方法： submitIdea
	 * @描述： 提交说说或留言
	 * @param url
	 * @throws NetException
	 * @throws JSONException
	 */
	public void submitIdea(String url) throws NetException, JSONException {
		// 检查网络连接
		if (!checkNet()) {
			throw new NetException(Constants.ExceptionMessage.NO_NET);
		}
		
		String resultStr = httpUtils.executeGetToString(url, TIME_OUT);
		
		if (resultStr != null) {
			JSONObject jsonObject = new JSONObject(resultStr);
		} 
	}

}
