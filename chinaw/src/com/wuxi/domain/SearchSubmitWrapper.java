package com.wuxi.domain;

public class SearchSubmitWrapper {
	private String query;  //查询的内容  必选
	private String sitename="site_1374209144805";  //固定值等于 site_1374209144805 必选
	private int countperpage;  //分页，每页的数据条数  必选
	private int pagenum;  //页码  必选
	private String DateSearchType;  //目前可选的值 ： Modified-Day
	private String FromDate;  //必须传入 DateSearchType后才有用。 Demo ： 20080101
	private String ToDate;   //同FromDate
	public String getQuery() {
		return query;
	}
	public void setQuery(String query) {
		this.query = query;
	}
	public String getSitename() {
		return sitename;
	}
	public void setSitename(String sitename) {
		this.sitename = sitename;
	}
	public int getCountperpage() {
		return countperpage;
	}
	public void setCountperpage(int countperpage) {
		this.countperpage = countperpage;
	}
	public int getPagenum() {
		return pagenum;
	}
	public void setPagenum(int pagenum) {
		this.pagenum = pagenum;
	}
	public String getDateSearchType() {
		return DateSearchType;
	}
	public void setDateSearchType(String dateSearchType) {
		DateSearchType = dateSearchType;
	}
	public String getFromDate() {
		return FromDate;
	}
	public void setFromDate(String fromDate) {
		FromDate = fromDate;
	}
	public String getToDate() {
		return ToDate;
	}
	public void setToDate(String toDate) {
		ToDate = toDate;
	}
}
