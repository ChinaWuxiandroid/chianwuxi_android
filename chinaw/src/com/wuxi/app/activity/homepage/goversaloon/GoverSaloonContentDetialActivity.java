package com.wuxi.app.activity.homepage.goversaloon;

import com.wuxi.app.activity.commactivity.ContentDetailActivity;

/**
 * 
 * @author wanglu 泰得利通
 * 政务大厅内容页
 *
 */
public class GoverSaloonContentDetialActivity extends ContentDetailActivity {

	@Override
	protected String getContentTitleText() {
		
		if(channel!=null){
			return channel.getChannelName();
		}else if(menuItem!=null){
			return menuItem.getName();
		}
		return "政务大厅";
	}

}
