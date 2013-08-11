/**
 * 
 */
package com.wuxi.app.engine;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.wuxi.app.util.Constants;
import com.wuxi.app.util.TimeFormateUtil;
import com.wuxi.domain.HotPostWrapper;
import com.wuxi.domain.HotPostWrapper.HotPostReplyWrapper;
import com.wuxi.domain.HotPostWrapper.HotPostReplyWrapper.HotPostReply;
import com.wuxi.exception.NODataException;
import com.wuxi.exception.NetException;

import android.content.Context;

/**
 * 热点话题型帖子详情 业务类
 * 
 * @author 智佳 罗森
 * 
 */
public class HotPostService extends Service {

	public HotPostService(Context context) {
		super(context);
	}

	/**
	 * 解析热点话题型帖子数据集
	 * 
	 * @param politicsMainId
	 * @param type
	 * @param start
	 * @param end
	 * @return
	 * @throws NetException
	 * @throws JSONException
	 * @throws NODataException
	 */
	public HotPostWrapper getHotPostWrapper(String politicsMainId, String type,
			int start, int end) throws NetException, JSONException,
			NODataException {
		// 检查网络连接状态
		if (!checkNet()) {
			throw new NetException(Constants.ExceptionMessage.NO_NET);
		}

		String url = Constants.Urls.FORUM_CONTENT_URL.replace("{id}",
				politicsMainId)
				+ "?type="
				+ type
				+ "&replystart="
				+ start
				+ "&replyend=" + end;

		String resultStr = httpUtils.executeGetToStringGBK(url, 5000);

		if (resultStr != null) {
			JSONObject jsonObject = new JSONObject(resultStr);
			JSONObject jresult = jsonObject.getJSONObject("result");

			HotPostWrapper hotPostWrapper = new HotPostWrapper();

			hotPostWrapper.setId(jresult.getString("id"));
			hotPostWrapper.setContent(jresult.getString("content"));
			hotPostWrapper.setEndTime(TimeFormateUtil.formateTime(
					String.valueOf(jresult.getLong("endTime")),
					TimeFormateUtil.DATE_TIME_PATTERN));
			hotPostWrapper.setTitle(jresult.getString("title"));
			hotPostWrapper.setDepName(jresult.getString("depName"));
			hotPostWrapper.setCanReply(jresult.getString("canReply"));

			JSONObject jData = jresult.getJSONObject("replies");
			
			if (jData != null) {
				hotPostWrapper
						.setHotPostReplyWrapper(getHotPostReplyWrapper(jData));
			}

			return hotPostWrapper;
		}else {
			// 没有获取到数据异常
			throw new NODataException(Constants.ExceptionMessage.NODATA_MEG);
		}

	}

	/**
	 * 解析热点话题类帖子回复数据集
	 * 
	 * @param jsonObject
	 * @return
	 * @throws JSONException
	 */
	private HotPostReplyWrapper getHotPostReplyWrapper(JSONObject jsonObject)
			throws JSONException {
		if (jsonObject != null) {
			HotPostWrapper hotPostWrapper = new HotPostWrapper();
			HotPostReplyWrapper hotPostReplyWrapper = hotPostWrapper.new HotPostReplyWrapper();

			hotPostReplyWrapper.setEnd(jsonObject.getInt("end"));
			hotPostReplyWrapper.setStart(jsonObject.getInt("start"));
			hotPostReplyWrapper.setNext(jsonObject.getBoolean("next"));
			hotPostReplyWrapper.setPrevious(jsonObject.getBoolean("previous"));
			hotPostReplyWrapper.setTotalRowsAmount(jsonObject
					.getInt("totalRowsAmount"));
			JSONArray jData = jsonObject.getJSONArray("data");

			if (jData != null) {
				hotPostReplyWrapper.setHotPostReplies(getHotPostReplies(jData));
			}

			return hotPostReplyWrapper;
		}
		return null;
	}

	/**
	 * 解析热点话题类帖子回复单条数据
	 * 
	 * @param jsonArray
	 * @return
	 * @throws JSONException
	 */
	private List<HotPostReply> getHotPostReplies(JSONArray jsonArray)
			throws JSONException {
		if (jsonArray != null) {
			List<HotPostReply> hotPostReplies = new ArrayList<HotPostReply>();

			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject jb = jsonArray.getJSONObject(i);

				HotPostWrapper hotPostWrapper = new HotPostWrapper();
				HotPostReplyWrapper hotPostReplyWrapper = hotPostWrapper.new HotPostReplyWrapper();
				HotPostReply hotPostReply = hotPostReplyWrapper.new HotPostReply();

				hotPostReply.setId(jb.getString("id"));
				hotPostReply.setContent(jb.getString("content"));
				hotPostReply.setHotreviewid(jb.getString("hotreviewid"));
				hotPostReply.setSenduser(jb.getString("senduser"));
				hotPostReply.setSendtime(jb.getString("sendtime"));
				hotPostReply.setAnswercontent(jb.getString("answercontent"));

				hotPostReplies.add(hotPostReply);
			}

			return hotPostReplies;
		}

		return null;
	}

}
