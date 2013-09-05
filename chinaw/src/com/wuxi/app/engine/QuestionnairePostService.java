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
import com.wuxi.domain.QuestionnairePostWrapper;
import com.wuxi.domain.QuestionnairePostWrapper.QuestionnaireAnswerWrapper.QuestionnaireAnswerDataWrapper;
import com.wuxi.domain.QuestionnairePostWrapper.QuestionnaireAnswerWrapper.QuestionnaireAnswerDataWrapper.QuestionnaireAnswerDat;
import com.wuxi.domain.QuestionnairePostWrapper.QuestionnaireAnswerWrapper;
import com.wuxi.domain.QuestionnairePostWrapper.QuestionnaireQuestionWrapper;
import com.wuxi.domain.QuestionnairePostWrapper.QuestionnaireQuestionWrapper.QuestionnaireQuestion;
import com.wuxi.exception.NODataException;
import com.wuxi.exception.NetException;

import android.content.Context;

/**
 * 调查问卷类帖子业务类
 * 
 * @author 智佳 罗森
 * 
 */
public class QuestionnairePostService extends Service {

	public QuestionnairePostService(Context context) {
		super(context);
	}

	/**
	 * 解析调查问卷数据集
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
	public QuestionnairePostWrapper getQuestionnairePostWrapper(
			String politicsMainId, String type, int start, int end)
			throws NetException, JSONException, NODataException {
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

			QuestionnairePostWrapper questionnairePostWrapper = new QuestionnairePostWrapper();

			if (!jresult.isNull("createDate")) {
				questionnairePostWrapper.setCreateDate(TimeFormateUtil
						.formateTime(
								String.valueOf(jresult.getLong("createDate")),
								TimeFormateUtil.DATE_PATTERN));
			}

			questionnairePostWrapper.setTitle(jresult.getString("title"));
			questionnairePostWrapper.setDoProjectId(jresult
					.getString("doProjectId"));
			questionnairePostWrapper.setDepId(jresult.getString("depId"));
			questionnairePostWrapper.setReadCount(jresult
					.getString("readCount"));

			if (!jresult.isNull("endDate")) {
				questionnairePostWrapper.setEndDate(TimeFormateUtil
						.formateTime(
								String.valueOf(jresult.getLong("endDate")),
								TimeFormateUtil.DATE_PATTERN));
			}

			questionnairePostWrapper.setIsAnonymous(jresult
					.getString("isAnonymous"));
			questionnairePostWrapper.setOrderId(jresult.getString("orderId"));
			questionnairePostWrapper.setIsTop(jresult.getString("isTop"));
			questionnairePostWrapper.setSummary(jresult.getString("summary"));
			questionnairePostWrapper.setSurveryId(jresult
					.getString("surveryId"));
			questionnairePostWrapper.setAuthor(jresult.getString("author"));
			questionnairePostWrapper.setIsEnabled(jresult
					.getString("isEnabled"));

			if (!jresult.isNull("updateDate")) {
				questionnairePostWrapper.setUpdateDate(TimeFormateUtil
						.formateTime(
								String.valueOf(jresult.getLong("updateDate")),
								TimeFormateUtil.DATE_PATTERN));
			}

			questionnairePostWrapper.setIsViewSurveryResult(jresult
					.getString("isViewSurveryResult"));
			questionnairePostWrapper.setIsAuditingInputText(jresult
					.getString("isAuditingInputText"));
			questionnairePostWrapper.setIsRootDisplay(jresult
					.getString("isRootDisplay"));
			questionnairePostWrapper.setIsCenterData(jresult
					.getString("isCenterData"));
			questionnairePostWrapper.setIsOnlyShowNo(jresult
					.getString("isOnlyShowNo"));

			JSONArray jData1 = jresult.getJSONArray("questions");
			if (jData1 != null) {
				questionnairePostWrapper
						.setQuestionnaireQuestionWrappers(getQuestionnaireQuestionWrappers(jData1));
			}

			JSONObject jData2 = jresult.getJSONObject("results");
			if (jData2 != null) {
				questionnairePostWrapper
						.setQuestionnaireAnswerWrapper(getQuestionnaireAnswerWrapper(jData2));
			}

			return questionnairePostWrapper;
		}

		return null;

	}

	/**
	 * 解析问题数据集
	 * 
	 * @param array
	 * @return
	 * @throws JSONException
	 */
	private List<QuestionnaireQuestionWrapper> getQuestionnaireQuestionWrappers(
			JSONArray array) throws JSONException {
		if (array != null) {
			List<QuestionnaireQuestionWrapper> questionWrappers = new ArrayList<QuestionnaireQuestionWrapper>();

			for (int i = 0; i < array.length(); i++) {
				JSONObject jb = array.getJSONObject(i);

				QuestionnairePostWrapper questionnairePostWrapper = new QuestionnairePostWrapper();
				QuestionnaireQuestionWrapper questionnaireQuestionWrapper = questionnairePostWrapper.new QuestionnaireQuestionWrapper();

				questionnaireQuestionWrapper.setDescription(jb
						.getString("description"));
				questionnaireQuestionWrapper.setQuestionId(jb
						.getString("questionId"));
				questionnaireQuestionWrapper
						.setOrderId(jb.getString("orderId"));
				questionnaireQuestionWrapper.setQuestionType(jb
						.getString("questionType"));
				questionnaireQuestionWrapper.setSurveryId(jb
						.getString("surveryId"));
				questionnaireQuestionWrapper.setOptionCount(jb
						.getString("optionCount"));
				questionnaireQuestionWrapper.setHasOther(jb
						.getString("hasOther"));
				questionnaireQuestionWrapper
						.setIsForce(jb.getString("isForce"));
				questionnaireQuestionWrapper.setMinSelect(jb
						.getString("minSelect"));
				questionnaireQuestionWrapper.setMaxSelect(jb
						.getString("maxSelect"));
				questionnaireQuestionWrapper
						.setColspan(jb.getString("colspan"));

				JSONArray jData = jb.getJSONArray("optiones");

				if (jData != null) {
					questionnaireQuestionWrapper
							.setQuestionnaireQuestions(getQuestionnaireQuestions(jData));
				}

				questionWrappers.add(questionnaireQuestionWrapper);
			}

			return questionWrappers;
		}

		return null;
	}

	/**
	 * 解析单个问题数据
	 * 
	 * @param array
	 * @return
	 * @throws JSONException
	 */
	private List<QuestionnaireQuestion> getQuestionnaireQuestions(
			JSONArray array) throws JSONException {
		if (array != null) {
			List<QuestionnaireQuestion> questions = new ArrayList<QuestionnaireQuestion>();

			for (int i = 0; i < array.length(); i++) {
				JSONObject jb = array.getJSONObject(i);

				QuestionnairePostWrapper questionnairePostWrapper = new QuestionnairePostWrapper();
				QuestionnaireQuestionWrapper questionnaireQuestionWrapper = questionnairePostWrapper.new QuestionnaireQuestionWrapper();
				QuestionnaireQuestion questionnaireQuestion = questionnaireQuestionWrapper.new QuestionnaireQuestion();

				questionnaireQuestion.setSerialNumber(jb
						.getString("serialNumber"));
				questionnaireQuestion.setOptionValue(jb
						.getString("optionValue"));
				questionnaireQuestion.setQuestionId(jb.getString("questionId"));
				questionnaireQuestion.setOptionId(jb.getString("optionId"));
				questionnaireQuestion.setClickCount(jb.getString("clickCount"));
				questionnaireQuestion.setLinkUrl(jb.getString("linkUrl"));

				questions.add(questionnaireQuestion);
			}

			return questions;
		}

		return null;
	}

	/**
	 * 解析答案数据
	 * 
	 * @param jsonObject
	 * @return
	 * @throws JSONException
	 */
	private QuestionnaireAnswerWrapper getQuestionnaireAnswerWrapper(
			JSONObject jsonObject) throws JSONException {
		if (jsonObject != null) {
			QuestionnairePostWrapper questionnairePostWrapper = new QuestionnairePostWrapper();
			QuestionnaireAnswerWrapper questionnaireAnswerWrapper = questionnairePostWrapper.new QuestionnaireAnswerWrapper();

			questionnaireAnswerWrapper.setIsnull(jsonObject.getString("null"));
			questionnaireAnswerWrapper.setEnd(jsonObject.getInt("end"));
			questionnaireAnswerWrapper.setStart(jsonObject.getInt("start"));
			questionnaireAnswerWrapper.setNext(jsonObject.getBoolean("next"));
			questionnaireAnswerWrapper.setPrevious(jsonObject
					.getBoolean("previous"));
			questionnaireAnswerWrapper.setTotalRowsAmount(jsonObject
					.getInt("totalRowsAmount"));
			JSONArray jData = jsonObject.getJSONArray("data");

			if (jData != null) {
				questionnaireAnswerWrapper
						.setAnswerDataWrappers(getQuestionnaireAnswerDataWrappers(jData));
			}

			return questionnaireAnswerWrapper;
		}

		return null;
	}

	/**
	 * 解析单个用户答案数据
	 * 
	 * @param array
	 * @return
	 * @throws JSONException
	 */
	private List<QuestionnaireAnswerDataWrapper> getQuestionnaireAnswerDataWrappers(
			JSONArray array) throws JSONException {
		if (array != null) {
			List<QuestionnaireAnswerDataWrapper> questionnaireAnswerDataWrappers = new ArrayList<QuestionnaireAnswerDataWrapper>();

			for (int i = 0; i < array.length(); i++) {
				JSONObject jb = array.getJSONObject(i);

				QuestionnairePostWrapper questionnairePostWrapper = new QuestionnairePostWrapper();
				QuestionnaireAnswerWrapper questionnaireAnswerWrapper = questionnairePostWrapper.new QuestionnaireAnswerWrapper();
				QuestionnaireAnswerDataWrapper questionnaireAnswerDataWrapper = questionnaireAnswerWrapper.new QuestionnaireAnswerDataWrapper();

				questionnaireAnswerDataWrapper.setUserName(jb
						.getString("userName"));
				questionnaireAnswerDataWrapper.setResultId(jb
						.getString("resultId"));
				questionnaireAnswerDataWrapper.setSurveryId(jb
						.getString("surveryId"));

				if (!jb.isNull("submitDate")) {
					questionnaireAnswerDataWrapper
							.setSubmitDate(TimeFormateUtil.formateTime(
									String.valueOf(jb.getLong("submitDate")),
									TimeFormateUtil.DATE_PATTERN));
				}

				questionnaireAnswerDataWrapper.setUserHostAddress(jb
						.getString("userHostAddress"));

				JSONArray jData = jb.getJSONArray("details");

				if (jData != null) {
					questionnaireAnswerDataWrapper
							.setQuestionnaireAnswerDats(getQuestionnaireAnswerDats(jData));
				}

				questionnaireAnswerDataWrappers
						.add(questionnaireAnswerDataWrapper);
			}

			return questionnaireAnswerDataWrappers;
		}

		return null;
	}

	/**
	 * 解析单个问题答案数据
	 * 
	 * @param array
	 * @return
	 * @throws JSONException
	 */
	private List<QuestionnaireAnswerDat> getQuestionnaireAnswerDats(
			JSONArray array) throws JSONException {
		if (array != null) {
			List<QuestionnaireAnswerDat> questionnaireAnswerDats = new ArrayList<QuestionnaireAnswerDat>();

			for (int i = 0; i < array.length(); i++) {
				JSONObject jb = array.getJSONObject(i);

				QuestionnairePostWrapper questionnairePostWrapper = new QuestionnairePostWrapper();
				QuestionnaireAnswerWrapper questionnaireAnswerWrapper = questionnairePostWrapper.new QuestionnaireAnswerWrapper();
				QuestionnaireAnswerDataWrapper questionnaireAnswerDataWrapper = questionnaireAnswerWrapper.new QuestionnaireAnswerDataWrapper();
				QuestionnaireAnswerDat questionnaireAnswerDat = questionnaireAnswerDataWrapper.new QuestionnaireAnswerDat();

				questionnaireAnswerDat.setState(jb.getString("state"));
				questionnaireAnswerDat.setUserName(jb.getString("userName"));
				questionnaireAnswerDat.setOptionValue(jb
						.getString("optionValue"));
				questionnaireAnswerDat.setResultId(jb.getString("resultId"));
				questionnaireAnswerDat.setOrderId(jb.getString("orderId"));
				questionnaireAnswerDat
						.setOptionText(jb.getString("optionText"));

				if (!jb.isNull("submitDate")) {
					questionnaireAnswerDat.setSubmitDate(TimeFormateUtil
							.formateTime(
									String.valueOf(jb.getLong("submitDate")),
									TimeFormateUtil.DATE_PATTERN));
				}

				questionnaireAnswerDat.setQuestionsId(jb
						.getString("questionsId"));

				questionnaireAnswerDats.add(questionnaireAnswerDat);
			}

			return questionnaireAnswerDats;
		}

		return null;
	}

}
