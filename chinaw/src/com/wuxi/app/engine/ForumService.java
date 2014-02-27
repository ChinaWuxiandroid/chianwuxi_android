/**
 * 
 */
package com.wuxi.app.engine;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

import com.wuxi.app.util.Constants;
import com.wuxi.app.util.TimeFormateUtil;
import com.wuxi.domain.ForumWrapper;
import com.wuxi.exception.NODataException;
import com.wuxi.exception.NetException;

/**
 * 公共论坛业务类
 * 
 * @author 智佳 罗森
 * 
 */
public class ForumService extends Service {

	public ForumService(Context context) {
		super(context);
	}

	/**
	 * 获取公共论坛数据
	 * 
	 * @param url
	 * @param startIndex
	 * @param endIndex
	 * @return
	 * @throws NetException
	 * @throws JSONException
	 * @throws NODataException
	 */
	public ForumWrapper getForumWrapper(String url, int startIndex, int endIndex)
			throws NetException, JSONException, NODataException {
		// 检查网络连接状态
		if (!checkNet()) {
			throw new NetException(Constants.ExceptionMessage.NO_NET);
		}
		// 构建数据加载url
		url = url + "?start=" + startIndex + "&end=" + endIndex;
		// 提交请求并返回结果
		String resultStr = httpUtils.executeGetToString(url, TIME_OUT);
		if (resultStr != null) {
			JSONObject jsonObject = new JSONObject(resultStr);
			JSONObject jresult = jsonObject.getJSONObject("result");

			ForumWrapper forumWrapper = new ForumWrapper();
			forumWrapper.setEnd(jresult.getInt("end"));
			forumWrapper.setStart(jresult.getInt("start"));
			forumWrapper.setNext(jresult.getBoolean("next"));
			forumWrapper.setPrevious(jresult.getBoolean("previous"));
			forumWrapper.setTotalRowsAmount(jresult.getInt("totalRowsAmount"));
			JSONArray jData = jresult.getJSONArray("data");
			if (jData != null) {
				// 解析数组
				forumWrapper.setForums(parseData(jData));
			}

			return forumWrapper;
		} else {
			// 没有获取到数据异常
			throw new NODataException(Constants.ExceptionMessage.NODATA_MEG);
		}
	}

	/**
	 * 解析数据
	 * 
	 * @param jData
	 * @param start
	 * @param end
	 * @return
	 * @throws JSONException
	 */
	private List<ForumWrapper.Forum> parseData(JSONArray jData) throws JSONException {
		if (jData != null) {
			List<ForumWrapper.Forum> forums = new ArrayList<ForumWrapper.Forum>();

			for (int i = 0; i < jData.length(); i++) {
				JSONObject jb = jData.getJSONObject(i);

				ForumWrapper forumWrapper = new ForumWrapper();
				ForumWrapper.Forum forum = forumWrapper.new Forum();

				forum.setId(jb.getString("politicsMainID"));
				forum.setTitle(jb.getString("title"));
				forum.setReadCount(jb.getString("readCount"));
				forum.setTop(jb.getString("top"));
				forum.setSentUser(jb.getString("sentUser"));
				forum.setDoProjectID(jb.getString("doProjectID"));
				forum.setDataNo(jb.getString("dataNo"));

				if (!jb.isNull("begintime")) {
					forum.setBeginTime(TimeFormateUtil.formateTime(
							String.valueOf(jb.getLong("begintime")),
							TimeFormateUtil.DATE_PATTERN));
				}

				forum.setViewpath(jb.getString("viewpath"));
				forum.setResultCount(jb.getString("resultCount"));

				forums.add(forum);
			}
			return forums;
		}
		return null;
	}

}
