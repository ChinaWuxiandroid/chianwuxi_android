/**
 * 
 */
package com.wuxi.domain;

import java.util.List;

/**
 * 政民互动 热点话题 回复数据集 实体类
 * 
 * @author 智佳 罗森
 * 
 */
public class HotReviewReplyWrapper extends CommonDataWrapper {

	private List<HotReviewReply> hotReviewReplies;

	public class HotReviewReply {

		private String id;
		private String content;
		private String hotreviewid;
		private String senduser;
		private String sendtime;
		private String answercontent;

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
		 * @return the hotreviewid
		 */
		public String getHotreviewid() {
			return hotreviewid;
		}

		/**
		 * @param hotreviewid
		 *            the hotreviewid to set
		 */
		public void setHotreviewid(String hotreviewid) {
			this.hotreviewid = hotreviewid;
		}

		/**
		 * @return the senduser
		 */
		public String getSenduser() {
			return senduser;
		}

		/**
		 * @param senduser
		 *            the senduser to set
		 */
		public void setSenduser(String senduser) {
			this.senduser = senduser;
		}

		/**
		 * @return the sendtime
		 */
		public String getSendtime() {
			return sendtime;
		}

		/**
		 * @param sendtime
		 *            the sendtime to set
		 */
		public void setSendtime(String sendtime) {
			this.sendtime = sendtime;
		}

		/**
		 * @return the answercontent
		 */
		public String getAnswercontent() {
			return answercontent;
		}

		/**
		 * @param answercontent
		 *            the answercontent to set
		 */
		public void setAnswercontent(String answercontent) {
			this.answercontent = answercontent;
		}

	}

	/**
	 * @return the hotReviewReplies
	 */
	public List<HotReviewReply> getHotReviewReplies() {
		return hotReviewReplies;
	}

	/**
	 * @param hotReviewReplies
	 *            the hotReviewReplies to set
	 */
	public void setHotReviewReplies(List<HotReviewReply> hotReviewReplies) {
		this.hotReviewReplies = hotReviewReplies;
	}
}
