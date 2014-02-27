package com.wuxi.domain;

/**
 * 
 * @author wanglu 泰得利通 部门
 */
public class Dept {

	private String name;
	private String id;
	private boolean isNull;

	public Dept() {

	}

	public Dept(String name) {
		this.name = name;
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

	public boolean isNull() {
		return isNull;
	}

	public void setNull(boolean isNull) {
		this.isNull = isNull;
	}

}
