package com.wuxi.app.activity.homepage.mygoverinteractpeople;

import com.wuxi.app.activity.commactivity.ContentDetailWebActivity;

public class GIPContentDetailWebActivity extends ContentDetailWebActivity{

	@Override
	protected String getUrl() {
		return (String) this.getIntent().getExtras().get("url");
	}

	@Override
	protected String getContentTitleText() {
		return (String) this.getIntent().getExtras().get("fragmentTitle");
	}

}
