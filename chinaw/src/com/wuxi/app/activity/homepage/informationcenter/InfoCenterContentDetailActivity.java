package com.wuxi.app.activity.homepage.informationcenter;

import com.wuxi.app.activity.commactivity.ContentDetailActivity;

/**
 * 
 * @author wanglu 泰得利通
 * 魅力锡城内容详细页
 *
 */
public class InfoCenterContentDetailActivity extends ContentDetailActivity {

	@Override
	protected String getContentTitleText() {
		
		if(channel!=null){
			return channel.getChannelName();
		}else if(menuItem!=null){
			return menuItem.getName();
		}else{
			return "资讯中心";
		}
		
	}

	
	

}
