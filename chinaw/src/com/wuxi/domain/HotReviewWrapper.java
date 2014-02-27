package com.wuxi.domain;

import java.io.Serializable;
import java.util.List;

/**
 * 热点话题包装类 及内部类 HotReview
 * 
 * @author 杨宸 智佳
 * */

public class HotReviewWrapper extends CommonDataWrapper {

	private List<HotReview> data;// 内容集合

	public List<HotReview> getData() {
		return data;
	}

	public void setData(List<HotReview> data) {
		this.data = data;
	}

	public class HotReview implements Serializable {

		/**
		 * serialVersionUID
		 */
		private static final long serialVersionUID = 1L;

		private String id; // 话题主键
		private String title; // 标题
		private String startTime; // 发布时间
		private String endTime; // 截止时间
		private String readcount;

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public String getTitle() {
			return title;
		}

		public void setTitle(String title) {
			this.title = title;
		}

		public String getStartTime() {
			return startTime;
		}

		public void setStartTime(String startTime) {
			this.startTime = startTime;
		}

		public String getEndTime() {
			return endTime;
		}

		public void setEndTime(String endTime) {
			this.endTime = endTime;
		}

		/**
		 * @return the readcount
		 */
		public String getReadcount() {
			return readcount;
		}

		/**
		 * @param readcount
		 *            the readcount to set
		 */
		public void setReadcount(String readcount) {
			this.readcount = readcount;
		}
	}

	// 热点话题内容
	public class HotReviewContent {
		private String id; // 话题主键
		private String content; // 内容
		private String endTime; // 结束时间
		private String title; // 标题
		private String depName; // 部门名称
		private boolean canReply; // 能否回复

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public String getContent() {
			return content;
		}

		public void setContent(String content) {
			this.content = content;
		}

		public String getEndTime() {
			return endTime;
		}

		public void setEndTime(String endTime) {
			this.endTime = endTime;
		}

		public String getTitle() {
			return title;
		}

		public void setTitle(String title) {
			this.title = title;
		}

		public String getDepName() {
			return depName;
		}

		public void setDepName(String depName) {
			this.depName = depName;
		}

		public boolean isCanReply() {
			return canReply;
		}

		public void setCanReply(boolean canReply) {
			this.canReply = canReply;
		}
	}
}
