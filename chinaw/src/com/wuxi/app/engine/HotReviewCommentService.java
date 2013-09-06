/**   
 * @公司: 重庆智佳信息科技有限公司
 * @文件: HotReviewCommentService.java 
 * @包名： com.wuxi.app.engine 
 * @描述:  热点话题 话题评价或回复 业务类
 * @作者： 罗森   
 * @创建时间： 2013 2013-8-28 下午3:11:30
 * @修改时间：  
 * @修改描述：
 * @版本： V1.0   
 */
package com.wuxi.app.engine;

import org.json.JSONException;
import org.json.JSONObject;

import com.wuxi.app.util.Constants;
import com.wuxi.exception.NetException;

import android.content.Context;

/**
 * @类名： HotReviewCommentService
 * @描述： 热点话题 话题评价或回复 业务类
 * @作者： 罗森
 * @创建时间： 2013 2013-8-28 下午3:11:30
 * @修改时间：
 * @修改描述：
 * 
 */
public class HotReviewCommentService extends Service {

	/**
	 * @方法： HotReviewCommentService
	 * @描述： 构造方法
	 * @param context
	 */
	public HotReviewCommentService(Context context) {
		super(context);
	}

	/**
	 * @方法： submitComment
	 * @描述： 提交热点话题评论
	 * @param id
	 * @param content
	 * @param access_token
	 * @throws NetException
	 * @throws JSONException
	 */
	public void submitComment(String id, String content, String access_token)
			throws NetException, JSONException {
		// 检查网络连接
		if (!checkNet()) {
			throw new NetException(Constants.ExceptionMessage.NO_NET);
		}

		String url = Constants.Urls.HOT_REVIEW_COMMENT_URL.replace("{id}", id)
				+ "?content=" + content + "&access_token=" + access_token;

		String resultStr = httpUtils.executeGetToString(url, TIME_OUT);

		if (resultStr != null) {
			JSONObject jsonObject = new JSONObject(resultStr);
		}
	}
}
