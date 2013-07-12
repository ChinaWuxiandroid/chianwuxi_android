package com.wuxi.domain;

import java.util.List;

/**
 * 公开电话包装类 
 * @author 杨宸 智佳
 * */
public class OpenTelWrapper {
	private List<OpenTel> data;    //公开电话的数据列表
	private int end;              //结束的索引
	private int start; 				//开始的索引
	private boolean previous; 		//是否可以上一页
	private int totalRowsAmount; 	//数据列表中元素个数
	private boolean next;			//是否可以下一页

	public List<OpenTel> getData() {
		return data;
	}
	public void setData(List<OpenTel> data) {
		this.data = data;
	}
	public int getEnd() {
		return end;
	}
	public void setEnd(int end) {
		this.end = end;
	}
	public int getStart() {
		return start;
	}
	public void setStart(int start) {
		this.start = start;
	}
	public boolean isPrevious() {
		return previous;
	}
	public void setPrevious(boolean previous) {
		this.previous = previous;
	}
	public int getTotalRowsAmount() {
		return totalRowsAmount;
	}
	public void setTotalRowsAmount(int totalRowsAmount) {
		this.totalRowsAmount = totalRowsAmount;
	}
	public boolean isNext() {
		return next;
	}
	public void setNext(boolean next) {
		this.next = next;
	}


}
