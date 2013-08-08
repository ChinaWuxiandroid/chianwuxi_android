package com.wuxi.domain;

public class FifterContentWrapper {

	private String id;  //频道编号 必选
	private int start;  //起始行数  必选
	private int end;  //结束行数  必选
	private String dept=null;  //部门     非必选
	private int year=-1;  //年份    非必选
	private String typeword=null;  // 分类词   非必选
	private String zone=null;  // 地区 非必选

	//默认构造函数，必选的参数
	public FifterContentWrapper(String id){
		this.id = id;
	}

	//地区时间过滤类型
	public FifterContentWrapper(String id, int start, int end,int year,String zone){
		this.id = id;
		this.start = start;
		this.end = end;
		this.year=year;
		this.zone=zone;
	}

	//部门时间过滤类型
	public FifterContentWrapper(String id, int start, int end,String dept,int year){
		this.id = id;
		this.start = start;
		this.end = end;
		this.year=year;
		this.dept=dept;
	}


	public FifterContentWrapper(String id, int start, int end, String dept,
			int year, String typeword, String zone) {
		this.id = id;
		this.start = start;
		this.end = end;
		this.dept = dept;
		this.year = year;
		this.typeword = typeword;
		this.zone = zone;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public int getEnd() {
		return end;
	}

	public void setEnd(int end) {
		this.end = end;
	}

	public String getDept() {
		return dept;
	}

	public void setDept(String dept) {
		this.dept = dept;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public String getTypeword() {
		return typeword;
	}

	public void setTypeword(String typeword) {
		this.typeword = typeword;
	}

	public String getZone() {
		return zone;
	}

	public void setZone(String zone) {
		this.zone = zone;
	}
}
