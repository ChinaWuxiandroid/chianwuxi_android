package com.wuxi.domain;


/**
 * 菜单
 * 
 * @author wanglu
 * 
 */
public class Menu {

	private String name;
	private String id;
	private int type;
	private boolean disabled;
	private String des;
	private int sort;
	private Menu childrens[];// 子菜单
	private String createDate;
	private String channelId;
	private String channelName;
	private boolean favorites;
	private String contentId;
	private boolean deleted;
	private String appUI;
	private String wapURI;
	private String parentMenuId;
	private String contentName;
	private String linkMenuItemName;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public boolean isDisabled() {
		return disabled;
	}
	public void setDisabled(boolean disabled) {
		this.disabled = disabled;
	}
	public String getDes() {
		return des;
	}
	public void setDes(String des) {
		this.des = des;
	}
	public int getSort() {
		return sort;
	}
	public void setSort(int sort) {
		this.sort = sort;
	}
	public Menu[] getChildrens() {
		return childrens;
	}
	public void setChildrens(Menu[] childrens) {
		this.childrens = childrens;
	}
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
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
	public boolean isFavorites() {
		return favorites;
	}
	public void setFavorites(boolean favorites) {
		this.favorites = favorites;
	}
	public String getContentId() {
		return contentId;
	}
	public void setContentId(String contentId) {
		this.contentId = contentId;
	}
	public boolean isDeleted() {
		return deleted;
	}
	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}
	public String getAppUI() {
		return appUI;
	}
	public void setAppUI(String appUI) {
		this.appUI = appUI;
	}
	public String getWapURI() {
		return wapURI;
	}
	public void setWapURI(String wapURI) {
		this.wapURI = wapURI;
	}
	public String getParentMenuId() {
		return parentMenuId;
	}
	public void setParentMenuId(String parentMenuId) {
		this.parentMenuId = parentMenuId;
	}
	public String getContentName() {
		return contentName;
	}
	public void setContentName(String contentName) {
		this.contentName = contentName;
	}
	public String getLinkMenuItemName() {
		return linkMenuItemName;
	}
	public void setLinkMenuItemName(String linkMenuItemName) {
		this.linkMenuItemName = linkMenuItemName;
	}
	
	
	

}
