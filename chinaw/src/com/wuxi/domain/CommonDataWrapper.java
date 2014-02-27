package com.wuxi.domain;

/**
 * 含有索引 如 热点话题，公共电话等一类 信息 的基类包装类
 * 
 * @author 杨宸 智佳
 * */

public class CommonDataWrapper {
	private int start;// 开始索引
	private int end;// 结束索引
	private boolean previous; // 是否可以上一页
	private int totalRowsAmount;// 总内容个数
	private boolean next; // 是否可以下一页
	private boolean isData;// 是否存在数据

	/**
	 * @return isData
	 */
	public boolean isData() {
		return isData;
	}

	/**
	 * @param isData
	 *            要设置的 isData
	 */
	public void setData(boolean isData) {
		this.isData = isData;
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
