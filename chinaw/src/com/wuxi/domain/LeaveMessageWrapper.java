/**
 * 
 */
package com.wuxi.domain;

import java.util.List;

/**
 * 栏目首页中留言提问数据包装类
 * 
 * @author 智佳 罗森
 * 
 */
public class LeaveMessageWrapper extends CommonDataWrapper {

	private List<LeaveMessage> leaveMessages;

	/**
	 * @return the leaveMessages
	 */
	public List<LeaveMessage> getLeaveMessages() {
		return leaveMessages;
	}

	/**
	 * @param leaveMessages
	 *            the leaveMessages to set
	 */
	public void setLeaveMessages(List<LeaveMessage> leaveMessages) {
		this.leaveMessages = leaveMessages;
	}

	/**
	 * 内部类，留言提问单条数据实体类
	 * @author Administrator
	 *
	 */
	public class LeaveMessage {

		// 问答编号
		private String id;
		// 问答状态(0：已审核 1：未审核 2：审核未通过)
		private int state;
		// 问题内容
		private String content;
		// 答题人用户编号
		private String answerId;
		// 直播编号
		private String interViewId;
		// 提交时间
		private String submitTime;
		// 提问用户
		private String sentUser;
		// 答复内容
		private String answerContent;
		// 回答状态(0:未回复,1:已回复)
		private int answerStatus;
		// 推荐状态(0:待处理,1:已推荐,2:已排除)
		private int isCommend;
		// 问题类型(1是指说说,2是指我要提问)
		private int questionType;
		// 推荐时间
		private String recommendTime;
		// 提问人IP
		private String sentIP;

		/**
		 * @return the id
		 */
		public String getId() {
			return id;
		}

		/**
		 * @param id
		 *            the id to set
		 */
		public void setId(String id) {
			this.id = id;
		}

		/**
		 * @return the state
		 */
		public int getState() {
			return state;
		}

		/**
		 * @param state
		 *            the state to set
		 */
		public void setState(int state) {
			this.state = state;
		}

		/**
		 * @return the content
		 */
		public String getContent() {
			return content;
		}

		/**
		 * @param content
		 *            the content to set
		 */
		public void setContent(String content) {
			this.content = content;
		}

		/**
		 * @return the answerId
		 */
		public String getAnswerId() {
			return answerId;
		}

		/**
		 * @param answerId
		 *            the answerId to set
		 */
		public void setAnswerId(String answerId) {
			this.answerId = answerId;
		}

		/**
		 * @return the interViewId
		 */
		public String getInterViewId() {
			return interViewId;
		}

		/**
		 * @param interViewId
		 *            the interViewId to set
		 */
		public void setInterViewId(String interViewId) {
			this.interViewId = interViewId;
		}

		/**
		 * @return the submitTime
		 */
		public String getSubmitTime() {
			return submitTime;
		}

		/**
		 * @param submitTime
		 *            the submitTime to set
		 */
		public void setSubmitTime(String submitTime) {
			this.submitTime = submitTime;
		}

		/**
		 * @return the sentUser
		 */
		public String getSentUser() {
			return sentUser;
		}

		/**
		 * @param sentUser
		 *            the sentUser to set
		 */
		public void setSentUser(String sentUser) {
			this.sentUser = sentUser;
		}

		/**
		 * @return the answerContent
		 */
		public String getAnswerContent() {
			return answerContent;
		}

		/**
		 * @param answerContent
		 *            the answerContent to set
		 */
		public void setAnswerContent(String answerContent) {
			this.answerContent = answerContent;
		}

		/**
		 * @return the answerStatus
		 */
		public int getAnswerStatus() {
			return answerStatus;
		}

		/**
		 * @param answerStatus
		 *            the answerStatus to set
		 */
		public void setAnswerStatus(int answerStatus) {
			this.answerStatus = answerStatus;
		}

		/**
		 * @return the isCommend
		 */
		public int getIsCommend() {
			return isCommend;
		}

		/**
		 * @param isCommend
		 *            the isCommend to set
		 */
		public void setIsCommend(int isCommend) {
			this.isCommend = isCommend;
		}

		/**
		 * @return the questionType
		 */
		public int getQuestionType() {
			return questionType;
		}

		/**
		 * @param questionType
		 *            the questionType to set
		 */
		public void setQuestionType(int questionType) {
			this.questionType = questionType;
		}

		/**
		 * @return the recommendTime
		 */
		public String getRecommendTime() {
			return recommendTime;
		}

		/**
		 * @param recommendTime
		 *            the recommendTime to set
		 */
		public void setRecommendTime(String recommendTime) {
			this.recommendTime = recommendTime;
		}

		/**
		 * @return the sentIP
		 */
		public String getSentIP() {
			return sentIP;
		}

		/**
		 * @param sentIP
		 *            the sentIP to set
		 */
		public void setSentIP(String sentIP) {
			this.sentIP = sentIP;
		}

	}
}
