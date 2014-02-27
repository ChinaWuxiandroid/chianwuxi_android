package com.wuxi.domain;

import java.util.List;


/**
 * 公众论坛里列表的帖子 的 包装类  及内部类 HotReview
 * @author 杨宸 智佳
 * */

public class BBSWrapper extends CommonDataWrapper{
	private List<BBS> data;//内容集合

	public List<BBS> getData() {
		return data;
	}

	public void setData(List<BBS> data) {
		this.data = data;
	}
	
	public class BBS{
		private String politicsMainID;   //公众论坛主键
		private String title;  //标题
		private int readCount; //阅读次数
		private boolean top;  //是置顶
		private String sentuser;  //发帖人
		private String doProjectID;  //帖子来源  项目主题ID
		private int dataNO;  //排序号
		private String beginTime; //开始时间
		private String viewpath;  //表单提交对于路径
		private int resultCount;  //回复数
		
		public String getPoliticsMainID() {
			return politicsMainID;
		}
		public void setPoliticsMainID(String politicsMainID) {
			this.politicsMainID = politicsMainID;
		}
		public String getTitle() {
			return title;
		}
		public void setTitle(String title) {
			this.title = title;
		}
		public int getReadCount() {
			return readCount;
		}
		public void setReadCount(int readCount) {
			this.readCount = readCount;
		}
		public boolean isTop() {
			return top;
		}
		public void setTop(boolean top) {
			this.top = top;
		}
		public String getSentuser() {
			return sentuser;
		}
		public void setSentuser(String sentuser) {
			this.sentuser = sentuser;
		}
		public String getDoProjectID() {
			return doProjectID;
		}
		public void setDoProjectID(String doProjectID) {
			this.doProjectID = doProjectID;
		}
		public int getDataNO() {
			return dataNO;
		}
		public void setDataNO(int dataNO) {
			this.dataNO = dataNO;
		}
		public String getBeginTime() {
			return beginTime;
		}
		public void setBeginTime(String beginTime) {
			this.beginTime = beginTime;
		}
		public String getViewpath() {
			return viewpath;
		}
		public void setViewpath(String viewpath) {
			this.viewpath = viewpath;
		}
		public int getResultCount() {
			return resultCount;
		}
		public void setResultCount(int resultCount) {
			this.resultCount = resultCount;
		}
		
	}
}
