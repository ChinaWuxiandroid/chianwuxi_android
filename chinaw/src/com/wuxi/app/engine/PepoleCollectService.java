/**   
 * @公司: 重庆智佳信息科技公司
 * @文件: PepoleCollectService.java 
 * @包名： com.wuxi.app.engine 
 * @描述: 解析民意征集的数据 
 * @作者： 罗森   
 * @创建时间： 2013 2013-8-21 下午2:47:33
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

import android.content.Context;

import com.wuxi.app.util.Constants;
import com.wuxi.app.util.TimeFormateUtil;
import com.wuxi.domain.PepoleIdeaCollectWrapper;
import com.wuxi.domain.PepoleIdeaCollectWrapper.PepoleIdeaReplyWrapper;
import com.wuxi.domain.PepoleIdeaCollectWrapper.PepoleIdeaReplyWrapper.PepoleIdeaReply;
import com.wuxi.exception.NODataException;
import com.wuxi.exception.NetException;

/**
 * @类名： PepoleCollectService
 * @描述： 解析民意征集的数据
 * @作者： 罗森
 * @创建时间： 2013 2013-8-21 下午2:47:33
 * @修改时间：
 * @修改描述：
 * 
 */
public class PepoleCollectService extends Service {

	/**
	 * 
	 * @方法： PepoleCollectService
	 * @描述：构造方法
	 * @param context
	 */
	public PepoleCollectService(Context context) {
		super(context);
	}

	/**
	 * 
	 * @方法： getPepoleIdeaCollectWrapper
	 * @描述： 解析民意征集数据集
	 * @param id
	 * @return PepoleIdeaCollectWrapper
	 * @throws NetException
	 * @throws JSONException
	 * @throws NODataException
	 */
	public PepoleIdeaCollectWrapper getPepoleIdeaCollectWrapper(String id)
			throws NetException, JSONException, NODataException {
		// 检查网络连接状态
		if (!checkNet()) {
			throw new NetException(Constants.ExceptionMessage.NO_NET);
		}

		// 构建url地址
		String url = Constants.Urls.LEGISLATION_CONTENT_URL.replace("{id}", id);
		// 访问服务器
		String resultStr = httpUtils.executeGetToString(url, TIME_OUT);

		// 解析数据
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

			if (!jresult.isNull("begintime")) {
				collectWrapper.setBegintime(TimeFormateUtil.formateTime(
						String.valueOf(jresult.getLong("begintime")),
						TimeFormateUtil.DATE_PATTERN));
			}

			if (!jresult.isNull("endtime")) {
				collectWrapper.setEndtime(TimeFormateUtil.formateTime(
						String.valueOf(jresult.getLong("endtime")),
						TimeFormateUtil.DATE_PATTERN));
			}

			collectWrapper.setDoprojectid(jresult.getString("doprojectid"));
			collectWrapper.setSumup(jresult.getString("sumup"));

			JSONObject jData = jsonObject.getJSONObject("replys");

			if (jData != null) {
				collectWrapper
						.setPepoleIdeaReplyWrapper(getPepoleIdeaReplyWrapper(jData));
			}

			return collectWrapper;
		} else {
			// 没有获取到数据异常
			throw new NODataException(Constants.ExceptionMessage.NODATA_MEG);
		}
	}

	/**
	 * @方法： getPepoleIdeaReplyWrapper
	 * @描述： 解析民意征集回复数据集
	 * @param jsonObject
	 * @return PepoleIdeaReplyWrapper
	 * @throws JSONException
	 */
	private PepoleIdeaReplyWrapper getPepoleIdeaReplyWrapper(
			JSONObject jsonObject) throws JSONException {
		if (jsonObject != null) {
			PepoleIdeaCollectWrapper collectWrapper = new PepoleIdeaCollectWrapper();
			PepoleIdeaReplyWrapper ideaReplyWrapper = collectWrapper.new PepoleIdeaReplyWrapper();

			ideaReplyWrapper.setEnd(jsonObject.getInt("end"));
			ideaReplyWrapper.setStart(jsonObject.getInt("start"));
			ideaReplyWrapper.setPrevious(jsonObject.getBoolean("previous"));
			ideaReplyWrapper.setTotalRowsAmount(jsonObject
					.getInt("totalRowsAmount"));
			ideaReplyWrapper.setNext(jsonObject.getBoolean("next"));

			JSONArray array = jsonObject.getJSONArray("data");
			if (array != null) {
				ideaReplyWrapper
						.setPepoleIdeaReplies(getPepoleIdeaReplies(array));
			}

			return ideaReplyWrapper;
		}

		return null;
	}

	/**
	 * 
	 * @方法： getPepoleIdeaReplies
	 * @描述： 解析民意征集单条回复数据
	 * @param array
	 * @throws JSONException
	 * @return List<PepoleIdeaReply>
	 * 
	 */
	private List<PepoleIdeaReply> getPepoleIdeaReplies(JSONArray array)
			throws JSONException {
		if (array != null) {
			List<PepoleIdeaReply> ideaReplies = new ArrayList<PepoleIdeaReply>();

			for (int i = 0; i < array.length(); i++) {
				JSONObject jo = array.getJSONObject(i);

				PepoleIdeaCollectWrapper collectWrapper = new PepoleIdeaCollectWrapper();
				PepoleIdeaReplyWrapper ideaReplyWrapper = collectWrapper.new PepoleIdeaReplyWrapper();
				PepoleIdeaReply ideaReply = ideaReplyWrapper.new PepoleIdeaReply();

				ideaReply.setId(jo.getString("id"));
				ideaReply.setContent(jo.getString("content"));
				ideaReply.setUsername(jo.getString("username"));
				ideaReply.setTitle(jo.getString("title"));

				if (!jo.isNull("sendtime")) {
					ideaReply.setSendtime(TimeFormateUtil.formateTime(
							String.valueOf(jo.getLong("sendtime")),
							TimeFormateUtil.DATE_PATTERN));
				}

				ideaReply.setAnswercontent(jo.getString("answercontent"));
				ideaReply.setMainid(jo.getString("mainid"));
				ideaReply.setAnswerman(jo.getString("answerman"));

				ideaReplies.add(ideaReply);
			}

			return ideaReplies;
		}

		return null;
	}

}
