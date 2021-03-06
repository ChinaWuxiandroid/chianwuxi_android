package com.wuxi.domain;

import java.util.List;


/**
 * 
 * @author wanglu 泰得利通
 * 我是申报列表包装类
 *
 */
public class MyApplyWrapper {

	private List<MyApply> myApplies;
	private int start;//开始页
	private int end;//结束
	private boolean previous;
	private boolean next;
	private int totalRowsAmount;//总记录
	public List<MyApply> getMyApplies() {
		return myApplies;
	}
	public void setMyApplies(List<MyApply> myApplies) {
		this.myApplies = myApplies;
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
	public int getTotalRowsAmount() {
		return totalRowsAmount;
	}
	public void setTotalRowsAmount(int totalRowsAmount) {
		this.totalRowsAmount = totalRowsAmount;
	}
	
	
	
	

}
