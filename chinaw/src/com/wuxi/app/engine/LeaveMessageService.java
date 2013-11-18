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
import com.wuxi.domain.LeaveMessageWrapper;
import com.wuxi.domain.VideoPreviewInfo;
import com.wuxi.exception.NODataException;
import com.wuxi.exception.NetException;

/**
 * 栏目首页 留言提问业务类
 * 
 * @author 智佳 罗森
 * 
 */
public class LeaveMessageService extends Service {

	/**
	 * 构造方法
	 * 
	 * @param context
	 */
	public LeaveMessageService(Context context) {
		super(context);
	}

	public VideoPreviewInfo getVideoPreview(int start, int end)
			throws NetException, JSONException, NODataException {

		// 检查网络连接状态
		if (!checkNet()) {
			throw new NetException(Constants.ExceptionMessage.NO_NET);
		}

		String url = Constants.Urls.VEDIO_PREVIEW_URL + "start=" + start
				+ "&end=" + end;

		String resultStr = httpUtils.executeGetToString(url, TIME_OUT);

		if (resultStr != null) {
		}

		return null;
	}

	/**
	 * 解析数据集
	 * 
	 * @param interViewId
	 * @param start
	 * @param end
	 * @return
	 * @throws NetException
	 * @throws JSONException
	 * @throws NODataException
	 */
	public LeaveMessageWrapper getLeaveMessageWrapper(String interViewId,
			int start, int end) throws NetException, JSONException,
			NODataException {

		// 检查网络连接状态
		if (!checkNet()) {
			throw new NetException(Constants.ExceptionMessage.NO_NET);
		}

		String url = Constants.Urls.LEAVE_MESSAGE_CONTENT_URL.replace(
				"{interViewId}", interViewId)
				+ "?start="
				+ start
				+ "&end="
				+ end;

		String resultStr = httpUtils.executeGetToString(url, TIME_OUT);

		if (resultStr != null) {
			JSONObject jsonObject = new JSONObject(resultStr);
			JSONObject jresult = jsonObject.getJSONObject("result");

			LeaveMessageWrapper messageWrapper = new LeaveMessageWrapper();

			messageWrapper.setEnd(jresult.getInt("end"));
			messageWrapper.setStart(jresult.getInt("start"));
			messageWrapper.setNext(jresult.getBoolean("next"));
			messageWrapper.setPrevious(jresult.getBoolean("previous"));
			messageWrapper
					.setTotalRowsAmount(jresult.getInt("totalRowsAmount"));

			JSONArray jData = jresult.getJSONArray("data");

			if (jData != null) {
				messageWrapper.setLeaveMessages(getLeaveMessages(jData));
			}

			return messageWrapper;
		} else {
			// 没有获取到数据异常
			throw new NODataException(Constants.ExceptionMessage.NODATA_MEG);
		}
	}

	/**
	 * 解析单条数据
	 * 
	 * @param jData
	 * @return
	 * @throws JSONException
	 */
	private List<LeaveMessageWrapper.LeaveMessage> getLeaveMessages(
			JSONArray jData) throws JSONException {
		if (jData != null) {
			List<LeaveMessageWrapper.LeaveMessage> leaveMessages = new ArrayList<LeaveMessageWrapper.LeaveMessage>();

			for (int i = 0; i < jData.length(); i++) {
				JSONObject jb = jData.getJSONObject(i);

				LeaveMessageWrapper leaveMessageWrapper = new LeaveMessageWrapper();
				LeaveMessageWrapper.LeaveMessage leaveMessage = leaveMessageWrapper.new LeaveMessage();

				leaveMessage.setId(jb.getString("id"));
				leaveMessage.setState(jb.getInt("state"));
				leaveMessage.setContent(jb.getString("content"));
				leaveMessage.setAnswerId(jb.getString("answerId"));
				leaveMessage.setInterViewId(jb.getString("interViewId"));

				if (!jb.isNull("submitTime")) {
					leaveMessage.setSubmitTime(TimeFormateUtil.formateTime(
							String.valueOf(jb.getLong("submitTime")),
							TimeFormateUtil.DATE_TIME_PATTERN));
				}

				leaveMessage.setSentUser(jb.getString("sentUser"));
				leaveMessage.setAnswerContent(jb.getString("answerContent"));
				leaveMessage.setAnswerStatus(jb.getInt("answerStatus"));
				leaveMessage.setIsCommend(jb.getInt("isCommend"));
				leaveMessage.setQuestionType(jb.getInt("questionType"));

				if (!jb.isNull("recommendTime")) {
					leaveMessage.setRecommendTime(TimeFormateUtil.formateTime(
							String.valueOf(jb.getLong("recommendTime")),
							TimeFormateUtil.DATE_TIME_PATTERN));
				}

				leaveMessage.setSentIP(jb.getString("sentIP"));

				leaveMessages.add(leaveMessage);
			}
			return leaveMessages;
		}
		return null;
	}

}
