package com.wuxi.app.activity.homepage;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.wuxi.app.MainTabActivity;
import com.wuxi.app.R;
import com.wuxi.app.activity.BaseItemContentActivity;

/**
 * 
 * @author wanglu 泰得利通 领导活动集锦
 * 
 */
public class LearActivity extends BaseItemContentActivity {

	private ProgressBar pb_content_wb;// webView加载进度条

	private WebView wuxicity_decontent_wb;// 加载数据的webView
	private RelativeLayout rl_down;
	
	
	@Override
	protected void findMainContentViews(View view) {
		
		super.findMainContentViews(view);
		rl_down = (RelativeLayout) view.findViewById(R.id.rl_down);
		rl_down.setVisibility(RelativeLayout.GONE);
		pb_content_wb = (ProgressBar) view.findViewById(R.id.pb_content_wb);

		wuxicity_decontent_wb = (WebView) view
				.findViewById(R.id.wuxicity_decontent_wb);// 加载数据的webView
		showContentData();
	}

	

	@SuppressLint("SetJavaScriptEnabled")
	private void showContentData() {

		wuxicity_decontent_wb.getSettings().setJavaScriptEnabled(true);

		wuxicity_decontent_wb.setWebChromeClient(new WebChromeClient() {
			public void onProgressChanged(WebView view, int progress) {

				if (progress == 100) {
					pb_content_wb.setVisibility(ProgressBar.GONE);// 移除进度条

				}

			}
		});

		wuxicity_decontent_wb.getSettings().setUseWideViewPort(true);
		wuxicity_decontent_wb.getSettings().setBuiltInZoomControls(true);
		wuxicity_decontent_wb.getSettings().setLoadWithOverviewMode(true);

		wuxicity_decontent_wb
				.loadUrl("http://www.wuxi.gov.cn/wap/zxzx/hdjj/index.shtml?showtoolbar=false");

		wuxicity_decontent_wb.setWebViewClient(new WebViewClient(){

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
				Intent intent=new Intent(LearActivity.this,HomeWebActivity.class);
				intent.putExtra("url", url);
				intent.putExtra("title", "领导活动集锦");
				MainTabActivity.instance.addView(intent);
				return true;
			}
			
			
			
		});

	}

	@Override
	protected int getContentLayoutId() {

		return R.layout.web_detial_layout;
	}

	@Override
	protected String getContentTitleText() {

		return "领导活动集锦";
	}

}
