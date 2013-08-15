package com.wuxi.domain;

/**
 * 
 * @author wanglu 泰得利通
 * 政务大厅信件内容分类
 *
 */
public class ContentType {

	private String name;
	private String id;
	private String doprojectid;
	
	public ContentType(){}
	public ContentType(String name){
		this.name=name;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getDoprojectid() {
		return doprojectid;
	}
	public void setDoprojectid(String doprojectid) {
		this.doprojectid = doprojectid;
	}
	
	
	

}
