/**
 * 
 */
package com.wuxi.domain;

import java.io.Serializable;
import java.util.List;

/**
 * 网上调查 列表实体类
 * 
 * @author 智佳 罗森
 * 
 */
public class InternetSurveyWrapper extends CommonDataWrapper {

	private List<InternetSurvey> internetSurveys;

	/**
	 * @return the internetSurveys
	 */
	public List<InternetSurvey> getInternetSurveys() {
		return internetSurveys;
	}

	/**
	 * @param internetSurveys
	 *            the internetSurveys to set
	 */
	public void setInternetSurveys(List<InternetSurvey> internetSurveys) {
		this.internetSurveys = internetSurveys;
	}

	public class InternetSurvey implements Serializable {

		/**
		 * serialVersionUID
		 */
		private static final long serialVersionUID = 1L;

		private String createDate;
		private String title;
		private String questions;
		private String doProjectId;
		private String depId;
		private String readCount;
		private String endDate;
		private String results;
		private String isAnonymous;
		private String orderId;
		private String isTop;
		private String summary;
		private String surveryId;
		private String author;
		private String isEnabled;
		private String updateDate;
		private String isViewSurveryResult;
		private String isAuditingInputText;
		private String isRootDisplay;
		private String isCenterData;
		private String isOnlyShowNo;

		/**
		 * @return the createDate
		 */
		public String getCreateDate() {
			return createDate;
		}

		/**
		 * @param createDate
		 *            the createDate to set
		 */
		public void setCreateDate(String createDate) {
			this.createDate = createDate;
		}

		/**
		 * @return the title
		 */
		public String getTitle() {
			return title;
		}

		/**
		 * @param title
		 *            the title to set
		 */
		public void setTitle(String title) {
			this.title = title;
		}

		/**
		 * @return the questions
		 */
		public String getQuestions() {
			return questions;
		}

		/**
		 * @param questions
		 *            the questions to set
		 */
		public void setQuestions(String questions) {
			this.questions = questions;
		}

		/**
		 * @return the doProjectId
		 */
		public String getDoProjectId() {
			return doProjectId;
		}

		/**
		 * @param doProjectId
		 *            the doProjectId to set
		 */
		public void setDoProjectId(String doProjectId) {
			this.doProjectId = doProjectId;
		}

		/**
		 * @return the depId
		 */
		public String getDepId() {
			return depId;
		}

		/**
		 * @param depId
		 *            the depId to set
		 */
		public void setDepId(String depId) {
			this.depId = depId;
		}

		/**
		 * @return the readCount
		 */
		public String getReadCount() {
			return readCount;
		}

		/**
		 * @param readCount
		 *            the readCount to set
		 */
		public void setReadCount(String readCount) {
			this.readCount = readCount;
		}

		/**
		 * @return the endDate
		 */
		public String getEndDate() {
			return endDate;
		}

		/**
		 * @param endDate
		 *            the endDate to set
		 */
		public void setEndDate(String endDate) {
			this.endDate = endDate;
		}

		/**
		 * @return the results
		 */
		public String getResults() {
			return results;
		}

		/**
		 * @param results
		 *            the results to set
		 */
		public void setResults(String results) {
			this.results = results;
		}

		/**
		 * @return the isAnonymous
		 */
		public String getIsAnonymous() {
			return isAnonymous;
		}

		/**
		 * @param isAnonymous
		 *            the isAnonymous to set
		 */
		public void setIsAnonymous(String isAnonymous) {
			this.isAnonymous = isAnonymous;
		}

		/**
		 * @return the orderId
		 */
		public String getOrderId() {
			return orderId;
		}

		/**
		 * @param orderId
		 *            the orderId to set
		 */
		public void setOrderId(String orderId) {
			this.orderId = orderId;
		}

		/**
		 * @return the isTop
		 */
		public String getIsTop() {
			return isTop;
		}

		/**
		 * @param isTop
		 *            the isTop to set
		 */
		public void setIsTop(String isTop) {
			this.isTop = isTop;
		}

		/**
		 * @return the summary
		 */
		public String getSummary() {
			return summary;
		}

		/**
		 * @param summary
		 *            the summary to set
		 */
		public void setSummary(String summary) {
			this.summary = summary;
		}

		/**
		 * @return the surveryId
		 */
		public String getSurveryId() {
			return surveryId;
		}

		/**
		 * @param surveryId
		 *            the surveryId to set
		 */
		public void setSurveryId(String surveryId) {
			this.surveryId = surveryId;
		}

		/**
		 * @return the author
		 */
		public String getAuthor() {
			return author;
		}

		/**
		 * @param author
		 *            the author to set
		 */
		public void setAuthor(String author) {
			this.author = author;
		}

		/**
		 * @return the isEnabled
		 */
		public String getIsEnabled() {
			return isEnabled;
		}

		/**
		 * @param isEnabled
		 *            the isEnabled to set
		 */
		public void setIsEnabled(String isEnabled) {
			this.isEnabled = isEnabled;
		}

		/**
		 * @return the updateDate
		 */
		public String getUpdateDate() {
			return updateDate;
		}

		/**
		 * @param updateDate
		 *            the updateDate to set
		 */
		public void setUpdateDate(String updateDate) {
			this.updateDate = updateDate;
		}

		/**
		 * @return the isViewSurveryResult
		 */
		public String getIsViewSurveryResult() {
			return isViewSurveryResult;
		}

		/**
		 * @param isViewSurveryResult
		 *            the isViewSurveryResult to set
		 */
		public void setIsViewSurveryResult(String isViewSurveryResult) {
			this.isViewSurveryResult = isViewSurveryResult;
		}

		/**
		 * @return the isAuditingInputText
		 */
		public String getIsAuditingInputText() {
			return isAuditingInputText;
		}

		/**
		 * @param isAuditingInputText
		 *            the isAuditingInputText to set
		 */
		public void setIsAuditingInputText(String isAuditingInputText) {
			this.isAuditingInputText = isAuditingInputText;
		}

		/**
		 * @return the isRootDisplay
		 */
		public String getIsRootDisplay() {
			return isRootDisplay;
		}

		/**
		 * @param isRootDisplay
		 *            the isRootDisplay to set
		 */
		public void setIsRootDisplay(String isRootDisplay) {
			this.isRootDisplay = isRootDisplay;
		}

		/**
		 * @return the isCenterData
		 */
		public String getIsCenterData() {
			return isCenterData;
		}

		/**
		 * @param isCenterData
		 *            the isCenterData to set
		 */
		public void setIsCenterData(String isCenterData) {
			this.isCenterData = isCenterData;
		}

		/**
		 * @return the isOnlyShowNo
		 */
		public String getIsOnlyShowNo() {
			return isOnlyShowNo;
		}

		/**
		 * @param isOnlyShowNo
		 *            the isOnlyShowNo to set
		 */
		public void setIsOnlyShowNo(String isOnlyShowNo) {
			this.isOnlyShowNo = isOnlyShowNo;
		}

	}

}
