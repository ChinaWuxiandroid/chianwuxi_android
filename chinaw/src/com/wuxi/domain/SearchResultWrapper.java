package com.wuxi.domain;

import java.util.List;

public class SearchResultWrapper {
	private SearchPage page;
	private List<SearchResult> results;

	public SearchPage getPage() {
		return page;
	}

	public void setPage(SearchPage page) {
		this.page = page;
	}

	public List<SearchResult> getResults() {
		return results;
	}

	public void setResults(List<SearchResult> results) {
		this.results = results;
	}

	public class SearchPage{
		private String pagesize;
		private String reqstatus;
		private String sitename;
		private String query;
		private String hitcount;
		private String searchtime;
		private String currentpage;
		public String getPagesize() {
			return pagesize;
		}
		public void setPagesize(String pagesize) {
			this.pagesize = pagesize;
		}
		public String getReqstatus() {
			return reqstatus;
		}
		public void setReqstatus(String reqstatus) {
			this.reqstatus = reqstatus;
		}
		public String getSitename() {
			return sitename;
		}
		public void setSitename(String sitename) {
			this.sitename = sitename;
		}
		public String getQuery() {
			return query;
		}
		public void setQuery(String query) {
			this.query = query;
		}
		public String getHitcount() {
			return hitcount;
		}
		public void setHitcount(String hitcount) {
			this.hitcount = hitcount;
		}
		public String getSearchtime() {
			return searchtime;
		}
		public void setSearchtime(String searchtime) {
			this.searchtime = searchtime;
		}
		public String getCurrentpage() {
			return currentpage;
		}
		public void setCurrentpage(String currentpage) {
			this.currentpage = currentpage;
		}
	} 

	public class SearchResult{
		private String docid;
		private String title;
		private String summarycontent;
		private String link;
		private String score;
		private String modifiedday;
		private String size;
		public String getDocid() {
			return docid;
		}
		public void setDocid(String docid) {
			this.docid = docid;
		}
		public String getTitle() {
			return title;
		}
		public void setTitle(String title) {
			this.title = title;
		}
		public String getSummarycontent() {
			return summarycontent;
		}
		public void setSummarycontent(String summarycontent) {
			this.summarycontent = summarycontent;
		}
		public String getLink() {
			return link;
		}
		public void setLink(String link) {
			this.link = link;
		}
		public String getScore() {
			return score;
		}
		public void setScore(String score) {
			this.score = score;
		}
		public String getModifiedday() {
			return modifiedday;
		}
		public void setModifiedday(String modifiedday) {
			this.modifiedday = modifiedday;
		}
		public String getSize() {
			return size;
		}
		public void setSize(String size) {
			this.size = size;
		}
	}


}
