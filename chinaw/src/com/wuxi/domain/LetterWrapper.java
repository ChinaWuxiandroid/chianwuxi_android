package com.wuxi.domain;

import java.util.List;

public class LetterWrapper extends CommonDataWrapper{
	private List<Letter> data;

	public List<Letter> getData() {
		return data;
	}

	public void setData(List<Letter> data) {
		this.data = data;
	}

	public class Letter{
		private String id;   //信件主键
		private String type;  //信件类型  包括 咨询  求助 投诉 。。。
		private String title;  //标题
		private String code; //信件编号
		private String appraise;  // 评价 
		private String depname;  //部门名称
		private String answerdate;  //回复时间
		private int readcount;  //阅读次数
		public String getId() {
			return id;
		}
		public void setId(String id) {
			this.id = id;
		}
		public String getType() {
			return type;
		}
		public void setType(String type) {
			this.type = type;
		}
		public String getCode() {
			return code;
		}
		public void setCode(String code) {
			this.code = code;
		}
		public String getAppraise() {
			return appraise;
		}
		public void setAppraise(String appraise) {
			this.appraise = appraise;
		}
		public String getDepname() {
			return depname;
		}
		public void setDepname(String depname) {
			this.depname = depname;
		}
		public String getAnswerdate() {
			return answerdate;
		}
		public void setAnswerdate(String answerdate) {
			this.answerdate = answerdate;
		}
		public int getReadcount() {
			return readcount;
		}
		public void setReadcount(int readcount) {
			this.readcount = readcount;
		}
		public String getTitle() {
			return title;
		}
		public void setTitle(String title) {
			this.title = title;
		}

	}

}
