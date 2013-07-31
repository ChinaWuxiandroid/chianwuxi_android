package com.wuxi.domain;

import java.io.Serializable;

public class MailBoxParameterItem implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int tpye;                       //字段类型（0—用户字段，1—管理字段）
	private String valueType;				//数据类型 DateTimeValue DecimalValue IntegerValue StringValue TextValue UpFile
	private String keyName;                 //参数名
	private int requiredForm;               //是否必须(1:必须 ，0:不必须)
	private String selfId;                  //主键ID
	private String alias;					//别名
	private int orderId;					//顺序（从大到小）
	private String selfObjectId;			//对应自定义表单ID
	private String inputType;				//输入格式
	private String valueList;				//初始值
	private String notice;					//参数描述
	private int searchMark;					//搜查标记
	private int listMark;					//列表标记
	private String jscontent;
	
	public int getTpye() {
		return tpye;
	}
	public void setTpye(int tpye) {
		this.tpye = tpye;
	}
	public String getValueType() {
		return valueType;
	}
	public void setValueType(String valueType) {
		this.valueType = valueType;
	}
	public String getKeyName() {
		return keyName;
	}
	public void setKeyName(String keyName) {
		this.keyName = keyName;
	}
	public int getRequiredForm() {
		return requiredForm;
	}
	public void setRequiredForm(int requiredForm) {
		this.requiredForm = requiredForm;
	}
	public String getSelfId() {
		return selfId;
	}
	public void setSelfId(String selfId) {
		this.selfId = selfId;
	}
	public String getAlias() {
		return alias;
	}
	public void setAlias(String alias) {
		this.alias = alias;
	}
	public int getOrderId() {
		return orderId;
	}
	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}
	public String getSelfObjectId() {
		return selfObjectId;
	}
	public void setSelfObjectId(String selfObjectId) {
		this.selfObjectId = selfObjectId;
	}
	public String getInputType() {
		return inputType;
	}
	public void setInputType(String inputType) {
		this.inputType = inputType;
	}
	public String getValueList() {
		return valueList;
	}
	public void setValueList(String valueList) {
		this.valueList = valueList;
	}
	public String getNotice() {
		return notice;
	}
	public void setNotice(String notice) {
		this.notice = notice;
	}
	public int getSearchMark() {
		return searchMark;
	}
	public void setSearchMark(int searchMark) {
		this.searchMark = searchMark;
	}
	public int getListMark() {
		return listMark;
	}
	public void setListMark(int listMark) {
		this.listMark = listMark;
	}
	public String getJscontent() {
		return jscontent;
	}
	public void setJscontent(String jscontent) {
		this.jscontent = jscontent;
	}
}

