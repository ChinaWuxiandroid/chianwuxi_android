package com.wuxi.app.activity.homepage.publicservice;

import com.wuxi.app.activity.commactivity.ContentDetailActivity;

/**
 * 
 * @author wanglu 泰得利通
 * 公共服务详细页
 *
 */
public class PublicServiceContentDetailActivity extends ContentDetailActivity {

	@Override
	protected String getContentTitleText() {
		
		if(channel!=null){
			return channel.getChannelName();
		}else if(menuItem!=null){
			return menuItem.getName();
		}else{
			return "公共服务";
		}
		
	}

	
	
	
	
}
