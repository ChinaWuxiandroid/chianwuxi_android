package com.wuxi.app.fragment.homepage.mygoverinteractpeople;

import com.wuxi.app.fragment.commonfragment.ContentDetailWebFragment;

public class GIPContentDetailWebFragment extends ContentDetailWebFragment{

	@Override
	protected String getUrl() {
		// TODO Auto-generated method stub
		return (String) this.getArguments().get("url");
	}

	@Override
	protected String getContentTitleText() {
		// TODO Auto-generated method stub
		return (String) this.getArguments().get("fragmentTitle");
	}

}
