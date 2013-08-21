/**
 * 
 */
package com.wuxi.domain;

import java.util.List;

/**
 * 政民互动 征求意见平台 民意征集 数据实体类
 * 
 * @author 智佳 罗森
 * 
 */
public class PepoleIdeaCollectWrapper {

	private String id;
	private String content;
	private String status;
	private String doprojectid;
	private String doAnonymous;
	private String readCount;
	private String summary;
	private String title;
	private String begintime;
	private String endtime;
	private String sumup;
	private PepoleIdeaReplyWrapper pepoleIdeaReplyWrapper;

	public class PepoleIdeaReplyWrapper extends CommonDataWrapper {

		private List<PepoleIdeaReply> pepoleIdeaReplies;

		public class PepoleIdeaReply {

			// 回复内容
			private String content;
			// 回复状态
			private String status;
			// 回复人
			private String userName;
			// 回复帖子ID
			private String politicsMainId;
			// 回复IP
			private String sentIp;
			// 回复时间
			private String sentTime;
			private String actorInfoId;

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
			 * @return the status
			 */
			public String getStatus() {
				return status;
			}

			/**
			 * @param status
			 *            the status to set
			 */
			public void setStatus(String status) {
				this.status = status;
			}

			/**
			 * @return the userName
			 */
			public String getUserName() {
				return userName;
			}

			/**
			 * @param userName
			 *            the userName to set
			 */
			public void setUserName(String userName) {
				this.userName = userName;
			}

			/**
			 * @return the politicsMainId
			 */
			public String getPoliticsMainId() {
				return politicsMainId;
			}

			/**
			 * @param politicsMainId
			 *            the politicsMainId to set
			 */
			public void setPoliticsMainId(String politicsMainId) {
				this.politicsMainId = politicsMainId;
			}

			/**
			 * @return the sentIp
			 */
			public String getSentIp() {
				return sentIp;
			}

			/**
			 * @param sentIp
			 *            the sentIp to set
			 */
			public void setSentIp(String sentIp) {
				this.sentIp = sentIp;
			}

			/**
			 * @return the sentTime
			 */
			public String getSentTime() {
				return sentTime;
			}

			/**
			 * @param sentTime
			 *            the sentTime to set
			 */
			public void setSentTime(String sentTime) {
				this.sentTime = sentTime;
			}

			/**
			 * @return the actorInfoId
			 */
			public String getActorInfoId() {
				return actorInfoId;
			}

			/**
			 * @param actorInfoId
			 *            the actorInfoId to set
			 */
			public void setActorInfoId(String actorInfoId) {
				this.actorInfoId = actorInfoId;
			}

		}

		/**
		 * @return the pepoleIdeaReplies
		 */
		public List<PepoleIdeaReply> getPepoleIdeaReplies() {
			return pepoleIdeaReplies;
		}

		/**
		 * @param pepoleIdeaReplies
		 *            the pepoleIdeaReplies to set
		 */
		public void setPepoleIdeaReplies(List<PepoleIdeaReply> pepoleIdeaReplies) {
			this.pepoleIdeaReplies = pepoleIdeaReplies;
		}
	}

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
	 * @return the doprojectid
	 */
	public String getDoprojectid() {
		return doprojectid;
	}

	/**
	 * @param doprojectid
	 *            the doprojectid to set
	 */
	public void setDoprojectid(String doprojectid) {
		this.doprojectid = doprojectid;
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
	 * @return the begintime
	 */
	public String getBegintime() {
		return begintime;
	}

	/**
	 * @param begintime
	 *            the begintime to set
	 */
	public void setBegintime(String begintime) {
		this.begintime = begintime;
	}

	/**
	 * @return the endtime
	 */
	public String getEndtime() {
		return endtime;
	}

	/**
	 * @param endtime
	 *            the endtime to set
	 */
	public void setEndtime(String endtime) {
		this.endtime = endtime;
	}

	/**
	 * @return the sumup
	 */
	public String getSumup() {
		return sumup;
	}

	/**
	 * @param sumup
	 *            the sumup to set
	 */
	public void setSumup(String sumup) {
		this.sumup = sumup;
	}

	/**
	 * @return the pepoleIdeaReplyWrapper
	 */
	public PepoleIdeaReplyWrapper getPepoleIdeaReplyWrapper() {
		return pepoleIdeaReplyWrapper;
	}

	/**
	 * @param pepoleIdeaReplyWrapper
	 *            the pepoleIdeaReplyWrapper to set
	 */
	public void setPepoleIdeaReplyWrapper(
			PepoleIdeaReplyWrapper pepoleIdeaReplyWrapper) {
		this.pepoleIdeaReplyWrapper = pepoleIdeaReplyWrapper;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status
	 *            the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return the doAnonymous
	 */
	public String getDoAnonymous() {
		return doAnonymous;
	}

	/**
	 * @param doAnonymous
	 *            the doAnonymous to set
	 */
	public void setDoAnonymous(String doAnonymous) {
		this.doAnonymous = doAnonymous;
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

}