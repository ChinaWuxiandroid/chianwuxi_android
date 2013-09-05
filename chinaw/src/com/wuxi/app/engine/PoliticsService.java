package com.wuxi.app.engine;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

import com.wuxi.app.util.Constants;
import com.wuxi.app.util.TimeFormateUtil;
import com.wuxi.domain.PoliticsWrapper;
import com.wuxi.exception.NODataException;
import com.wuxi.exception.NetException;

/**
 * 立法征求或者民意征集 业务类
 * 
 * @author 杨宸 智佳
 * */

public class PoliticsService extends Service {

	public PoliticsService(Context context) {
		super(context);
	}

	/**
	 * 获取当前用户参与的立法征求或民意征集
	 * */
	public PoliticsWrapper getMyPoliticsWrapper(String url, String accessToken,
			int type, int startIndex, int endIndex) throws NetException,
			JSONException, NODataException {
		url = url + "?access_token=" + accessToken + "&type=" + type
				+ "&start=" + startIndex + "&end=" + endIndex;
		return getPoliticsWrapper(url);
	}

	/**
	 * 
	 * 杨宸 智佳
	 * 
	 * @param start
	 *            开始索引
	 * @param end
	 *            结束索引
	 * @param previous
	 *            是否可以上一页
	 * @param totalRowsAmount
	 *            ; 数据列表中元素个数
	 * @param next
	 *            ; 是否可以下一页
	 * @return PoliticsWrapper Politics包装对象
	 * @throws NetException
	 * @throws JSONException
	 * @throws NODataException
	 */
	public PoliticsWrapper getPoliticsWrapper(String url) throws NetException,
			JSONException, NODataException {

		if (!checkNet()) {
			throw new NetException(Constants.ExceptionMessage.NO_NET);
		}

		String resultStr = httpUtils.executeGetToString(url, 5000);

		if (resultStr != null) {
			JSONObject jsonObject = new JSONObject(resultStr);
			JSONObject jresult = jsonObject.getJSONObject("result");

			PoliticsWrapper politicsWrapper = new PoliticsWrapper();
			politicsWrapper.setEnd(jresult.getInt("end"));
			politicsWrapper.setStart(jresult.getInt("start"));
			politicsWrapper.setNext(jresult.getBoolean("next"));
			politicsWrapper.setPrevious(jresult.getBoolean("previous"));
			politicsWrapper.setTotalRowsAmount(jresult
					.getInt("totalRowsAmount"));
			JSONArray jData = jresult.getJSONArray("data");
			if (jData != null) {
				politicsWrapper.setData(parseData(jData));// 解析数组
			}

			return politicsWrapper;

		} else {
			throw new NODataException(Constants.ExceptionMessage.NODATA_MEG);// 没有获取到数据异常
		}
	}

	/**
	 * 
	 * 杨宸 智佳
	 * 
	 * @param jData
	 * @return 从 索引start 到 end-1 的 List<HotReview>
	 * @throws JSONException
	 */

	private List<PoliticsWrapper.Politics> parseData(JSONArray jData)
			throws JSONException {

		if (jData != null) {
			List<PoliticsWrapper.Politics> politicsList = new ArrayList<PoliticsWrapper.Politics>();

			for (int index = 0; index < jData.length(); index++) {

				JSONObject jb = jData.getJSONObject(index);
				PoliticsWrapper h = new PoliticsWrapper();
				PoliticsWrapper.Politics politics = h.new Politics();
				politics.setId(jb.getString("id"));
				politics.setTitle(jb.getString("title"));
				politics.setDoprojectid(jb.getString("doprojectid"));

				if (!jb.isNull("begintime")) {
					politics.setBeginTime(TimeFormateUtil.formateTime(
							String.valueOf(jb.getLong("begintime")),
							TimeFormateUtil.DATE_PATTERN));
				}

				if (!jb.isNull("endtime")) {
					politics.setEndTime(TimeFormateUtil.formateTime(
							String.valueOf(jb.getLong("endtime")),
							TimeFormateUtil.DATE_PATTERN));
				}

				politicsList.add(politics);
			}
			return politicsList;
		}
		return null;
	}

}
