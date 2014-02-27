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
import com.wuxi.domain.HotReviewReplyWrapper;
import com.wuxi.domain.HotReviewReplyWrapper.HotReviewReply;
import com.wuxi.exception.NODataException;
import com.wuxi.exception.NetException;

/**
 * 政民互动 热点话题 回复列表数据解析业务类
 * 
 * @author 智佳 罗森
 * 
 */
public class HotReviewReplayService extends Service {

	public HotReviewReplayService(Context context) {
		super(context);
	}

	/**
	 * 解析数据集
	 * @param hotid
	 * @param start
	 * @param end
	 * @return
	 * @throws NetException
	 * @throws JSONException
	 * @throws NODataException
	 */
	public HotReviewReplyWrapper getHotReviewReplyWrapper(String hotid,
			int start, int end) throws NetException, JSONException,
			NODataException {
		if (!checkNet()) {
			throw new NetException(Constants.ExceptionMessage.NO_NET);
		}

		String url = Constants.Urls.HOTREVIEW_REPLY_LIST_URL.replace("{id}",
				hotid) + "?start=" + start + "&end=" + end;

		String resultStr = httpUtils.executeGetToString(url, TIME_OUT);

		if (resultStr != null) {
			JSONObject jsonObject = new JSONObject(resultStr);
			JSONObject jresult = jsonObject.getJSONObject("result");

			HotReviewReplyWrapper replyWrapper = new HotReviewReplyWrapper();

			replyWrapper.setEnd(jresult.getInt("end"));
			replyWrapper.setStart(jresult.getInt("start"));
			replyWrapper.setNext(jresult.getBoolean("next"));
			replyWrapper.setPrevious(jresult.getBoolean("previous"));
			replyWrapper.setTotalRowsAmount(jresult.getInt("totalRowsAmount"));

			JSONArray jData = jresult.getJSONArray("data");

			if (jData != null) {
				replyWrapper.setHotReviewReplies(getHotReviewReplies(jData));
			}

			return replyWrapper;
		} else {
			// 没有获取到数据异常
			throw new NODataException(Constants.ExceptionMessage.NODATA_MEG);
		}
	}

	/**
	 * 解析单条数据
	 * 
	 * @param array
	 * @return
	 * @throws JSONException
	 */
	private List<HotReviewReply> getHotReviewReplies(JSONArray array)
			throws JSONException {
		if (array != null) {
			List<HotReviewReply> hotReviewReplies = new ArrayList<HotReviewReply>();

			for (int i = 0; i < array.length(); i++) {
				JSONObject jo = array.getJSONObject(i);

				HotReviewReplyWrapper replyWrapper = new HotReviewReplyWrapper();
				HotReviewReply hotReviewReply = replyWrapper.new HotReviewReply();

				hotReviewReply.setId(jo.getString("id"));
				hotReviewReply.setContent(jo.getString("content"));
				hotReviewReply.setHotreviewid(jo.getString("hotreviewid"));
				hotReviewReply.setSenduser(jo.getString("senduser"));
				hotReviewReply.setSendtime(jo.getString("sendtime"));
				hotReviewReply.setAnswercontent(jo.getString("answercontent"));

				hotReviewReplies.add(hotReviewReply);
			}

			return hotReviewReplies;
		}

		return null;
	}

}
