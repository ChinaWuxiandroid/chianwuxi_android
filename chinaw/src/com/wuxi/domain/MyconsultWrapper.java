package com.wuxi.domain;

import java.util.List;

/**
 * 
 * @author wanglu 泰得利通
 * 我的在线咨询包装类
 *
 */
public class MyconsultWrapper {

  private List<Myconsult> myconsults;
  private int start;
  private int end;
  private boolean next;
  private boolean previous;
  private int totalRowsAmount;
public List<Myconsult> getMyconsults() {
	return myconsults;
}
public void setMyconsults(List<Myconsult> myconsults) {
	this.myconsults = myconsults;
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
public int getTotalRowsAmount() {
	return totalRowsAmount;
}
public void setTotalRowsAmount(int totalRowsAmount) {
	this.totalRowsAmount = totalRowsAmount;
}
  
}
