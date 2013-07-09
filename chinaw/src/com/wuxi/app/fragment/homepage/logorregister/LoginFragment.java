package com.wuxi.app.fragment.homepage.logorregister;

import android.view.View;
import android.widget.Button;

import com.wuxi.app.R;
import com.wuxi.app.fragment.commonfragment.HomeBaseSlideLevelFragment;

public class LoginFragment extends HomeBaseSlideLevelFragment {

	private Button login_btn_login;
	private Button login_btn_regist;

	@Override
	protected void initUI() {
		super.initUI();
		login_btn_login = (Button) view.findViewById(R.id.login_btn_login);
		login_btn_regist = (Button) view.findViewById(R.id.login_btn_regist);

		login_btn_login.setOnClickListener(this);
		login_btn_regist.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		super.onClick(v);

		switch (v.getId()) {
		case R.id.login_btn_login:
			break;
		case R.id.login_btn_regist:
			HomeBaseSlideLevelFragment registFragment = new RegisterFragment();
			managers.IntentFragment(registFragment);
			break;
		}
	}

	@Override
	protected int getLayoutId() {
		return R.layout.login_slide_layout;
	}

	@Override
	protected void onBack() {

	}

	@Override
	protected String getTtitle() {
		return "登录注册";
	}

}