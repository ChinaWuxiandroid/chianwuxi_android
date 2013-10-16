package com.wuxi.app.fragment.homepage.goverpublicmsg;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebSettings.TextSize;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.wuxi.app.BaseFragment;
import com.wuxi.app.MainTabActivity;
import com.wuxi.app.R;
import com.wuxi.app.activity.homepage.goverpublicmsg.GoverMsgContentDetailWebActivity;
import com.wuxi.domain.MenuItem;

/**
 * web网页 界面
 * 
 * @author 杨宸 智佳
 * */
public class GoverMsgWebFragment extends BaseFragment implements
		KeyEvent.Callback {

	private View view;
	private MenuItem parentItem;
	private Context context;

	private WebView wbView;
	private ProgressBar wb_pb;

	public void setParentItem(MenuItem parentItem) {
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

	/**
	 * @方法： initUI
	 * @描述： 初始化视图
	 */
	@SuppressLint("SetJavaScriptEnabled")
	private void initUI() {

		wbView = (WebView) view.findViewById(R.id.leader_wv_main);
		wb_pb = (ProgressBar) view.findViewById(R.id.wb_progress);
		wbView.getSettings().setJavaScriptEnabled(true);

		wbView.setWebChromeClient(new WebChromeClient() {
			public void onProgressChanged(WebView view, int progress) {

				if (progress == 100) {
					wb_pb.setVisibility(ProgressBar.GONE);// 移除进度条
				}
			}
		});

		// 设置WebView中链接的点击处理问题
		wbView.setWebViewClient(new WebViewClient() {

			/*
			 * (non-Javadoc)
			 * 
			 * @方法： shouldOverrideUrlLoading
			 * 
			 * @描述： WebView中存在链接的，希望在该WebView中处理，不用打开系统的浏览器
			 * 
			 * @param view
			 * 
			 * @param url
			 * 
			 * @return
			 * 
			 * @see
			 * android.webkit.WebViewClient#shouldOverrideUrlLoading(android
			 * .webkit.WebView, java.lang.String)
			 */
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {

				Intent intent = new Intent(context,
						GoverMsgContentDetailWebActivity.class);
				intent.putExtra("url", url);
				intent.putExtra("fragmentTitle", parentItem.getName());
				MainTabActivity.instance.addView(intent);
				return true;
			}

			@Override
			public void onFormResubmission(WebView view, Message dontResend,
					Message resend) {
				resend.sendToTarget();
			}

		});

		wbView.getSettings().setUseWideViewPort(true);
		wbView.getSettings().setBuiltInZoomControls(true);
		wbView.getSettings().setLoadWithOverviewMode(true);

		wbView.getSettings().setUseWideViewPort(true);
		wbView.getSettings().setLoadWithOverviewMode(true);

		if (parentItem.getName().equals("最新信息公开")) {
			wbView.getSettings().setDefaultTextEncodingName("gb2312");
			wbView.getSettings().setTextSize(TextSize.NORMAL);
		}

		wbView.loadUrl(parentItem.getWapURI());
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if ((keyCode == KeyEvent.KEYCODE_BACK) && wbView.canGoBack()) {

			wbView.goBack();

			return true;
		}
		return false;
	}

	@Override
	public boolean onKeyLongPress(int keyCode, KeyEvent event) {
		return false;
	}

	@Override
	public boolean onKeyMultiple(int keyCode, int count, KeyEvent event) {
		return false;
	}

	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event) {
		return false;
	}

	// @Override
	// public boolean onKey(View v, int keyCode, KeyEvent event) {
	// if ((keyCode == KeyEvent.KEYCODE_BACK)&&wbView.canGoBack()) {
	//
	// System.out.println("测试WebView返回功能");
	//
	// wbView.goBack();
	// return true;
	// }
	// return false;
	// }

}
