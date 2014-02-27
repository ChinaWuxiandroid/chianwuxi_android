package com.wuxi.app.activity.homepage;

import android.content.Intent;

import com.wuxi.app.activity.commactivity.ContentDetailWebActivity;

public class HomeWebActivity extends ContentDetailWebActivity {

	@Override
	protected String getUrl() {
		Intent intent = getIntent();
		String url = intent.getExtras().getString("url");

		return url;
	}

	@Override
	protected String getContentTitleText() {

		String title=getIntent().getExtras().getString("title");
		if(title!=null){
			return title;
		}else{
			return "";
		}
		
	}

}
