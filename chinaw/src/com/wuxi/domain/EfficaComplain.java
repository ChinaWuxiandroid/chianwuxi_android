package com.wuxi.domain;

/**
 * 
 * @author wanglu 泰得利通
 * 效能投诉
 *
 */
public class EfficaComplain {
	private String id;
	private String endTime;
	private String title;
	private String doDpetname;//答复部门
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
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
	public String getDoDpetname() {
		return doDpetname;
	}
	public void setDoDpetname(String doDpetname) {
		this.doDpetname = doDpetname;
	}
	
	
}
