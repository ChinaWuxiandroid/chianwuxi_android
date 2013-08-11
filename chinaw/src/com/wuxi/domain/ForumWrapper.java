/**
 * 
 */
package com.wuxi.domain;

import java.io.Serializable;
import java.util.List;

/**
 * 论坛数据包装类，含有一个论坛实体内部类
 * 
 * @author 智佳 罗森
 * 
 */
public class ForumWrapper extends CommonDataWrapper {

	// 内容集合
	private List<Forum> forums;

	/**
	 * @return the forums
	 */
	public List<Forum> getForums() {
		return forums;
	}

	/**
	 * @param forums
	 *            the forums to set
	 */
	public void setForums(List<Forum> forums) {
		this.forums = forums;
	}

	/**
	 * 论坛实体类
	 * 
	 * @author 智佳 罗森
	 * 
	 */
	public class Forum implements Serializable {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		// 帖子ID
		private String id;
		// 帖子题目
		private String title;
		// 发帖时间
		private String beginTime;
		// 点击数
		private String readCount;
		// 回帖数
		private String resultCount;
		// 发帖人
		private String sentUser;
		// 是否置顶
		private String top;
		// 帖子来源 项目主题 ID
		private String doProjectID;
		// 排序号
		private String dataNo;
		// 类型
		private String viewpath;

		/**
		 * @return the top
		 */
		public String getTop() {
			return top;
		}

		/**
		 * @param top
		 *            the top to set
		 */
		public void setTop(String top) {
			this.top = top;
		}

		/**
		 * @return the doProjectID
		 */
		public String getDoProjectID() {
			return doProjectID;
		}

		/**
		 * @param doProjectID
		 *            the doProjectID to set
		 */
		public void setDoProjectID(String doProjectID) {
			this.doProjectID = doProjectID;
		}

		/**
		 * @return the dataNo
		 */
		public String getDataNo() {
			return dataNo;
		}

		/**
		 * @param dataNo
		 *            the dataNo to set
		 */
		public void setDataNo(String dataNo) {
			this.dataNo = dataNo;
		}

		/**
		 * @return the viewpath
		 */
		public String getViewpath() {
			return viewpath;
		}

		/**
		 * @param viewpath
		 *            the viewpath to set
		 */
		public void setViewpath(String viewpath) {
			this.viewpath = viewpath;
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
		 * @return the resultCount
		 */
		public String getResultCount() {
			return resultCount;
		}

		/**
		 * @param resultCount
		 *            the resultCount to set
		 */
		public void setResultCount(String resultCount) {
			this.resultCount = resultCount;
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
	}

}
