package com.wuxi.app.fragment.homepage.logorregister;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

import com.wuxi.app.FragmentManagers;
import com.wuxi.app.R;
import com.wuxi.app.fragment.BaseSlideFragment;
import com.wuxi.app.fragment.homepage.SlideLevelFragment;
import com.wuxi.app.util.Constants;

/**
 * 
 * @author wanglu 泰得利通 登录fragment
 */
public class LoginFragment extends BaseSlideFragment implements OnClickListener {

	private Button login_btn_login;
	private Button login_btn_regist;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.index_login_layout, null);
		InitBtn();
		this.setFragmentTitle("登录/注册");// 设置头部名称

		initUI();
		return view;
	}

	/**
	 * 
	 * wanglu 泰得利通 初始化界面
	 */
	private void initUI() {
		login_btn_login = (Button) view.findViewById(R.id.login_btn_login);
		login_btn_regist = (Button) view.findViewById(R.id.login_btn_regist);

		login_btn_login.setOnClickListener(this);
		login_btn_regist.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.login_btn_login:
			break;
		case R.id.login_btn_regist:// 注册

			SlideLevelFragment slideLevelFragment = new SlideLevelFragment();
			slideLevelFragment
					.setFragmentName(Constants.FragmentName.REGIST_FRAGMENT);
			managers.IntentFragment(slideLevelFragment);
			
			
			break;

		}
	}

}
