package com.wuxi.app.fragment.homepage.logorregister;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wuxi.app.R;
import com.wuxi.app.fragment.BaseSlideFragment;
import com.wuxi.app.fragment.commonfragment.HomeBaseSlideLevelFragment;

public class RegisterFragment extends HomeBaseSlideLevelFragment{

	@Override
	protected int getLayoutId() {
		return R.layout.regist_slide_layout;
	}

	@Override
	protected void onBack() {
		
	}

	@Override
	protected String getTtitle() {
		
		return "登录注册";
	}
	
}
