package com.wuxi.app.fragment.homepage.fantasticwuxi;

import com.wuxi.app.fragment.commonfragment.ContentDetailFragment;

/**
 * 
 * @author wanglu 泰得利通
 * 魅力锡城内容详细页
 *
 */
public class WuxiChannelContentDetailFragment extends ContentDetailFragment {

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
