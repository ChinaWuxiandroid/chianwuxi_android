package com.wuxi.app.activity.homepage.goverpublicmsg;

import com.wuxi.app.activity.commactivity.ContentDetailWebActivity;

public class GoverMsgContentDetailWebActivity extends ContentDetailWebActivity {

	@Override
	protected String getUrl() {

		return (String) this.getIntent().getExtras().get("url");
	}

	@Override
	protected String getContentTitleText() {
		return (String) this.getIntent().getExtras().get("fragmentTitle");
	}

}
