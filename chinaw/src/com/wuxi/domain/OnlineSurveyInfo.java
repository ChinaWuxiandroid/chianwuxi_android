package com.wuxi.domain;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.widget.Toast;

/**
 * 
 * 网上调查详细页面的数据解析
 * 
 * @类名： OnlineSurveyInfo
 * @作者： 陈彬
 * @创建时间： 2013 2013-11-19 上午11:31:53
 * @修改时间：
 * @修改描述：
 */
public class OnlineSurveyInfo {

	// 需要解析的json数据
	private String json;

	private Context mContext;

	OnlineSurveyInfo mInfo = null;

	private OnlineSurveyInfo() {
	}

	/**
	 * 获取实例
	 * 
	 * @方法：
	 * @描述：
	 * @param json
	 *            需要解析的json数据
	 * @param mContext
	 *            当前上下文
	 */
	public OnlineSurveyInfo(String json, Context mContext) {
		this.json = json;
		this.mContext = mContext;
		if (mInfo == null) {
			mInfo = new OnlineSurveyInfo();
		}
		// 解析数据
		resolveJson(json);
	}

	/*--------------------第一级json数据的解析-------------------------*/
	private String createDate;

	/**
	 * @return 获取当前时间
	 */
	public String getCreateDate() {
		return createDate;
	}

	/**
	 * @param createDate
	 *            要设置的 当前时间
	 */
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	/**
	 * 解析第一级json数据
	 * 
	 * @方法： resolveData
	 */
	private void resolveJson(String json) {
		try {
			JSONObject object = new JSONObject(json);
			boolean success = object.getBoolean("success");
			if (success) {
				JSONObject jsonObject = object.getJSONObject("result");
				if (jsonObject != null) {
					// 调用方法，解析数据
					resolveResultData(jsonObject);
				}
				if (isNULL(object.getString("createDate"))) {
					mInfo.setCreateDate(object.getString("createDate"));
				}
			} else {
				String nullStr = object.getString("null");
				Toast.makeText(mContext, "" + nullStr, Toast.LENGTH_SHORT)
						.show();
			}

		} catch (JSONException e) {
			e.printStackTrace();
			System.out.println("解析数据异常信息：" + e.getMessage());
		}
	}

	/*-----------------------第二级json数据的解析-----------------------*/

	private String res_createDate = null;

	/**
	 * @return res_createDate
	 */
	public String getRes_createDate() {
		return res_createDate;
	}

	/**
	 * @param res_createDate
	 *            要设置的 res_createDate
	 */
	public void setRes_createDate(String res_createDate) {
		this.res_createDate = res_createDate;
	}

	private String res_title = null;

	/**
	 * @return res_title
	 */
	public String getRes_title() {
		return res_title;
	}

	/**
	 * @param res_title
	 *            要设置的 res_title
	 */
	public void setRes_title(String res_title) {
		this.res_title = res_title;
	}

	private String res_doProjectId = null;

	/**
	 * @return res_doProjectId
	 */
	public String getRes_doProjectId() {
		return res_doProjectId;
	}

	/**
	 * @param res_doProjectId
	 *            要设置的 res_doProjectId
	 */
	public void setRes_doProjectId(String res_doProjectId) {
		this.res_doProjectId = res_doProjectId;
	}

	private String res_depId = null;

	/**
	 * @return res_depId
	 */
	public String getRes_depId() {
		return res_depId;
	}

	/**
	 * @param res_depId
	 *            要设置的 res_depId
	 */
	public void setRes_depId(String res_depId) {
		this.res_depId = res_depId;
	}

	private int res_readCount = 0;

	/**
	 * @return res_readCount
	 */
	public int getRes_readCount() {
		return res_readCount;
	}

	/**
	 * @param res_readCount
	 *            要设置的 res_readCount
	 */
	public void setRes_readCount(int res_readCount) {
		this.res_readCount = res_readCount;
	}

	private String res_endDate = null;

	/**
	 * @return res_endDate
	 */
	public String getRes_endDate() {
		return res_endDate;
	}

	/**
	 * @param res_endDate
	 *            要设置的 res_endDate
	 */
	public void setRes_endDate(String res_endDate) {
		this.res_endDate = res_endDate;
	}

	private int res_isAnonymous = 0;

	/*
	 * 解析第二级json数据
	 */
	/**
	 * @return res_isAnonymous
	 */
	public int getRes_isAnonymous() {
		return res_isAnonymous;
	}

	private String res_orderId = null;

	/**
	 * @return res_orderId
	 */
	public String getRes_orderId() {
		return res_orderId;
	}

	/**
	 * @param res_orderId
	 *            要设置的 res_orderId
	 */
	public void setRes_orderId(String res_orderId) {
		this.res_orderId = res_orderId;
	}

	private String res_isTop = null;

	/**
	 * @return res_isTop
	 */
	public String getRes_isTop() {
		return res_isTop;
	}

	/**
	 * @param res_isTop
	 *            要设置的 res_isTop
	 */
	public void setRes_isTop(String res_isTop) {
		this.res_isTop = res_isTop;
	}

	/**
	 * @param res_isAnonymous
	 *            要设置的 res_isAnonymous
	 */
	public void setRes_isAnonymous(int res_isAnonymous) {
		this.res_isAnonymous = res_isAnonymous;
	}

	private String res_summary = null;

	/**
	 * @return res_summary
	 */
	public String getRes_summary() {
		return res_summary;
	}

	/**
	 * @param res_summary
	 *            要设置的 res_summary
	 */
	public void setRes_summary(String res_summary) {
		this.res_summary = res_summary;
	}

	private String res_surveryId = null;

	/**
	 * @return res_surveryId
	 */
	public String getRes_surveryId() {
		return res_surveryId;
	}

	/**
	 * @param res_surveryId
	 *            要设置的 res_surveryId
	 */
	public void setRes_surveryId(String res_surveryId) {
		this.res_surveryId = res_surveryId;
	}

	private String res_author = null;

	/**
	 * @return res_author
	 */
	public String getRes_author() {
		return res_author;
	}

	/**
	 * @param res_author
	 *            要设置的 res_author
	 */
	public void setRes_author(String res_author) {
		this.res_author = res_author;
	}

	private String res_isEnabled = null;

	/**
	 * @return res_isEnabled
	 */
	public String getRes_isEnabled() {
		return res_isEnabled;
	}

	/**
	 * @param res_isEnabled
	 *            要设置的 res_isEnabled
	 */
	public void setRes_isEnabled(String res_isEnabled) {
		this.res_isEnabled = res_isEnabled;
	}

	private String res_updateDate = null;

	/**
	 * @return res_updateDate
	 */
	public String getRes_updateDate() {
		return res_updateDate;
	}

	/**
	 * @param res_updateDate
	 *            要设置的 res_updateDate
	 */
	public void setRes_updateDate(String res_updateDate) {
		this.res_updateDate = res_updateDate;
	}

	private String res_isViewSurveryResult = null;

	/**
	 * @return res_isViewSurveryResult
	 */
	public String getRes_isViewSurveryResult() {
		return res_isViewSurveryResult;
	}

	/**
	 * @param res_isViewSurveryResult
	 *            要设置的 res_isViewSurveryResult
	 */
	public void setRes_isViewSurveryResult(String res_isViewSurveryResult) {
		this.res_isViewSurveryResult = res_isViewSurveryResult;
	}

	private String res_isAuditingInputText = null;

	/**
	 * @return res_isAuditingInputText
	 */
	public String getRes_isAuditingInputText() {
		return res_isAuditingInputText;
	}

	/**
	 * @param res_isAuditingInputText
	 *            要设置的 res_isAuditingInputText
	 */
	public void setRes_isAuditingInputText(String res_isAuditingInputText) {
		this.res_isAuditingInputText = res_isAuditingInputText;
	}

	private String res_isRootDisplay = null;

	/**
	 * @return res_isRootDisplay
	 */
	public String getRes_isRootDisplay() {
		return res_isRootDisplay;
	}

	/**
	 * @param res_isRootDisplay
	 *            要设置的 res_isRootDisplay
	 */
	public void setRes_isRootDisplay(String res_isRootDisplay) {
		this.res_isRootDisplay = res_isRootDisplay;
	}

	private String res_isCenterData = null;

	/**
	 * @return res_isCenterData
	 */
	public String getRes_isCenterData() {
		return res_isCenterData;
	}

	/**
	 * @param res_isCenterData
	 *            要设置的 res_isCenterData
	 */
	public void setRes_isCenterData(String res_isCenterData) {
		this.res_isCenterData = res_isCenterData;
	}

	private String res_isOnlyShowNo = null;

	/**
	 * @return res_isOnlyShowNo
	 */
	public String getRes_isOnlyShowNo() {
		return res_isOnlyShowNo;
	}

	/**
	 * @param res_isOnlyShowNo
	 *            要设置的 res_isOnlyShowNo
	 */
	public void setRes_isOnlyShowNo(String res_isOnlyShowNo) {
		this.res_isOnlyShowNo = res_isOnlyShowNo;
	}

	private String res_questions = null;

	/**
	 * @return res_questions
	 */
	public String getRes_questions() {
		return res_questions;
	}

	/**
	 * @param res_questions
	 *            要设置的 res_questions
	 */
	public void setRes_questions(String res_questions) {
		this.res_questions = res_questions;
	}

	/**
	 * 解析第二级json数据
	 * 
	 * @方法： resolveResultData
	 * @param object
	 */
	private void resolveResultData(JSONObject object) {
		try {
			// 时间
			if (isNULL(object.getString("createDate"))) {
				mInfo.setRes_createDate(object.getString("createDate"));
			}

			// 标题
			if (isNULL(object.getString("title"))) {
				mInfo.setRes_title(object.getString("title"));
			}

			if (isNULL(object.getString("questions"))) {
				mInfo.setRes_questions(object.getString("questions"));
			}

			// 解析数据
			// resolveQuestionsData();

			if (isNULL(object.getString("doProjectId"))) {
				mInfo.setRes_doProjectId(object.getString("doProjectId"));
			}

			if (isNULL(object.getString("depId"))) {
				mInfo.setRes_depId(object.getString("depId"));
			}

			setRes_readCount(object.getInt("readCount"));

			if (isNULL(object.getString("endDate"))) {
				mInfo.setRes_endDate(object.getString("endDate"));
			}

			// 解析数据
			resolveResultsData(object.getString("results"));

			mInfo.setRes_isAnonymous(object.getInt("isAnonymous"));

			if (isNULL(object.getString("orderId"))) {
				mInfo.setRes_orderId(object.getString("orderId"));
			}

			if (isNULL(object.getString("isTop"))) {
				mInfo.setRes_isTop(object.getString("isTop"));
			}

			if (isNULL(object.getString("summary"))) {
				mInfo.setRes_summary(object.getString("summary"));
			}

			if (isNULL(object.getString("surveryId"))) {
				mInfo.setRes_surveryId(object.getString("surveryId"));
			}

			if (isNULL(object.getString("author"))) {
				mInfo.setRes_author(object.getString("author"));
			}

			if (isNULL(object.getString("isEnabled"))) {
				mInfo.setRes_isEnabled(object.getString("isEnabled"));
			}

			if (isNULL(object.getString("updateDate"))) {
				mInfo.setRes_updateDate(object.getString("updateDate"));
			}

			if (isNULL(object.getString("isViewSurveryResult"))) {
				mInfo.setRes_isViewSurveryResult(object
						.getString("isViewSurveryResult"));
			}

			if (isNULL(object.getString("isAuditingInputText"))) {
				mInfo.setRes_isAuditingInputText(object
						.getString("isAuditingInputText"));
			}

			if (isNULL(object.getString("isRootDisplay"))) {
				mInfo.setRes_isRootDisplay(object.getString("isRootDisplay"));
			}

			if (isNULL(object.getString("isCenterData"))) {
				mInfo.setRes_isCenterData(object.getString("isCenterData"));
			}

			if (isNULL(object.getString("isOnlyShowNo"))) {
				mInfo.setRes_isOnlyShowNo(object.getString("isOnlyShowNo"));
			}

		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	/*----------------第三级json数据的解析---------------------*/

	/**
	 * 解析第三级json数据
	 * 
	 * @方法： resolveQuestionsData
	 * @param object
	 */
	// private void resolveQuestionsData(String data) {
	//
	// JSONObject object = null;
	//
	// JSONArray array = null;
	// try {
	//
	// array = new JSONArray(data);
	//
	// object = (JSONObject) array.get(0);
	//
	// if (isNULL(object.getString("description"))) {
	// mInfo.setqDescription(object.getString("description"));
	// }
	//
	// if (isNULL(object.getString("questionId"))) {
	// mInfo.setqQuestionId(object.getString("questionId"));
	// }
	//
	// if (isNULL(object.getString("orderId"))) {
	// mInfo.setqOrderId(object.getString("orderId"));
	// }
	//
	// if (isNULL(object.getString("questionType"))) {
	// mInfo.setqQuestionType(object.getString("questionType"));
	// }
	//
	// if (isNULL(object.getString("surveryId"))) {
	// mInfo.setqSurveryI(object.getString("surveryId"));
	// }
	//
	// if (isNULL(object.getString("optionCount"))) {
	// mInfo.setqOptionCount(object.getString("optionCount"));
	// }
	//
	// if (isNULL(object.getString("hasOther"))) {
	// mInfo.setqHasOther(object.getString("hasOther"));
	// }
	//
	// if (isNULL(object.getString("isForce"))) {
	// mInfo.setqIsForce(object.getString("isForce"));
	// }
	//
	// if (isNULL(object.getString("minSelect"))) {
	// mInfo.setqMinSelect(object.getString("minSelect"));
	// }
	//
	// if (isNULL(object.getString("maxSelect"))) {
	// mInfo.setqMaxSelect(object.getString("maxSelect"));
	// }
	//
	// if (isNULL(object.getString("colspan"))) {
	// mInfo.setqColspan(object.getString("colspan"));
	// }
	//
	// if (isNULL(object.getString("optiones"))) {
	// mInfo.setqOptiones(object.getString("optiones"));
	// }
	//
	// } catch (JSONException e) {
	// e.printStackTrace();
	// System.out.println("异常：" + e.getMessage());
	// }
	// }

	private String re_null = null;

	/**
	 * @return re_null
	 */
	public String getRe_null() {
		return re_null;
	}

	/**
	 * @param re_null
	 *            要设置的 re_null
	 */
	public void setRe_null(String re_null) {
		this.re_null = re_null;
	}

	private String re_end = null;

	/**
	 * @return re_end
	 */
	public String getRe_end() {
		return re_end;
	}

	/**
	 * @param re_end
	 *            要设置的 re_end
	 */
	public void setRe_end(String re_end) {
		this.re_end = re_end;
	}

	private String re_start;

	/**
	 * @return re_start
	 */
	public String getRe_start() {
		return re_start;
	}

	/**
	 * @param re_start
	 *            要设置的 re_start
	 */
	public void setRe_start(String re_start) {
		this.re_start = re_start;
	}

	private String re_previous = null;

	/**
	 * @return re_previous
	 */
	public String getRe_previous() {
		return re_previous;
	}

	/**
	 * @param re_previous
	 *            要设置的 re_previous
	 */
	public void setRe_previous(String re_previous) {
		this.re_previous = re_previous;
	}

	private String re_totalRowsAmount;

	/**
	 * @return re_totalRowsAmount
	 */
	public String getRe_totalRowsAmount() {
		return re_totalRowsAmount;
	}

	/**
	 * @param re_totalRowsAmount
	 *            要设置的 re_totalRowsAmount
	 */
	public void setRe_totalRowsAmount(String re_totalRowsAmount) {
		this.re_totalRowsAmount = re_totalRowsAmount;
	}

	private boolean re_next;

	/**
	 * @return re_next
	 */
	public boolean isRe_next() {
		return re_next;
	}

	/**
	 * @param re_next
	 *            要设置的 re_next
	 */
	public void setRe_next(boolean re_next) {
		this.re_next = re_next;
	}

	/**
	 * 解析第三级json数据
	 * 
	 * @方法： resolveResultsData
	 * @param object
	 */
	private void resolveResultsData(String data) {

		JSONObject object = null;

		try {

			object = new JSONObject(data);

			// 解析数据
			resolveDataData(object.getString("data"));

			if (isNULL(object.getString("null"))) {
				mInfo.setRe_null(object.getString("null"));
			}

			if (isNULL(object.getString("end"))) {
				mInfo.setRe_end(object.getString("end"));
			}

			if (isNULL(object.getString("start"))) {
				mInfo.setRe_start(object.getString("start"));
			}

			if (isNULL(object.getString("previous"))) {
				mInfo.setRe_previous(object.getString("previous"));
			}

			if (isNULL(object.getString("totalRowsAmount"))) {
				mInfo.setRe_totalRowsAmount(object.getString("totalRowsAmount"));
			}

			if (isNULL(object.getString("next"))) {
				mInfo.setRe_next(object.getBoolean("next"));
			}

		} catch (JSONException e) {
			e.printStackTrace();
			System.out.println("异常信息：" + e.getMessage());
		}
	}

	/*------------------------解析第四级json数据--------------------------*/

	private String data_userName = null;

	private String data_resultId = null;

	private String data_surveryId = null;

	private String data_submitDate = null;

	private String data_userHostAddress = null;

	/**
	 * @return data_userName
	 */
	public String getData_userName() {
		return data_userName;
	}

	/**
	 * @param data_userName
	 *            要设置的 data_userName
	 */
	public void setData_userName(String data_userName) {
		this.data_userName = data_userName;
	}

	/**
	 * @return data_resultId
	 */
	public String getData_resultId() {
		return data_resultId;
	}

	/**
	 * @param data_resultId
	 *            要设置的 data_resultId
	 */
	public void setData_resultId(String data_resultId) {
		this.data_resultId = data_resultId;
	}

	/**
	 * @return data_surveryId
	 */
	public String getData_surveryId() {
		return data_surveryId;
	}

	/**
	 * @param data_surveryId
	 *            要设置的 data_surveryId
	 */
	public void setData_surveryId(String data_surveryId) {
		this.data_surveryId = data_surveryId;
	}

	/**
	 * @return data_submitDate
	 */
	public String getData_submitDate() {
		return data_submitDate;
	}

	/**
	 * @param data_submitDate
	 *            要设置的 data_submitDate
	 */
	public void setData_submitDate(String data_submitDate) {
		this.data_submitDate = data_submitDate;
	}

	/**
	 * @return data_userHostAddress
	 */
	public String getData_userHostAddress() {
		return data_userHostAddress;
	}

	/**
	 * @param data_userHostAddress
	 *            要设置的 data_userHostAddress
	 */
	public void setData_userHostAddress(String data_userHostAddress) {
		this.data_userHostAddress = data_userHostAddress;
	}

	private String data_details = null;

	/**
	 * @return data_details
	 */
	public String getData_details() {
		return data_details;
	}

	/**
	 * @param data_details
	 *            要设置的 data_details
	 */
	public void setData_details(String data_details) {
		this.data_details = data_details;
	}

	/**
	 * 解析第四级json数据
	 * 
	 * @方法： resolveDataData
	 */
	private void resolveDataData(String data) {

		JSONObject object = null;

		JSONArray array = null;

		try {

			array = new JSONArray(data);

			object = (JSONObject) array.get(0);

			if (isNULL(object.getString("userName"))) {
				mInfo.setData_userName(object.getString("userName"));
			}

			if (isNULL(object.getString("resultId"))) {
				mInfo.setData_resultId(object.getString("resultId"));
			}

			if (isNULL(object.getString("surveryId"))) {
				mInfo.setData_surveryId(object.getString("surveryId"));
			}

			if (isNULL(object.getString("submitDate"))) {
				mInfo.setData_submitDate(object.getString("submitDate"));
			}

			if (isNULL(object.getString("userHostAddress"))) {
				mInfo.setData_userHostAddress(object
						.getString("userHostAddress"));
			}

			if (isNULL(object.getString("details"))) {
				mInfo.setData_details(object.getString("details"));
			}

			// // 第五级json数据
			//
			// JSONArray array = new JSONArray(object.getString("details"));
			// // 解析数据
			// resolveDetails(array);

		} catch (JSONException e) {
			e.printStackTrace();
		}

	}

	/**
	 * 解析第五级json数据
	 * 
	 * @方法： resolveDetails
	 * @param array
	 * @return
	 */
	// public ArrayList<OnlineSurveyDetailsInfo> resolveDetails(JSONArray array)
	// {
	// return new OnlineSurveyDetailsInfo().resolveData(array);
	// }

	/**
	 * 获取数据
	 * 
	 * @方法： getData
	 * @return
	 */
	public OnlineSurveyInfo getData() {
		return mInfo;
	}

	/**
	 * 检查字符串是否为空
	 * 
	 * @方法： isNULL
	 * @param str
	 * @return
	 */
	private boolean isNULL(String str) {
		if (str == null || str == "" || str.equals(null) || str.equals("")
				|| str.length() < 0) {
			return false;
		} else {
			return true;
		}
	}

}
