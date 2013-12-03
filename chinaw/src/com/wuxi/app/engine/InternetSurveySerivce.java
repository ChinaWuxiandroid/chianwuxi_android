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
import com.wuxi.domain.InternetSurveyWrapper;
import com.wuxi.domain.InternetSurveyWrapper.InternetSurvey;
import com.wuxi.exception.NODataException;
import com.wuxi.exception.NetException;

/**
 * @author Administrator
 * 
 */
public class InternetSurveySerivce extends Service {

	public InternetSurveySerivce(Context context) {
		super(context);
	}

	public InternetSurveyWrapper getInternetSurveyWrapper(int type, int start,
			int end) throws NetException, JSONException, NODataException {

		if (!checkNet()) {
			throw new NetException(Constants.ExceptionMessage.NO_NET);
		}

		String url = Constants.Urls.INTERNET_SURVEY_URL + "?type=" + type
				+ "&start=" + start + "&end=" + end;
		String resultStr = httpUtils.executeGetToString(url, 5000);

		if (resultStr != null) {
			JSONObject jsonObject = new JSONObject(resultStr);
			JSONObject jresult = jsonObject.getJSONObject("result");

			InternetSurveyWrapper internetSurveyWrapper = new InternetSurveyWrapper();

			internetSurveyWrapper.setEnd(jresult.getInt("end"));
			internetSurveyWrapper.setStart(jresult.getInt("start"));
			internetSurveyWrapper.setNext(jresult.getBoolean("next"));
			internetSurveyWrapper.setPrevious(jresult.getBoolean("previous"));
			internetSurveyWrapper.setTotalRowsAmount(jresult
					.getInt("totalRowsAmount"));

			JSONArray jData = jresult.getJSONArray("data");
			if (jData != null) {
				internetSurveyWrapper
						.setInternetSurveys(getInternetSurveys(jData));
			}

			return internetSurveyWrapper;

		} else {
			throw new NODataException(Constants.ExceptionMessage.NODATA_MEG);// 没有获取到数据异常
		}
	}

	private List<InternetSurvey> getInternetSurveys(JSONArray jData)
			throws JSONException {
		if (jData != null) {

			List<InternetSurvey> internetSurveys = new ArrayList<InternetSurvey>();

			for (int index = 0; index < jData.length(); index++) {
				JSONObject jb = jData.getJSONObject(index);

				InternetSurveyWrapper internetSurveyWrapper = new InternetSurveyWrapper();
				InternetSurvey internetSurvey = internetSurveyWrapper.new InternetSurvey();

				if (!jb.isNull("createDate")) {
					internetSurvey.setCreateDate(TimeFormateUtil.formateTime(
							String.valueOf(jb.getLong("createDate")),
							TimeFormateUtil.DATE_PATTERN));
				}

				internetSurvey.setTitle(jb.getString("title"));
				internetSurvey.setQuestions(jb.getString("questions"));
				internetSurvey.setDoProjectId(jb.getString("doProjectId"));
				internetSurvey.setDepId(jb.getString("depId"));
				internetSurvey.setReadCount(jb.getString("readCount"));

				if (!jb.isNull("endDate")) {
					internetSurvey.setEndDate(TimeFormateUtil.formateTime(
							String.valueOf(jb.getLong("endDate")),
							TimeFormateUtil.DATE_PATTERN));
				}

				internetSurvey.setResults(jb.getString("results"));
				internetSurvey.setIsAnonymous(jb.getString("isAnonymous"));
				internetSurvey.setOrderId(jb.getString("orderId"));
				internetSurvey.setIsTop(jb.getString("isTop"));
				internetSurvey.setSummary(jb.getString("summary"));
				internetSurvey.setSurveryId(jb.getString("surveryId"));
				internetSurvey.setAuthor(jb.getString("author"));
				internetSurvey.setIsEnabled(jb.getString("isEnabled"));

				if (!jb.isNull("updateDate")) {
					internetSurvey.setUpdateDate(TimeFormateUtil.formateTime(
							String.valueOf(jb.getLong("updateDate")),
							TimeFormateUtil.DATE_PATTERN));
				}

				internetSurvey.setIsViewSurveryResult(jb
						.getString("isViewSurveryResult"));
				internetSurvey.setIsAuditingInputText(jb
						.getString("isAuditingInputText"));
				internetSurvey.setIsRootDisplay(jb.getString("isRootDisplay"));
				internetSurvey.setIsCenterData(jb.getString("isCenterData"));
				internetSurvey.setIsOnlyShowNo(jb.getString("isOnlyShowNo"));

				internetSurveys.add(internetSurvey);
			}

			return internetSurveys;
		}

		return null;
	}
}
