package com.wuxi.app.fragment.homepage.goverpublicmsg;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings.TextSize;
import android.webkit.WebView;
import android.widget.ProgressBar;

import com.wuxi.app.BaseFragment;
import com.wuxi.app.R;
import com.wuxi.domain.MenuItem;

/**
 * 显示碎片菜单类型的Fragment
 * */
public class GoverMsgFragmentWebFragment extends BaseFragment{
	private View view;
	private MenuItem parentItem;
	private Context context;

	private WebView wbView;
	private ProgressBar wb_pb;
	private String urlHeader="http://www.wuxi.gov.cn";

	public void setParentMenuItem(MenuItem parentItem) {
		this.parentItem = parentItem;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		view = inflater.inflate(R.layout.leader_window_layout, null);
		context = getActivity();
		initUI();
		return view;
	}

	@SuppressLint("SetJavaScriptEnabled")
	private void initUI() {

		wbView = (WebView) view.findViewById(R.id.leader_wv_main);
		
		wb_pb = (ProgressBar) view.findViewById(R.id.wb_progress);
		wbView.getSettings().setJavaScriptEnabled(true);
		wbView.getSettings().setTextSize(TextSize.LARGER);
		wbView.setWebChromeClient(new WebChromeClient() {
			public void onProgressChanged(WebView view, int progress) {

				if (progress == 100) {
					wb_pb.setVisibility(ProgressBar.GONE);//移除进度条
				}
			}
		});

		wbView.getSettings().setUseWideViewPort(true); 
		wbView.getSettings().setLoadWithOverviewMode(true);
		wbView.getSettings().setDefaultTextEncodingName("GB2312");
		wbView.loadUrl(urlHeader+parentItem.getPfBuildPath());
	}

}
