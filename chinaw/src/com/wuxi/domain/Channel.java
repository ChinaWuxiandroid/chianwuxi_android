package com.wuxi.domain;

import android.support.v4.app.Fragment;

/**
 * 频道信息
 * @author wanglu
 *
 */
public class Channel {
  private Boolean isNull;//是否允许为空
  private String contents;//频道子内容 
  private Channel childrens[];//频道子频道
  private String channelId;//频道Id
  private String channelName;//频道名
  private Fragment fragment;//
public Fragment getFragment() {
	return fragment;
}
public void setFragment(Fragment fragment) {
	this.fragment = fragment;
}
public Boolean getIsNull() {
	return isNull;
}
public void setIsNull(Boolean isNull) {
	this.isNull = isNull;
}
public String getContents() {
	return contents;
}
public void setContents(String contents) {
	this.contents = contents;
}
public Channel[] getChildrens() {
	return childrens;
}
public void setChildrens(Channel[] childrens) {
	this.childrens = childrens;
}
public String getChannelId() {
	return channelId;
}
public void setChannelId(String channelId) {
	this.channelId = channelId;
}
public String getChannelName() {
	return channelName;
}
public void setChannelName(String channelName) {
	this.channelName = channelName;
}
  
  
  
}
