package com.wuxi.app.fragment.commonfragment;

import android.annotation.SuppressLint;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.wuxi.app.R;
import com.wuxi.app.fragment.BaseItemContentFragment;

public abstract class ContentDetailWebFragment extends BaseItemContentFragment {
	private ProgressBar searchDetail_pb;// webView加载进度条

	private String url;

	private WebView searchDetail_wb;// 加载数据的webView

	private RelativeLayout rl_down;

	@Override
	public void initBtn() {

		super.initBtn();

		rl_down = (RelativeLayout) view.findViewById(R.id.rl_down);
		rl_down.setVisibility(RelativeLayout.GONE);//隐藏下载
		searchDetail_pb = (ProgressBar) view.findViewById(R.id.content_detailwb_pb);

		searchDetail_wb = (WebView) view.findViewById(R.id.content_detailwb_wb);// 加载数据的webView

		url = getUrl();

		if (url != null) {
			searchDetail_pb.setVisibility(ProgressBar.VISIBLE);
			showResultData();
		}

	}

	@SuppressLint("SetJavaScriptEnabled")
	private void showResultData() {
		String wapUrl = url;
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

		return R.layout.content_detailweb_layout;
	}

	protected abstract String getUrl();
}
