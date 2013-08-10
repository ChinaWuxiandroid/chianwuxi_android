package com.wuxi.app.fragment.homepage.goverpublicmsg;

import android.annotation.SuppressLint;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.wuxi.app.R;
import com.wuxi.app.fragment.BaseItemContentFragment;
import com.wuxi.domain.Content;

/**
 * 政府信息公开   信息公开指南 信息公开制度内容页
 * */
public class GoverMsgOpenInfoDetailFragment extends BaseItemContentFragment{

	private ProgressBar pb_content_wb;// webView加载进度条
	private Content content;
	private String fragmentTitle;
	private WebView decontent_wb;// 加载数据的webView

	private TextView title_txtview;// 标题
	private TextView publicTime_txtview;// 发布时间
	private TextView updateTime_txtview;// 更新时间
	private TextView browseTime_txtview;// 浏览次数
	private TextView buildTime_txtview;// 生产时间
	private TextView openTime_txtview;//公开时间
	private TextView organization_tvtime;// 发布机构
	private TextView contentSource_tvtime;// 来源

	@Override
	public void initBtn() {

		super.initBtn();
		super.download_btn.setVisibility(ImageView.INVISIBLE);// 隐藏下载图标
		pb_content_wb = (ProgressBar) view.findViewById(R.id.govermsg_infoopen_pb);
		decontent_wb = (WebView) view.findViewById(R.id.govermsg_infoopen_webview);// 加载数据的webView

		title_txtview = (TextView) view.findViewById(R.id.govermsg_infoopen_title);
		publicTime_txtview = (TextView) view.findViewById(R.id.govermsg_infoopen_publishTime);
		updateTime_txtview = (TextView) view.findViewById(R.id.govermsg_infoopen_updateTime);
		browseTime_txtview = (TextView) view.findViewById(R.id.govermsg_infoopen_browseCount);
		buildTime_txtview = (TextView) view.findViewById(R.id.govermsg_infoopen_buildTime);
		openTime_txtview = (TextView) view.findViewById(R.id.govermsg_infoopen_openTime);
		organization_tvtime = (TextView) view.findViewById(R.id.govermsg_infoopen_organization);
		contentSource_tvtime = (TextView) view.findViewById(R.id.govermsg_infoopen_contentSource);

		content = (Content) getArguments().get("content");
		fragmentTitle = (String) getArguments().get("fragmentTitle");

		if (content != null) {
			showContentData();
		}

	}

	@SuppressLint("SetJavaScriptEnabled")
	private void showContentData() {

		title_txtview.setText(content.getTitle());
		publicTime_txtview.setText(content.getPublishTime());
		updateTime_txtview.setText(content.getUpdateTime());
		browseTime_txtview.setText(String.valueOf(content.getBrowseCount()));
		buildTime_txtview.setText(content.getBuildTime());
		openTime_txtview.setText(content.getOpenTime());
		organization_tvtime.setText(content.getOrganization());
		contentSource_tvtime.setText(content.getContentSource());

		decontent_wb.getSettings().setJavaScriptEnabled(true);

		decontent_wb.setWebChromeClient(new WebChromeClient() {
			public void onProgressChanged(WebView view, int progress) {
				if (progress == 100) {
					pb_content_wb.setVisibility(ProgressBar.GONE);// 移除进度条
				}
			}
		});

		decontent_wb.getSettings().setUseWideViewPort(true);
		decontent_wb.getSettings().setBuiltInZoomControls(true);
		decontent_wb.getSettings().setLoadWithOverviewMode(true);

		decontent_wb.loadUrl(content.getWapUrl());
	}

	@Override
	protected int getContentLayoutId() {
		// TODO Auto-generated method stub
		return R.layout.govermsg_openinfo_detail_layout;
	}

	@Override
	protected String getContentTitleText() {
		// TODO Auto-generated method stub
		return fragmentTitle;
	}


}
