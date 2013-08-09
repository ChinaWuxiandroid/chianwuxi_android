package com.wuxi.domain;

import java.util.List;

/**
 * 政府信息公开 和 证明互动 中   获取我的依申请公开列表  业务类
 * @author 杨宸   智佳
 * */
public class MyApplyPageWrapper extends CommonDataWrapper{
	private List<MyApplyPage> data;//内容集合

	public List<MyApplyPage> getData() {
		return data;
	}

	public void setData(List<MyApplyPage> data) {
		this.data = data;
	}

	public class MyApplyPage{
		private String id;
		private String content;
		private int code;
		private String title;
		private String applyDate;
		private String readCount;
		private String answerDep;
		private String answerDate;
		private String answerUser;
		private String answerContent;

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
		public int getCode() {
			return code;
		}
		public void setCode(int code) {
			this.code = code;
		}
		public String getTitle() {
			return title;
		}
		public void setTitle(String title) {
			this.title = title;
		}
		public String getApplyDate() {
			return applyDate;
		}
		public void setApplyDate(String applyDate) {
			this.applyDate = applyDate;
		}
		public String getReadCount() {
			return readCount;
		}
		public void setReadCount(String readCount) {
			this.readCount = readCount;
		}
		public String getAnswerDep() {
			return answerDep;
		}
		public void setAnswerDep(String answerDep) {
			this.answerDep = answerDep;
		}
		public String getAnswerDate() {
			return answerDate;
		}
		public void setAnswerDate(String answerDate) {
			this.answerDate = answerDate;
		}
		public String getAnswerUser() {
			return answerUser;
		}
		public void setAnswerUser(String answerUser) {
			this.answerUser = answerUser;
		}
		public String getAnswerContent() {
			return answerContent;
		}
		public void setAnswerContent(String answerContent) {
			this.answerContent = answerContent;
		}
	}
}
