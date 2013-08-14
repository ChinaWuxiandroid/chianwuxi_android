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
import com.wuxi.domain.NoticePostWrapper;
import com.wuxi.domain.NoticePostWrapper.NoticePostReplyWrapper;
import com.wuxi.domain.NoticePostWrapper.NoticePostReplyWrapper.NoticePostReply;
import com.wuxi.exception.NODataException;
import com.wuxi.exception.NetException;

import android.content.Context;

/**
 * 政民互动 征求意见平台 立法征求意见详情内容 业务类
 * 
 * @author 智佳 罗森
 * 
 */
public class LegislationContentService extends Service {

	public LegislationContentService(Context context) {
		super(context);
	}

	/**
	 * 解析数据集
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
	public NoticePostWrapper getNoticePostWrapper(String politicsMainId)
			throws NetException, JSONException, NODataException {
		// 检查网络连接状态
		if (!checkNet()) {
			throw new NetException(Constants.ExceptionMessage.NO_NET);
		}

		String url = Constants.Urls.LEGISLATION_CONTENT_URL.replace("{id}",
				politicsMainId);

		String resultStr = httpUtils.executeGetToString(url, 5000);
		
		if (resultStr != null) {
			JSONObject jsonObject = new JSONObject(resultStr);
			JSONObject jresult = jsonObject.getJSONObject("result");

			NoticePostWrapper noticePostWrapper = new NoticePostWrapper();

			noticePostWrapper.setId(jresult.getString("id"));
			noticePostWrapper.setContent(jresult.getString("content"));
			noticePostWrapper.setStatus(jresult.getString("status"));
			noticePostWrapper.setTitle(jresult.getString("title"));
			noticePostWrapper.setDoAnonymous(jresult.getString("doAnonymous"));
			noticePostWrapper.setReadCount(jresult.getString("readCount"));
			noticePostWrapper.setSummary(jresult.getString("summary"));
			noticePostWrapper.setBegintime(TimeFormateUtil.formateTime(
					String.valueOf(jresult.getLong("begintime")),
					TimeFormateUtil.DATE_PATTERN));
			noticePostWrapper.setEndtime(TimeFormateUtil.formateTime(
					String.valueOf(jresult.getLong("endtime")),
					TimeFormateUtil.DATE_PATTERN));
			noticePostWrapper.setDoprojectid(jresult.getString("doprojectid"));
			noticePostWrapper.setSumup(jresult.getString("sumup"));

			JSONObject jData = jresult.getJSONObject("replys");

			if (jData != null) {
				noticePostWrapper
						.setNoticePostReplyWrapper(getNoticePostReplyWrapper(jData));
			}

			return noticePostWrapper;
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
	private NoticePostReplyWrapper getNoticePostReplyWrapper(
			JSONObject jsonObject) throws JSONException {
		if (jsonObject != null) {
			NoticePostWrapper noticePostWrapper = new NoticePostWrapper();
			NoticePostReplyWrapper noticePostReplyWrapper = noticePostWrapper.new NoticePostReplyWrapper();

			noticePostReplyWrapper.setEnd(jsonObject.getInt("end"));
			noticePostReplyWrapper.setStart(jsonObject.getInt("start"));
			noticePostReplyWrapper.setNext(jsonObject.getBoolean("next"));
			noticePostReplyWrapper.setPrevious(jsonObject
					.getBoolean("previous"));
			noticePostReplyWrapper.setTotalRowsAmount(jsonObject
					.getInt("totalRowsAmount"));
			JSONArray jData = jsonObject.getJSONArray("data");

			if (jData != null) {
				noticePostReplyWrapper
						.setNoticePostReplies(getNoticePostReplies(jData));
			}
			return noticePostReplyWrapper;
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
	private List<NoticePostReply> getNoticePostReplies(JSONArray jsonArray)
			throws JSONException {
		if (jsonArray != null) {
			List<NoticePostReply> noticePostReplies = new ArrayList<NoticePostReply>();

			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject jb = jsonArray.getJSONObject(i);

				NoticePostWrapper noticePostWrapper = new NoticePostWrapper();
				NoticePostReplyWrapper noticePostReplyWrapper = noticePostWrapper.new NoticePostReplyWrapper();
				NoticePostReply noticePostReply = noticePostReplyWrapper.new NoticePostReply();

				noticePostReply.setContent(jb.getString("content"));
				noticePostReply.setStatus(jb.getString("status"));
				noticePostReply.setUserName(jb.getString("userName"));
				noticePostReply.setPoliticsMainId(jb
						.getString("politicsMainId"));
				noticePostReply.setSentIp(jb.getString("sentIp"));
				noticePostReply.setSentTime(TimeFormateUtil.formateTime(
						String.valueOf(jb.getLong("sentTime")),
						TimeFormateUtil.DATE_PATTERN));
				noticePostReply.setActorInfoId(jb.getString("actorInfoId"));

				noticePostReplies.add(noticePostReply);
			}

			return noticePostReplies;
		}
		return null;
	}

}
