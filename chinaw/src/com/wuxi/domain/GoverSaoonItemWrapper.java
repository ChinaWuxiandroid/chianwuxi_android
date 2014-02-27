package com.wuxi.domain;

import java.util.List;

/**'
 * 
 * @author wanglu 泰得利通
 * 政务大厅网上办件  包装类javaBean
 *
 */
public class GoverSaoonItemWrapper {

	private List<GoverSaoonItem> goverSaoonItems;
	private int start;
	private int end;
	private boolean next;
	private boolean previous;
	private int totalRowsAmount;
	public int getTotalRowsAmount() {
		return totalRowsAmount;
	}
	public void setTotalRowsAmount(int totalRowsAmount) {
		this.totalRowsAmount = totalRowsAmount;
	}
	public List<GoverSaoonItem> getGoverSaoonItems() {
		return goverSaoonItems;
	}
	public void setGoverSaoonItems(List<GoverSaoonItem> goverSaoonItems) {
		this.goverSaoonItems = goverSaoonItems;
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
	public boolean isNext() {
		return next;
	}
	public void setNext(boolean next) {
		this.next = next;
	}
	public boolean isPrevious() {
		return previous;
	}
	public void setPrevious(boolean previous) {
		this.previous = previous;
	}
	
	
	
	
	
}
