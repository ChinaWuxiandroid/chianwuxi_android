package com.wuxi.app.util;


/**
 * 高级搜索 参数类 
 * @author 杨宸 智佳
 * */
public class AdvancedSearchInfoUtil {


	private String newsType;         //新闻类型
	private int  resultsPerPage;         //每页结果数
	private String startDate,endDate;         //开始和结束时间  暂定为 String 类型
	private int contentSearchType;         //内容检索   default 为0  全部   ，1 正文  2标题 
	private String keyWord;         //关键字

	public AdvancedSearchInfoUtil(String newsType, int resultsPerPage,
			String startDate, String endDate, int contentSearchType,
			String keyWord) {
		super();
		this.newsType = newsType;
		this.resultsPerPage = resultsPerPage;
		this.startDate = startDate;
		this.endDate = endDate;
		this.contentSearchType = contentSearchType;
		this.keyWord = keyWord;
	}

	public String getNewsType() {
		return newsType;
	}
	public void setNewsType(String newsType) {
		this.newsType = newsType;
	}
	public int getResultsPerPage() {
		return resultsPerPage;
	}
	public void setResultsPerPage(int resultsPerPage) {
		this.resultsPerPage = resultsPerPage;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public int getContentSearchType() {
		return contentSearchType;
	}
	public void setContentSearchType(int contentSearchType) {
		this.contentSearchType = contentSearchType;
	}
	public String getKeyWord() {
		return keyWord;
	}
	public void setKeyWord(String keyWord) {
		this.keyWord = keyWord;
	}

}
