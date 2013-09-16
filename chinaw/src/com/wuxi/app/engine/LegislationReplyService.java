/**   
 * @公司: 重庆智佳信息科技有限公司
 * @文件: LegislationReplyService.java 
 * @包名： com.wuxi.app.engine 
 * @描述: 民意征集回复列表业务类
 * @作者： 罗森   
 * @创建时间： 2013 2013-9-13 上午10:25:13
 * @修改时间：  
 * @修改描述：
 * @版本： V1.0   
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
 * @类名： LegislationReplyService
 * @描述： 民意征集回复列表业务类
 * @作者： 罗森
 * @创建时间： 2013 2013-9-13 上午10:25:13
 * @修改时间：
 * @修改描述：
 */
public class LegislationReplyService extends Service {

	public LegislationReplyService(Context context) {
		super(context);
	}

	/**
	 * 解析回复数据集
	 * 
	 * @param jsonObject
	 * @return
	 * @throws JSONException
	 */
	public NoticePostReplyWrapper getNoticePostReplyWrapper(String id,
			int start, int end) throws NetException, JSONException,
			NODataException {

		// 检查网络连接状态
		if (!checkNet()) {
			throw new NetException(Constants.ExceptionMessage.NO_NET);
		}

		String url = Constants.Urls.LEGISLATION_REPLY_URL + "?mainid=" + id
				+ "&start=" + start + "&end=" + end;

		String resultStr = httpUtils.executeGetToString(url, TIME_OUT);

		if (resultStr != null) {
			JSONObject jsonObject = new JSONObject(resultStr);
			JSONObject jresult = jsonObject.getJSONObject("result");

			NoticePostWrapper noticePostWrapper = new NoticePostWrapper();
			NoticePostReplyWrapper noticePostReplyWrapper = noticePostWrapper.new NoticePostReplyWrapper();

			noticePostReplyWrapper.setEnd(jresult.getInt("end"));
			noticePostReplyWrapper.setStart(jresult.getInt("start"));
			noticePostReplyWrapper.setNext(jresult.getBoolean("next"));
			noticePostReplyWrapper.setPrevious(jresult.getBoolean("previous"));
			noticePostReplyWrapper.setTotalRowsAmount(jresult
					.getInt("totalRowsAmount"));

			JSONArray jData = jresult.getJSONArray("data");

			if (jData != null) {
				noticePostReplyWrapper
						.setNoticePostReplies(getNoticePostReplies(jData));
			}
			return noticePostReplyWrapper;
		} else {
			// 没有获取到数据异常
			throw new NODataException(Constants.ExceptionMessage.NODATA_MEG);
		}
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
