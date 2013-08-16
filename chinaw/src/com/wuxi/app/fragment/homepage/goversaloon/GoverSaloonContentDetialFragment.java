package com.wuxi.app.fragment.homepage.goversaloon;

import com.wuxi.app.fragment.commonfragment.ContentDetailFragment;

/**
 * 
 * @author wanglu 泰得利通
 * 政务大厅内容页
 *
 */
public class GoverSaloonContentDetialFragment extends ContentDetailFragment {

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
