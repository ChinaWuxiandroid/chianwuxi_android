/**
 * 
 */
package com.wuxi.app.engine;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

import com.wuxi.app.util.Constants;
import com.wuxi.exception.NetException;

/**
 * 论坛帖子评论业务类
 * 
 * @author 智佳 罗森
 * 
 */
public class ForumCommentService extends Service {

	/**
	 * 构造方法
	 * 
	 * @param context
	 */
	public ForumCommentService(Context context) {
		super(context);
	}

	/**
	 * 提交论坛帖子评论
	 * @param id
	 * @param access_token
	 * @param type
	 * @param content
	 * @return
	 * @throws NetException
	 * @throws JSONException
	 */
	public boolean submitComment(String id, String access_token, String type,
			String content) throws NetException, JSONException {

		if (!checkNet()) {
			throw new NetException(Constants.ExceptionMessage.NO_NET);
		}

		String url = Constants.Urls.FORUM_COMMENT_URL.replace("{id}", id)
				+ "?access_token=" + access_token + "&type=" + type
				+ "&content=" + content;

		String resultStr = httpUtils.executeGetToString(url, TIME_OUT);

		System.out.println(">>>>"+resultStr);
		
		if (resultStr != null) {
			JSONObject jsonObject = new JSONObject(resultStr);
			return jsonObject.getBoolean("success");
		} else {
			return false;
		}
	}

}
