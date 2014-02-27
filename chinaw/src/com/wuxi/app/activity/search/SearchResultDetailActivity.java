package com.wuxi.app.activity.search;

import android.annotation.SuppressLint;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.wuxi.app.R;
import com.wuxi.app.activity.BaseItemContentActivity;
import com.wuxi.domain.SearchResultWrapper.SearchResult;

/**
 * 搜索内容 fragment
 * 
 * @author 杨宸 智佳
 * */

public class SearchResultDetailActivity extends BaseItemContentActivity {

	private ProgressBar searchDetail_pb;// webView加载进度条
	private SearchResult resultContent;
	private WebView searchDetail_wb;// 加载数据的webView

	@SuppressLint("SetJavaScriptEnabled")
	private void showResultData() {
		String wapUrl = resultContent.getLink()+"?backurl=false";
		searchDetail_wb.getSettings().setJavaScriptEnabled(true);
		searchDetail_wb.setWebChromeClient(new WebChromeClient() {
			public void onProgressChanged(WebView view, int progress) {

				if (progress == 100) {
					searchDetail_pb.setVisibility(ProgressBar.GONE);// 移除进度条

				}

			}
		});
		searchDetail_wb.getSettings().setUseWideViewPort(true);
		searchDetail_wb.getSettings().setBuiltInZoomControls(true);
		searchDetail_wb.getSettings().setLoadWithOverviewMode(true);
		searchDetail_wb.loadUrl(wapUrl);
	}

	@Override
	protected int getContentLayoutId() {
		return R.layout.search_detail_layout;
	}

	@Override
	protected String getContentTitleText() {
		return "全站搜索";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @方法： findMainContentViews
	 * 
	 * @描述：
	 * 
	 * @param view
	 * 
	 * @see
	 * com.wuxi.app.activity.BaseItemContentActivity#findMainContentViews(android
	 * .view.View)
	 */
	@Override
	protected void findMainContentViews(View view) {
		super.findMainContentViews(view);
		rl_down.setVisibility(RelativeLayout.GONE);

		searchDetail_pb = (ProgressBar) view
				.findViewById(R.id.search_detail_pb);

		searchDetail_wb = (WebView) view.findViewById(R.id.search_detail_wb);// 加载数据的webView

		resultContent = (SearchResult) getIntent().getExtras().get("result");

		if (resultContent != null) {
			showResultData();
		}
	}

}
