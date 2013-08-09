/**
 * 
 */
package com.wuxi.domain;

import java.util.List;

/**
 * 访谈实录返回数据包装类
 * 
 * @author 智佳 罗森
 * @createtime 2013年8月9日 19:30
 * 
 */
public class MemoirWrapper extends CommonDataWrapper {

	//实录数据列表
	private List<Memoir> memoirs;

	/**
	 * @return the memoirs
	 */
	public List<Memoir> getMemoirs() {
		return memoirs;
	}

	/**
	 * @param memoirs
	 *            the memoirs to set
	 */
	public void setMemoirs(List<Memoir> memoirs) {
		this.memoirs = memoirs;
	}

	/**
	 * 
	 * 内部类，访谈实录单条数据实体类
	 * 
	 * @author 智佳 罗森
	 * @createtime 2013年8月9日 19:34
	 * 
	 */
	public class Memoir {

		// 访谈实录的编号
		private String id;
		// 内容
		private String content;
		// 对应直播编号
		private String interViewId;
		// 提交时间
		private String submitTime;
		// 发言人
		private String answerUser;
		// 类型,0:主持人(问),1:嘉宾(答)
		private int answerType;

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
		 * @return the answerUser
		 */
		public String getAnswerUser() {
			return answerUser;
		}

		/**
		 * @param answerUser
		 *            the answerUser to set
		 */
		public void setAnswerUser(String answerUser) {
			this.answerUser = answerUser;
		}

		/**
		 * @return the answerType
		 */
		public int getAnswerType() {
			return answerType;
		}

		/**
		 * @param answerType
		 *            the answerType to set
		 */
		public void setAnswerType(int answerType) {
			this.answerType = answerType;
		}

	}

}
