/**   
 * @公司: 重庆智佳信息科技有限公司
 * @文件: LegislationCommentService.java 
 * @包名： com.wuxi.app.engine 
 * @描述: 提交立法征求意见和民意征集回复数据
 * @作者： 罗森   
 * @创建时间： 2013 2013-9-13 上午11:13:23
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
 * @类名： LegislationCommentService
 * @描述： 提交立法征求意见和民意征集回复数据业务类
 * @作者： 罗森
 * @创建时间： 2013 2013-9-13 上午11:13:23
 * @修改时间：
 * @修改描述：
 */
public class LegislationCommentService extends Service {

	public LegislationCommentService(Context context) {
		super(context);
	}

	/**
	 * @方法： submitData
	 * @描述： 提交回复数据
	 * @param access_token
	 * @param mainid
	 * @param title
	 * @param content
	 * @return
	 * @throws NetException
	 * @throws JSONException
	 */
	public boolean submitData(String access_token, String mainid, String content)
			throws NetException, JSONException {
		if (!checkNet()) {
			throw new NetException(Constants.ExceptionMessage.NO_NET);
		}

		String url = Constants.Urls.LEGISLATION_SUBMIT_DATA_URL
				+ "?access_token=" + access_token + "&mainid=" + mainid
				+ "&content=" + content;

		System.out.println("回复："+url);
		
		String resultStr = httpUtils.executeGetToString(url, TIME_OUT);

		if (resultStr != null) {
			JSONObject jsonObject = new JSONObject(resultStr);
			return jsonObject.getBoolean("success");
		} else {
			return false;
		}
	}

}
