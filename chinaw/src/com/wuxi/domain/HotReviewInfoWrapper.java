/**
 * 
 */
package com.wuxi.domain;

/**
 * 单个话题实体类
 * 
 * @author 智佳 罗森
 * 
 */
public class HotReviewInfoWrapper {

	private String id;
	private String content;
	private String endTime;
	private String title;
	private String depName;
	private String canReply;
	private String replies;

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
	 * @return the replies
	 */
	public String getReplies() {
		return replies;
	}

	/**
	 * @param replies
	 *            the replies to set
	 */
	public void setReplies(String replies) {
		this.replies = replies;
	}

}
