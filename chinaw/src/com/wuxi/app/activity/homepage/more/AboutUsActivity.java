package com.wuxi.app.activity.homepage.more;

import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.TextView;

import com.wuxi.app.R;
import com.wuxi.app.activity.BaseSlideActivity;

/**
 * 
 * @author wanglu 泰得利通 关于我们
 * 
 */
public class AboutUsActivity extends BaseSlideActivity {

	
	private TextView tv_about;
	
	
	@Override
	protected void findMainContentViews(View view) {
		tv_about=(TextView) view.findViewById(R.id.tv_about);
		tv_about.setMovementMethod(ScrollingMovementMethod.getInstance());
		
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
