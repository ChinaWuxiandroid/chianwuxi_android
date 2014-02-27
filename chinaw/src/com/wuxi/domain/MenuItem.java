package com.wuxi.domain;

import java.io.Serializable;

import android.support.v4.app.Fragment;

/**
 * 菜单
 * 
 * @author wanglu
 * 
 */
public class MenuItem implements Comparable<MenuItem>, Serializable {

	

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -963854395463548938L;

	/**
	 * 普通菜单 一般它的下级也可以认为是menuitem
	 */
	public static final int CUSTOM_MENU = 0;

	/**
	 * 频道菜单 一般它需要绑定到一个channel,它的子内容由绑定的channel的子channel确定
	 */
	public static final int CHANNEL_MENU = 1;

	/**
	 * 内容菜单 一般它需要绑定到一个channel上，它的子内容由绑定的channel的子content确定
	 */
	public static final int CONTENT_MENU = 2;

	/**
	 * APP菜单 一般会指向一个APP的UI
	 */
	public static final int APP_MENU = 3;

	/**
	 * WAP PAGE菜单 一般会指向一个wab url
	 */
	public static final int WAP_MENU = 4;

	/**
	 * link菜单 会指向另外一个muenitem
	 */
	public static final int LINK_MENU = 5;
	/**
	 * 碎片菜单 
	 */
	public static final int FRAGMENT_MENU = 6;

	
	public static final int LEVEL_ONE=1;//一级菜单即顶级菜单
	public static final int LEVEL_TWO=2;//二级菜单
	public static final int LEVEL_THREE=3;//三级菜单
	
	private String name;// 菜单名称
	private String id;// ID 唯一标识
	private int type;// 菜单类型
	private boolean disabled;// 是否被禁用
	private String des;
	private int sort;// 排序
	private MenuItem childrens[];// 子菜单
	private String createDate;// 创建日期
	private String channelId;// 绑定频道Id
	private String channelName;// 绑定频道名称
	private boolean favorites;// 是否为收藏
	private String contentId;
	private boolean deleted;// 是否已经删除
	private String appUI;// 绑定的APP UI名称
	private String wapURI;// 绑定的wap url
	private String parentMenuId;// 父菜单id
	private String contentName;
	private String linkMenuItemName;
	private String linkMenuItemId;// 连接菜单Id
	private boolean isHasChildern;// 是否子菜单
	private String icon;// 图标
	private String pfId;  
	private String pfBuildPath; 
	private boolean isLocalFavorites=false;//是不是本地收藏菜单
	
	private int level=LEVEL_ONE;//菜单的层次
	private int level_two_p=0;//二级菜单的位置
	private int level_three_p=0;//三级菜单的位置
	
	public int getLevel_three_p() {
		return level_three_p;
	}

	public void setLevel_three_p(int level_three_p) {
		this.level_three_p = level_three_p;
	}

	public int getLevel_two_p() {
		return level_two_p;
	}

	public void setLevel_two_p(int level_two_p) {
		this.level_two_p = level_two_p;
	}

	

	public boolean isLocalFavorites() {
		return isLocalFavorites;
	}

	public void setLocalFavorites(boolean isLocalFavorites) {
		this.isLocalFavorites = isLocalFavorites;
	}

	
	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public static final String MENUITEM_KEY="menuitem_key";

	private Class<? extends Fragment> contentFragment;

	public Class<? extends Fragment> getContentFragment() {
		return contentFragment;
	}

	public void setContentFragment(Class<? extends Fragment> contentFragment) {
		this.contentFragment = contentFragment;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public boolean isHasChildern() {
		return isHasChildern;
	}

	public void setHasChildern(boolean isHasChildern) {
		this.isHasChildern = isHasChildern;
	}

	public String getLinkMenuItemId() {
		return linkMenuItemId;
	}

	public void setLinkMenuItemId(String linkMenuItemId) {
		this.linkMenuItemId = linkMenuItemId;
	}

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

	public MenuItem[] getChildrens() {
		return childrens;
	}

	public void setChildrens(MenuItem[] childrens) {
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

	@Override
	public int compareTo(MenuItem another) {

		if (this.sort > another.getSort()) {
			return 1;
		} else if (this.sort < another.getSort()) {
			return -1;
		} else {
			return 0;
		}
	}

	public String getPfId() {
		return pfId;
	}

	public void setPfId(String pfId) {
		this.pfId = pfId;
	}

	public String getPfBuildPath() {
		return pfBuildPath;
	}

	public void setPfBuildPath(String pfBuildPath) {
		this.pfBuildPath = pfBuildPath;
	}
	
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MenuItem other = (MenuItem) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
