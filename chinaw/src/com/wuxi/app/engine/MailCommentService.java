/**   
 * @公司: 重庆智佳信息科技有限公司
 * @文件: MailCommentService.java 
 * @包名： com.wuxi.app.engine 
 * @描述: 提交信件评价业务类
 * @作者： 罗森   
 * @创建时间： 2013 2013-8-28 下午1:37:43
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
 * @类名： MailCommentService
 * @描述： 提交信件评价业务类
 * @作者： 罗森
 * @创建时间： 2013 2013-8-28 下午1:37:43
 * @修改时间：
 * @修改描述：
 * 
 */
public class MailCommentService extends Service {

	/**
	 * @方法： MailCommentService
	 * @描述： 构造方法
	 * @param context
	 */
	public MailCommentService(Context context) {
		super(context);
	}

	/**
	 * @方法： submitMailComment
	 * @描述： 提交信件评论数据
	 * @param id
	 * @param rank
	 * @return boolean
	 * @throws NetException
	 * @throws JSONException
	 */
	public boolean submitMailComment(String id, int rank) throws NetException,
			JSONException {
		// 检查网络连接
		if (!checkNet()) {
			throw new NetException(Constants.ExceptionMessage.NO_NET);
		}

		String url = Constants.Urls.MAIL_COMMENT_SUBMIT_URL + "?id=" + id
				+ "&rank=" + rank;

		String resultStr = httpUtils.executeGetToString(url, TIME_OUT);

		if (resultStr != null) {
			JSONObject jsonObject = new JSONObject(resultStr);
			return jsonObject.getBoolean("success");
		} else {
			return false;
		}

	}

}
