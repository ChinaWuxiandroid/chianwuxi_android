package com.wuxi.app.fragment.homepage.goverpublicmsg;

import com.wuxi.app.fragment.commonfragment.ContentDetailWebFragment;

public class GoverMsgContentDetailWebFragment extends ContentDetailWebFragment{
	
	
	@Override
	protected String getUrl() {
		
		return (String) this.getArguments().get("url");
	}

	@Override
	protected String getContentTitleText() {
		// TODO Auto-generated method stub
		return (String) this.getArguments().get("fragmentTitle");
	}

}
