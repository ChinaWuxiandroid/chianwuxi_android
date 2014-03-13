package com.wuxi.domain;

import java.io.Serializable;

public class PublicMsgKeyWordsSearchFilter implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 668843699109203351L;

	/**
	 * 
	 */
	
	private String channelId;
	
	private String content;
	public String channelName;
	private int depth;
	private String paramString;
	private String typeName;
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	public String getParamString() {
		return paramString;
	}
	public void setParamString(String paramString) {
		this.paramString = paramString;
	}
	public int getDepth() {
		return depth;
	}
	public void setDepth(int depth) {
		this.depth = depth;
	}
	public String getChannelId() {
		return channelId;
	}
	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}
	
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getChannelName() {
		return channelName;
	}
	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}
	


}
