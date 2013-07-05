package com.wuxi.app.fragment.index.type.infocenter;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.ProgressBar;

import com.wuxi.app.BaseFragment;
import com.wuxi.app.R;
import com.wuxi.domain.MenuItem;

/**
 * 
 * @author wanglu 泰得利通 领导之窗fragment
 * 
 */
public class LeaderWindowFragment extends BaseFragment {

	private View view;
	private MenuItem parentItem;
	

	private WebView mWb_leader;
	private ProgressBar wb_pb;

	public void setParentItem(MenuItem parentItem) {
		this.parentItem = parentItem;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		view = inflater.inflate(R.layout.leader_window_layout, null);
		initUI();
		return view;
	}

	@SuppressLint("SetJavaScriptEnabled")
	private void initUI() {

		mWb_leader = (WebView) view.findViewById(R.id.leader_wv_main);
		wb_pb = (ProgressBar) view.findViewById(R.id.wb_progress);
		mWb_leader.getSettings().setJavaScriptEnabled(true);

		mWb_leader.setWebChromeClient(new WebChromeClient() {
			public void onProgressChanged(WebView view, int progress) {

				if (progress == 100) {
					wb_pb.setVisibility(ProgressBar.GONE);//移除进度条

				}

			}
		});

		mWb_leader.loadUrl(parentItem.getWapURI());

	}

}
