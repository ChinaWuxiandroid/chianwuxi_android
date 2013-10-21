package com.wuxi.app.activity.homepage.informationcenter;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.wuxi.app.MainTabActivity;
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
		rl_down.setVisibility(RelativeLayout.GONE);
		rl_setting.setVisibility(RelativeLayout.GONE);
		rl_search_share.setVisibility(RelativeLayout.GONE);

		opearn_btn.setVisibility(ImageView.GONE);
		member_btnm.setVisibility(ImageView.GONE);
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
		
		
		
		hottopic_wv.setWebViewClient(new WebViewClient(){

			/* (non-Javadoc)
			 * @方法： shouldOverrideUrlLoading
			 * @描述： WebView中存在链接的，希望在该WebView中处理，不用打开系统的浏览器
			 * @param view
			 * @param url
			 * @return 
			 * @see android.webkit.WebViewClient#shouldOverrideUrlLoading(android.webkit.WebView, java.lang.String)
			 */
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				Intent intent=new Intent(HotTopicContentActivity.this,InfoWebActivity.class);
				intent.putExtra("url", url);
				intent.putExtra("title", "热点专题");
				MainTabActivity.instance.addView(intent);
				return true;
			}
			
			
			
		});


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
