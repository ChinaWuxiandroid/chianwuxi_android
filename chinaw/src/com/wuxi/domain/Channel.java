package com.wuxi.domain;

import java.io.Serializable;

import android.support.v4.app.Fragment;

/**
 * 频道信息
 * 
 * @author wanglu
 * 
 */
public class Channel implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7602053445610183214L;
	private Boolean isNull;// 是否允许为空
	private String contents;// 频道子内容
	private Channel childrens[];// 频道子频道
	private String channelId;// 频道Id
	private String channelName;// 频道名
	private int childrenChannelsCount;// 子频道数目
	private int childrenContentsCount;// 子内容个数
	public static final String CHANNEL_KEY="channel_key";

	public int getChildrenChannelsCount() {
		return childrenChannelsCount;
	}

	public void setChildrenChannelsCount(int childrenChannelsCount) {
		this.childrenChannelsCount = childrenChannelsCount;
	}

	public int getChildrenContentsCount() {
		return childrenContentsCount;
	}

	public void setChildrenContentsCount(int childrenContentsCount) {
		this.childrenContentsCount = childrenContentsCount;
	}

	private Class<? extends Fragment> contentFragment;

	public Class<? extends Fragment> getContentFragment() {
		return contentFragment;
	}

	public void setContentFragment(Class<? extends Fragment> contentFragment) {
		this.contentFragment = contentFragment;
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
