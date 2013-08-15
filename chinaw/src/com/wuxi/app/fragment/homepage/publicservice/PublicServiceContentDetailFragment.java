package com.wuxi.app.fragment.homepage.publicservice;

import com.wuxi.app.fragment.commonfragment.ContentDetailFragment;

/**
 * 
 * @author wanglu 泰得利通
 * 公共服务详细页
 *
 */
public class PublicServiceContentDetailFragment extends ContentDetailFragment {

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
