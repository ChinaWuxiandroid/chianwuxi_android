package com.wuxi.domain;

import java.util.List;

/**
 * 
 * @author wanglu 泰得利通
 * 政务大厅表格下载包装
 *
 */
public class GoverTableDownLoadWrapper {

	private List<GoverTableDownLoad> goverTableDownLoads;
	private int start;
	private int end;
	private boolean previous;
	private int totalRowsAmount;
	private boolean  next;
	public List<GoverTableDownLoad> getGoverTableDownLoads() {
		return goverTableDownLoads;
	}
	public void setGoverTableDownLoads(List<GoverTableDownLoad> goverTableDownLoads) {
		this.goverTableDownLoads = goverTableDownLoads;
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
