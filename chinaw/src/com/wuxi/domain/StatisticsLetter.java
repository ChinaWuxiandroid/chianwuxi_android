package com.wuxi.domain;

/**
 * 各信箱部门答复率总数统计 
 * @author 杨宸 智佳
 * */

public class StatisticsLetter {
	private boolean isNull;
	private String depname;  //部门
	private int acceptedNum;   //收件数量
	private int replyNum;    //处理数量
	private String replyRate;  //回复率
	private String replyDay;    //平均答复工作日数
	public boolean isNull() {
		return isNull;
	}
	public void setNull(boolean isNull) {
		this.isNull = isNull;
	}
	public String getDepname() {
		return depname;
	}
	public void setDepname(String depname) {
		this.depname = depname;
	}
	public int getAcceptedNum() {
		return acceptedNum;
	}
	public void setAcceptedNum(int acceptedNum) {
		this.acceptedNum = acceptedNum;
	}
	public int getReplyNum() {
		return replyNum;
	}
	public void setReplyNum(int replyNum) {
		this.replyNum = replyNum;
	}
	public String getReplyRate() {
		return replyRate;
	}
	public void setReplyRate(String replyRate) {
		this.replyRate = replyRate;
	}
	public String getReplyDay() {
		return replyDay;
	}
	public void setReplyDay(String replyDay) {
		this.replyDay = replyDay;
	}

}
