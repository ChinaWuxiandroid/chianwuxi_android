package com.wuxi.domain;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

/**
 * 网上调查详细数据解析
 * 
 * @类名： OnlineSurveyQuestionsInfo
 * @作者： 陈彬
 * @创建时间： 2013 2013-11-20 上午9:25:59
 * @修改时间：
 * @修改描述：
 */
public class OnlineSurveyQuestionsInfo {

	private Context mContext;

	public OnlineSurveyQuestionsInfo(Context context) {
		this.mContext = context;
	}

	public OnlineSurveyQuestionsInfo() {
	}

	private String description = null;
	private String questionId = null;
	private String orderId = null;
	private String questionType = null;
	private String surveryId = null;
	private String optionCount = null;
	private String hasOther = null;
	private String isForce = null;
	private String minSelect = null;
	private String maxSelect = null;
	private String colspan = null;
	private String optiones = null;

	/**
	 * @return description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description
	 *            要设置的 description
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return questionId
	 */
	public String getQuestionId() {
		return questionId;
	}

	/**
	 * @param questionId
	 *            要设置的 questionId
	 */
	public void setQuestionId(String questionId) {
		this.questionId = questionId;
	}

	/**
	 * @return orderId
	 */
	public String getOrderId() {
		return orderId;
	}

	/**
	 * @param orderId
	 *            要设置的 orderId
	 */
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	/**
	 * @return questionType
	 */
	public String getQuestionType() {
		return questionType;
	}

	/**
	 * @param questionType
	 *            要设置的 questionType
	 */
	public void setQuestionType(String questionType) {
		this.questionType = questionType;
	}

	/**
	 * @return surveryId
	 */
	public String getSurveryId() {
		return surveryId;
	}

	/**
	 * @param surveryId
	 *            要设置的 surveryId
	 */
	public void setSurveryId(String surveryId) {
		this.surveryId = surveryId;
	}

	/**
	 * @return optionCount
	 */
	public String getOptionCount() {
		return optionCount;
	}

	/**
	 * @param optionCount
	 *            要设置的 optionCount
	 */
	public void setOptionCount(String optionCount) {
		this.optionCount = optionCount;
	}

	/**
	 * @return hasOther
	 */
	public String getHasOther() {
		return hasOther;
	}

	/**
	 * @param hasOther
	 *            要设置的 hasOther
	 */
	public void setHasOther(String hasOther) {
		this.hasOther = hasOther;
	}

	/**
	 * @return isForce
	 */
	public String getIsForce() {
		return isForce;
	}

	/**
	 * @param isForce
	 *            要设置的 isForce
	 */
	public void setIsForce(String isForce) {
		this.isForce = isForce;
	}

	/**
	 * @return minSelect
	 */
	public String getMinSelect() {
		return minSelect;
	}

	/**
	 * @param minSelect
	 *            要设置的 minSelect
	 */
	public void setMinSelect(String minSelect) {
		this.minSelect = minSelect;
	}

	/**
	 * @return maxSelect
	 */
	public String getMaxSelect() {
		return maxSelect;
	}

	/**
	 * @param maxSelect
	 *            要设置的 maxSelect
	 */
	public void setMaxSelect(String maxSelect) {
		this.maxSelect = maxSelect;
	}

	/**
	 * @return colspan
	 */
	public String getColspan() {
		return colspan;
	}

	/**
	 * @param colspan
	 *            要设置的 colspan
	 */
	public void setColspan(String colspan) {
		this.colspan = colspan;
	}

	/**
	 * @return optiones
	 */
	public String getOptiones() {
		return optiones;
	}

	/**
	 * @param optiones
	 *            要设置的 optiones
	 */
	public void setOptiones(String optiones) {
		this.optiones = optiones;
	}

	/**
	 * 解析数据
	 * 
	 * @方法： resolveData
	 * @param array
	 * @return
	 */
	public ArrayList<OnlineSurveyQuestionsInfo> resolveData(String data) {
		ArrayList<OnlineSurveyQuestionsInfo> mArrayList = new ArrayList<OnlineSurveyQuestionsInfo>();
		JSONArray array = null;
		JSONObject object = null;
		try {
			array = new JSONArray(data);
			for (int i = 0; i < array.length(); i++) {
				object = (JSONObject) array.get(i);
				OnlineSurveyQuestionsInfo mInfo = new OnlineSurveyQuestionsInfo();
				mInfo.setDescription(object.getString("description"));
				mInfo.setQuestionId(object.getString("questionId"));
				mInfo.setOrderId(object.getString("orderId"));
				mInfo.setQuestionType(object.getString("questionType"));
				mInfo.setSurveryId(object.getString("surveryId"));
				mInfo.setOptionCount(object.getString("optionCount"));
				mInfo.setHasOther(object.getString("hasOther"));
				mInfo.setIsForce(object.getString("isForce"));
				mInfo.setMinSelect(object.getString("minSelect"));
				mInfo.setMaxSelect(object.getString("maxSelect"));
				mInfo.setColspan(object.getString("colspan"));
				mInfo.setOptiones(object.getString("optiones"));
				mArrayList.add(mInfo);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}

		return mArrayList;

		// System.out.println("数据的长度：" + array.length());
		//
		// if (array.length() > 1) {
		//
		// // 得到Type对象
		// Type type = new TypeToken<LinkedList<OnlineSurveyQuestionsInfo>>() {
		// }.getType();
		// // 获取Gson对象
		// Gson gson = new Gson();
		// // 把Json数据转换为BearingImageInfo对象
		// LinkedList<OnlineSurveyQuestionsInfo> infos = null;
		// try {
		// infos = gson.fromJson(data, type);
		// } catch (Exception e) {
		// e.printStackTrace();
		// }
		//
		// for (Iterator<OnlineSurveyQuestionsInfo> iterator = infos
		// .iterator(); iterator.hasNext();) {
		// // 读取对象中的数据
		// OnlineSurveyQuestionsInfo info = (OnlineSurveyQuestionsInfo) iterator
		// .next();
		// // 把读取出来的数据装到实体类中
		// mArrayList.add(info);
		// }
		// } else {
		// Gson gson = new Gson();
		// OnlineSurveyQuestionsInfo mInfo = null;
		// try {
		// mInfo = gson.fromJson(data, OnlineSurveyQuestionsInfo.class);
		// } catch (Exception e) {
		// e.printStackTrace();
		// }
		// mArrayList.add(mInfo);
		// }
		// return mArrayList;
	}
}
