package com.wuxi.domain;

import java.io.Serializable;

/**
 * 高级搜索实体类
 * @author 杨宸 智佳
 * */
public class AdvancedSearchUtil implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String infoType;  //信息类型
	private String pageSize;
	private String DateSearchType="Modified-Day";
	private String beginDate;
	private String endDate;
	private String contentType;
	private String keyWord;
	public String getInfoType() {
		return infoType;
	}
	public void setInfoType(String infoType) {
		this.infoType = infoType;
	}
	public String getPageSize() {
		return pageSize;
	}
	public void setPageSize(String pageSize) {
		this.pageSize = pageSize;
	}
	public String getBeginDate() {
		return beginDate;
	}
	public void setBeginDate(String beginDate) {
		this.beginDate = beginDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public String getContentType() {
		return contentType;
	}
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}
	public String getKeyWord() {
		return keyWord;
	}
	public void setKeyWord(String keyWord) {
		this.keyWord = keyWord;
	}
	public String getDateSearchType() {
		return DateSearchType;
	}
	public void setDateSearchType(String dateSearchType) {
		DateSearchType = dateSearchType;
	}
}
