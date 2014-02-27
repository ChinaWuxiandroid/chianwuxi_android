package com.wuxi.app.activity.homepage.more;

import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.webkit.WebView;
import android.widget.TextView;

import com.wuxi.app.R;
import com.wuxi.app.activity.BaseSlideActivity;

/**
 * 
 * @author wanglu 泰得利通 关于我们
 * 
 */
public class AboutUsActivity extends BaseSlideActivity {

	
	
	private WebView wb_about_us;
	
	@Override
	protected void findMainContentViews(View view) {
		wb_about_us=(WebView) view.findViewById(R.id.wb_about_us);
		wb_about_us.loadUrl("file:///android_asset/soft_about.html");//加载关于我们html文件
		
	}

	

	@Override
	protected int getLayoutId() {

		return R.layout.index_about_us_layout;
	}

	@Override
	protected String getTitleText() {

		return "关于我们";
	}
	
	
	

}
