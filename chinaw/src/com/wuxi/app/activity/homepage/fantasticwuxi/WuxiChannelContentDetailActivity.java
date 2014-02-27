package com.wuxi.app.activity.homepage.fantasticwuxi;

import com.wuxi.app.activity.commactivity.ContentDetailActivity;

/**
 * 
 * @author wanglu 泰得利通
 * 魅力锡城内容详细页
 *
 */
public class WuxiChannelContentDetailActivity extends ContentDetailActivity {

	@Override
	protected String getContentTitleText() {
		
		if(channel!=null){
			return channel.getChannelName();
		}else if(menuItem!=null){
			return menuItem.getName();
		}else{
			return "魅力锡城";
		}
		
	}

	
	
	
}
