package com.wuxi.domain;

/**
 * 
 * @author wanglu 泰得利通
 * 政务大厅办件分类
 *
 */
public class Kindtype {

	private String id;
	private  boolean isNull;
	
	private String subKindType;
	private String kindType;
	public String getKindType() {
		return kindType;
	}
	public void setKindType(String kindType) {
		this.kindType = kindType;
	}
	private String kindName;//分类名称
	private String subKindName;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public boolean isNull() {
		return isNull;
	}
	public void setNull(boolean isNull) {
		this.isNull = isNull;
	}
	public String getSubKindType() {
		return subKindType;
	}
	public void setSubKindType(String subKindType) {
		this.subKindType = subKindType;
	}
	public String getKindName() {
		return kindName;
	}
	public void setKindName(String kindName) {
		this.kindName = kindName;
	}
	public String getSubKindName() {
		return subKindName;
	}
	public void setSubKindName(String subKindName) {
		this.subKindName = subKindName;
	} 
	
	
}
