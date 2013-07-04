package com.wuxi.app.fragment.index;


import com.wuxi.app.fragment.CityMapFragment;
import com.wuxi.app.fragment.NavigatorWithContentFragment;
import com.wuxi.app.fragment.SimpleListViewFragment;
import com.wuxi.app.fragment.WorkSuggestionBoxFragment;
import com.wuxi.domain.Channel;
import com.wuxi.domain.MenuItem;

/**
 * 根据MenuItem、Channel等消息类型的name(之后为APPUI)来set Fragment
 * @author 杨宸 智佳
 * */
public class InitializContentLayout {
	
	
	static final String[] navigatorContentLayoutTypeNames={"信息公开工作年度报告",
		"依申请展开","新闻发布会"};
	static final String[] simpleListViewLayoutTypeNames={"最新信息公开","信息公开指南","信息公开制度"};
	
	
//	static Map<String,String[]> UIInfoContainer=new HashMap<String,String[]>();
//			
//	
//	public static void init(){
//		UIInfoContainer.put("NavigatorWithContentFragment", navigatorContentLayoutTypeNames);
//		UIInfoContainer.put("SimpleListViewFragment", simpleListViewLayoutTypeNames);
//	}
	
	
	public static void initMenuItemContentLayout(MenuItem menuItem){
		if(menuItem.getName().equals("工作意见箱"))
			menuItem.setContentFragment(WorkSuggestionBoxFragment.class);
		else if(judgeBelongNavigatorContentLayout(menuItem.getName(),navigatorContentLayoutTypeNames))
		  	menuItem.setContentFragment(NavigatorWithContentFragment.class);
		else if(judgeBelongSimpleListViewLayout(menuItem.getName(),simpleListViewLayoutTypeNames))
			menuItem.setContentFragment(SimpleListViewFragment.class);
		else
			menuItem.setContentFragment(NavigatorWithContentFragment.class);
	}
	
	public static void initChannelContentLayout(Channel channel){
		if (channel.getChannelName().equals("城市地图")) {//处理城市题图显示的视图
			channel.setContentFragment(CityMapFragment.class);
		} else {
			channel.setContentFragment(NavigatorWithContentFragment.class);
		}
	}
	
	/*
	 * 判断是否属于NavigatorWithContentFragment
	 * */
	private static boolean judgeBelongNavigatorContentLayout(String menuItemName,String[] names){
		for(int i=0;i<names.length;i++){
			if(menuItemName.equals(names[i]))
				return true;
		}
		return false;
	}
	
	private static boolean judgeBelongSimpleListViewLayout(String menuItemName,String[] names){
		for(int i=0;i<names.length;i++){
			if(menuItemName.equals(names[i]))
				return true;
		}
		return false;
	}
	
}
