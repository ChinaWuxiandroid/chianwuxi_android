package com.wuxi.domain;

import java.io.Serializable;

/**'
 * 
 * @author wanglu 泰得利通
 * 政务大厅网上办件 javaBean
 *
 */
public class GoverSaoonItem implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String type;
	private int num;
	private int kindtype;
	private int subkindtype;
	private String kindname;
	private String typename;
	private String name;
	private String id;
	private String deptid;
	private String deptname;
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public int getKindtype() {
		return kindtype;
	}
	public void setKindtype(int kindtype) {
		this.kindtype = kindtype;
	}
	public int getSubkindtype() {
		return subkindtype;
	}
	public void setSubkindtype(int subkindtype) {
		this.subkindtype = subkindtype;
	}
	public String getKindname() {
		return kindname;
	}
	public void setKindname(String kindname) {
		this.kindname = kindname;
	}
	public String getTypename() {
		return typename;
	}
	public void setTypename(String typename) {
		this.typename = typename;
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
	public String getDeptid() {
		return deptid;
	}
	public void setDeptid(String deptid) {
		this.deptid = deptid;
	}
	public String getDeptname() {
		return deptname;
	}
	public void setDeptname(String deptname) {
		this.deptname = deptname;
	}
	
}
