package com.wuxi.domain;

import java.io.Serializable;

public class PublicMsgTypeSearchFilter implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 668843699109203351L;

	/**
	 * 
	 */
	
	private String channelId;
	private String typeword;
	private int dept=0;
	public String channelName;
	public String getChannelName() {
		return channelName;
	}
	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}
	public String getChannelId() {
		return channelId;
	}
	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}
	public String getTypeword() {
		return typeword;
	}
	public void setTypeword(String typeword) {
		this.typeword = typeword;
	}
	public int getDept() {
		return dept;
	}
	public void setDept(int dept) {
		this.dept = dept;
	}
	
	


}
