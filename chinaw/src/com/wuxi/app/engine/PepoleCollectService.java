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
import com.wuxi.domain.PepoleIdeaCollectWrapper;
import com.wuxi.domain.PepoleIdeaCollectWrapper.PepoleIdeaReplyWrapper;
import com.wuxi.domain.PepoleIdeaCollectWrapper.PepoleIdeaReplyWrapper.PepoleIdeaReply;
import com.wuxi.exception.NODataException;
import com.wuxi.exception.NetException;

import android.content.Context;

/**
 * 政民互动 征求意见平台 民意征集 业务类
 * 
 * @author 智佳 罗森
 * 
 */
public class PepoleCollectService extends Service {

	public PepoleCollectService(Context context) {
		super(context);
	}

	/**
	 * 解析数据集
	 * @param politicsMainId
	 * @return
	 * @throws NetException
	 * @throws JSONException
	 * @throws NODataException
	 */
	public PepoleIdeaCollectWrapper getPepoleIdeaCollectWrapper(
			String politicsMainId) throws NetException, JSONException,
			NODataException {
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
			
			PepoleIdeaCollectWrapper collectWrapper = new PepoleIdeaCollectWrapper();
			
			collectWrapper.setId(jresult.getString("id"));
			collectWrapper.setContent(jresult.getString("content"));
			collectWrapper.setStatus(jresult.getString("status"));
			collectWrapper.setTitle(jresult.getString("title"));
			collectWrapper.setDoAnonymous(jresult.getString("doAnonymous"));
			collectWrapper.setReadCount(jresult.getString("readCount"));
			collectWrapper.setSummary(jresult.getString("summary"));
			collectWrapper.setBegintime(TimeFormateUtil.formateTime(
					String.valueOf(jresult.getLong("begintime")),
					TimeFormateUtil.DATE_PATTERN));
			collectWrapper.setEndtime(TimeFormateUtil.formateTime(
					String.valueOf(jresult.getLong("endtime")),
					TimeFormateUtil.DATE_PATTERN));
			collectWrapper.setDoprojectid(jresult.getString("doprojectid"));
			collectWrapper.setSumup(jresult.getString("sumup"));

			JSONObject jData = jresult.getJSONObject("replys");
			
			if (jData != null) {
				collectWrapper.setPepoleIdeaReplyWrapper(getPepoleIdeaReplyWrapper(jData));
			}
			
			return collectWrapper;
		}else {
			// 没有获取到数据异常
			throw new NODataException(Constants.ExceptionMessage.NODATA_MEG);
		}
	}

	/**
	 * 解析回复数据集
	 * 
	 * @param jsonObject
	 * @return
	 * @throws JSONException
	 */
	private PepoleIdeaReplyWrapper getPepoleIdeaReplyWrapper(
			JSONObject jsonObject) throws JSONException {
		if (jsonObject != null) {
			PepoleIdeaCollectWrapper collectWrapper = new PepoleIdeaCollectWrapper();
			PepoleIdeaReplyWrapper ideaReplyWrapper = collectWrapper.new PepoleIdeaReplyWrapper();

			ideaReplyWrapper.setEnd(jsonObject.getInt("end"));
			ideaReplyWrapper.setStart(jsonObject.getInt("start"));
			ideaReplyWrapper.setNext(jsonObject.getBoolean("next"));
			ideaReplyWrapper.setPrevious(jsonObject.getBoolean("previous"));
			ideaReplyWrapper.setTotalRowsAmount(jsonObject
					.getInt("totalRowsAmount"));
			JSONArray jData = jsonObject.getJSONArray("data");

			if (jData != null) {
				ideaReplyWrapper
						.setPepoleIdeaReplies(getPepoleIdeaReplies(jData));
			}

			return ideaReplyWrapper;
		}

		return null;
	}

	/**
	 * 解析单条回复数据
	 * 
	 * @param array
	 * @return
	 * @throws JSONException
	 */
	private List<PepoleIdeaReply> getPepoleIdeaReplies(JSONArray array)
			throws JSONException {
		if (array != null) {
			List<PepoleIdeaReply> ideaReplies = new ArrayList<PepoleIdeaReply>();

			for (int i = 0; i < array.length(); i++) {
				JSONObject jb = array.getJSONObject(i);

				PepoleIdeaCollectWrapper collectWrapper = new PepoleIdeaCollectWrapper();
				PepoleIdeaReplyWrapper ideaReplyWrapper = collectWrapper.new PepoleIdeaReplyWrapper();
				PepoleIdeaReply ideaReply = ideaReplyWrapper.new PepoleIdeaReply();

				ideaReply.setContent(jb.getString("content"));
				ideaReply.setStatus(jb.getString("status"));
				ideaReply.setUserName(jb.getString("userName"));
				ideaReply.setPoliticsMainId(jb.getString("politicsMainId"));
				ideaReply.setSentIp(jb.getString("sentIp"));
				ideaReply.setSentTime(TimeFormateUtil.formateTime(
						String.valueOf(jb.getLong("sentTime")),
						TimeFormateUtil.DATE_PATTERN));
				ideaReply.setActorInfoId(jb.getString("actorInfoId"));

				ideaReplies.add(ideaReply);
			}

			return ideaReplies;
		}

		return null;
	}
}
