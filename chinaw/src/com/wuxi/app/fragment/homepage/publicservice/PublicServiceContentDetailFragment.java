package com.wuxi.app.fragment.homepage.publicservice;

import android.annotation.SuppressLint;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.wuxi.app.R;
import com.wuxi.app.fragment.BaseItemContentFragment;
import com.wuxi.app.util.TimeFormateUtil;
import com.wuxi.domain.Content;

/**
 * 
 * @author wanglu 泰得利通
 * 公共服务详细页
 *
 */
public class PublicServiceContentDetailFragment extends BaseItemContentFragment {

	
	
	
	private ProgressBar pb_content_wb;// webView加载进度条
	private Content content;
	private WebView wuxicity_decontent_wb;// 加载数据的webView
	//private TextView wuxicity_decontent_title;// 标题
	private TextView wuxi_decontent_tvtime;// 时间
	private TextView wuxi_decontent_tvbrowcount;// 浏览次数
	private TextView wuxi_content_author;//作者
	
	@Override
	public void initBtn() {
		
		super.initBtn();
		super.download_btn.setVisibility(ImageView.INVISIBLE);//隐藏下载图标
		
		pb_content_wb = (ProgressBar) view.findViewById(R.id.pb_content_wb);

		wuxicity_decontent_wb = (WebView) view
				.findViewById(R.id.wuxicity_decontent_wb);// 加载数据的webView
		/*wuxicity_decontent_title = (TextView) view
				.findViewById(R.id.wuxicity_decontent_title);// 标题
*/		wuxi_decontent_tvtime = (TextView) view
				.findViewById(R.id.wuxi_decontent_tvtime);// 时间
		wuxi_decontent_tvbrowcount = (TextView) view
				.findViewById(R.id.wuxi_decontent_tvbrowcount);// 浏览次数
		wuxi_content_author=(TextView) view.findViewById(R.id.wuxi_content_author);
		
		
		
		content=(Content) getArguments().get("content");
		
		if(content!=null){
			showContentData();
		}
		

	}

	
	
	@SuppressLint("SetJavaScriptEnabled")
	private void showContentData() {
		String wapUrl = content.getWapUrl();
		String title = content.getTitle();
		String time = content.getPublishTime();
		int browCount = content.getBrowseCount();

	/*	wuxicity_decontent_title.setText(title);*/
		wuxi_decontent_tvtime.setText("时间:"
				+ TimeFormateUtil.formateTime(time,
						TimeFormateUtil.DATE_PATTERN));// 时间
		wuxi_content_author.setText("作者:"+content.getOrganization());//发布机构
		wuxi_decontent_tvbrowcount.setText("浏览次数:".toString() + browCount);// 浏览次数

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

		wuxicity_decontent_wb.loadUrl(wapUrl);

	}
	
	@Override
	protected int getContentLayoutId() {
		
		return R.layout.wuxicity_content_detial_layout;
	}

	@Override
	protected String getContentTitleText() {
		
		return "公共服务";
	}

}
