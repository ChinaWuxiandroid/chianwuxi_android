package com.wuxi.app.fragment.homepage.more;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;

import com.wuxi.app.R;
import com.wuxi.app.fragment.BaseSlideFragment;

/**
 * 
 * @author wanglu 泰得利通 关于我们
 * 
 */
public class AboutUsFragment extends BaseSlideFragment {

	
	@Override
	public void initUI() {
		super.initUI();

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
