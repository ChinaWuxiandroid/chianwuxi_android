package com.wuxi.app.activity.homepage.informationcenter;

import android.annotation.SuppressLint;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.wuxi.app.R;
import com.wuxi.app.activity.BaseItemContentActivity;
import com.wuxi.domain.Content;

/**
 * 
 * @author wanglu 泰得利通 热点专题内容页
 * 
 */

public class HotTopicContentActivity extends BaseItemContentActivity {

	private WebView hottopic_wv;
	private ProgressBar pb_hottopic;
	private Content content;

	
	
	@Override
	protected void findMainContentViews(View view) {
		
		super.findMainContentViews(view);
		
		setting_btn.setVisibility(ImageView.INVISIBLE);
		share_btn.setVisibility(ImageView.INVISIBLE);
		download_btn.setVisibility(ImageView.INVISIBLE);
		opearn_btn.setVisibility(ImageView.INVISIBLE);
		member_btnm.setVisibility(ImageView.INVISIBLE);
		hottopic_wv = (WebView) view.findViewById(R.id.hottopic_wv);
		pb_hottopic = (ProgressBar) view.findViewById(R.id.pb_hottopic);

		content = (Content) getIntent().getExtras().get("content");
		showContent();
	}
	
	@SuppressLint("SetJavaScriptEnabled")
	private void showContent() {
		hottopic_wv.getSettings().setJavaScriptEnabled(true);

		hottopic_wv.setWebChromeClient(new WebChromeClient() {
			public void onProgressChanged(WebView view, int progress) {

				if (progress == 100) {
					pb_hottopic.setVisibility(ProgressBar.GONE);// 移除进度条

				}

			}
		});

		hottopic_wv.getSettings().setUseWideViewPort(true);
		hottopic_wv.getSettings().setLoadWithOverviewMode(true);
		hottopic_wv.loadUrl(content.getWapUrl());

	}

	@Override
	protected int getContentLayoutId() {
		return R.layout.infor_hottopic_detial_layout;
	}

	@Override
	protected String getContentTitleText() {
		return "热点专题";
	}

}
