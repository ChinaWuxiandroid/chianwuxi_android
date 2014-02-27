package com.wuxi.domain;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * 网上调查详细数据解析
 * 
 * @类名： OnlineSurveyOptionesInfo
 * @作者： 陈彬
 * @创建时间： 2013 2013-11-19 下午3:33:21
 * @修改时间：
 * @修改描述：
 */
public class OnlineSurveyOptionesInfo {

	private String serialNumber;
	private String optionValue;
	private String questionId;
	private String optionId;
	private String clickCount;
	private String linkUrl;

	/**
	 * @return serialNumber
	 */
	public String getSerialNumber() {
		return serialNumber;
	}

	/**
	 * @param serialNumber
	 *            要设置的 serialNumber
	 */
	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
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
	 * @return optionId
	 */
	public String getOptionId() {
		return optionId;
	}

	/**
	 * @param optionId
	 *            要设置的 optionId
	 */
	public void setOptionId(String optionId) {
		this.optionId = optionId;
	}

	/**
	 * @return clickCount
	 */
	public String getClickCount() {
		return clickCount;
	}

	/**
	 * @param clickCount
	 *            要设置的 clickCount
	 */
	public void setClickCount(String clickCount) {
		this.clickCount = clickCount;
	}

	/**
	 * @return linkUrl
	 */
	public String getLinkUrl() {
		return linkUrl;
	}

	/**
	 * @param linkUrl
	 *            要设置的 linkUrl
	 */
	public void setLinkUrl(String linkUrl) {
		this.linkUrl = linkUrl;
	}

	/**
	 * 解析数据
	 * 
	 * @方法： resolveData
	 * @param array
	 * @return
	 */
	public ArrayList<OnlineSurveyOptionesInfo> resolveData(String data) {
		ArrayList<OnlineSurveyOptionesInfo> mArrayList = new ArrayList<OnlineSurveyOptionesInfo>();
		// 得到Type对象
		Type type = new TypeToken<LinkedList<OnlineSurveyOptionesInfo>>() {
		}.getType();
		// 获取Gson对象
		Gson gson = new Gson();
		// 把Json数据转换为BearingImageInfo对象
		LinkedList<OnlineSurveyOptionesInfo> infos = gson.fromJson(data, type);
		for (Iterator<OnlineSurveyOptionesInfo> iterator = infos.iterator(); iterator
				.hasNext();) {
			// 读取对象中的数据
			OnlineSurveyOptionesInfo info = (OnlineSurveyOptionesInfo) iterator
					.next();
			// 把读取出来的数据装到实体类中
			mArrayList.add(info);
		}

		// 排序
		if (mArrayList.size() > 0) {
			Collections.sort(mArrayList, comparator);
		}

		return mArrayList;
	}

	/**
	 * 
	 * 排序
	 * 
	 */
	Comparator<OnlineSurveyOptionesInfo> comparator = new Comparator<OnlineSurveyOptionesInfo>() {
		@Override
		public int compare(OnlineSurveyOptionesInfo lhs,
				OnlineSurveyOptionesInfo rhs) {
			if (lhs.getSerialNumber() != rhs.getSerialNumber()) {
				int len2 = lhs.getSerialNumber().compareToIgnoreCase(
						rhs.getSerialNumber());
				return len2;
			} else {
				return 0;
			}
		}
	};

}
