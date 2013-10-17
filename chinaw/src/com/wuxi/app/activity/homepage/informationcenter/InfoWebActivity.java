package com.wuxi.app.activity.homepage.informationcenter;

import android.content.Intent;

import com.wuxi.app.activity.commactivity.ContentDetailWebActivity;

public class InfoWebActivity extends ContentDetailWebActivity {

	@Override
	protected String getUrl() {
		Intent intent = getIntent();
		String url = intent.getExtras().getString("url");

		return url;
	}

	@Override
	protected String getContentTitleText() {

		return "热点专题";
	}

}
