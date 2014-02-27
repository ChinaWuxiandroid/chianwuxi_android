/**
 * 
 */
package com.wuxi.app.engine;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

import com.wuxi.app.util.Constants;
import com.wuxi.app.util.TimeFormateUtil;
import com.wuxi.domain.HotReviewInfoWrapper;
import com.wuxi.exception.NODataException;
import com.wuxi.exception.NetException;

/**
 * 热点话题 单个话题 业务类
 * 
 * @author 智佳 罗森
 * 
 */
public class HotReviewInfoService extends Service {

	public HotReviewInfoService(Context context) {
		super(context);
	}

	/**
	 * 解析数据
	 * 
	 * @param id
	 * @return
	 * @throws NetException
	 * @throws JSONException
	 * @throws NODataException
	 */
	public HotReviewInfoWrapper getHotReviewInfoWrapper(String id)
			throws NetException, JSONException, NODataException {
		if (!checkNet()) {
			throw new NetException(Constants.ExceptionMessage.NO_NET);
		}

		String url = Constants.Urls.HOTREVIEWCONTENT_LIST_URL.replace("{id}",
				id);

		String resultStr = httpUtils.executeGetToString(url, 5000);

		if (resultStr != null) {
			JSONObject jsonObject = new JSONObject(resultStr);
			JSONObject jresult = jsonObject.getJSONObject("result");

			HotReviewInfoWrapper wrapper = new HotReviewInfoWrapper();

			wrapper.setId(jresult.getString("id"));
			wrapper.setContent(jresult.getString("content"));

			if (!jresult.isNull("endTime")) {
				wrapper.setEndTime(TimeFormateUtil.formateTime(
						String.valueOf(jresult.getLong("endTime")),
						TimeFormateUtil.DATE_PATTERN));
			}

			wrapper.setTitle(jresult.getString("title"));
			wrapper.setDepName(jresult.getString("depName"));
			wrapper.setCanReply(jresult.getString("canReply"));
			wrapper.setReplies(jresult.getString("replies"));

			return wrapper;
		} else {
			// 没有获取到数据异常
			throw new NODataException(Constants.ExceptionMessage.NODATA_MEG);
		}
	}

}
