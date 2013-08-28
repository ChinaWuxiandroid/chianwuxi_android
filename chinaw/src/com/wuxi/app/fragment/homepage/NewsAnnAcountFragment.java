package com.wuxi.app.fragment.homepage;

import android.annotation.SuppressLint;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.wuxi.app.R;
import com.wuxi.app.fragment.BaseItemContentFragment;
import com.wuxi.domain.Content;

/**
 * 
 * @author wanglu 泰得利通 首页推荐新闻和公告内容页
 * 
 */
public class NewsAnnAcountFragment extends BaseItemContentFragment {

	private ProgressBar pb_content_wb;// webView加载进度条

	private WebView wuxicity_decontent_wb;// 加载数据的webView

	private Content content;

	public final static int NEWS = 0;

	public final static int ANNACOUNT = 1;

	private int showType = NEWS;

	public final static String SHOWTYPE_KEY = "showType";

	private RelativeLayout rl_down;

	@Override
	public void initBtn() {

		super.initBtn();

		rl_down = (RelativeLayout) view.findViewById(R.id.rl_down);
		rl_down.setVisibility(RelativeLayout.GONE);
		pb_content_wb = (ProgressBar) view.findViewById(R.id.pb_content_wb);

		wuxicity_decontent_wb = (WebView) view.findViewById(R.id.wuxicity_decontent_wb);// 加载数据的webView
		content = (Content) getArguments().get("content");

		if (content != null) {
			showContentData();
		}

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

		wuxicity_decontent_wb.loadUrl(content.getWapUrl());

	}

	@Override
	protected int getContentLayoutId() {

		return R.layout.web_detial_layout;
	}

	@Override
	protected String getContentTitleText() {
		showType = getArguments().getInt(SHOWTYPE_KEY);
		String name = "";
		switch (showType) {
		case NEWS:
			name = "无锡要闻";
			break;
		case ANNACOUNT:
			name = "公告公示";
			break;
		}
		return name;
	}

}
