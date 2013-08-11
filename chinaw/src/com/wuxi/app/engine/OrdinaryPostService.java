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
import com.wuxi.domain.OrdinaryPostWrapper;
import com.wuxi.domain.OrdinaryPostWrapper.OrdinaryPostRaplyWrapper;
import com.wuxi.domain.OrdinaryPostWrapper.OrdinaryPostRaplyWrapper.OrdinaryPostRaply;
import com.wuxi.exception.NODataException;
import com.wuxi.exception.NetException;

import android.content.Context;

/**
 * 普通帖子 详细内容 业务类
 * 
 * @author 智佳 罗森
 * 
 */
public class OrdinaryPostService extends Service {

	/**
	 * 构造方法
	 * 
	 * @param context
	 */
	public OrdinaryPostService(Context context) {
		super(context);
	}

	public OrdinaryPostWrapper getOrdinaryPostWrapper(String politicsMainId,
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

			OrdinaryPostWrapper postWrapper = new OrdinaryPostWrapper();

			postWrapper.setContent(jresult.getString("content"));
			postWrapper.setEndTime(TimeFormateUtil.formateTime(
					String.valueOf(jresult.getLong("endTime")),
					TimeFormateUtil.DATE_TIME_PATTERN));
			postWrapper.setStatus(jresult.getString("status"));
			postWrapper.setTitle(jresult.getString("title"));
			postWrapper.setDoProjectId(jresult.getString("doProjectId"));
			postWrapper.setReadCount(jresult.getString("readCount"));
			postWrapper.setPoliticsMainId(jresult.getString("politicsMainId"));
			postWrapper.setSentIp(jresult.getString("sentIp"));
			postWrapper.setSentUser(jresult.getString("sentUser"));
			postWrapper.setOrderId(jresult.getString("orderId"));
			postWrapper.setBeginTime(TimeFormateUtil.formateTime(
					String.valueOf(jresult.getLong("beginTime")),
					TimeFormateUtil.DATE_TIME_PATTERN));
			postWrapper.setIsTop(jresult.getString("isTop"));
			postWrapper.setModifyTime(jresult.getString("modifyTime"));
			postWrapper.setRedTitle(jresult.getString("redTitle"));

			JSONObject jData = jresult.getJSONObject("replaies");

			if (jData != null) {
				postWrapper.setRaplyWrapper(getOrdinaryPostRaplyWrapper(jData));
			}

			return postWrapper;
		} else {
			// 没有获取到数据异常
			throw new NODataException(Constants.ExceptionMessage.NODATA_MEG);
		}

	}

	/**
	 * 解析普通帖子回复数据集
	 * 
	 * @param jsonArray
	 * @return
	 * @throws JSONException
	 */
	private OrdinaryPostRaplyWrapper getOrdinaryPostRaplyWrapper(
			JSONObject jsonObject) throws JSONException {
		if (jsonObject != null) {

			OrdinaryPostWrapper postWrapper = new OrdinaryPostWrapper();
			OrdinaryPostRaplyWrapper postRaplyWrapper = postWrapper.new OrdinaryPostRaplyWrapper();

			postRaplyWrapper.setEnd(jsonObject.getInt("end"));
			postRaplyWrapper.setStart(jsonObject.getInt("start"));
			postRaplyWrapper.setNext(jsonObject.getBoolean("next"));
			postRaplyWrapper.setPrevious(jsonObject.getBoolean("previous"));
			postRaplyWrapper.setTotalRowsAmount(jsonObject
					.getInt("totalRowsAmount"));
			JSONArray jData = jsonObject.getJSONArray("data");

			if (jData != null) {
				// 解析数组
				postRaplyWrapper.setPostRaplies(getOrdinaryPostRaplies(jData));
			}

			return postRaplyWrapper;
		}

		return null;
	}

	/**
	 * 解析普通帖子回复单条数据
	 * 
	 * @param jsonArray
	 * @return
	 * @throws JSONException
	 */
	private List<OrdinaryPostRaply> getOrdinaryPostRaplies(JSONArray jsonArray)
			throws JSONException {
		if (jsonArray != null) {
			List<OrdinaryPostRaply> postRaplies = new ArrayList<OrdinaryPostRaply>();

			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject jb = jsonArray.getJSONObject(i);

				OrdinaryPostWrapper postWrapper = new OrdinaryPostWrapper();
				OrdinaryPostRaplyWrapper postRaplyWrapper = postWrapper.new OrdinaryPostRaplyWrapper();
				OrdinaryPostRaply postRaply = postRaplyWrapper.new OrdinaryPostRaply();

				postRaply.setContent(jb.getString("content"));
				postRaply.setStatus(jb.getString("status"));
				postRaply.setUserName(jb.getString("userName"));
				postRaply.setPoliticsMainId(jb.getString("politicsMainId"));
				postRaply.setSentIp(jb.getString("sentIp"));
				postRaply.setSentTime(TimeFormateUtil.formateTime(
						String.valueOf(jb.getLong("sentTime")),
						TimeFormateUtil.DATE_TIME_PATTERN));
				postRaply.setActorInfoId(jb.getString("actorInfoId"));

				postRaplies.add(postRaply);
			}

			return postRaplies;
		}

		return null;
	}

}
