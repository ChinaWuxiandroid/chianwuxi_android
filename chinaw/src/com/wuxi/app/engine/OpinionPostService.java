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
import com.wuxi.domain.OpinionPostWrapper;
import com.wuxi.domain.OpinionPostWrapper.OpinionPostReplyWrapper;
import com.wuxi.domain.OpinionPostWrapper.OpinionPostReplyWrapper.OpinionPostReply;
import com.wuxi.exception.NODataException;
import com.wuxi.exception.NetException;

/**
 * 征求意见类帖子 业务类
 * 
 * @author 智佳 罗森
 * 
 */
public class OpinionPostService extends Service {

	public OpinionPostService(Context context) {
		super(context);
	}

	/**
	 * 解析征求意见类数据
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
	public OpinionPostWrapper getOpinionPostWrapper(String politicsMainId,
			String type, int start, int end) throws NetException,
			JSONException, NODataException {
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

		String resultStr = httpUtils.executeGetToString(url, 5000);

		if (resultStr != null) {
			JSONObject jsonObject = new JSONObject(resultStr);
			JSONObject jresult = jsonObject.getJSONObject("result");

			OpinionPostWrapper opinionPostWrapper = new OpinionPostWrapper();

			opinionPostWrapper.setId(jresult.getString("id"));
			opinionPostWrapper.setContent(jresult.getString("content"));
			opinionPostWrapper.setStatus(jresult.getString("status"));
			opinionPostWrapper.setTitle(jresult.getString("title"));
			opinionPostWrapper.setDoAnonymous(jresult.getString("doAnonymous"));
			opinionPostWrapper.setReadCount(jresult.getString("readCount"));
			opinionPostWrapper.setSummary(jresult.getString("summary"));

			if (!jresult.isNull("begintime")) {
				opinionPostWrapper.setBegintime(TimeFormateUtil.formateTime(
						String.valueOf(jresult.getLong("begintime")),
						TimeFormateUtil.DATE_PATTERN));
			}

			if (!jresult.isNull("endtime")) {
				opinionPostWrapper.setEndtime(TimeFormateUtil.formateTime(
						String.valueOf(jresult.getLong("endtime")),
						TimeFormateUtil.DATE_PATTERN));
			}

			opinionPostWrapper.setDoprojectid(jresult.getString("doprojectid"));
			opinionPostWrapper.setSumup(jresult.getString("sumup"));

			JSONObject jData = jresult.getJSONObject("replys");

			if (jData != null) {
				opinionPostWrapper
						.setOpinionPostReplyWrapper(getOpinionPostReplyWrapper(jData));
			}

			return opinionPostWrapper;
		}

		return null;
	}

	/**
	 * 解析回复数据集
	 * 
	 * @param jsonObject
	 * @return
	 * @throws JSONException
	 */
	private OpinionPostReplyWrapper getOpinionPostReplyWrapper(
			JSONObject jsonObject) throws JSONException {
		if (jsonObject != null) {
			OpinionPostWrapper opinionPostWrapper = new OpinionPostWrapper();
			OpinionPostReplyWrapper opinionPostReplyWrapper = opinionPostWrapper.new OpinionPostReplyWrapper();

			opinionPostReplyWrapper.setEnd(jsonObject.getInt("end"));
			opinionPostReplyWrapper.setStart(jsonObject.getInt("start"));
			opinionPostReplyWrapper.setNext(jsonObject.getBoolean("next"));
			opinionPostReplyWrapper.setPrevious(jsonObject
					.getBoolean("previous"));
			opinionPostReplyWrapper.setTotalRowsAmount(jsonObject
					.getInt("totalRowsAmount"));
			JSONArray jData = jsonObject.getJSONArray("data");

			if (jData != null) {
				opinionPostReplyWrapper
						.setOpinionPostReplies(getOpinionPostReplies(jData));
			}
			return opinionPostReplyWrapper;
		}
		return null;
	}

	/**
	 * 解析单条回复数据
	 * 
	 * @param jsonArray
	 * @return
	 * @throws JSONException
	 */
	private List<OpinionPostReply> getOpinionPostReplies(JSONArray jsonArray)
			throws JSONException {
		if (jsonArray != null) {
			List<OpinionPostReply> opinionPostReplies = new ArrayList<OpinionPostReply>();

			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject jb = jsonArray.getJSONObject(i);

				OpinionPostWrapper opinionPostWrapper = new OpinionPostWrapper();
				OpinionPostReplyWrapper opinionPostReplyWrapper = opinionPostWrapper.new OpinionPostReplyWrapper();
				OpinionPostReply opinionPostReply = opinionPostReplyWrapper.new OpinionPostReply();

				opinionPostReply.setContent(jb.getString("content"));
				opinionPostReply.setStatus(jb.getString("status"));
				opinionPostReply.setUserName(jb.getString("userName"));
				opinionPostReply.setPoliticsMainId(jb
						.getString("politicsMainId"));
				opinionPostReply.setSentIp(jb.getString("sentIp"));

				if (!jb.isNull("sentTime")) {
					opinionPostReply.setSentTime(TimeFormateUtil.formateTime(
							String.valueOf(jb.getLong("sentTime")),
							TimeFormateUtil.DATE_PATTERN));
				}

				opinionPostReply.setActorInfoId(jb.getString("actorInfoId"));

				opinionPostReplies.add(opinionPostReply);
			}

			return opinionPostReplies;
		}

		return null;
	}

}
