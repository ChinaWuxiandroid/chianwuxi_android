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
import com.wuxi.domain.NoticePostWrapper;
import com.wuxi.domain.NoticePostWrapper.NoticePostReplyWrapper;
import com.wuxi.domain.NoticePostWrapper.NoticePostReplyWrapper.NoticePostReply;
import com.wuxi.exception.NODataException;
import com.wuxi.exception.NetException;

/**
 * 公告类帖子 业务类
 * 
 * @author 智佳 罗森
 * 
 */
public class NoticePostService extends Service {

	public NoticePostService(Context context) {
		super(context);
	}

	/**
	 * 解析公告型帖子数据集
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
	public NoticePostWrapper getNoticePostWrapper(String politicsMainId,
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

			NoticePostWrapper noticePostWrapper = new NoticePostWrapper();

			noticePostWrapper.setId(jresult.getString("id"));
			noticePostWrapper.setContent(jresult.getString("content"));
			noticePostWrapper.setStatus(jresult.getString("status"));
			noticePostWrapper.setTitle(jresult.getString("title"));
			noticePostWrapper.setDoAnonymous(jresult.getString("doAnonymous"));
			noticePostWrapper.setReadCount(jresult.getString("readCount"));
			noticePostWrapper.setSummary(jresult.getString("summary"));

			if (!jresult.isNull("begintime")) {
				noticePostWrapper.setBegintime(TimeFormateUtil.formateTime(
						String.valueOf(jresult.getLong("begintime")),
						TimeFormateUtil.DATE_PATTERN));
			}

			if (!jresult.isNull("endtime")) {
				noticePostWrapper.setEndtime(TimeFormateUtil.formateTime(
						String.valueOf(jresult.getLong("endtime")),
						TimeFormateUtil.DATE_PATTERN));
			}

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
	 * 解析公告类帖子回复数据集
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
	 * 解析公告类帖子单条回复数据
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

				noticePostReply.setId(jb.getString("id"));
				noticePostReply.setContent(jb.getString("content"));
				noticePostReply.setUsername(jb.getString("username"));
				noticePostReply.setTitle(jb.getString("title"));

				if (!jb.isNull("sendtime")) {
					noticePostReply.setSendtime(TimeFormateUtil.formateTime(
							String.valueOf(jb.getLong("sendtime")),
							TimeFormateUtil.DATE_PATTERN));
				}

				noticePostReply.setAnswercontent(jb.getString("answercontent"));
				noticePostReply.setMainid(jb.getString("mainid"));
				noticePostReply.setAnswerman(jb.getString("answerman"));

				noticePostReplies.add(noticePostReply);
			}

			return noticePostReplies;
		}
		return null;
	}

}
