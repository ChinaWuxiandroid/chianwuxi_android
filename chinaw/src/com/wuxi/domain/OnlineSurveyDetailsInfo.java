package com.wuxi.domain;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * 网上调查详细数据解析
 * 
 * @类名： OnlineSurveyDetailsInfo
 * @作者： 陈彬
 * @创建时间： 2013 2013-11-19 下午3:34:17
 * @修改时间：
 * @修改描述：
 */
public class OnlineSurveyDetailsInfo {

	private String state;
	private String userName;
	private String optionValue;
	private String resultId;
	private String orderId;
	private String optionText;
	private String submitDate;
	private String questionsId;

	/**
	 * @return state
	 */
	public String getState() {
		return state;
	}

	/**
	 * @param state
	 *            要设置的 state
	 */
	public void setState(String state) {
		this.state = state;
	}

	/**
	 * @return userName
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * @param userName
	 *            要设置的 userName
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * @return optionValue
	 */
	public String getOptionValue() {
		return optionValue;
	}

	/**
	 * @param optionValue
	 *            要设置的 optionValue
	 */
	public void setOptionValue(String optionValue) {
		this.optionValue = optionValue;
	}

	/**
	 * @return resultId
	 */
	public String getResultId() {
		return resultId;
	}

	/**
	 * @param resultId
	 *            要设置的 resultId
	 */
	public void setResultId(String resultId) {
		this.resultId = resultId;
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
	 * @return optionText
	 */
	public String getOptionText() {
		return optionText;
	}

	/**
	 * @param optionText
	 *            要设置的 optionText
	 */
	public void setOptionText(String optionText) {
		this.optionText = optionText;
	}

	/**
	 * @return submitDate
	 */
	public String getSubmitDate() {
		return submitDate;
	}

	/**
	 * @param submitDate
	 *            要设置的 submitDate
	 */
	public void setSubmitDate(String submitDate) {
		this.submitDate = submitDate;
	}

	/**
	 * @return questionsId
	 */
	public String getQuestionsId() {
		return questionsId;
	}

	/**
	 * @param questionsId
	 *            要设置的 questionsId
	 */
	public void setQuestionsId(String questionsId) {
		this.questionsId = questionsId;
	}

	/**
	 * 解析数据
	 * 
	 * @方法： resolveData
	 * @return
	 */
	public ArrayList<OnlineSurveyDetailsInfo> resolveData(String data) {

		ArrayList<OnlineSurveyDetailsInfo> mArrayList = new ArrayList<OnlineSurveyDetailsInfo>();
		// 得到Type对象
		Type type = new TypeToken<LinkedList<OnlineSurveyDetailsInfo>>() {
		}.getType();
		// 获取Gson对象
		Gson gson = new Gson();
		// 把Json数据转换为BearingImageInfo对象
		LinkedList<OnlineSurveyDetailsInfo> infos = gson.fromJson(data, type);
		for (Iterator<OnlineSurveyDetailsInfo> iterator = infos.iterator(); iterator
				.hasNext();) {
			// 读取对象中的数据
			OnlineSurveyDetailsInfo info = (OnlineSurveyDetailsInfo) iterator
					.next();
			// 把读取出来的数据装到实体类中
			mArrayList.add(info);
		}
		return mArrayList;
	}
}
