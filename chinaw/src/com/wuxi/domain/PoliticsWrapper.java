package com.wuxi.domain;

import java.util.List;

public class PoliticsWrapper extends CommonDataWrapper{
	List<Politics> data;

	public List<Politics> getData() {
		return data;
	}

	public void setData(List<Politics> data) {
		this.data = data;
	}
	
	public class Politics{
		private String id;   //立法征求或民意征集的id
		private String title;  //标题
		private String doprojectid;  //项目编号
		private String beginTime; //开始时间
		private String endTime; //结束时间
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
		public String getDoprojectid() {
			return doprojectid;
		}
		public void setDoprojectid(String doprojectid) {
			this.doprojectid = doprojectid;
		}
		public String getBeginTime() {
			return beginTime;
		}
		public void setBeginTime(String beginTime) {
			this.beginTime = beginTime;
		}
		public String getEndTime() {
			return endTime;
		}
		public void setEndTime(String endTime) {
			this.endTime = endTime;
		}
	}
}
