/**
 * 
 */
package com.wuxi.domain;

import java.util.List;

/**
 * 走进直播间 往期回顾 数据集实体类
 * 
 * @author 智佳 罗森
 * 
 */
public class VedioReviewWrapper extends CommonDataWrapper {

	private List<VedioReview> reviews;

	/**
	 * @return the reviews
	 */
	public List<VedioReview> getReviews() {
		return reviews;
	}

	/**
	 * @param reviews
	 *            the reviews to set
	 */
	public void setReviews(List<VedioReview> reviews) {
		this.reviews = reviews;
	}

	/**
	 * 内部类，往期回顾单条数据实体类
	 * 
	 * @author 智佳 罗森
	 * 
	 */
	public class VedioReview {

		// 直播编号
		private String id;
		// 主题
		private String subject;
		// 嘉宾
		private String guests;
		// 访谈时间
		private String workDate;
		// 主要内容
		private String subjectContent;
		// 开始时间
		private String beginTime;
		// 结束时间
		private String endTime;

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
		 * @return the subject
		 */
		public String getSubject() {
			return subject;
		}

		/**
		 * @param subject
		 *            the subject to set
		 */
		public void setSubject(String subject) {
			this.subject = subject;
		}

		/**
		 * @return the guests
		 */
		public String getGuests() {
			return guests;
		}

		/**
		 * @param guests
		 *            the guests to set
		 */
		public void setGuests(String guests) {
			this.guests = guests;
		}

		/**
		 * @return the workDate
		 */
		public String getWorkDate() {
			return workDate;
		}

		/**
		 * @param workDate
		 *            the workDate to set
		 */
		public void setWorkDate(String workDate) {
			this.workDate = workDate;
		}

		/**
		 * @return the subjectContent
		 */
		public String getSubjectContent() {
			return subjectContent;
		}

		/**
		 * @param subjectContent
		 *            the subjectContent to set
		 */
		public void setSubjectContent(String subjectContent) {
			this.subjectContent = subjectContent;
		}

		/**
		 * @return the beginTime
		 */
		public String getBeginTime() {
			return beginTime;
		}

		/**
		 * @param beginTime
		 *            the beginTime to set
		 */
		public void setBeginTime(String beginTime) {
			this.beginTime = beginTime;
		}

		/**
		 * @return the endTime
		 */
		public String getEndTime() {
			return endTime;
		}

		/**
		 * @param endTime
		 *            the endTime to set
		 */
		public void setEndTime(String endTime) {
			this.endTime = endTime;
		}

	}
}
