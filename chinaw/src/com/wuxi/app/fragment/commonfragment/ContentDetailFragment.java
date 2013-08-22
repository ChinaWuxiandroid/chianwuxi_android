package com.wuxi.app.fragment.commonfragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.wuxi.app.R;
import com.wuxi.app.fragment.BaseItemContentFragment;
import com.wuxi.app.util.TimeFormateUtil;
import com.wuxi.domain.Channel;
import com.wuxi.domain.Content;
import com.wuxi.domain.MenuItem;

/**
 * 
 * @author wanglu 泰得利通 内容详细界面
 * 
 */
public abstract class ContentDetailFragment extends BaseItemContentFragment {

	private ProgressBar pb_content_wb;// webView加载进度条
	private Content content;
	private WebView decontent_wb;// 加载数据的webView
	
	private TextView decontent_tvtime;// 时间
	private TextView tvbrowcount;// 浏览次数
	private TextView content_author;// 作者
	protected Channel channel;
	protected MenuItem menuItem;
	public static final String CHANNEL_KEY="channel_key";
	public static final String MENUITEM_KEY="menuitem_key";
	public static final String CONTENT_KEY="content";
	protected Bundle bunel;

	@Override
	public void initBtn() {

		super.initBtn();
		super.download_btn.setVisibility(ImageView.INVISIBLE);// 隐藏下载图标

		pb_content_wb = (ProgressBar) view.findViewById(R.id.pb_content_wb);

		decontent_wb = (WebView) view.findViewById(R.id.decontent_wb);// 加载数据的webView
		decontent_tvtime = (TextView) view
				.findViewById(R.id.decontent_tvtime);// 时间
		tvbrowcount = (TextView) view.findViewById(R.id.tvbrowcount);// 浏览次数
		content_author = (TextView) view.findViewById(R.id.content_author);

		
		content = (Content) bunel.get(CONTENT_KEY);
	
		if (content != null) {
			showContentData();
		}

	}

	@SuppressLint("SetJavaScriptEnabled")
	private void showContentData() {
		String wapUrl = content.getWapUrl();
		
		String time = content.getPublishTime();
		int browCount = content.getBrowseCount();

		/* wuxicity_decontent_title.setText(title); */
		decontent_tvtime.setText("时间:"
				+ TimeFormateUtil.formateTime(time,
						TimeFormateUtil.DATE_PATTERN));// 时间
		content_author.setText("作者:" + content.getOrganization());// 发布机构
		tvbrowcount.setText("浏览次数:".toString() + browCount);// 浏览次数

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

		decontent_wb.loadUrl(wapUrl);

	}

	@Override
	protected int getContentLayoutId() {
		bunel=getArguments();
		channel=(Channel) bunel.get(CHANNEL_KEY);
		menuItem=(MenuItem) bunel.get(MENUITEM_KEY);
		return R.layout.content_detial_layout;
	}

	
	
	
	

}
