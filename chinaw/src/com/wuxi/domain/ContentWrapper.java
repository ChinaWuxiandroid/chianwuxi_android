package com.wuxi.domain;

import java.util.List;

/**
 *  
 * @author wanglu 泰得利通
 * content包装类
 *
 */
public class ContentWrapper {
	private List<Content> contents;//内容集合
	private int start;//开始索引
	private int end;//结束索引
	private boolean previous;//是否还有前一条数据
	private int  totalRowsAmount;//总内容个数
	public int getTotalRowsAmount() {
		return totalRowsAmount;
	}
	public void setTotalRowsAmount(int totalRowsAmount) {
		this.totalRowsAmount = totalRowsAmount;
	}
	private boolean next;//是否还有下一条
	public List<Content> getContents() {
		return contents;
	}
	public void setContents(List<Content> contents) {
		this.contents = contents;
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
	public boolean isPrevious() {
		return previous;
	}
	public void setPrevious(boolean previous) {
		this.previous = previous;
	}
	
	public boolean isNext() {
		return next;
	}
	public void setNext(boolean next) {
		this.next = next;
	}
	
	

}
