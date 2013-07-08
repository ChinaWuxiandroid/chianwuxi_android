package com.wuxi.app.fragment.homepage.logorregister;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wuxi.app.R;
import com.wuxi.app.fragment.BaseSlideFragment;

public class RegisterFragment extends BaseSlideFragment{
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.index_login_regist_layout, null);
		InitBtn();
		this.setFragmentTitle("登录/注册");// 设置头部名称

		return view;
	}
}
