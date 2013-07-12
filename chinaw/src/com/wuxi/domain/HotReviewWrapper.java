package com.wuxi.domain;




import java.util.List;

/**
 * 热点话题包装类  及内部类 HotReview
 * @author 杨宸 智佳
 * */

public class HotReviewWrapper extends CommonDataWrapper{


	private List<HotReview> data;//内容集合

	public List<HotReview> getData() {
		return data;
	}

	public void setData(List<HotReview> data) {
		this.data = data;
	}

	public class HotReview{
		
		private String id;   //话题主键
		private String title;   //标题
		private String startTime;  //发布时间
		private String endTime;    //截止时间

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
	}
}
