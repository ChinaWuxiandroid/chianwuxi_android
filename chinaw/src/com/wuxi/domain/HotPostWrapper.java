/**
 * 
 */
package com.wuxi.domain;

import java.util.List;

/**
 * 热点话题类帖子数据集实体类
 * 
 * @author 智佳 罗森
 * 
 */
public class HotPostWrapper {

	private String id;
	private String content;
	private String endTime;
	private String title;
	private String depName;
	private String canReply;
	private HotPostReplyWrapper hotPostReplyWrapper;

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
	 * @return the depName
	 */
	public String getDepName() {
		return depName;
	}

	/**
	 * @param depName
	 *            the depName to set
	 */
	public void setDepName(String depName) {
		this.depName = depName;
	}

	/**
	 * @return the canReply
	 */
	public String getCanReply() {
		return canReply;
	}

	/**
	 * @param canReply
	 *            the canReply to set
	 */
	public void setCanReply(String canReply) {
		this.canReply = canReply;
	}

	/**
	 * @return the hotPostReplyWrapper
	 */
	public HotPostReplyWrapper getHotPostReplyWrapper() {
		return hotPostReplyWrapper;
	}

	/**
	 * @param hotPostReplyWrapper
	 *            the hotPostReplyWrapper to set
	 */
	public void setHotPostReplyWrapper(HotPostReplyWrapper hotPostReplyWrapper) {
		this.hotPostReplyWrapper = hotPostReplyWrapper;
	}

	/**
	 * 内部类，热点话题类帖子回复数据集实体类
	 * 
	 * @author 智佳 罗森
	 * 
	 */
	public class HotPostReplyWrapper extends CommonDataWrapper {

		private List<HotPostReply> hotPostReplies;

		/**
		 * @return the hotPostReplies
		 */
		public List<HotPostReply> getHotPostReplies() {
			return hotPostReplies;
		}

		/**
		 * @param hotPostReplies
		 *            the hotPostReplies to set
		 */
		public void setHotPostReplies(List<HotPostReply> hotPostReplies) {
			this.hotPostReplies = hotPostReplies;
		}

		/**
		 * 内部类，热点话题类帖子回复单条数据实体类
		 * 
		 * @author Administrator
		 * 
		 */
		public class HotPostReply {

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
	}

}
