/**
 * 
 */
package com.wuxi.domain;

import java.util.List;

/**
 * 公告类帖子数据集实体类
 * 
 * @author 智佳 罗森
 * 
 */
public class NoticePostWrapper {

	private String id;
	private String content;
	private String status;
	private String title;
	private String doAnonymous;
	private String readCount;
	private String summary;
	private String begintime;
	private String endtime;
	private String doprojectid;
	private String sumup;
	private NoticePostReplyWrapper noticePostReplyWrapper;

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
	 * @return the noticePostReplyWrapper
	 */
	public NoticePostReplyWrapper getNoticePostReplyWrapper() {
		return noticePostReplyWrapper;
	}

	/**
	 * @param noticePostReplyWrapper
	 *            the noticePostReplyWrapper to set
	 */
	public void setNoticePostReplyWrapper(
			NoticePostReplyWrapper noticePostReplyWrapper) {
		this.noticePostReplyWrapper = noticePostReplyWrapper;
	}

	/**
	 * 公告类帖子回复数据集实体类
	 * 
	 * @author 智佳 罗森
	 * 
	 */
	public class NoticePostReplyWrapper extends CommonDataWrapper {

		private List<NoticePostReply> noticePostReplies;

		/**
		 * @return the noticePostReplies
		 */
		public List<NoticePostReply> getNoticePostReplies() {
			return noticePostReplies;
		}

		/**
		 * @param noticePostReplies
		 *            the noticePostReplies to set
		 */
		public void setNoticePostReplies(List<NoticePostReply> noticePostReplies) {
			this.noticePostReplies = noticePostReplies;
		}

		/**
		 * 公告类帖子单条回复数据实体类
		 * 
		 * @author 智佳 罗森
		 * 
		 */
		public class NoticePostReply {

			private String id;
			private String content;
			private String username;
			private String title;
			private String sendtime;
			private String answercontent;
			private String mainid;
			private String answerman;

			/**
			 * @return id
			 */
			public String getId() {
				return id;
			}

			/**
			 * @param id
			 *            要设置的 id
			 */
			public void setId(String id) {
				this.id = id;
			}

			/**
			 * @return content
			 */
			public String getContent() {
				return content;
			}

			/**
			 * @param content
			 *            要设置的 content
			 */
			public void setContent(String content) {
				this.content = content;
			}

			/**
			 * @return username
			 */
			public String getUsername() {
				return username;
			}

			/**
			 * @param username
			 *            要设置的 username
			 */
			public void setUsername(String username) {
				this.username = username;
			}

			/**
			 * @return title
			 */
			public String getTitle() {
				return title;
			}

			/**
			 * @param title
			 *            要设置的 title
			 */
			public void setTitle(String title) {
				this.title = title;
			}

			/**
			 * @return sendtime
			 */
			public String getSendtime() {
				return sendtime;
			}

			/**
			 * @param sendtime
			 *            要设置的 sendtime
			 */
			public void setSendtime(String sendtime) {
				this.sendtime = sendtime;
			}

			/**
			 * @return answercontent
			 */
			public String getAnswercontent() {
				return answercontent;
			}

			/**
			 * @param answercontent
			 *            要设置的 answercontent
			 */
			public void setAnswercontent(String answercontent) {
				this.answercontent = answercontent;
			}

			/**
			 * @return mainid
			 */
			public String getMainid() {
				return mainid;
			}

			/**
			 * @param mainid
			 *            要设置的 mainid
			 */
			public void setMainid(String mainid) {
				this.mainid = mainid;
			}

			/**
			 * @return answerman
			 */
			public String getAnswerman() {
				return answerman;
			}

			/**
			 * @param answerman
			 *            要设置的 answerman
			 */
			public void setAnswerman(String answerman) {
				this.answerman = answerman;
			}

		}
	}
}
