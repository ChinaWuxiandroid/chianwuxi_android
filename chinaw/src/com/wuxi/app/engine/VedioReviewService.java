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
import com.wuxi.domain.VedioReviewWrapper;
import com.wuxi.exception.NODataException;
import com.wuxi.exception.NetException;

/**
 * 走进直播间 往期回顾 业务类
 * 
 * @author 智佳 罗森
 * 
 */
public class VedioReviewService extends Service {

	/**
	 * 构造方法
	 * 
	 * @param context
	 */
	public VedioReviewService(Context context) {
		super(context);
	}

	/**
	 * 解析数据集
	 * 
	 * @param start
	 * @param end
	 * @return
	 * @throws NetException
	 * @throws JSONException
	 * @throws NODataException
	 */
	public VedioReviewWrapper getVedioReviewWrapper(int start, int end)
			throws NetException, JSONException, NODataException {

		// 检查网络连接状态
		if (!checkNet()) {
			throw new NetException(Constants.ExceptionMessage.NO_NET);
		}

		String url = Constants.Urls.VEDIO_REVIEW_CONTENT_URL + "?start="
				+ start + "&end=" + end;

		String resultStr = httpUtils.executeGetToString(url, TIME_OUT);

		if (resultStr != null) {
			JSONObject jsonObject = new JSONObject(resultStr);
			JSONObject jresult = jsonObject.getJSONObject("result");

			VedioReviewWrapper reviewWrapper = new VedioReviewWrapper();

			reviewWrapper.setEnd(jresult.getInt("end"));
			reviewWrapper.setStart(jresult.getInt("start"));
			reviewWrapper.setNext(jresult.getBoolean("next"));
			reviewWrapper.setPrevious(jresult.getBoolean("previous"));
			reviewWrapper.setTotalRowsAmount(jresult.getInt("totalRowsAmount"));

			JSONArray jData = jresult.getJSONArray("data");

			if (jData != null) {
				reviewWrapper.setReviews(getVedioReviews(jData));
			}

			return reviewWrapper;
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
	private List<VedioReviewWrapper.VedioReview> getVedioReviews(JSONArray jData)
			throws JSONException {
		if (jData != null) {
			List<VedioReviewWrapper.VedioReview> reviews = new ArrayList<VedioReviewWrapper.VedioReview>();

			for (int i = 0; i < jData.length(); i++) {
				JSONObject jb = jData.getJSONObject(i);

				VedioReviewWrapper reviewWrapper = new VedioReviewWrapper();
				VedioReviewWrapper.VedioReview review = reviewWrapper.new VedioReview();

				review.setId(jb.getString("id"));
				review.setSubject(jb.getString("subject"));
				review.setGuests(jb.getString("guests"));
				review.setWorkDate(jb.getString("workDate"));
				review.setSubjectContent(jb.getString("subjectContent"));

				if (!jb.isNull("beginTime")) {
					review.setBeginTime(TimeFormateUtil.formateTime(
							String.valueOf(jb.getLong("beginTime")),
							TimeFormateUtil.DATE_TIME_PATTERN));
				}

				if (!jb.isNull("endTime")) {
					review.setEndTime(TimeFormateUtil.formateTime(
							String.valueOf(jb.getLong("endTime")),
							TimeFormateUtil.DATE_TIME_PATTERN));
				}

				reviews.add(review);
			}

			return reviews;
		}
		return null;
	}
}
